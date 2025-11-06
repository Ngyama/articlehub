<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

// Article category data
const categorys = ref([])

// Selected category ID for search
const categoryId = ref('')

// Selected publish status for search
const state = ref('')

// Article list data
const articles = ref([])

// Pagination data
const pageNum = ref(1) // Current page
const total = ref(20) // Total count
const pageSize = ref(3) // Page size

// Handle page size change
const onSizeChange = (size) => {
    pageSize.value = size
    articleList()
}
// Handle current page change
const onCurrentChange = (num) => {
    pageNum.value = num
    articleList()
}


// Load article categories
import { articleCategoryListService, articleListService, articleAddService, articleUpdateService, articleDeleteService } from '@/api/article.js'
const articleCategoryList = async () => {
    let result = await articleCategoryListService();

    categorys.value = result.data;
}

// Get article list data
const articleList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value ? categoryId.value : null,
        state: state.value ? state.value : null
    }
    let result = await articleListService(params);

    // Update view
    total.value = result.data.total;
    articles.value = result.data.items;

    // Add category name to articles
    for (let i = 0; i < articles.value.length; i++) {
        let article = articles.value[i];
        for (let j = 0; j < categorys.value.length; j++) {
            if (article.categoryId == categorys.value[j].id) {
                article.categoryName = categorys.value[j].categoryName;
            }
        }
    }
}


articleCategoryList()
articleList();

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'
// Control drawer visibility
const visibleDrawer = ref(false)
// Control edit mode
const isEdit = ref(false)
// Drawer title
const drawerTitle = ref('Add Article')
// Add form data model
const articleModel = ref({
    id: null,
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
})


// Import token store
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

// Upload success callback
const uploadSuccess = (result)=>{
    articleModel.value.coverImg = result.data;
    console.log(result.data);
}

// Clear form data
const clearArticleModel = () => {
    articleModel.value = {
        id: null,
        title: '',
        categoryId: '',
        coverImg: '',
        content: '',
        state: ''
    }
}

// Show edit drawer
const showEditDrawer = (row) => {
    isEdit.value = true
    drawerTitle.value = 'Edit Article'
    // Copy article data to form
    articleModel.value.id = row.id
    articleModel.value.title = row.title
    articleModel.value.categoryId = row.categoryId
    articleModel.value.coverImg = row.coverImg
    articleModel.value.content = row.content
    articleModel.value.state = row.state
    // Open drawer
    visibleDrawer.value = true
}

// Add or update article
import {ElMessage} from 'element-plus'
const addArticle = async (clickState)=>{
    // Set publish status
    articleModel.value.state = clickState;

    let result
    if (isEdit.value) {
        // Update article
        result = await articleUpdateService(articleModel.value);
        ElMessage.success(result.msg? result.msg:'Updated successfully');
    } else {
        // Add article
        result = await articleAddService(articleModel.value);
        ElMessage.success(result.msg? result.msg:'Added successfully');
    }

    // Close drawer
    visibleDrawer.value = false;
    // Reset edit mode
    isEdit.value = false
    drawerTitle.value = 'Add Article'
    // Clear form
    clearArticleModel()

    // Refresh list
    articleList()
}

// Delete article
import {ElMessageBox} from 'element-plus'
const deleteArticle = (row) => {
    ElMessageBox.confirm(
        'Are you sure you want to delete this article?',
        'Warning',
        {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning',
        }
    )
        .then(async () => {
            // Call API
            let result = await articleDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: 'Deleted successfully',
            })
            // Refresh list
            articleList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'User cancelled deletion',
            })
        })
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>Article Management</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true; isEdit = false; drawerTitle = 'Add Article'; clearArticleModel()">Add Article</el-button>
                </div>
            </div>
        </template>
        <!-- Search form -->
        <el-form inline>
            <el-form-item label="Article Category:">
                <el-select placeholder="Please select" v-model="categoryId">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="Publish Status:">
                <el-select placeholder="Please select" v-model="state">
                    <el-option label="Published" value="published"></el-option>
                    <el-option label="Draft" value="draft"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="articleList">Search</el-button>
                <el-button @click="categoryId = ''; state = ''">Reset</el-button>
            </el-form-item>
        </el-form>
        <!-- Article list -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="Article Title" width="400" prop="title"></el-table-column>
            <el-table-column label="Category" prop="categoryName"></el-table-column>
            <el-table-column label="Publish Time" prop="createTime"> </el-table-column>
            <el-table-column label="Status" prop="state"></el-table-column>
            <el-table-column label="Actions" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showEditDrawer(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="No data" />
            </template>
        </el-table>
        <!-- Pagination -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- Drawer -->
        <el-drawer v-model="visibleDrawer" :title="drawerTitle" direction="rtl" size="50%">
            <!-- Add article form -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="Article Title">
                    <el-input v-model="articleModel.title" placeholder="Please enter title"></el-input>
                </el-form-item>
                <el-form-item label="Article Category">
                    <el-select placeholder="Please select" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Article Cover">

                    <!-- 
                        auto-upload: Set auto upload
                        action: Set server API path
                        name: Set upload file field name
                        headers: Set upload request headers
                        on-success: Set upload success callback
                     -->
                   
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/upload"
                    name="file"
                    :headers="{'Authorization':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="Article Content">
                    <div class="editor">
                        <quill-editor theme="snow" v-model:content="articleModel.content" contentType="html">
                        </quill-editor>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addArticle('published')">Publish</el-button>
                    <el-button type="info" @click="addArticle('draft')">Draft</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* Drawer styles */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>
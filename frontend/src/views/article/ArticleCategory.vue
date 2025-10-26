<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([])
// Declare async function
import { articleCategoryListService, articleCategoryAddService, articleCategoryUpdateService,articleCategoryDeleteService } from '@/api/article.js'
const articleCategoryList = async () => {
    let result = await articleCategoryListService();
    categorys.value = result.data;

}
articleCategoryList();
// Control add category dialog
const dialogVisible = ref(false)

// Add category data model
const categoryModel = ref({
    categoryName: '',
    categoryAlias: ''
})
// Add category form validation
const rules = {
    categoryName: [
        { required: true, message: 'Please enter category name', trigger: 'blur' },
    ],
    categoryAlias: [
        { required: true, message: 'Please enter category alias', trigger: 'blur' },
    ]
}


// Call API to add category
import { ElMessage } from 'element-plus'
const addCategory = async () => {
    // Call API
    let result = await articleCategoryAddService(categoryModel.value);
    ElMessage.success(result.msg ? result.msg : 'Added successfully')

    // Call function to get all article categories
    articleCategoryList();
    dialogVisible.value = false;
}

// Define variable to control title display
const title = ref('')

// Show edit dialog
const showDialog = (row) => {
    dialogVisible.value = true; title.value = 'Edit Category'
    // Data copy
    categoryModel.value.categoryName = row.categoryName;
    categoryModel.value.categoryAlias = row.categoryAlias;
    // Extend id property for backend update
    categoryModel.value.id = row.id
}

// Edit category
const updateCategory = async () => {
    // Call API
    let result = await articleCategoryUpdateService(categoryModel.value);

    ElMessage.success(result.msg ? result.msg : 'Updated successfully')

    // Call function to get all categories
    articleCategoryList();

    // Hide dialog
    dialogVisible.value = false;
}

// Clear model data
const clearData = () => {
    categoryModel.value.categoryName = '';
    categoryModel.value.categoryAlias = '';
}

// Delete category
import {ElMessageBox} from 'element-plus'
const deleteCategory = (row) => {
    // User confirmation dialog

    ElMessageBox.confirm(
        'Are you sure you want to delete this category?',
        'Warning',
        {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning',
        }
    )
        .then(async () => {
            // Call API
            let result = await articleCategoryDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: 'Deleted successfully',
            })
            // Refresh list
            articleCategoryList();
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
                <span>Article Categories</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = 'Add Category'; clearData()">Add Category</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="No." width="100" type="index"> </el-table-column>
            <el-table-column label="Category Name" prop="categoryName"></el-table-column>
            <el-table-column label="Category Alias" prop="categoryAlias"></el-table-column>
            <el-table-column label="Actions" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="No data" />
            </template>
        </el-table>

        <!-- Add category dialog -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="Category Name" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="Category Alias" prop="categoryAlias">
                    <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="title == 'Add Category' ? addCategory() : updateCategory()"> Confirm </el-button>
                </span>
            </template>
        </el-dialog>
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
</style>
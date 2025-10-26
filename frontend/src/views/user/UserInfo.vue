<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();

const userInfo = ref({...userInfoStore.info})
const rules = {
    nickname: [
        { required: true, message: 'Please enter nickname', trigger: 'blur' },
        {
            pattern: /^\S{2,10}$/,
            message: 'Nickname must be 2-10 non-empty characters',
            trigger: 'blur'
        }
    ],
    email: [
        { required: true, message: 'Please enter email', trigger: 'blur' },
        { type: 'email', message: 'Invalid email format', trigger: 'blur' }
    ]
}

// Update user info
import {userInfoUpdateService} from '@/api/user.js'
import {ElMessage} from 'element-plus'
const updateUserInfo = async ()=>{
    // Call API
    let result = await userInfoUpdateService(userInfo.value);
    ElMessage.success(result.msg? result.msg : 'Updated successfully');
    
    // Update user info in Pinia
    userInfoStore.setInfo(userInfo.value)
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>Basic Info</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="Username">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Nickname" prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="Email" prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">Submit Changes</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>
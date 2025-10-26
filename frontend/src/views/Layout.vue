<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import {userInfoService} from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
// Get user details
const getUserInfo = async()=>{
    // Call API
    let result = await userInfoService();
    // Store data in Pinia
    userInfoStore.setInfo(result.data);
}

getUserInfo();
// Handle dropdown menu click
import {useRouter} from 'vue-router'
const router = useRouter();
import {ElMessage,ElMessageBox} from 'element-plus'
const handleCommand = (command)=>{
    // Check command
    if(command === 'logout'){
        // Logout
        ElMessageBox.confirm(
        'Are you sure you want to logout?',
        'Warning',
        {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning',
        }
    )
        .then(async () => {
            // Clear token and user info
            tokenStore.removeToken()
            userInfoStore.removeInfo()

            // Navigate to login page
            router.push('/login')
            ElMessage({
                type: 'success',
                message: 'Logout successful',
            })
            
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'User cancelled logout',
            })
        })
    }else{
        // Navigate to user page
        router.push('/user/'+command)
    }
}
</script>

<template>
    <!-- Element Plus container -->
    <el-container class="layout-container">
        <!-- Left menu -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <!-- Element Plus menu -->
            <el-menu active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
                router>
                <el-menu-item index="/article/category">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>Article Categories</span>
                </el-menu-item>
                <el-menu-item index="/article/manage">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>Article Management</span>
                </el-menu-item>
                <el-sub-menu >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>Personal Center</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>Basic Info</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>Change Avatar</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>Reset Password</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- Right main area -->
        <el-container>
            <!-- Header area -->
            <el-header>
                <div>ArticleHub：<strong>{{ userInfoStore.info.nickname }}</strong></div>
                <!-- Dropdown menu -->
                <!-- command: triggered when item is clicked, can declare parameter in event function to receive command -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic? userInfoStore.info.userPic:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">Basic Info</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">Change Avatar</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">Reset Password</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">Logout</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- Main area -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    Content display area
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- Footer area -->
            <el-footer>ArticleHub ©2023 Created by ArticleHub Team</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>
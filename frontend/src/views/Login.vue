<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
// Control register/login form display
const isRegister = ref(false)
// Data model
const registerData = ref({
    username: '',
    password: '',
    rePassword: ''
})

// Password validation function
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please confirm password again'))
    } else if (value !== registerData.value.password) {
        callback(new Error('Please ensure both passwords match'))
    } else {
        callback()
    }
}

// Form validation rules
const rules = {
    username: [
        { required: true, message: 'Please enter username', trigger: 'blur' },
        { min: 5, max: 16, message: 'Length should be 5-16 non-empty characters', trigger: 'blur' }
    ],
    password: [
        { required: true, message: 'Please enter password', trigger: 'blur' },
        { min: 5, max: 16, message: 'Length should be 5-16 non-empty characters', trigger: 'blur' }
    ],
    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ]
}

// Call backend API for registration
import { userRegisterService, userLoginService} from '@/api/user.js'
const register = async () => {
    // Get reactive object value
    let result = await userRegisterService(registerData.value);
    /* if (result.code === 0) {
        // Success
        alert(result.msg ? result.msg : 'Registration successful');
    }else{
        // Failed
        alert('Registration failed')
    } */
    //alert(result.msg ? result.msg : 'Registration successful');
    ElMessage.success(result.msg ? result.msg : 'Registration successful')
}

// Login function
import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();
const login =async ()=>{
    try {
        // Call API for login
        console.log('Login data:', registerData.value);
        let result = await userLoginService(registerData.value);
        console.log('Login result:', result);
        
        // Check if login was successful
        if(result.code === 0) {
            ElMessage.success(result.message || 'Login successful')
            // Store token in Pinia
            tokenStore.setToken(result.data)
            // Navigate to home page
            router.push('/')
        } else {
            ElMessage.error(result.message || 'Login failed')
        }
    } catch (error) {
        console.error('Login error:', error);
        ElMessage.error('Login failed: ' + (error.message || 'Unknown error'))
    }
}

// Clear form data
const clearRegisterData = ()=>{
    registerData.value={
        username:'',
        password:'',
        rePassword:''
    }
}
</script>

<template>
    <div class="login-page">
        <div class="login-container">
            <!-- Registration form -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>Register</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="Please enter username" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="Please enter password"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="Please enter password again"
                        v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- Register button -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        Register
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
                        ← Back
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- Login form -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>Login</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="Please enter username" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="Please enter password" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>Remember me</el-checkbox>
                        <el-link type="primary" :underline="false">Forgot password?</el-link>
                    </div>
                </el-form-item>
                <!-- Login button -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">Login</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
                        Register →
                    </el-link>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<style lang="scss" scoped>
/* Styles */
.login-page {
    height: 100vh;
    width: 100vw;
    position: relative;
    background: url('@/assets/login_bg.jpg') no-repeat center / cover;
    display: flex;
    align-items: center;
    justify-content: center;
    
    // Add overlay for better readability
    &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.3);
        z-index: 1;
    }

    .login-container {
        position: relative;
        z-index: 2;
        background: rgba(255, 255, 255, 0.95);
        padding: 40px;
        border-radius: 20px;
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        backdrop-filter: blur(10px);
        width: 400px;
        max-width: 90vw;
        user-select: none;

        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-size: 28px;
            font-weight: 600;
        }

        .button {
            width: 100%;
            height: 45px;
            font-size: 16px;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        // Form item spacing
        .el-form-item {
            margin-bottom: 20px;
        }

        // Input styling
        .el-input {
            height: 45px;
            
            :deep(.el-input__wrapper) {
                height: 45px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }
        }

        // Link styling
        .el-link {
            font-size: 14px;
        }
    }
}
</style>
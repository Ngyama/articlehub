// Custom request instance

// Import axios
import axios from 'axios';

import { ElMessage } from 'element-plus'
// Define base URL
//const baseURL = 'http://localhost:8080';
const baseURL = '/api';
const instance = axios.create({ baseURL })

import {useTokenStore} from '@/stores/token.js'
// Add request interceptor
instance.interceptors.request.use(
    (config)=>{
        // Before request
        // Add token
        const tokenStore = useTokenStore();
        // Check if token exists
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err)=>{
        // Request error
        Promise.reject(err)
    }
)

/* import {useRouter} from 'vue-router'
const router = useRouter(); */

import router from '@/router'
// Add response interceptor
instance.interceptors.response.use(
    result => {
        // Debug: Log the response
        console.log('Response data:', result.data);
        
        // Check business status code
        if(result.data.code===0){
            return result.data;
        }

        // Operation failed
        //alert(result.data.msg?result.data.msg:'Service error')
        ElMessage.error(result.data.message || result.data.msg || 'Service error')
        // Convert async operation to failed state
        return Promise.reject(result.data)
        
    },
    err => {
        // Check response status code, 401 means not logged in
        if(err.response.status===401){
            ElMessage.error('Please login first')
            router.push('/login')
        }else{
            ElMessage.error('Service error')
        }
       
        return Promise.reject(err); // Convert async state to failed
    }
)

export default instance;
// Define store
import {defineStore} from 'pinia'
import {ref} from 'vue'
/* 
    First parameter: name, unique
    Second parameter: function, defines all state content
    Return value: function
*/
export const useTokenStore = defineStore('token',()=>{
    // Define state content

    // 1. Reactive variable
    const token = ref('')

    // 2. Function to modify token value
    const setToken = (newToken)=>{
        token.value = newToken
    }

    // 3. Function to remove token value
    const removeToken = ()=>{
        token.value=''
    }

    return {
        token,setToken,removeToken
    }
},{
    persist:true // Persistent storage
});
// Import request utility
import request from '@/utils/request.js'

// User registration API
export const userRegisterService = (registerData)=>{
    // Use UrlSearchParams for data transfer
    const params = new URLSearchParams()
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/user/register',params);
}

// User login API
export const userLoginService = (loginData)=>{
    const params = new URLSearchParams();
    for(let key in loginData){
        params.append(key,loginData[key])
    }
    return request.post('/user/login',params)
}


// Get user details
export const userInfoService = ()=>{
    return request.get('/user/userInfo')
}

// Update user info
export const userInfoUpdateService = (userInfoData)=>{
   return request.put('/user/update',userInfoData)
}

// Update avatar
export const userAvatarUpdateService = (avatarUrl)=>{
    const params = new URLSearchParams();
    params.append('avatarUrl',avatarUrl)
    return request.patch('/user/updateAvatar',params)
}
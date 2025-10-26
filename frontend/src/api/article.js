import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
// Article category list query
export const articleCategoryListService = ()=>{
    //const tokenStore = useTokenStore();
    // Reactive data in Pinia doesn't need .value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/category')
}

// Add article category
export const articleCategoryAddService = (categoryData)=>{
    return request.post('/category',categoryData)
}

// Update article category
export const articleCategoryUpdateService = (categoryData)=>{
   return  request.put('/category',categoryData)
}

// Delete article category
export const articleCategoryDeleteService = (id)=>{
    return request.delete('/category?id='+id)
}

// Article list query
export const articleListService = (params)=>{
   return  request.get('/article',{params:params})
}

// Add article
export const articleAddService = (articleData)=>{
    return request.post('/article',articleData);

}
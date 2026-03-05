import axios from 'axios' 
import { ElMessage } from 'element-plus';
import router from '../router'


const request = axios.create({
    baseURL:"/api", //在每次请求url最前面自动加上 
    timeout: 5000
})

// request 拦截器
// 请求发送前对请求做处理
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';//设置请求头
    let loginUser = JSON.parse(localStorage.getItem("loginUser"));
    if(loginUser && loginUser.token){
        // 对请求参数统一加token
        config.headers.token = loginUser.token;
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => { // 成功回调
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => { // 失败回调
        console.log('err' + error)  
        if (error.response.status === 401) {
            ElMessage.error('登录信息已过期，请重新登录')
            router.push('/login')
        }else{
            ElMessage.error('接口访问异常')
        }
        return Promise.reject(error)
})

export default request
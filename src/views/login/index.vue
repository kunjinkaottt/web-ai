<template>
    <h1 style="text-align: center;">
        登录页
    </h1>

        <el-card style="max-width: 480px ; min-height:300px ;margin : 100px auto;display: flex;align-items: center;justify-content: center;">
        <el-form ref="ruleref" :rules="loginRules" :model="loginForm"  label-width="80px">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="loginForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="loginForm.password" type="password"></el-input>
            </el-form-item>
        </el-form>
        <el-row :gutter="20" style="margin-left: 40px;">
            <el-col :span="12">
                <el-button type="primary" @click="login">登录</el-button>
            </el-col>
            <el-col :span="12">
                <el-button @click="clear">重置</el-button>
            </el-col>
        </el-row>
    </el-card>

</template>

<script setup>
 import { ref } from 'vue'
 import { loginApi } from '@/api/login'
 import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router'
 
 const loginForm = ref({
    username: '',
    password: ''
})
const router = useRouter()

const loginRules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min:2, max: 10, message: '长度在 2 到 10个字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
    ]
}
const clear = () => {
    loginForm = {username: '',password: ''}
}
const ruleref = ref()
const login = async () => {
    if(!ruleref.value) return
    ruleref.value.validate(async valid=>{
        if(valid) {
            const result = await loginApi(loginForm.value)
            if(result.code) {
                ElMessage.success('登录成功')
                // 存储token
                localStorage.setItem('loginUser',JSON.stringify(result.data))
                router.push('/')
            }else{
                ElMessage.error(result.msg)
            }
        }else{
            ElMessage.error('请正确填写')
        }
    })
}
</script>

<style scoped>

</style>
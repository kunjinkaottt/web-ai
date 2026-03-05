<template>
  <div class="">
    <el-container>
      <el-header style="background-color: skyblue;display: flex;justify-content: space-between;align-items: center;" > 
        <span>  talis管理系统 </span>
        <span>  <span class="logout" @click="logout"> 退出登录 </span> 【 {{loginName}} 】  </span> 
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-scrollbar>
            <el-menu router="true" :default-openeds="['1']">
              <el-menu-item index="/home"><el-icon><message /></el-icon>首页</el-menu-item>
              <el-sub-menu  index="1">
                <template #title>
                  <el-icon><message /></el-icon>班级学员管理
                </template>
                <el-menu-item index="/clazz"><el-icon><HomeFilled /></el-icon> 班级管理</el-menu-item>
                <el-menu-item index="/stu"><el-icon><UserFilled /></el-icon> 学员管理</el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="2">
                <template #title>
                  <el-icon><message /></el-icon>系统信息管理
                </template>
                <el-menu-item index="/dept">
                    <el-icon><HomeFilled /></el-icon>部门管理
                </el-menu-item>
                <el-menu-item index="/emp">
                    <el-icon><UserFilled /></el-icon>员工管理
                </el-menu-item>
              </el-sub-menu>

              <el-sub-menu index="3">
                <template #title>
                  <el-icon><message /></el-icon>数据统计管理
                </template>
                <el-menu-item index="/empReport"
                  ><el-icon><HomeFilled /></el-icon>员工信息统计</el-menu-item
                >
                <el-menu-item index="/stuReport"
                  ><el-icon><UserFilled /></el-icon>学员信息统计</el-menu-item
                >
              </el-sub-menu>
            </el-menu>
          </el-scrollbar>
        </el-aside>

        <el-main>
            <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ElMessageBox,ElMessage } from "element-plus";
import { ref ,onMounted} from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
const loginName = ref("");

onMounted(() => {
  const loginUser  = JSON.parse(localStorage.getItem("loginUser"));
  if(loginUser && loginUser.name) {
    loginName.value = loginUser.name;
  }
});

const logout = () => {
  ElMessageBox.confirm("确定退出登录吗？", "提示", { 
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    ElMessage.success("退出成功~");
    localStorage.removeItem("loginUser");
    router.push("/login");
  }).catch(() => {
    ElMessage.info("取消退出");
  })
};

</script>


<style  scoped>

.logout{
  cursor: pointer;
}

</style>
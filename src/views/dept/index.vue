<template>
    <h1>
        部门管理
    </h1>
    <div class="content">
        <el-button type="primary" size="large" @click="add"> + 新增部门</el-button>
    </div>
    <div class="table">
        <el-table :data="res">
            <el-table-column type="index" label="序号" width="50"></el-table-column>
            <el-table-column prop="name" label="部门名称"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间"></el-table-column>
            <el-table-column  label="操作">
              <template #default="scope"><!-- scope.row 获取这行的数据  -->
                <el-button type="success" size="small" @click="edit(scope.row.id)" >编辑</el-button>
        <el-popconfirm
        class="box-item"
        title="是否确认删除"
        placement="bottom-end"
        icon="WarningFilled"
        icon-color="red"
        confirm-button-text="确认"
        cancel-button-text="再想想"
        @cancel="thinkthink"
        @confirm="del(scope.row.id)">
        <template #reference>
          <el-button type="danger" size="small" >删除</el-button>
        </template>
      </el-popconfirm>
              </template>
            </el-table-column>
        </el-table>
    </div>
    

    <el-dialog v-model="dialogFormVisible" :title="dialogTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="formLues">
      <el-form-item label="部门名称" :label-width="formLabelWidth"  prop="name">
        <el-input v-model="dept.name" autocomplete="off" clearable/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="confirm">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup>
import { ref, onMounted } from 'vue'

// import {getDeptList} from '@/api/dept';
// 导入api/dept中的所有方法
import * as deptApi from '@/api/dept';
import {ElMessage} from 'element-plus';

const res = ref([]) // 初始化为数组 而不是对象
const dialogFormVisible = ref(false);
const dialogTitle = ref('');
const dept = ref({name:""});
const rules = ref({
  name: [
    { required: true, message: '部门名称不能为空', trigger: 'blur' },
    { min: 2, message: '部门名称不能少于2个字符', trigger: 'blur' },
    { max: 10, message: '部门名称不能多于10个字符', trigger: 'blur' }
  ]
});
const formLues = ref();

const search = async () =>{
   const result = await deptApi.getDeptList();
   if(result.code) res.value = result.data;
}

const add = () => {
  dialogTitle.value = '新增部门';
  dept.value = {name:''};
  if(formLues.value)  formLues.value.resetFields(); // 清除上一次非法输入的校验结果
  dialogFormVisible.value = true;
}

const edit = async (id) => {
  const result = await deptApi.getDeptById(id)
  dialogTitle.value = '编辑部门';
  if(formLues.value)  formLues.value.resetFields(); // 清除上一次非法输入的校验结果
  if(result.code) {
    dept.value = result.data;
    dialogFormVisible.value = true;
  }else{
      ElMessage.error(result.msg);
  }
}

const confirm = async () => {
    // 表单校验 
    if(!formLues.value) return; // 表单不存在 返回
    formLues.value.validate(async valid=>{
      let result = {};
      if(valid) { // 表单校验通过
        dept.value.id ? result = await deptApi.editDept(dept.value) : result = await deptApi.addDept(dept.value)
        if(result.code) {
            ElMessage.success(dialogTitle.value+'成功');
            dialogFormVisible.value = false;
            search();
        }else{
            ElMessage.error(result.msg);
        }
      }else{  // 表单校验不通过
          ElMessage.error('请正确填写');
      }
    })
}

const del = async (id) => {
  const result = await deptApi.delDept(id)
  if(result.code) {
    ElMessage.success('删除成功');
    search();
  }else{
    ElMessage.error(result.msg);
  }
}
const thinkthink = () => {
  ElMessage.info('已取消')
}

onMounted(() => {
  search();
})

</script>

<style scoped>

</style>
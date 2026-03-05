<template>
    <h1 style="margin-bottom: 10px;"> 员工管理 </h1>
    <!-- 条件查询 -->
    <el-form :inline="true" :model="empsSearch" class="demo-form-inline" >
    <el-form-item label="姓名">
      <el-input v-model="empsSearch.name" placeholder="请填写姓名" clearable />
    </el-form-item>
    <el-form-item label="性别">
      <el-select
        v-model="empsSearch.gender"
        placeholder="请选择性别"
        clearable
      >
        <el-option label="男" value="1" />
        <el-option label="女" value="2" />
      </el-select>
    </el-form-item>
    <el-form-item label="入职时间">
        <el-date-picker
        v-model="empsSearch.date"
        type="daterange"
        value-format="YYYY-MM-DD"
        range-separator="到"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="query">查询</el-button>
      <el-button type="primary" @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

  <div style="margin: 0  0 20px;">
    <el-button type="success"  @click="add" >新增员工</el-button>
    <el-button type="danger" @click="delBatch"  >批量删除</el-button>
  </div>
  <!-- 表格 -->
  <el-table :data="emps" style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"  align="ceter"/>
    <el-table-column property="name" label="姓名" width="120"  align="ceter"/>
    <el-table-column  label="性别" width="120"  align="ceter">
        <template #default="scope">
          {{ scope.row.gender == 1? "男" :"女"  }}
        </template>
    </el-table-column>
    <el-table-column label="头像" width="120" align="ceter">
        <template #default="scope">
            <img :src="scope.row.image" height="40px" >
        </template>
    </el-table-column>
    <el-table-column prop="deptName" label="所属部门" width="120" align="ceter" />
    <el-table-column  label="职位" width="120" align="ceter">
        <template #default="scope">
            <span v-if="scope.row.job==1">班主任</span>
            <span v-else-if="scope.row.job==2">讲师</span>
            <span v-else-if="scope.row.job==3">学工主管</span>
            <span v-else-if="scope.row.job==4">教研主管</span>
            <span v-else-if="scope.row.job==5">咨询师</span>
            <span v-else>其他</span>
        </template>
    </el-table-column>
    <el-table-column prop="entryDate" label="入职日期" width="120" align="ceter"/>
    <el-table-column prop="updateTime"  label="最后操作时间" width="120" align="ceter"/>
    <el-table-column label="操作" align="ceter">
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
                    <el-button type="danger" size="small"  >删除</el-button>
                </template>
            </el-popconfirm>
        </template>
    </el-table-column>
  </el-table>
  <!-- 分页 -->
  <el-pagination
  style="margin: 10px 0;"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[2,5,10, 20,30,50,100]"
      background="true"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    
<el-dialog v-model="dialogFormVisible" :title="dialogTitle" width="700">
    <el-form :model="employee" :rules="rules" ref="formLues">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="employee.username" placeholder="请输入员工用户名"  ></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="employee.name" placeholder="请输入员工姓名,2-10字符"  ></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="性别" prop="gender">
                    <el-select v-model="employee.gender">
                        <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12"> 
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="employee.phone" placeholder="请输入员工手机号"  ></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="职位" >
                    <el-select v-model="employee.job" placeholder="请选择职位" >
                        <el-option  v-for="j in jobs " :key="j.value"  :label="j.name" :value="j.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12"> 
                <el-form-item label="薪资" >
                    <el-input v-model="employee.salary" placeholder="请输入员工薪资"  ></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="所属部门" >
                    <el-select v-model="employee.deptId" placeholder="请选择部门" >
                        <el-option v-for="d in depts" :key="d.id" :label="d.name" :value="d.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12"> 
                <el-form-item label="入职日期" >
                    <el-date-picker value-format="YYYY-MM-DD"  v-model="employee.entryDate" type="date" placeholder="请选择入职日期"  ></el-date-picker>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="24">
                <el-form-item label="头像" >
                    <el-upload
                        class="avatar-uploader"
                        action="/api/emps/uploads"
                        :headers="{'token':token}"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                    >
                        <img v-if="employee.image" :src="employee.image" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                    </el-upload>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="24">
                <el-form-item label="工作经历"  >
                    <el-button type="success" size="small" @click="addExprItem">添加工作经历</el-button>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="3" v-for="(expr,index) in employee.exprList" :key="index">
            <el-col :span="10">
                <el-form-item label="时间" size="small" >
                    <el-date-picker v-model="expr.daterange" type="daterange" value-format="YYYY-MM-DD" range-separator="到" start-placeholder="开始日期" end-placeholder="结束日期" ></el-date-picker>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item size="small" label="公司" label-width="60px" >
                    <el-input v-model="expr.company" placeholder="请输入公司名称" ></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item size="small" label="职位" label-width="60px" >
                    <el-input v-model="expr.job" placeholder="请输入职位" ></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="2">
                <el-form-item size="small" label-width="0px">
                    <el-button type="danger" @click="delExprItem(index)">删除</el-button>
                </el-form-item>
            </el-col>
        </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>


<script setup>

    import  * as empsApi from "@/api/emp";
    import { ElMessage,ElMessageBox } from "element-plus";
    import { ref,watch,onMounted } from "vue";
    import {getDeptList} from "@/api/dept";

    const genders = ref([
        { name: "男", value: "1" },
        { name: "女", value: "0" },
    ]);
    const jobs = ref([
        { name: "班主任", value: "1" },
        { name: "讲师", value: "2" },
        { name: "学工主管", value: "3" },
        { name: "教研主管", value: "4" },
        { name: "咨询师", value: "5" },
    ]);
    const rules = ref({
        name: [
            { required: true, message: "请输入员工姓名", trigger: "blur" },
            { min: 2, max: 10, message: "长度在 2 到 10 个字符", trigger: "blur" },
        ],
        username: [
            { required: true, message: "请输入员工用户名", trigger: "blur" },
            { min: 2, max: 10, message: "长度在 2 到 10 个字符", trigger: "blur" },
        ],
        gender: [
            { required: true, message: "请选择性别", trigger: "change" },
        ],
        phone: [
            { required: true, message: "请输入手机号", trigger: "blur" },
            { pattern:/^1[3-9]\d{9}$/, message: "请输入有效的手机号", trigger: "blur" },
        ],
    })

    const empsSearch = ref({
        name: '',
        gender: '',
        date: [],
        begin:'',
        end:''
    })
    let total = ref(0)
    let currentPage = ref(1)
    let pageSize = ref(10)

    const emps = ref([])
    const formLues = ref()
    const query = () => {
        console.log(empsSearch.value);
        search()
    }

    const clear = () => {
        empsSearch.value = {name: '',
        gender: '',
        date: [],
        begin:'',
        end:'' }
        search()
    }

    const search = async () => {
        const result = await empsApi.getEmps(empsSearch.value.name,empsSearch.value.gender,empsSearch.value.begin,empsSearch.value.end,currentPage.value,pageSize.value)
        console.log(result);
        emps.value = result.data.rows
        total.value = result.data.total
    }

    watch(() => empsSearch.value.date, (newValue) => {
        if(newValue){
            empsSearch.value.begin = newValue[0]||'';
            empsSearch.value.end = newValue[1]||'';
        }else{
            empsSearch.value.begin=''
            empsSearch.value.end = ''
        }
    })

    const handleSizeChange = (val) => {
        console.log(` 每页 ${val} 条数据 `)
        search() 
    }
    const handleCurrentChange = (val) => {
        console.log(`当前页码: ${val}`)
        search()
    }

    let dialogTitle = ref('')
    let employee = ref({username:'',name:'',gender:'',phone:'',job:'',salary:'',deptId:'',entryDate:'',image:'',exprList:[]})
    let dialogFormVisible = ref(false)
    // 新增员工
    const add = () =>{
        employee.value = {username:'',name:'',gender:'',phone:'',job:'',salary:'',deptId:'',entryDate:'',image:'',exprList:[]}
        if(formLues.value)  formLues.value.resetFields(); // 清除上一次非法输入的校验结果
        dialogFormVisible.value = true;
        dialogTitle.value = '新增员工';
    }

    // 传图之前
    const beforeAvatarUpload = (rawFile) => { 
        if (rawFile.type!=='image/jpeg' && rawFile.type!=='image/png') {
            ElMessage.error('上传头像只能是图片格式的!');
            return false;
        }else if(rawFile.size>1024*1024*10){
            ElMessage.error('上传头像图片大小不能超过10M!')
            return false;
        }
        return true
    }
    // 传图成功
    const handleAvatarSuccess = (res, file)=>{
        console.log(res);
        employee.value.image = res.data
    }

    // 添加工作经历
    const addExprItem = ()=>{
        employee.value.exprList.push({company:'',job:'',begin:'',end:'',daterange :[]})
    }
    const delExprItem = (index)=>{
        employee.value.exprList.splice(index,1)
    }
    // 侦听工作经历数组
    watch(() => employee.value.exprList , (newVal) => { 
        if ( employee.value.exprList ) {
            employee.value.exprList.forEach(expr => { 
                expr.begin =expr.daterange? expr.daterange[0] : ""
                expr.end = expr.daterange? expr.daterange[1] : ""
            })
        }
    },{deep: true})
    // 保存（新增/编辑员工）
    const save = async () => { 
        if(!formLues.value) return
        formLues.value.validate( async valid=>{
            if(valid){
                let result;
                employee.value.id ? result = await empsApi.editEmp(employee.value) : result = await empsApi.addEmp(employee.value)
                if(result.code){
                    ElMessage.success('保存成功');
                    dialogFormVisible.value = false;
                    search();
                }else{
                    ElMessage.error(result.msg);
                }
            }else{
                ElMessage.error('请正确填写各项信息');
            }
        })
    }
    // 编辑员工
    const edit = async (id) => { 
        dialogTitle.value = '编辑员工';
        const result = await empsApi.getEmpById(id)
        if(formLues.value)  formLues.value.resetFields(); // 清除上一次非法输入的校验结果
        employee.value = result.data;
        if(employee.value.exprList && employee.value.exprList.length > 0){
            employee.value.exprList.forEach(expr => {
               expr.daterange = [expr.begin, expr.end]
            });
        }
        dialogFormVisible.value = true;
    }

    // 再想想
    const thinkthink = () => {
        ElMessage.info('已取消')
    }
    // 删除员工
    const del = async (id) => {
        empsApi.delEmp(id).then(result => {
            ElMessage.success('删除成功')
            search()
        }).catch(error => {
            ElMessage.error(result.msg)
        })
    }

    // 多选表格项
    const ids = ref([])
    const handleSelectionChange = (selection) =>{
        ids.value = selection.map(item => item.id)
        console.log(ids.value);
    }
    
    //批量删除员工
    const delBatch = async () => {
        ElMessageBox.confirm('确认批量删除选中的员工吗？','温馨提示',{
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning', // 图标类型
        }).then(() => {
            if (ids.value && ids.value.length > 0) {
                empsApi.delEmp(ids.value).then(result => {
                    if(result.code) { 
                        ElMessage.success('批量删除成功')
                        search()
                    }else{
                        ElMessage.error(result.msg)
                    }
                })
            }else{
                ElMessage.warning('请选择要删除的员工')
            }
        }).catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消批量删除',
            })
        })
    }

    onMounted(() => {
        search()
        getDepts()
        getToken()
    })
    const token = ref('')
    // 获取token
    const getToken = () => {
       const loginUser =  JSON.parse(localStorage.getItem('loginUser'))
       token.value = loginUser ? loginUser.token : ''
    }

    let depts = ref([])
    const getDepts = async () => {
        const result = await getDeptList()
        console.log(result.data);
        if(result.code) depts.value =  result.data;
    }
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 10px;
}
</style>
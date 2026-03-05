<template>
    <h1 style="margin-bottom: 10px;"> 班级管理 </h1>
    <!-- 条件查询 -->
    <el-form :inline="true" :model="clazzSearch" class="demo-form-inline" >
    <el-form-item label="班级名称">
      <el-input v-model="clazzSearch.name" placeholder="请填写班级名称" clearable />
    </el-form-item>
    <el-form-item label="结课时间">
        <el-date-picker
        v-model="clazzSearch.daterange"
        type="daterange"
        value-format="YYYY-MM-DD"
        range-separator="到"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button type="info" @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

  <div style="margin: 0  0 20px;">
    <el-button type="success"  @click="add" >新增班级</el-button>
  </div>
  
  <!-- 表格 -->
  <el-table :data="clazzList" style="width: 100%" >
    <!--@selection-change="handleSelectionChange" <el-table-column type="selection" width="55"  align="ceter"/> -->
    <el-table-column type="index" label="序号" width="50"  align="ceter"/>"
    <el-table-column property="name" label="班级名称" width="120"  align="ceter"/>
    <el-table-column property="room" label="班级教室" width="120"  align="ceter"/>
    <el-table-column property="masterName" label="班主任" width="120"  align="ceter"/>
    <el-table-column prop="beginDate" label="开课时间" width="120" align="ceter"/>
    <el-table-column prop="endDate" label="结课时间" width="120" align="ceter"/>
    <el-table-column prop="status" label="状态" width="120" align="ceter"/>
    <el-table-column prop="updateTime"  label="最后修改时间" width="120" align="ceter"/>
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

    <!-- 新增/编辑弹窗 -->
  <el-dialog v-model="dialogFormVisible" :title="dialogTitle" width="700">
    <el-form :model="clazz" :rules="rules" ref="formRef">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="班级名称" prop="name">
                    <el-input v-model="clazz.name" placeholder="请输入班级名称"  ></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="教室"  >
                    <el-input v-model="clazz.room" placeholder="请输入教室"  ></el-input>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12"> 
                <el-form-item label="开课日期" prop="beginDate">
                    <el-date-picker value-format="YYYY-MM-DD"  v-model="clazz.beginDate" type="date" placeholder="请选择开课日期"></el-date-picker>
                </el-form-item>
            </el-col>
            <el-col :span="12"> 
                <el-form-item label="结课日期" prop="endDate">
                    <el-date-picker value-format="YYYY-MM-DD"  v-model="clazz.endDate" type="date" placeholder="请选择结课日期"></el-date-picker>
                </el-form-item>
            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="班主任" >
                    <el-select v-model="clazz.masterId" placeholder="请选择班主任" >
                        <el-option  v-for="m in masterList " :key="m.id"  :label="m.name" :value="m.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="学科" prop="subject" >
                    <el-select v-model="clazz.subject" placeholder="请选择学科" >
                        <el-option  v-for="s in subject " :key="s.value"  :label="s.name" :value="s.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save"> 保存 </el-button>
      </div>
    </template>
  </el-dialog>
    
</template>

<script setup>
import { ref, reactive, toRaw, watch, onMounted } from "vue";
import { ElMessage,ElMessageBox } from "element-plus";
import * as clazzApi from "@/api/clazz"
import { getEmps } from "@/api/emp"

const clazzList = ref([]);
const total = ref(0);
let clazzSearch = ref({name:"",daterange:[],beginDate:"",endDate:""})
const dialogFormVisible = ref(false);
const dialogTitle = ref("");
const rules = ref({
     name: [{ required: true, message: "请输入班级名称", trigger: "blur" }] ,
     beginDate: [{ required: true, message: "请选择开课时间", trigger: "blur" }] ,
     endDate: [{ required: true, message: "请选择结课时间", trigger: "blur" }] ,
     subject: [{ required: true, message: "请选择学科", trigger: "blur" }] ,
})
const formRef = ref()

watch (()=>clazzSearch.value.daterange,(newVal)=>{ 
    console.log(newVal);
    if(newVal){
        clazzSearch.value.beginDate = clazzSearch.value.daterange[0]||''
        clazzSearch.value.endDate = clazzSearch.value.daterange[1]||''
    }else{
        clazzSearch.value.beginDate = ''
        clazzSearch.value.endDate = ''
    }
})
const currentPage = ref(1)
const pageSize = ref(5)
const clear = () => { 
    clazzSearch.value = {name:"",daterange:[],beginDate:"",endDate:""}
    search()
}

const search = async () => { 
    const result = await clazzApi.getClazzList(clazzSearch.value.name,clazzSearch.value.beginDate,clazzSearch.value.endDate,currentPage.value,pageSize.value)
    console.log(result);
    clazzList.value = result.data.rows
    total.value = result.data.total
    // 赋值status课程状态
    if(clazzList.value){
        clazzList.value.forEach(item => { 
            item.status = new Date(item.endDate) > new Date() ? '未结课' : '已结课'
        })
    }
}

let clazz = ref({name:'',room:'',beginDate:'',endDate:'',masterId:'',subject:''})
const subject = ref([{name:'java',value:1},{name:'前端',value:2},{name:'大数据',value:3},{name:'python',value:4},{name:'go',value:5},{name:'嵌入式',value:6}])
const masterList = ref([])
// 新增
const add = () => { 
    clazz.value = {name:'',room:'',beginDate:'',endDate:'',masterId:'',subject:''}
    if(formRef.value) formRef.value.resetFields()
    dialogTitle.value = '新增班级'
    dialogFormVisible.value = true
}
// 编辑
const edit = async (id) => { 
    const result = await clazzApi.getClazzById(id)
    clazz.value = result.data
    if(formRef.value) formRef.value.resetFields()
    dialogTitle.value = '编辑班级'
    dialogFormVisible.value = true
}
// 新增/编辑保存
const save = () => { 
    if(!formRef.value) return
    formRef.value.validate( async valid=>{ 
        if(valid) { 
            let result;
             clazz.value.id ? result = await clazzApi.editClazz(clazz.value) 
             : result = await clazzApi.addClazz(clazz.value)
            console.log(clazz.value);
            if(result.code){
                ElMessage.success('添加成功')
                dialogFormVisible.value = false
                search()
            }else{
                ElMessage.error(result.msg)
            }
        }else{
            ElMessage.error('请正确填写')
        }
    })
}
// 再想想
const thinkthink = () => {
    ElMessage.info('已取消')
}

// 删除
const del = async (id) => { 
    const result = await clazzApi.delClazz(id)
    if(result.code) {
        ElMessage.success('删除成功')
        search()
    }else{
        ElMessage.error(result.msg)
    }
}


const handleSizeChange = (val) => { 
    pageSize.value = val
    search()
}
const handleCurrentChange = (val) => { 
    currentPage.value = val
    search()
}
onMounted(() => { 
    search()
    getEmpList()
})
const getEmpList  = async ()=>{
    const result = await getEmps('','','','',1,1000);
    console.log('masterList:',result);
    if(result.code) masterList.value = result.data.rows
}


</script>

<style scoped>

</style>
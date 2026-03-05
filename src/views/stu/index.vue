<template>
    <h1 style="margin-bottom: 10px;"> 学员管理 </h1>
    <!-- 条件查询 -->
    <el-form :inline="true" :model="stuSearch" class="demo-form-inline" >
    <el-form-item label="姓名">
      <el-input v-model="stuSearch.name" placeholder="请输入学生姓名" clearable />
    </el-form-item>
    <el-form-item label="最高学历">
        <el-select v-model="stuSearch.degree" placeholder="请选择" clearable>
          <el-option v-for="d in degree" :key="d.value" :label="d.name" :value="d.value"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="所属班级">
        <el-select v-model="stuSearch.clazzId" placeholder="请选择" clearable>
          <el-option v-for="c in clazzList" :key="c.id" :label="c.name" :value="c.id"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button type="info" @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

  <div style="margin: 0  0 20px;">
    <el-button type="success"  @click="add" >新增学员</el-button>
  </div>
  
  <!-- 表格 -->
  <el-table :data="stuList" style="width: 100%"  @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"  align="ceter"/>
    <el-table-column property="name" label="姓名" width="120"  align="ceter"/>
    <el-table-column property="no" label="学号" width="120"  align="ceter"/>
    <el-table-column property="clazzName" label="班级" width="120"  align="ceter"/>
    <el-table-column prop="gender" label="性别" width="120" align="ceter"/>
    <el-table-column prop="phone" label="手机号" width="120" align="ceter"/>
    <el-table-column prop="degree" label="最高学历" width="120" align="ceter"/>
    <el-table-column prop="violationCount"  label="违纪次数" width="120" align="ceter"/>
    <el-table-column prop="violationScore"  label="违纪扣分" width="120" align="ceter"/>
    <el-table-column prop="updateTime"  label="最后修改时间" width="120" align="ceter"/>
    <el-table-column label="操作" align="ceter">
        <template #default="scope"><!-- scope.row 获取这行的数据  -->
            <el-button type="success" size="small" @click="edit(scope.row.id)" >编辑</el-button>
            <el-button type="warning" size="small" @click="violate(scope.row.id)" >违纪</el-button>
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
<!--   <el-dialog v-model="dialogFormVisible" :title="dialogTitle" width="700">
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
  </el-dialog> -->
    
</template>


<script setup>
import { ref, reactive, toRaw, watch,onMounted } from 'vue'
import * as stuApi from '@/api/stu'
import { ElMessage,ElMessageBox } from 'element-plus';
import {getClazzList} from '@/api/clazz'

const degree = ref([{name:'初中',value:1},{name:'高中',value:2},{name:'大专',value:3},
{name:'本科',value:4},{name:'硕士',value:5},{name:'博士',value:6}])
const currentPage = ref(1)
const pageSize = ref(5)
const total = ref(0)
const stuSearch = ref({name:'',degree:'',clazzId:''})
const stuList = ref([])
const search = async () => { 
    const result = await stuApi.getStuList(stuSearch.value.name,stuSearch.value.degree,stuSearch.value.clazzId,currentPage.value,pageSize.value)
    console.log(result);
    stuList.value = result.data.rows
    total.value = result.data.total
}
const clear = () => { 
    stuSearch.value = {name:'',degree:'',clazzId:''}
    search()
}
onMounted(() => {
    search()
    clazzs()
})

const clazzList = ref([])
const clazzs = async () => { 
    const result = await getClazzList('','','',1,1000)
    console.log(result);
    clazzList.value = result.data.rows
}
const handleSizeChange = (size) => { 
    pageSize.value = size
    search()
}
const handleCurrentChange = (page) => { 
    currentPage.value = page
    search()
}

</script>

<style scoped>

</style>
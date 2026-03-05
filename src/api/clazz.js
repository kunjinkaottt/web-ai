import request from "../utils/request";

// 查询班级
export const getClazzList = (name,beginDate,endDate,page,pageSize) => 
request.get(`/clazz?name=${name}&beginDate=${beginDate}&endDate=${endDate}&page=${page}&pageSize=${pageSize}`)

// 添加班级
export const addClazz = (clazz) => request.post("/clazz", clazz)

//根据id查询班级
export const getClazzById = (id) => request.get(`/clazz/${id}`)

// 修改班级
export const editClazz = (clazz) => request.put("/clazz", clazz)

// 删除班级
export const delClazz = (ids) => request.delete(`/clazz?ids=${ids}`)


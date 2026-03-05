import request from "@/utils/request";

// 条件查询员工
export const getEmps = (name,gender,begin,end,page,pageSize) => request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`);

// 新增员工 
export const addEmp = (emp) => request.post("/emps", emp);

// 根据id查员工
export const getEmpById = (id) => request.get(`/emps/${id}`);

// 修改员工
export const editEmp = (emp) => request.put(`/emps`, emp);

// 删除员工
export const delEmp = (ids) => request.delete(`/emps?ids=${ids}`);

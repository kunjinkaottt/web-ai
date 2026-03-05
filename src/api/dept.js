import request from "@/utils/request";

// 获取部门列表
export const getDeptList = () => request.get("/depts");

// 新增部门
export const addDept = (dept) => request.post("/depts", dept);

// 根据id查询部门
export const getDeptById = (id) => request.get(`/depts/${id}`);

// 修改部门
export const editDept = (dept) => request.put(`/depts`, dept);

// 删除部门
export const delDept = (id) => request.delete(`/depts?id=${id}`);
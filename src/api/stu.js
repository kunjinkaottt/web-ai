import request from "@/utils/request";

// 条件查询学员
export const getStuList = (name,degree,clazzId,page,pageSize) =>
 request.get(`/student?name=${name}&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`)
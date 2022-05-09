import request from '@/utils/request'

export default {

  // 1 讲师列表（条件查询分页）
  getTeacherListPage(current, limit, teacherQuery) {
    return request({
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    })
  },

  // 删除讲师
  deleteTeacherId(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
    })
  },

  // 添加讲师
  addTeacher(data) {
    return request({
      url: `/eduservice/teacher/addTeacher`,
      method: 'post',
      data
    })
  },

  // 查询讲师信息
  getTeacherInfo(id) {
    return request({
      url: `/eduservice/teacher/getTeacher/${id}`,
      method: 'get'
    })
  },

    // 修改讲师信息
    updateTeacherInfo(id ,data) {
      return request({
        url: `/eduservice/teacher/${id}`,
        method: 'put',
        data
      })
    }
}

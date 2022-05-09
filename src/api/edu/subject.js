import request from '@/utils/request'

export default {

  // 1 讲师列表（条件查询分页）
  getSubjectList() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  },

}

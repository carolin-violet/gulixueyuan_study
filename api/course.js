export default {

  getPageList() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  },

}
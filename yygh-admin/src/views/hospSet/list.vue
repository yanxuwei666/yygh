<template>
  <div>
    医院设置列表
  </div>
</template>

<script>

// 引入接口定义的js文件
// eslint-disable-next-line no-unused-vars
import hospset from '@/api/hospset'
export default {
  name: 'List',
  data() {
    return {
      current: 1,
      limit: 3,
      searchObj: {},
      list: {},
      total: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      hospset.getHospSetList(this.current, this.limit, this.searchObj)
        .then(response => {
          console.log(response.data)
        }) // 请求成功
        .catch(error => {
          console.log(error)
        }) // 请求失败
    }
  }
}
</script>

<style scoped>

</style>

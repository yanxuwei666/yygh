<template>
  <div>
    医院设置列表
  </div>
</template>

<script>
import hospset from '@/api/hospset'
export default {
  name: 'Add',
  data() {
    return {
      current: 1,
      limit: 3,
      searchObj: {}, // 传递的数据对象
      list: [], // 每页显示的数据集合
      total: 0 // 总条数
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 医院设置列表
    getList() {
      hospset.getHospSetList(this.current, this.limit, this.searchObj).then(response => { // response是接口返回数据
        this.list = response.data.records
        this.total = response.data.total
        console.log(response)
      }).catch(error => { // 请求失败
        console.log(error)
      })
    }
  }
}
</script>

<style scoped>

</style>

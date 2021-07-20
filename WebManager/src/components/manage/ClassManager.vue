<template>
  <!-- 面包屑导航区域 -->
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>教室管理</el-breadcrumb-item>
      <el-breadcrumb-item>教室列表</el-breadcrumb-item>
    </el-breadcrumb>
  <!-- card -->
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="7">
          <!-- input搜做框 -->
          <el-input placeholder="请输入查询内容" class="input-with-select">
            <el-button slot="append" type="primary" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary">新增教室</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'ClassManager',
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 2
      }
    }
  },
  created () {
  },
  methods: {
    getClassList () {
      this.$http.get('/getCollegeClass', { params: {} })
    }
  }
}
</script>

<style scoped>

</style>

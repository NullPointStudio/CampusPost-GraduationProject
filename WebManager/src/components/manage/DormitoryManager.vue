<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>宿舍管理</el-breadcrumb-item>
      <el-breadcrumb-item>宿舍列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="7">
          <!-- input搜做框 -->
          <el-input placeholder="请输入宿舍名查询" clearable @clear="selectList" v-model="queryInfo.query"
                    class="input-with-select">
            <el-button slot="append" type="primary" @click="selectList" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加宿舍</el-button>
        </el-col>
      </el-row>
      <el-table
        :data="dormitoryList"
        border
        style="width: 382px">
        <!-- 索引列 -->
        <el-table-column type="index" label="#" width="80"/>
        <el-table-column prop="dormitory_name" label="宿舍名称" width="100"></el-table-column>
        <el-table-column prop="building_name" label="宿舍楼号" width="100"></el-table-column>
        <el-table-column
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="deleteDormitory(scope.row.id)" type="danger" size="mini" icon="el-icon-delete">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[2, 8, 32, 64]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>
    <!--  Dialog添加对话框  -->
    <el-dialog
      title="添加宿舍"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogClose">
      <!-- 内容主题区域  -->
      <el-form :model="dormitoryInfoForm" :rules="dormitoryInfoFormRules" ref="dormitoryInfoFormRef" label-width="70px">
        <el-form-item label="宿舍楼">
          <el-select v-model="dormitoryInfoForm.building_id" placeholder="请选择楼号">
            <el-option
              v-for="item in buildingList"
              :key="item.building_id"
              :label="item.building_name"
              :value="item.building_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍号" prop="name">
          <el-input placeholder="请输入宿舍号，如：101、102、103" v-model="dormitoryInfoForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addDormitory">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'DormitoryManager',
  created () {
    this.getDormitoryList()
    this.getBuildingList()
  },
  data () {
    return {
      queryInfo: {
        query: '',
        pageNum: 1,
        pageSize: 8
      },
      addDialogVisible: false,
      dormitoryList: [],
      total: 0,
      pageSize: 0,
      dormitoryInfoForm: {
        name: '',
        building_id: 1
      },
      dormitoryInfoFormRules: {},
      buildingList: []
    }
  },
  methods: {
    async getBuildingList() {
      const { data: res } = await this.$http.get('/dormitory/getBuildings')
      this.buildingList = res.data
      console.log(this.buildingList)
    },
    async getDormitoryList () {
      const { data: res } = await this.$http.get('/dormitory/getDormitoryListList', { params: this.queryInfo })
      if (res.code === 200) {
        this.dormitoryList = res.pageInfo.list
        this.total = res.pageInfo.total
        this.pageSize = res.pageInfo.pageSize
        console.log(this.dormitoryList)
      } else {
        this.$message.error('获取宿舍列表失败')
      }
    },
    selectList() {
      this.getDormitoryList()
    },
    // pageSize改变
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getDormitoryList()
    },
    // 页码值改变
    handleCurrentChange (newPage) {
      this.queryInfo.pageNum = newPage
      this.getDormitoryList()
    },
    // 当dialog关闭的时候出发的操作
    addDialogClose () {
      this.$refs.dormitoryInfoFormRef.resetFields()
    },
    async addDormitory () {
      const { data: res } = await this.$http.post('/dormitory/addDormitoryByAdmin', this.dormitoryInfoForm)
      if (res.code === 200) {
        this.$message.success('添加成功!')
        this.addDialogVisible = false
        await this.getDormitoryList()
      } else {
        this.$message.error('添加失败：' + res.msg)
      }
    },
    deleteDormitory(id) {
      this.$confirm('此操作将永久删除该宿舍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const { data: res } = await this.$http.post('/dormitory/deleteDormitory', { id: id })
        console.log(res)
        if (res.code !== 200) {
          return this.$message.error('删除失败')
        } else {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          await this.getDormitoryList()
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }
}
</script>

<style scoped>
.el-table {
  margin-top: 20px;
  margin-bottom: 20px;
  border: #e2e2e2 1px solid;
}
</style>

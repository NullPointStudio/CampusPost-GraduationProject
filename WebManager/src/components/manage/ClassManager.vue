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
          <el-button type="primary" @click="addDialogShow">新增教室</el-button>
        </el-col>
      </el-row>
      <el-table
        :data="classList"
        border
        style="width: 722px">
        <!-- 索引列 -->
        <el-table-column type="index" label="#" width="80"/>
        <el-table-column prop="class_name" label="班级名称" width="100"></el-table-column>
        <el-table-column prop="college_name" label="所属学院" width="150"></el-table-column>
        <el-table-column prop="headteacher_name" label="班主任姓名" width="100"></el-table-column>
        <el-table-column prop="monitor_name" label="班长姓名" width="100"></el-table-column>
        <el-table-column
          label="操作"
          width="190">
          <template slot-scope="scope">
            <el-button @click="editTableBtn(scope.row)" type="warning" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button @click="deleteDormitory(scope.row.class_id)" type="danger" size="mini" icon="el-icon-delete">删除
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

    <el-dialog
      title="新增班级"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogClose">
      <!-- 内容主题区域  -->
      <el-form :model="classInfoForm" :rules="classInfoFormRules" ref="classInfoFormRef" label-width="80px">
        <el-form-item label="班级名称" prop="name">
          <el-input placeholder="请输入班级名称" v-model="classInfoForm.name"></el-input>
        </el-form-item>
        <el-form-item label="学院名称">
          <el-select v-model="classInfoForm.college_id" placeholder="请选择所属学院" @change="collegeChange">
            <el-option
              v-for="item in collegeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班主任">
          <el-select v-model="classInfoForm.headteacher_id" placeholder="请选择班主任(选填)">
            <el-option
              v-for="item in headteacherList"
              :key="item.account_id"
              :label="item.turename"
              :value="item.account_id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addClass">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="分配班主任和班长"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogClose">
      <!-- 内容主题区域  -->
      <el-form :model="classEditForm" ref="classEditFormRef" label-width="70px">
        <el-form-item label="班主任">
          <el-select v-model="classEditForm.headteacher_id" placeholder="请选择班主任">
            <el-option
              v-for="item in headteacherList"
              :key="item.account_id"
              :label="item.turename"
              :value="item.account_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班长">
          <el-select v-model="classEditForm.monitor_id" placeholder="请选择班长">
            <el-option
              v-for="item in monitorList"
              :key="item.account_id"
              :label="item.turename"
              :value="item.account_id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editClass">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'ClassManager',
  data () {
    return {
      collegeList: [
        {
          value: 1,
          label: '电子与信息学院'
        }, {
          value: 2,
          label: '经济与贸易学院'
        }, {
          value: 3,
          label: '世博艺术与传媒学院'
        }, {
          value: 4,
          label: '建筑工程与管理学院'
        }, {
          value: 5,
          label: '电商与物流学院'
        }, {
          value: 6,
          label: '会计与金融学院'
        }
      ],
      editDialogVisible: false,
      addDialogVisible: false,
      queryInfo: {
        query: '',
        pageNum: 1,
        pageSize: 8
      },
      classList: [],
      total: 0,
      pageSize: 0,
      classEditForm: {
        headteacher_id: null,
        monitor_id: null,
        class_id: 0
      },
      headteacherList: [],
      monitorList: [],
      classInfoForm: {
        name: '',
        college_id: 1,
        headteacher_id: null
      },
      classInfoFormRules: {
        name: [
          {
            required: true,
            message: '请输入班级名称',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created () {
    this.getClassList()
  },
  methods: {
    async getClassList () {
      const { data: res } = await this.$http.get('/collegeClass/getCollegeClassList', { params: this.queryInfo })
      if (res.code !== 200) return this.$message.error(res.msg)
      this.classList = res.pageInfo.list
      this.total = res.pageInfo.total
      this.pageSize = res.pageInfo.pageSize
    },
    editDialogClose () {
      this.headteacher_id = null
      this.monitor_id = null
      this.$refs.classEditFormRef.resetFields()
    },
    async editTableBtn (row) {
      this.classEditForm.headteacher_id = row.headteacher_id
      this.classEditForm.monitor_id = row.monitor_id
      this.classEditForm.class_id = row.class_id
      const { data: res } = await this.$http.get('/teacher/getHeadTeacherList', { params: { college_id: row.college_id } })
      if (res.code !== 200) return this.$message.error(res.msg)
      const { data: res2 } = await this.$http.get('/student/getClassStudentList', { params: { class_id: row.class_id } })
      if (res2.code !== 200) return this.$message.error(res2.msg)
      this.headteacherList = res.data
      this.monitorList = res2.data
      this.editDialogVisible = true
    },
    async editClass () {
      const { data: res } = await this.$http.post('/collegeClass/editClass', this.classEditForm)
      if (res.code !== 200) return this.$message.error(res.msg)
      this.$message.success(res.msg)
      this.$refs.classEditFormRef.resetFields()
      this.editDialogVisible = false
      await this.getClassList()
    },
    // pageSize改变
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getClassList()
    },
    // 页码值改变
    handleCurrentChange (newPage) {
      this.queryInfo.pageNum = newPage
      this.getClassList()
    },
    // 搜索教师名称
    selectList () {
      this.getClassList()
    },
    addDialogShow () {
      this.addDialogVisible = true
      this.collegeChange()
    },
    addDialogClose () {
      this.classInfoForm.college_id = 1
      this.$refs.classInfoFormRef.resetFields()
    },
    async collegeChange () {
      this.classInfoForm.headteacher_id = null
      const { data: res } = await this.$http.get('/teacher/getHeadTeacherList', { params: { college_id: this.classInfoForm.college_id } })
      if (res.code !== 200) return this.$message.error(res.msg)
      this.headteacherList = res.data
    },
    async addClass () {
      const { data: res } = await this.$http.post('/collegeClass/addClass', this.classInfoForm)
      if (res.code !== 200) return this.$message.error(res.msg)
      this.$message.success(res.msg)
      this.$refs.classInfoFormRef.resetFields()
      this.addDialogVisible = false
      await this.getClassList()
    },
    deleteDormitory(id) {
      this.$confirm('此操作将永久删除该班级, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const { data: res } = await this.$http.post('/collegeClass/deleteClass', { id: id })
        if (res.code !== 200) {
          return this.$message.error('删除失败')
        } else {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          await this.getClassList()
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

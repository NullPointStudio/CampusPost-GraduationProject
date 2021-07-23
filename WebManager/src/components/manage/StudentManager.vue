<template>
  <!-- 面包屑导航区域 -->
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- card -->
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="7">
          <!-- input搜做框 -->
          <el-input placeholder="请输入学生姓名查询" clearable @clear="selectList" v-model="queryInfo.query"
                    class="input-with-select">
            <el-button slot="append" type="primary" @click="selectList" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加学生</el-button>
        </el-col>
      </el-row>
      <el-table
        :data="teacherList"
        border
        style="width: 1122px">
        <!-- 索引列 -->
        <el-table-column type="index" label="#" width="80"/>
        <el-table-column prop="account_id" label="账号Id" width="100"></el-table-column>
        <el-table-column prop="turename" label="姓名" width="100"></el-table-column>
        <el-table-column prop="sex" label="性别" width="100"></el-table-column>
        <el-table-column prop="college_id" label="所属二级学院名称" width="200">
          <template slot-scope="scope">
            <span>
<!--                {{ scope.row.college_id | formduty }}-->
                {{ options[scope.row.college_id - 1].label }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="150"></el-table-column>
        <el-table-column prop="dormitory_name" label="宿舍" width="100"></el-table-column>
        <el-table-column prop="class_name" label="班级" width="100"></el-table-column>
        <el-table-column
          label="操作"
          width="190">
          <template slot-scope="scope">
            <el-button @click="editTableBtn(scope.row)" type="warning" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button @click="deleteTeacher(scope.row.account_id)" type="danger" size="mini" icon="el-icon-delete">删除
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
      title="添加学生"
      :visible.sync="addDialogVisible"
      width="30%"
      @close="addDialogClose">
      <!-- 内容主题区域  -->
      <el-form :model="teacherInfoForm" :rules="teacherInfoFormRules" ref="teacherInfoFormRef" label-width="70px">
        <el-form-item label="姓名" prop="name">
          <el-input placeholder="请输入姓名" v-model="teacherInfoForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="teacherInfoForm.sex" label="男">男</el-radio>
          <el-radio v-model="teacherInfoForm.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input placeholder="手机号" v-model="teacherInfoForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="二级学院">
          <el-select v-model="teacherInfoForm.select_college" placeholder="请选择二级学院名称">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addTeacher">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改学生信息"
      :visible.sync="editDialogVisible"
      width="30%"
      @close="editDialogClose">
      <!-- 内容主题区域  -->
      <el-form :model="teacherEditForm" :rules="teacherEditFormRules" ref="teacherEditFormRef" label-width="70px">
        <el-form-item label="账号ID" prop="phone">
          <el-input placeholder="姓名" disabled v-model="teacherEditForm.id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="phone">
          <el-input placeholder="姓名" v-model="teacherEditForm.name"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input placeholder="手机号" v-model="teacherEditForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="teacherEditForm.sex" label="男">男</el-radio>
          <el-radio v-model="teacherEditForm.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="二级学院">
          <el-select v-model="teacherEditForm.select_college" placeholder="请选择二级学院名称">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editTeacher">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'StudentManager',
  created () {
    this.getTeacherList()
  },
  data () {
    return {
      addDialogVisible: false,
      editDialogVisible: false,
      teacherInfoForm: {
        name: '',
        phone: '',
        sex: '男',
        select_college: 1
      },
      teacherEditForm: {
        id: '',
        name: '',
        phone: '',
        sex: '男',
        select_college: 1
      },
      teacherEditFormRules: {
        name: [
          {
            required: true,
            message: '请输入姓名',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            message: '请输入手机号',
            trigger: 'blur'
          },
          {
            min: 11,
            max: 11,
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
        ]
      },
      teacherInfoFormRules: {
        name: [
          {
            required: true,
            message: '请输入姓名',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            message: '请输入手机号',
            trigger: 'blur'
          },
          {
            min: 11,
            max: 11,
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
        ]
      },

      options: [
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
      queryInfo: {
        query: '',
        pageNum: 1,
        pageSize: 8
      },
      teacherList: [],
      total: 0
    }
  },
  methods: {
    async getTeacherList () {
      const { data: res } = await this.$http.get('/student/getStudentList', { params: this.queryInfo })
      if (res.code === 200) {
        this.teacherList = res.pageInfo.list
        this.total = res.pageInfo.total
        this.pageSize = res.pageInfo.pageSize
        console.log(this.teacherList)
      } else {
        this.$message.error('获取学生列表失败')
      }
    },
    // 当dialog关闭的时候出发的操作
    addDialogClose () {
      this.teacherInfoForm.select_college = 1
      this.teacherInfoForm.sex = '男'
      this.$refs.teacherInfoFormRef.resetFields()
    },
    // 添加教师
    addTeacher () {
      this.$refs.teacherInfoFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('/student/addStudentByAdmin', this.teacherInfoForm)
        if (res.code === 200) {
          this.$message.success('添加成功!')
          this.addDialogVisible = false
          await this.getTeacherList()
        } else {
          this.$message.error('添加失败：' + res.msg)
        }
      })
    },
    // pageSize改变
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getTeacherList()
    },
    // 页码值改变
    handleCurrentChange (newPage) {
      this.queryInfo.pageNum = newPage
      this.getTeacherList()
    },
    // 搜索教师名称
    selectList () {
      this.getTeacherList()
    },
    editTableBtn (row) {
      this.editDialogVisible = true
      this.teacherEditForm.id = row.account_id + ''
      this.teacherEditForm.name = row.turename
      this.teacherEditForm.sex = row.sex
      this.teacherEditForm.phone = row.phone
      this.teacherEditForm.college_id = row.college_id
    },
    editDialogClose () {
      this.$refs.teacherEditFormRef.resetFields()
    },
    editTeacher () {
      this.$refs.teacherEditFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('/student/editStudentByAdmin', this.teacherEditForm)
        if (res.code === 200) {
          this.$message.success('修改成功!')
          this.editDialogVisible = false
          await this.getTeacherList()
        } else {
          console.log(res)
          this.$message.error('修改失败：' + res.msg)
        }
      })
    },
    deleteTeacher (id) {
      this.$confirm('此操作将永久删除该学生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const { data: res } = await this.$http.post('/student/deleteStudent', { id: id + '' })
        console.log(res)
        if (res.code !== 200) {
          return this.$message.error('删除失败')
        } else {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          await this.getTeacherList()
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

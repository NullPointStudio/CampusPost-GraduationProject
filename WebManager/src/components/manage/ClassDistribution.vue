<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>班级管理</el-breadcrumb-item>
      <el-breadcrumb-item>班级分配</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-steps :active="step_active" finish-status="success">
        <el-step title="选择班级"></el-step>
        <el-step title="添加学生"></el-step>
        <el-step title="确认添加"></el-step>
      </el-steps>
      <div class="stepForm" v-show="step_active === 0">
        <el-form :model="collegeInfoForm" :rules="collegeInfoFormRules" ref="dormitoryInfoFormRef">
          <el-form-item label-width="350">
            请选择学院：
            <el-select v-model="selectCollege_id" placeholder="请选择学院" @change="onCollegeSelect">
              <el-option
                v-for="item in collegeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="350">
            请选择班级：
            <el-select v-model="collegeInfoForm.class_id" placeholder="请选择班级">
              <el-option
                v-for="item in classList"
                :key="item.class_id"
                :label="item.class_name"
                :value="item.class_id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div class="stepForm" v-show="step_active === 1">
        <div class="tags">
          <el-tag
            class="tags"
            :key="tag.key"
            v-for="tag in studentsTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{ tag.name }}
          </el-tag>
          <el-button class="button-new-tag" size="small" v-show="studentsTags.length < 8"
                     @click="addStuDialogVisible = true">+ New Tag
          </el-button>
        </div>
      </div>
      <div class="stepForm3" v-show="step_active === 2">
        <h4>确定要修改班级为以下学生吗？</h4>
        <span v-for="item in studentsTags" :key="item.account_id">&nbsp;&nbsp;{{ item.name }}&nbsp;&nbsp;</span>
      </div>
      <div class="stepForm3" v-show="step_active === 3">
        <h4>最后的确认</h4>
      </div>
      <div class="stepForm">
        <el-button @click="prev"
                   v-if="step_active >= 1 && step_active <= 3">
          上一步
        </el-button>
        <el-button @click="next" type="primary" v-if="
        ((step_active === 0) && (collegeInfoForm.class_id !== null)) || (step_active === 1) || step_active === 2">
          下一步
        </el-button>
        <el-button type="primary" @click="submit" v-if="step_active === 3">提交</el-button>
      </div>
    </el-card>
    <el-dialog
      title="添加学生信息"
      :visible.sync="addStuDialogVisible"
      width="30%"
      @close="dialogClose">
      <el-form :model="ClassInfoForm" ref="dormitoryInfoFormRef">
        <el-form-item label-width="350">
          请选择学院：
          <el-select v-model="DialogSelectCollege_id" placeholder="请选择学院" @change="onCollegeSelectChange">
            <el-option
              v-for="item in collegeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="350">
          请选择学生：
          <el-select v-model="studentInfoForm.selected_student" placeholder="请选择学生">
            <el-option
              v-for="item in dialogStudentsList"
              :key="item.account_id"
              :label="item.turename"
              :value="item.account_id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addStuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addStuConfirm"
                   :disabled="studentInfoForm.selected_student === null">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'ClassDistribution',
  data () {
    return {
      step_active: 0,
      collegeInfoForm: {
        class_id: null
      },
      collegeInfoFormRules: {},
      selectCollege_id: null,
      DialogSelectCollege_id: null,
      collegeList: [{
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
      }],
      classList: [],
      dialogClassList: [],
      // tag标签对线，储存姓名和Id
      studentsTags: [],
      // tag中的学生Id，单独储存为一个list
      studentTagsIds: [0, 1],
      // 新增的学生Id
      addStudentIds: [],
      // 从班级中移除的学生Id
      dropStudents: [],
      // 添加学生的Dialog显示与否
      addStuDialogVisible: false,
      // Dialog表单中的信息
      ClassInfoForm: {
        college_id: null,
        class_id: null
      },
      dialogStudentsList: [],
      studentInfoForm: {
        selected_student: null
      }
    }
  },
  methods: {
    prev () {
      --this.step_active
    },
    next () {
      if (++this.step_active > 3) {
        this.step_active = 0
        this.nextBtn = '下一步'
      }
      if (this.step_active === 1) {
        this.studentsTags = []
        this.studentTagsIds = [0, 1]
        // 获取教室已有的学生
        this.getClassStudents()
      }
    },
    async onCollegeSelect () {
      this.collegeInfoForm.class_id = null
      const { data: res } = await this.$http.get('/collegeClass/getCollegeClass', { params: { college_id: this.selectCollege_id } })
      console.log(res)
      this.classList = res.data
    },
    async getClassStudents () {
      const { data: res } = await this.$http.get('/student/getClassStudentList', { params: { class_id: this.collegeInfoForm.class_id } })
      for (let j = 0; j < res.data.length; j++) {
        this.studentsTags.push({
          account_id: res.data[j].account_id,
          name: res.data[j].turename
        })
        this.studentTagsIds.push(res.data[j].account_id)
      }
    },
    dialogClose () {
      this.DialogSelectCollege_id = null
      this.studentInfoForm.selected_student = null
    },
    // async getCollegeClassList () {
    //   const { data: res } = await this.$http.get('/collegeClass/getCollegeClass', { params: { college_id: this.DialogSelectCollege_id } })
    //   this.dialogClassList = res.data
    // },
    async onCollegeSelectChange () {
      this.studentInfoForm.selected_student = null
      const { data: res } = await this.$http.post('/student/getCollegeStudentListNotIn', {
        college_id: this.DialogSelectCollege_id,
        ids: this.studentTagsIds
      })
      console.log(res)
      if (res.code !== 200) this.$message.error(res.msg)
      this.dialogStudentsList = res.data
    },
    async addStuConfirm () {
      if (this.dropStudents.indexOf(this.studentInfoForm.selected_student) === -1) {
        this.addStudentIds.push(this.studentInfoForm.selected_student)
      } else {
        this.dropStudents.splice(this.dropStudents.indexOf(this.studentInfoForm.selected_student), 1)
      }
      const { data: res } = await this.$http.get('/student/getStudentByAccountId', { params: { account_id: this.studentInfoForm.selected_student } })
      this.studentsTags.push({
        account_id: this.studentInfoForm.selected_student,
        name: res.data.turename
      })
      this.studentTagsIds.push(this.studentInfoForm.selected_student)
      this.addStuDialogVisible = false
      console.log('新增的学生')
      console.log(this.addStudentIds)
      console.log('删除的学生')
      console.log(this.dropStudents)
      console.log('已经选中的学生')
      console.log(this.studentTagsIds)
    },
    handleClose (tag) {
      if (this.addStudentIds.indexOf(tag.account_id) === -1) {
        this.dropStudents.push(tag.account_id)
      } else {
        this.addStudentIds.splice(this.addStudentIds.indexOf(tag.account_id), 1)
      }
      this.studentsTags.splice(this.studentsTags.indexOf(tag), 1)
      this.studentTagsIds.splice(this.studentTagsIds.indexOf(tag.account_id), 1)
      console.log('新增的学生')
      console.log(this.addStudentIds)
      console.log('删除的学生')
      console.log(this.dropStudents)
      console.log('已经选中的学生')
      console.log(this.studentTagsIds)
    },
    async submit () {
      const { data: res } = await this.$http.post('/student/changeClass', {
        class_id: this.collegeInfoForm.class_id,
        dropIds: this.dropStudents,
        addIds: this.addStudentIds
      })
      if (res.code !== 200) return this.$message.error(res.msg)
      this.$message.success(res.msg)
      this.step_active = 0
      this.collegeInfoForm.class_id = null
      this.studentTagsIds = [0, 1]
      this.addStudentIds = []
      this.dropStudents = []
      this.dialogStudentsList = []
    }
  }
}
</script>

<style scoped>
.stepForm {
  text-align: center;
  margin-top: 64px;
}

.tags {
  margin-top: 12px;
  text-align: center;
  margin-bottom: 12px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.stepForm3 {
  text-align: center;
}
</style>

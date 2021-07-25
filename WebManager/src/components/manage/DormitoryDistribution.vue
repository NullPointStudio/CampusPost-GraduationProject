<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>宿舍管理</el-breadcrumb-item>
      <el-breadcrumb-item>宿舍分配</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-steps :active="$store.state.addStudentToDormitoryAction" finish-status="success">
        <el-step title="选择宿舍"></el-step>
        <el-step title="添加学生"></el-step>
        <el-step title="确认添加"></el-step>
      </el-steps>
      <div class="stepForm" v-show="$store.state.addStudentToDormitoryAction === 0">
        <el-form :model="dormitoryInfoForm" :rules="dormitoryInfoFormRules" ref="dormitoryInfoFormRef">
          <el-form-item label-width="350">
            请选择楼号：
            <el-select v-model="selectBuilding_id" placeholder="请选择楼号" @change="onBuildingSelectChange">
              <el-option
                v-for="item in buildingList"
                :key="item.building_id"
                :label="item.building_name"
                :value="item.building_id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="350">
            请选择宿舍：
            <el-select v-model="dormitoryInfoForm.dormitory_id" placeholder="请选择宿舍" @change="onDormitorySelectChange">
              <el-option
                v-for="item in dormitoryList"
                :key="item.id"
                :label="item.dormitory_name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div class="stepForm" v-show="$store.state.addStudentToDormitoryAction === 1">
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
      <div class="stepForm3" v-show="$store.state.addStudentToDormitoryAction === 2">
        <h4>确定要修改宿舍为以下学生吗？</h4>
        <span v-for="item in studentsTags" :key="item.account_id">&nbsp;&nbsp;{{ item.name }}&nbsp;&nbsp;</span>
      </div>
      <div class="stepForm3" v-show="$store.state.addStudentToDormitoryAction === 3">
        <h4>最后的确认</h4>
      </div>
      <div class="stepForm">
        <el-button @click="prev"
                   v-if="$store.state.addStudentToDormitoryAction >= 1 && $store.state.addStudentToDormitoryAction <= 3">
          上一步
        </el-button>
        <el-button @click="next" type="primary" v-if="
        (($store.state.addStudentToDormitoryAction === 0) && (dormitoryInfoForm.dormitory_id !== null)) || (($store.state.addStudentToDormitoryAction === 1) && (studentsTags.length !== 0)) || $store.state.addStudentToDormitoryAction === 2">
          下一步
        </el-button>
        <el-button type="primary" @click="submit" v-if="$store.state.addStudentToDormitoryAction === 3">提交</el-button>
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
          <el-select v-model="selectCollege_id" placeholder="请选择学院" @change="onCollegeSelectChange">
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
          <el-select v-model="ClassInfoForm.class_id" placeholder="请选择班级" @change="onClassChanged">
            <el-option
              v-for="item in ClassList"
              :key="item.class_id"
              :label="item.class_name"
              :value="item.class_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="350">
          请选择学生：
          <el-select v-model="studentInfoForm.selected_student" placeholder="请选择学生" @change="onStudentSelect">
            <el-option
              v-for="item in studentsList"
              :key="item.account_id"
              :label="item.turename"
              :value="item">
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
  name: 'DormitoryDistribution',
  created () {
    this.$store.state.addStudentToDormitoryAction = 0
    this.getBuildingList()
    this.getDormitoryList()
    this.getCollegeClassList()
  },
  data () {
    return {
      studentsTags: [],
      studentTagsIds: [
        0, 1
      ],
      addStudentIds: [],
      dropStudents: [],
      addStuDialogVisible: false,
      active: 0,
      selectBuilding_id: 1,
      selectCollege_id: 1,
      nextBtn: '下一步',
      dormitoryList: [],
      buildingList: [],
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
      ClassList: [],
      dormitoryInfoForm: {
        dormitory_id: null
      },
      dormitoryInfoFormRules: {},
      ClassInfoForm: {
        college_id: '',
        class_id: null
      },
      studentsList: [],
      studentInfoForm: {
        selected_student: null
      }
    }
  },
  methods: {
    async getBuildingList () {
      const { data: res } = await this.$http.get('/dormitory/getBuildings')
      this.buildingList = res.data
      console.log(this.buildingList)
    },
    async getDormitoryList () {
      const { data: res } = await this.$http.get('/dormitory/getDormitoryList', { params: { id: this.selectBuilding_id } })
      this.dormitoryList = res.data
    },
    prev () {
      console.log(this.$store.state.addStudentToDormitoryAction)
      --this.$store.state.addStudentToDormitoryAction
    },
    next () {
      if (++this.$store.state.addStudentToDormitoryAction > 3) {
        this.$store.state.addStudentToDormitoryAction = 0
        this.nextBtn = '下一步'
      }
      if (this.$store.state.addStudentToDormitoryAction === 1) {
        this.studentsTags = []
        this.studentTagsIds = [0, 1]
        // 获取宿舍已有的学生
        this.getDormitoryStudents()
      }
    },
    onBuildingSelectChange () {
      this.dormitoryInfoForm.dormitory_id = null
      this.getDormitoryList()
    },
    onDormitorySelectChange () {
      console.log(this.dormitoryInfoForm.dormitory_id)
    },
    async getCollegeClassList () {
      const { data: res } = await this.$http.get('/collegeClass/getCollegeClass', { params: { college_id: this.selectCollege_id } })
      this.ClassList = res.data
    },
    onCollegeSelectChange () {
      this.ClassInfoForm.class_id = null
      this.studentInfoForm.selected_student = null
      this.getCollegeClassList()
    },
    dialogClose () {
      this.ClassInfoForm.class_id = null
      this.studentInfoForm.selected_student = null
    },
    async onClassChanged () {
      this.studentInfoForm.student_id = null
      const { data: res } = await this.$http.post('/student/getClassStudentListNotIn', {
        class_id: this.ClassInfoForm.class_id,
        ids: this.studentTagsIds
      })
      console.log(res)
      this.studentsList = res.data
    },
    onStudentSelect () {
      console.log(this.studentInfoForm.selected_student)
    },
    addStuConfirm () {
      if (this.dropStudents.indexOf(this.studentInfoForm.selected_student.account_id) === -1) {
        this.addStudentIds.push(this.studentInfoForm.selected_student.account_id)
      } else {
        this.dropStudents.splice(this.dropStudents.indexOf(this.studentInfoForm.selected_student.account_id), 1)
      }
      this.studentsTags.push({
        account_id: this.studentInfoForm.selected_student.account_id,
        name: this.studentInfoForm.selected_student.turename
      })
      this.studentTagsIds.push(this.studentInfoForm.selected_student.account_id)
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
    async getDormitoryStudents () {
      const { data: res } = await this.$http.get('/student/getDormitoryStudentList', { params: { dormitory_id: this.dormitoryInfoForm.dormitory_id } })
      for (let j = 0; j < res.data.length; j++) {
        this.studentsTags.push({
          account_id: res.data[j].account_id,
          name: res.data[j].turename
        })
        this.studentTagsIds.push(res.data[j].account_id)
      }
    },
    async submit () {
      const { data: res } = await this.$http.post('/student/changeDormitory', {
        dormitory_id: this.dormitoryInfoForm.dormitory_id,
        dropIds: this.dropStudents,
        addIds: this.addStudentIds
      })
      if (res.code !== 200) return this.$message.error(res.msg)
      this.$message.success(res.msg)
      this.$store.state.addStudentToDormitoryAction = 0
      this.dormitoryInfoForm.dormitory_id = null
      this.studentTagsIds = [0, 1]
      this.addStudentIds = []
      this.dropStudents = []
      this.studentsList = []
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

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
          <el-form-item  label-width="350">
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
        <el-form :model="studentInfoForm" :rules="studentInfoFormRules" ref="dormitoryInfoFormRef">
          <el-form-item  label-width="350">
            请选择学院名：
            <el-select v-model="selectCollege_id" placeholder="请选择学院" @change="onBuildingSelectChange">
              <el-option
                v-for="item in collegeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="350">
            请选择宿舍：
            <el-select v-model="studentInfoForm.dormitory_id" placeholder="请选择班级">
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
      <div class="stepForm" v-show="$store.state.addStudentToDormitoryAction === 2">
      </div>
      <div class="stepForm">
        <el-button @click="prev"
                   v-if="$store.state.addStudentToDormitoryAction >= 1 && $store.state.addStudentToDormitoryAction <= 3">
          上一步
        </el-button>
        <el-button @click="next" type="primary" v-if="($store.state.addStudentToDormitoryAction <= 2) && (dormitoryInfoForm.dormitory_id !== '')">下一步</el-button>
        <el-button type="primary" v-if="$store.state.addStudentToDormitoryAction === 3">提交</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'DormitoryDistribution',
  created () {
    this.getBuildingList()
    this.getDormitoryList()
  },
  data () {
    return {
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
      dormitoryInfoForm: {
        dormitory_id: ''
      },
      dormitoryInfoFormRules: {},
      studentInfoForm: {
        college_id: ''
      },
      studentInfoFormRules: {}
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
      console.log(this.$store.state.addStudentToDormitoryAction)
      if (++this.$store.state.addStudentToDormitoryAction > 3) {
        this.$store.state.addStudentToDormitoryAction = 0
        this.nextBtn = '下一步'
      }
    },
    onBuildingSelectChange () {
      this.getDormitoryList()
    },
    onDormitorySelectChange() {
      console.log(this.dormitoryInfoForm.dormitory_id)
    }
  }
}
</script>

<style scoped>
.stepForm {
  text-align: center;
  margin-top: 64px;
}

</style>

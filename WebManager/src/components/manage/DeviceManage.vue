<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="宿舍设备" name="DormitoryDevices"></el-tab-pane>
      <el-tab-pane label="教室设备" name="ClassDevice"></el-tab-pane>
      <el-tab-pane label="其他设备" name="OtherDevice"></el-tab-pane>
    </el-tabs>
    <el-card class="box-card">
            <el-row :gutter="20">
              <el-col :span="7">
                <!-- input搜做框 -->
                <el-input placeholder="请输入教师姓名查询" clearable @clear="selectList" v-model="queryInfo.query"
                          class="input-with-select">
                  <el-button slot="append" type="primary" @click="selectList" icon="el-icon-search"></el-button>
                </el-input>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" @click="addDialogVisible = true">添加教师</el-button>
              </el-col>
            </el-row>
      <el-table
        :data="deviceList"
        border
        style="width: 922px">
        <!-- 索引列 -->
        <el-table-column type="index" label="#" width="80"/>
        <el-table-column prop="device_icon" label="图标" width="50">
          <template slot-scope="scope">
            <el-image
              style="width: 25px; height: 23px"
              :src="scope.row.deviceIcon"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="device_name" label="设备名称" width="150"></el-table-column>
        <el-table-column label="所属房间" width="100">
          <template slot-scope="scope">
            {{(activeName === "DormitoryDevices") ? scope.row.dormitory_name : (activeName === "ClassDevice") ? scope.row.class_name : "其他"}}
          </template>
        </el-table-column>
        <el-table-column prop="device_sub_path" label="设备订阅路径" width="200"></el-table-column>
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
      <!--      <el-pagination-->
      <!--        @size-change="handleSizeChange"-->
      <!--        @current-change="handleCurrentChange"-->
      <!--        :current-page="queryInfo.pageNum"-->
      <!--        :page-sizes="[2, 8, 32, 64]"-->
      <!--        :page-size="queryInfo.pageSize"-->
      <!--        layout="total, sizes, prev, pager, next, jumper"-->
      <!--        :total="total">-->
      <!--      </el-pagination>-->
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'DeviceManage',
  created () {
    this.getDevice()
  },
  data () {
    return {
      activeName: 'DormitoryDevices',
      queryInfo: {
        query: '',
        pageNum: 1,
        pageSize: 8
      },
      deviceList: []
    }
  },
  methods: {
    async getDevice () {
      const { data: res } = await this.$http.get('/device/get' + this.activeName, { params: this.queryInfo })
      console.log(res)
      this.deviceList = res.pageInfo.list
    },
    handleClick() {
      this.deviceList = []
      this.getDevice()
    }
  }
}
</script>

<style scoped>

</style>

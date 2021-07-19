<template>
  <el-container class="manage_menu_container">
    <el-aside width="200px">
      <el-menu
        default-active="2"
        background-color="#3c3f41"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-submenu :index="item.menuId+''" v-for="item in secondMenuList" :key="item.menuId">
          <template slot="title">
            <i :class="item.menuIco"></i>
            <span>{{ item.menuName }}</span>
          </template>
          <el-menu-item :index="child.menuId+''" v-for="child in item.childs" :key="child.menuId">
            <i :class="child.menuIco"></i>
            {{
              child.menuName
            }}
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-main>Main</el-main>
      <!--  底部区域  -->
      <el-footer>
        <div id="copyright">
          &copy;CopyRight NullPointStudio
        </div>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
export default {
  created () {
    console.log('ManageMenu:' + this.$route.query.parentId)
    this.getSecondMenu()
  },
  name: 'ManageMenu',
  data () {
    return {
      secondMenuIndex: '201',
      secondMenuList: []
    }
  },
  methods: {
    // 获取左侧菜单
    async getSecondMenu () {
      const { data: res } = await this.$http.get('/adminMenu/getSecondMenu?parentId=' + this.$route.query.parentId)
      console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.secondMenuList = res.data
    }
  }
}
</script>

<style scoped>
.manage_menu_container {
  height: 100%;
  margin: 0;
  padding: 0;
}

.el-aside {
  /*background-color: #b2bcd3;*/
  background-color: #3c3f41;
}

.el-main {
  background-color: #ececec;
}

.el-footer {
  background-color: #2b2b2c;
}
#copyright{
  height: 100%;
  width: 100%;
  font-family: pangmen;
  font-style: italic;
  line-height: 60px;
  color: white;
  text-align: center;
  font-size: 8px;
}

.el-aside .el-menu {
  border-right: none;
}

.el-submenu {
  padding: 0;
  margin: 0;
}

.el-menu-item {
  text-align: center;
  padding: 0;
  margin: 0;
}

</style>

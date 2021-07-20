<template>
  <el-container class="manage_menu_container">
    <el-aside width="200px">
      <el-menu
        class="el-menu-vertical-demo"
        :default-active="secondMenuIndex"
        background-color="#3c3f41"
        text-color="#fff"
        active-text-color="#409eff"
        router>
        <el-submenu :index="item.menuId+''" v-for="item in secondMenuList" :key="item.menuId">
          <template slot="title">
            <i :class="item.menuIco"></i>
            <span>{{ item.menuName }}</span>
          </template>
          <el-menu-item
            :index="child.menuId+''" v-for="child in item.childs" :key="child.menuId" :route="{ path: child.path}">
            <i :class="child.menuIco"></i>
            {{ child.menuName }}
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-main>
        <router-view/>
      </el-main>
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
    this.getSecondMenu()
  },
  name: 'ManageMenu',
  data () {
    return {
      secondMenuIndex: '',
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
      if (res.data[0].childs[0] !== null && res.data[0].childs[0].menuId !== 0) {
        this.secondMenuIndex = res.data[0].childs[0].menuId + ''
        await this.$router.push(res.data[0].childs[0].path)
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

#copyright {
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

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>

<template>
  <el-container class="manage_container">
    <!--  头部区域  -->
    <el-header>
      <span id="manage_title">校园驿站-后台管理</span>
      <el-menu
        mode="horizontal"
        background-color="#2b2b2c"
        text-color="#fff"
        active-text-color="#409eff"
        :default-active="titleMenuIndex"
        @select="handleSelect"
        router>
        <el-menu-item :index="item.menuId+''" v-for="item in firstMenuList" :key="item.menuId"
                      :route="{ path: '/manageMenu', query: { parentId: item.menuId } }">
          {{ item.menuName }}
        </el-menu-item>
      </el-menu>
      <el-button type="text" @click="signOut">退出登录</el-button>
    </el-header>
    <!--  内容主题  -->
    <el-main>
      <router-view v-if="isRouterAlive"/>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: 'Manage',
  data () {
    return {
      fit: 'cover',
      firstMenuList: [],
      titleMenuIndex: '101',
      LeftMenuIndex: '201',
      isRouterAlive: true
    }
  },
  created () {
    this.getFirstMenu()
  },
  methods: {
    signOut () {
      // 清空sessionStorage
      window.sessionStorage.clear()
      // 跳转回login页面
      this.$router.push('/login')
      this.$message({
        message: '已退出账户',
        type: 'warning'
      })
    },
    // 获取头部菜单
    async getFirstMenu () {
      const { data: res } = await this.$http.get('/adminMenu/getFirstMenu')
      console.log(res)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.firstMenuList = res.data
    },
    handleSelect (key, keyPath) {
      this.titleMenuIndex = key
      console.log(key, keyPath)
      this.reload()
    },
    reload () {
      this.isRouterAlive = false
      this.$nextTick(() => (this.isRouterAlive = true))
    }
  }
}
</script>

<style scoped>
.manage_container {
  height: 100%;
  margin: 0;
  padding: 0;
}

.el-header {
  background-color: #2b2b2c;
  /*background-color: rgb(202, 170, 215);*/
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-main {
  background-color: #ececec;
  padding: 0;
  margin: 0;
}

.el-button {
  font-size: 8px;
  color: white;
}

#manage_title {
  font-family: pangmen;
  color: white;
  font-size: 30px;
}

.head_el-menu {
  background-color: rgba(255, 255, 255, 0);
}

.el-aside .el-menu {
  border-right: none;
}
</style>

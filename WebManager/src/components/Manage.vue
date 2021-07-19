<template>
  <el-container class="manage_container">
    <!--  头部区域  -->
    <el-header>
      <span id="manage_title">校园驿站-后台管理</span>
      <el-menu
        class="el-menu-demo"
        mode="horizontal"
        background-color="#2b2b2c"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-menu-item index="1">首页</el-menu-item>
        <el-menu-item index="2">处理中心</el-menu-item>
        <el-submenu index="3">
          <template slot="title">我的工作台</template>
          <el-menu-item index="3-1">选项1</el-menu-item>
          <el-menu-item index="3-2">选项2</el-menu-item>
          <el-menu-item index="3-3">选项3</el-menu-item>
          <el-submenu index="3-4">
            <template slot="title">选项4</template>
            <el-menu-item index="3-4-1">选项1</el-menu-item>
            <el-menu-item index="3-4-2">选项2</el-menu-item>
            <el-menu-item index="3-4-3">选项3</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="4" disabled>消息中心</el-menu-item>
        <el-menu-item index="5">个人中心</el-menu-item>
      </el-menu>
      <el-button type="text" @click="signOut">退出登录</el-button>
    </el-header>
    <el-container>
      <!--  侧边栏  -->
      <el-aside width="200px">
        <el-menu
          background-color="#3c3f41"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <!--  内容主题  -->
        <el-main>Main</el-main>
        <!--  底部区域  -->
        <el-footer>
          <span id="copyright">@CopyRight NullPointStudio</span>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>

export default {
  name: 'Manage',
  data () {
    return {
      fit: 'cover'
    }
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

.el-button {
  font-size: 8px;
  color: white;
}

#copyright {
  font-family: pangmen;
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
.el-aside .el-menu{
  border-right: none;
}
</style>

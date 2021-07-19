<template>
  <div class="login_container">
    <div class="login_box">
      <el-card id="login_avatar" shadow="always">
        <el-image
          id="avatar" :src="require('@/assets/logo.png')"
          :fit="fit"/>
      </el-card>
      <el-card id="login_card" shadow="hover">
        <div id="login_title">
          <span><b>校园驿站-后台管理</b></span>
        </div>
        <el-form class="login_form" :rules="loginFormRules" :model="loginForm" ref="LoginFormRef">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="账号/邮箱/手机号" prefix-icon="el-icon-user"/>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="密码"
                      prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="login">登录</el-button>
            <el-button type="default" @click="register">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      fit: 'cover',
      loginForm: {
        username: '',
        password: ''
      },
      loginFormRules: {
        username: [
          {
            required: true,
            message: '请输入 账号/邮箱/手机号',
            trigger: 'blur'
          },
          {
            min: 6,
            max: 30,
            message: '长度在 6 到 30 个字符',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入 密码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    register () {
      console.log(this.$refs)
      this.$refs.LoginFormRef.resetFields()
      // this.$router.push('/register')
    },
    login () {
      this.$refs.LoginFormRef.validate(async (valid) => {
        if (!valid) return
        const { data: result } = await this.$http.post('/account/adminLogin', this.loginForm)
        if (result.code === 200) {
          console.log(result)
          await this.$alert('登录成功', '登录信息', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'success',
                message: '登录成功,正在跳转'
              })
              // 1. 将登录成功的token保存到sessionStorage中
              //  1.1 项目中除了登录之外的所有请求，必须在头部信息中绑定Authorization设置token值
              window.sessionStorage.setItem('token', result.token)
              // 2. 通过编程式导航跳转到后台主页，路由地址是/manage
              this.$router.push('/manage')
            }
          })
        } else {
          console.log(result)
          this.$message.error('登录失败：' + result.msg)
          this.$refs.LoginFormRef.resetFields()
        }
      })
    }
  }
}
</script>

<style scoped>
h1 {
  margin: 0;
  padding: 0;
}

.login_container {
  /*background-color: #3c3f41;*/
  /*background-color: white;*/
  background-image: url('~@/assets/images/just_do_it.jpg');
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: top;
  height: 100%;
  margin: 0;
  padding: 0;
}

.login_box {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

#login_avatar {
  background-color: #ffffff;
  width: 120px;
  height: 120px;
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  top: -60px;
  border-radius: 60px;
}

#login_card {
  padding-top: 60px;
  background-color: #ffffff99;
  border-radius: 15px;
  width: 450px;
}

.login_form {
  padding-left: 20%;
  padding-right: 20%;;
  left: 50%;
  top: 50%;
}

.btns {
  text-align: right;
}

#login_title {
  margin-bottom: 20px;
  width: 100%;
  text-align: center;
  font-size: 24px;
  font-family: 庞门正道标题体;
}
</style>

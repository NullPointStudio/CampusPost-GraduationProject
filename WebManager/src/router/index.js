import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login'
import Manage from '@/components/Manage'
import ManageMenu from '@/components/ManageMenu'
import StudentManager from '@/components/manage/StudentManager'
import TeacherManager from '@/components/manage/TeacherManager'
import ViewsNum from '@/components/manage/ViewsNum'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Home',
    component: Login
  },
  {
    path: '/manage',
    component: Manage,
    children: [
      {
        path: '/manageMenu',
        component: ManageMenu,
        children: [{
          path: '/teacherManager',
          component: TeacherManager
        }, {
          path: '/studentManager',
          component: StudentManager
        }, {
          path: '/viewsNum',
          component: ViewsNum
        }
        ]
      }
    ]
  }
]

const router = new VueRouter({
  routes
})
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

// 挂在路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  // next('/login) 表示强制跳转到login页面
  if (to.path === '/login') return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) {
    this.$message.error('请先登录')
    return next('/login')
  }
  next()
})

export default router

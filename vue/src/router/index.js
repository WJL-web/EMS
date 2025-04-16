import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

//解决连续点击相同路由链接时的控制台报错问题

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push= function push(location) {
    return originalPush.call(this,location).catch(err=>err)
}

const routes = [
    {

    //后台管理路由

      path: '/',
      name: 'Manager',
      component: () => import('../views/Manager.vue'),
      redirect: '/home',  // 重定向到主页
      children: [
        { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
        { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
        { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
        { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
        { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
        { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      ]
    },


    {

    //前台路由

      path: '/front',
      name: 'Front',
      component: () => import('../views/Front.vue'),
      children: [
        { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
        { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
      ]
    },

    //登录，注册等路由
    
    { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
    { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
    { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
  ]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
  
  
export default router
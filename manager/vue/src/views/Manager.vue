<template>
  <el-container style="height: 100vh; background-color: #f4f6f9;">
    <!-- 侧边栏: 根据是否折叠动态变宽, 加入好看的深蓝色系 -->
    <el-aside :width="isCollapse ? '64px' : '230px'" class="custom-aside">
      <div class="logo-box">
        <img src="@/assets/imgs/logo.png" class="logo-img" />
        <span v-if="!isCollapse" class="logo-title">教务管理系统</span>
      </div>

      <el-menu
        :default-active="$route.path"
        class="custom-menu"
        router
        :collapse="isCollapse"
        background-color="#001529"
        text-color="#a6adb4"
        active-text-color="#ffffff"
        style="border: none;"
      >
        <el-menu-item index="/home">
          <i class="el-icon-s-home"></i>
          <span slot="title">系统首页</span>
        </el-menu-item>
        
        <!-- 业务动态菜单区：根据角色权限过滤呈现 -->
        <template v-for="(menu, index) in computedMenus">
          <!-- 有子菜单的项 -->
          <el-submenu :index="menu.id" :key="index" v-if="menu.children && menu.children.length > 0">
            <template slot="title">
              <i :class="menu.icon"></i>
              <span slot="title">{{ menu.name }}</span>
            </template>
            <el-menu-item v-for="child in menu.children" :key="child.path" :index="child.path">
              {{ child.name }}
            </el-menu-item>
          </el-submenu>
          
          <!-- 无子菜单的独立项 (备用) -->
          <el-menu-item :index="menu.path" :key="index" v-else>
            <i :class="menu.icon"></i>
            <span slot="title">{{ menu.name }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 顶部导航栏 -->
      <el-header class="custom-header">
        <div class="header-left">
          <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'" @click="toggleCollapse" class="collapse-btn"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="$route.path !== '/home'">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <el-dropdown placement="bottom" trigger="click">
            <div class="user-profile">
              <el-avatar :size="36" :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
              <div class="user-info">
                <span class="user-name">{{ user.name || '未登录' }}</span>
                <span class="user-role-badge">{{ userRoleText }}</span>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown" class="user-dropdown">
              <el-dropdown-item @click.native="goToPerson"><i class="el-icon-user"></i> 个人中心</el-dropdown-item>
              <el-dropdown-item @click.native="$router.push('/password')"><i class="el-icon-lock"></i> 修改密码</el-dropdown-item>
              <el-dropdown-item divided @click.native="logout" style="color: #f56c6c;"><i class="el-icon-switch-button"></i> 退出账号</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主体视图区 -->
      <el-main class="custom-main">
        <transition name="el-fade-in-linear" mode="out-in">
          <router-view @update:user="updateUser" class="main-card-view" />
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// 这里抽出所有的菜单配置，方便以后管理和扩展权限
const ALL_MENUS = [
  {
    id: 'info', name: '信息公告', icon: 'el-icon-message-solid', roles: ['ADMIN', 'TEACHER', 'STUDENT'],
    children: [
      { name: '教务通知', path: '/notice' },
      { name: '考试安排', path: '/examPlan' },
      { name: '教室安排', path: '/roomPlan' }
    ]
  },
  {
    id: 'administration', name: '行政管理', icon: 'el-icon-s-cooperation', roles: ['ADMIN'], // 仅管理员可见
    children: [
      { name: '学院信息', path: '/college' },
      { name: '专业信息', path: '/speciality' },
      { name: '班级信息', path: '/classes' }
    ]
  },
  {
    id: 'teach', name: '排课与教学', icon: 'el-icon-reading', roles: ['ADMIN', 'TEACHER'], // 只有老师和管理员参与排课有关
    children: [
      { name: '课程信息(总)', path: '/course' },
      { name: '教务任务下达池', path: '/teacherIntentApply' }
    ]
  },
  {
    id: 'user', name: '系统用户管理', icon: 'el-icon-user-solid', roles: ['ADMIN'], // 仅管理员可见
    children: [
      { name: '管理员信息', path: '/admin' },
      { name: '教师信息', path: '/teacher' },
      { name: '学生信息', path: '/student' }
    ]
  }
]

export default {
  name: "Manager",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      isCollapse: false // 控制侧边栏收缩状态
    }
  },
  created() {
    if (!this.user.id) {
      this.$router.push('/login')
    }
  },
  computed: {
    // 动态计算该角色可以渲染的菜单列表 (严格菜单鉴权隔离)
    computedMenus() {
      const myRole = this.user.role || 'STUDENT';
      return ALL_MENUS.filter(menu => menu.roles.includes(myRole));
    },
    // 当前角色的中文显示
    userRoleText() {
      const roleMap = { 'ADMIN': '教务管理员', 'TEACHER': '教师工作者', 'STUDENT': '学生' };
      return roleMap[this.user.role] || '未知身份';
    }
  },
  methods: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}');
    },
    goToPerson() {
      const routeMap = {
        'ADMIN': '/adminPerson',
        'TEACHER': '/teacherPerson',
        'STUDENT': '/studentPerson'
      };
      const targetPath = routeMap[this.user.role];
      if (targetPath) this.$router.push(targetPath);
    },
    logout() {
      localStorage.removeItem('xm-user');
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
/* 现代风暗色侧边栏 */
.custom-aside {
  background-color: #001529;
  transition: width 0.3s;
  box-shadow: 2px 0 6px rgb(0 21 41 / 35%);
  z-index: 10;
}
.logo-box {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  color: white;
  background-color: #002140;
}
.logo-box .logo-img { width: 32px; height: 32px; margin: 0 10px; border-radius: 50%; }
.logo-box .logo-title { font-size: 18px; font-weight: bold; white-space: nowrap; }
.custom-menu .el-submenu__title i, .custom-menu .el-menu-item i { color: #a4b2c2; margin-right: 8px; }
.custom-menu .el-menu-item.is-active { background-color: #1890ff !important; color: white !important; }

/* 现代风纯白顶部条 */
.custom-header {
  background-color: white;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgb(0 21 41 / 8%);
  padding: 0 20px;
  z-index: 9;
}
.header-left { display: flex; align-items: center; }
.collapse-btn { font-size: 24px; cursor: pointer; color: #666; margin-right: 20px; transition: color 0.3s; }
.collapse-btn:hover { color: #1890ff; }
.breadcrumb { margin-left: 10px; }

/* 用户信息下拉区 */
.user-profile { display: flex; align-items: center; cursor: pointer; padding: 2px 8px; border-radius: 8px; transition: background 0.3s; }
.user-profile:hover { background-color: #f5f7fa; }
.user-info { display: flex; flex-direction: column; margin-left: 10px; }
.user-name { font-size: 14px; font-weight: bold; color: #333; line-height: 1.2; }
.user-role-badge { font-size: 12px; color: #1890ff; background: #e6f7ff; padding: 0 6px; border-radius: 4px; margin-top: 2px; }

/* 卡片式内容区 */
.custom-main { padding: 20px; }
.main-card-view {
  background: white;
  border-radius: 8px;
  padding: 20px;
  min-height: calc(100vh - 100px);
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}
</style>
<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入课程名称查询" style="width: 200px" v-model="searchName" clearable @clear="load()"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load()">搜索计划</el-button>
    </div>

    <!-- 教务处下达的授课计划列表 -->
    <div class="table">
      <!-- Element-UI 卡片样式展示 -->
      <el-table :data="tableData" stripe highlight-current-row>
        <el-table-column prop="id" label="计划编号" width="80" align="center"></el-table-column>
        <el-table-column prop="semester" label="学期" width="160"></el-table-column>
        <el-table-column prop="courseName" label="限定授课课程" min-width="180">
          <template #default="scope">
            <strong style="color: #409EFF">{{ scope.row.courseName }}</strong>
          </template>
        </el-table-column>
        <el-table-column prop="courseType" label="类型" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.courseType === '必修' ? 'danger' : 'success'">{{ scope.row.courseType || '其他' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="totalHours" label="计划总学时" align="center"></el-table-column>
        <el-table-column prop="weeklyHours" label="周学时" align="center"></el-table-column>
        <el-table-column label="教学班信息" align="center">
          <template #default="scope">
            <el-button type="text">查看开课班级 (ID:{{scope.row.classesId}})</el-button>
          </template>
        </el-table-column>
        
        <el-table-column label="操作 / 意向申报" align="center" width="180">
          <template #default="scope">
            <!-- 仅教师可见申请按钮 -->
            <el-button v-if="user.role === 'TEACHER'" size="mini" type="primary" icon="el-icon-circle-check" @click="applyForPlan(scope.row)">自报授课</el-button>
            <el-tag v-else type="info">教务查阅模式</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>

  </div>
</template>

<script>
export default {
  name: "TeacherIntentApply",
  data() {
    return {
      tableData: [],
      searchName: '',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      // 拉取所有待安排的教学计划
      this.$request.get('/teachingPlan/selectAll').then(res => {
        if (res.code === '200') {
          // 本地做个简单名子过滤
          let data = res.data || [];
          if (this.searchName) {
            data = data.filter(item => item.courseName && item.courseName.includes(this.searchName))
          }
          this.tableData = data;
        }
      })
    },
    applyForPlan(row) {
      if(this.user.role !== 'TEACHER') {
        return this.$message.warning("仅限教师身份可以发起自报授课");
      }
      this.$confirm(`您确认申请担任【${row.courseName}】(${row.semester})的授课任务吗？`, '自报确认', { type: "warning" }).then(() => {
        // 构建传给后端的DTO
        const dto = {
          planId: row.id,
          teacherId: this.user.id,
          semester: row.semester
        };
        // 调用统一业务校验引擎的接口
        this.$request.post('/teacherIntent/submit', dto).then(res => {
          if (res.code === '200') {
            this.$message.success('申请申报成功！已提交至教研室协调阶段。');
          } else {
            // 如果触发了我们在Java Service里写的异常，例如：MaxCourseLimitException，会走到这里！
            this.$alert(res.msg, '排课系统驳回提示', {
              confirmButtonText: '了解',
              type: 'error' // 弹出红色大X错误提示，完美体现硬性拦截！
            });
          }
        });
      }).catch(() => {});
    }
  }
}
</script>

<style scoped>
.search {
  margin-bottom: 20px;
}
.table {
  background: white;
}
</style>

<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">教务通知</div>
        <div >
          <el-timeline  reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>

      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">考试安排</div>
        <div >
          <el-timeline  reverse slot="reference">
            <el-timeline-item v-for="item in examplans" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.name }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      examplans:[],

    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
    this.$request.get('/examplan/selectAll').then(res => {
      if (res.code === '200') {
        this.examplans = res.data || []
      } else {
        this.$message.error(res.msg)
      }
    })
  }
}
</script>

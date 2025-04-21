<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入课程名称" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="课程名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="type" label="课程类型" show-overflow-tooltip></el-table-column>
        <el-table-column prop="teacherName" label="授课教室" show-overflow-tooltip></el-table-column>
        <el-table-column prop="score" label="学分" show-overflow-tooltip></el-table-column>
        <el-table-column prop="num" label="上课人数" show-overflow-tooltip></el-table-column>
        <el-table-column prop="room" label="上课教室" show-overflow-tooltip></el-table-column>
        <el-table-column prop="week" label="周几" show-overflow-tooltip></el-table-column>
        <el-table-column prop="segment" label="第几大节" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="上课状态" show-overflow-tooltip></el-table-column>

        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button  type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button  type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="form.name" autocmplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="type">
          <el-select v-model="form.type" placholder="请选择类型" style="width: 100%">
            <el-option>label="必修"value="必修"</el-option>
            <el-option>label="选修"value="选修"</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="授课教师" prop="teacherId">
          <el-select v-model="form.teacherId" placholder="请选择教师" style="width: 100%">
            <el-option>v-for="item in teacherData" :label="item.name":value="item.id"</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学分" prop="score" >
          <el-input> v-model="form.score" autocomplete="off"</el-input>
        </el-form-item>
        <el-form-item label="上课人数" prop="num">
          <el-input> v-model="form.num" autocomplete="off"</el-input>
        </el-form-item>
        <el-form-item label="上课教室" prop="room">
          <el-input> v-model="form.room" autocomplete="off"</el-input>
        </el-form-item>
        <el-form-item label="周几" prop="week">
          <el-select v-model="form.week" placholder="请选择日期" style="width: 100%">
            <el-option>label="星期一"value="星期一"</el-option>
            <el-option>label="星期二"value="星期二"</el-option>
            <el-option>label="星期三"value="星期三"</el-option>
            <el-option>label="星期四"value="星期四"</el-option>
            <el-option>label="星期五"value="星期五"</el-option>
            <el-option>label="星期六"value="星期六"</el-option>
            <el-option>label="星期日"value="星期日"</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="第几大节" prop="segment">
          <el-select v-model="form.segment" placholder="请选择日期" style="width: 100%">
            <el-option>label="第一大节(08:00 ~ 09:30)"value="第一大节(08:00 ~ 09:30)"</el-option>
            <el-option>label="第二大节(09:00 ~ 12:00)"value="第二大节(09:00 ~ 12:00)"</el-option>
            <el-option>label="第三大节(14:00 ~ 15:30)"value="第三大节(14:00 ~ 15:30)"</el-option>
            <el-option>label="第四大节(15:40 ~ 17:00)"value="第四大节(15:40 ~ 17:00)"</el-option>
            <el-option>label="第五大节(18:00 ~ 20:00)"value="第五大节(18:00 ~ 20:00)"</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上课状态" prop="status">
          <el-select v-model="form.status" placholder="请选择日期" style="width: 100%">
            <el-option>label="未开课"value="未开课"</el-option>
            <el-option>label="已开课"value="已开课"</el-option>
            <el-option>label="已结课"value="已结课"</el-option>

          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Course",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      username: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
        ]
      },
      ids: [],
      teacherData:[]
    }
  },
  created() {
    this.load(1)
    this.loadTeacher()
  },
  methods: {
    loadTeacher(){
      this.$request.get('/teacher/selectAll').then(res=>{
        if(res.code === '200'){
          this.specialityData = res.data
        } else {
          this.$message.error(res.msq)
        }
      })
    },

    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/course/update' : '/course/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/course/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/course/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/course/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.username = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把头像属性换成上传的图片的链接
      this.form.avatar = response.data
    },
  }
}
</script>

<style scoped>

</style>
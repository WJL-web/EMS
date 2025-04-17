use edu_management;
-- Administrator Information Table
CREATE TABLE admin_info (
    avatar VARCHAR(255) COMMENT '头像',
    email VARCHAR(255) COMMENT '邮箱',
    id INT(11) NOT NULL PRIMARY KEY COMMENT 'ID',
    name VARCHAR(255) COMMENT '姓名',
    password VARCHAR(255) COMMENT '密码',
    phone VARCHAR(255) COMMENT '电话',
    role VARCHAR(255) COMMENT '角色标识',
    username VARCHAR(255) COMMENT '用户名'
);

-- Leave Information Table
CREATE TABLE leave_info (
    content TEXT COMMENT '请假说明',
    day INT(11) COMMENT '请假天数',
    descr VARCHAR(255) COMMENT '审核说明',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    status VARCHAR(255) COMMENT '审核状态',
    student_id INT(11) COMMENT '学生ID',
    time VARCHAR(255) COMMENT '请假时间'
);

-- Attendance Information Table
CREATE TABLE attendance_info (
    course_id INT(11) COMMENT '课程ID',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    status VARCHAR(255) COMMENT '考勤状态',
    student_id INT(11) COMMENT '学生ID',
    teacher_id INT(11) COMMENT '教师ID',
    time VARCHAR(255) COMMENT '上课时间'
);

-- Course Selection Information Table
CREATE TABLE course_selection_info (
    course_id INT(11) COMMENT '课程ID',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    student_id INT(11) COMMENT '学生ID',
    teacher_id INT(11) COMMENT '授课教师'
);

-- Class Information Table
CREATE TABLE class_info (
    content VARCHAR(255) COMMENT '班级描述',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '班级名称',
    speciality_id INT(11) COMMENT '专业ID',
    teacher_id INT(11) COMMENT '教师ID'
);

-- College Information Table
CREATE TABLE college_info (
    content VARCHAR(255) COMMENT '学院介绍',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '学院名称'
);

-- Evaluation Information Table
CREATE TABLE evaluation_info (
    content TEXT COMMENT '评教内容',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '课程名称',
    student VARCHAR(255) COMMENT '评教学生',
    teacher VARCHAR(255) COMMENT '授课教师',
    time VARCHAR(255) COMMENT '评教时间'
);

-- Course Information Table
CREATE TABLE course_info (
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '课程名称',
    num INT(11) COMMENT '上课人数',
    room VARCHAR(255) COMMENT '上课教室',
    score INT(11) COMMENT '课程学分',
    segment VARCHAR(255) COMMENT '第几大节',
    status VARCHAR(255) COMMENT '上课状态',
    teacher_id INT(11) COMMENT '授课教师',
    type VARCHAR(255) COMMENT '课程类型',
    week VARCHAR(255) COMMENT '周几'
);

-- Exam Arrangement Table
CREATE TABLE exam_arrangement (
    content VARCHAR(255) COMMENT '内容',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '标题',
    time VARCHAR(255) COMMENT '发布时间'
);

-- Homework Information Table
CREATE TABLE homework_info (
    content VARCHAR(255) COMMENT '课程说明',
    course_id INT(11) COMMENT '课程ID',
    descr VARCHAR(255) COMMENT '说明',
    file VARCHAR(255) COMMENT '作业文件',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    score VARCHAR(255) COMMENT '打分',
    student_id INT(11) COMMENT '学生ID',
    teacher_id INT(11) COMMENT '教师ID'
);

-- Announcement Information Table
CREATE TABLE announcement_info (
    content VARCHAR(255) COMMENT '内容',
    id INT(11) NOT NULL PRIMARY KEY COMMENT 'ID',
    time VARCHAR(255) COMMENT '创建时间',
    title VARCHAR(255) COMMENT '标题',
    user VARCHAR(255) COMMENT '创建人'
);

-- Classroom Arrangement Table
CREATE TABLE classroom_arrangement (
    content VARCHAR(255) COMMENT '使用说明',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '教室名称',
    num INT(11) COMMENT '容纳人数',
    status VARCHAR(255) COMMENT '教室状态'
);

-- Grade Information Table
CREATE TABLE grade_info (
    course_id INT(11) COMMENT '课程ID',
    exam_score DOUBLE COMMENT '考试分',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    ordinary_score DOUBLE COMMENT '平时分',
    score DOUBLE COMMENT '总成绩',
    student_id INT(11) COMMENT '学生ID',
    teacher_id INT(11) COMMENT '教师ID'
);

-- Major Information Table
CREATE TABLE major_info (
    college_id INT(11) COMMENT '所属学院',
    content VARCHAR(255) COMMENT '专业描述',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '专业名称',
    score INT(11) COMMENT '学分限定'
);

-- Student Information Table
CREATE TABLE student_info (
    avatar VARCHAR(255) COMMENT '头像',
    class_id INT(11) COMMENT '班级ID',
    college_id INT(11) COMMENT '学院ID',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '姓名',
    password VARCHAR(255) COMMENT '密码',
    role VARCHAR(255) COMMENT '角色',
    score INT(11) COMMENT '学分',
    speciality_id INT(11) COMMENT '专业ID',
    username VARCHAR(255) COMMENT '用户名'
);

-- Teacher Information Table
CREATE TABLE teacher_info (
    avatar VARCHAR(255) COMMENT '头像',
    email VARCHAR(255) COMMENT '邮箱',
    id INT(11) NOT NULL PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) COMMENT '姓名',
    password VARCHAR(255) COMMENT '密码',
    phone VARCHAR(255) COMMENT '电话',
    role VARCHAR(255) COMMENT '角色',
    title VARCHAR(255) COMMENT '职称',
    username VARCHAR(255) COMMENT '用户名'
);
commit;
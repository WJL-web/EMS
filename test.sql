-- 1. 扩充教师表 (teacher)
ALTER TABLE `teacher`
ADD COLUMN `title` VARCHAR(50) DEFAULT '助教' COMMENT '职称 (如: 助教, 讲师, 副教授, 教授)',
ADD COLUMN `duty` VARCHAR(50) DEFAULT '普通教师' COMMENT '行政职务 (如: 普通教师, 教研室主任, 学院中层干部)',
ADD COLUMN `has_severe_accident` TINYINT(1) DEFAULT 0 COMMENT '本学期是否有严重教学事故 (0:否, 1:是) - 影响下学期排课资格';

-- 2. 扩充课程表 (course)
ALTER TABLE `course`
ADD COLUMN `is_core` TINYINT(1) DEFAULT 0 COMMENT '是否核心课/主课 (0:否, 1:是) - 用于讲师以上职称拦截校验',
ADD COLUMN `weekly_hours` INT DEFAULT 2 COMMENT '该课程的标准周学时 - 用于计算干部周学时上限';

-- 1. 教学计划表 (teaching_plan) - 由教务处最初下达
CREATE TABLE `teaching_plan` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `semester` VARCHAR(20) NOT NULL COMMENT '学期 (如: 2026-2027-1)',
  `course_id` INT NOT NULL COMMENT '关联课程ID',
  `classes_id` INT NOT NULL COMMENT '关联班级ID (授课对象)',
  `total_hours` INT NOT NULL COMMENT '计划总学时',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING(待学院处理), PUBLISHED(已发布至教师)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) Engine=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教务处教学计划表';

-- 2. 学院附加限制条件表 (college_constraint) - 学院主管针对计划制定的特定要求
CREATE TABLE `college_constraint` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `plan_id` INT NOT NULL COMMENT '关联的具体教学计划ID',
  `min_title_req` VARCHAR(50) DEFAULT '助教' COMMENT '最低职称要求',
  `max_classes_allowed` INT DEFAULT 2 COMMENT '允许合班的最大数量',
  `weekly_hours_limit` INT COMMENT '特定周学时上限/下限限制',
  FOREIGN KEY (`plan_id`) REFERENCES `teaching_plan`(`id`) ON DELETE CASCADE
) Engine=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院附加排课限制表';

-- 3. 教师自报与审批流转表 (teacher_intent) - 承载从教师自报到教研室、学院审批的整个流转生命周期
CREATE TABLE `teacher_intent` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `plan_id` INT NOT NULL COMMENT '关联的教学计划ID',
  `teacher_id` INT NOT NULL COMMENT '申报教师ID',
  `approval_status` VARCHAR(30) DEFAULT 'SUBMITTED' 
      COMMENT '审批流状态: SUBMITTED(已自报), COORDINATED(教研室协调通过), APPROVED(学院审批通过), REJECTED(驳回)',
  `reject_reason` VARCHAR(255) COMMENT '驳回理由',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_plan_teacher` (`plan_id`, `teacher_id`), -- 防重复申报
  FOREIGN KEY (`plan_id`) REFERENCES `teaching_plan`(`id`) ON DELETE CASCADE
) Engine=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师授课申报及审批流表';

-- 4. 最终教学任务书表 (teaching_task) - 教务处最终生成的定稿任务，供前端查课表用
CREATE TABLE `teaching_task` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `semester` VARCHAR(20) NOT NULL COMMENT '学期',
  `plan_id` INT NOT NULL COMMENT '关联计划',
  `teacher_id` INT NOT NULL COMMENT '最终任课教师ID',
  `course_id` INT NOT NULL COMMENT '课程ID',
  `classes_id` INT NOT NULL COMMENT '班级ID',
  `task_status` VARCHAR(20) DEFAULT 'CONFIRMED' COMMENT '任务状态: CONFIRMED(已确认下达)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) Engine=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='最终教师教学任务书';
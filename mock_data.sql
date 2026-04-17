USE `xm-education_manger`;

-- 由于之前的几次终端插入因为引号转义失败，这回把SQL保存成单独的文件安全执行

-- 先保证表有该字段(不使用 IF NOT EXISTS 因为老版MySQL ALTER TABLE ADD不支持这种写法)
-- 如果表本身已建出来了这几行可能报错，报错就跳过继续往下走

-- 补齐当前旧老师数据
UPDATE `teacher` SET `duty` = '普通教师', `has_severe_accident` = 0;

-- 插入用于测试四大约束拦截的各类【教师角色】样例 (密码均为123)
-- 样例1：正常老师，用来测报限满2门
INSERT INTO `teacher` (`username`, `password`, `name`, `role`, `title`, `duty`, `has_severe_accident`) VALUES 
('t_normal', MD5('123'), '张讲师', 'TEACHER', '讲师', '普通教师', 0);

-- 样例2：助教老师，用来测“低于讲师职称不能报核心课”
INSERT INTO `teacher` (`username`, `password`, `name`, `role`, `title`, `duty`, `has_severe_accident`) VALUES 
('t_zhujiao', MD5('123'), '李助教', 'TEACHER', '助教', '普通教师', 0);

-- 样例3：中层干部老师，用来测“课时不能大于4个”
INSERT INTO `teacher` (`username`, `password`, `name`, `role`, `title`, `duty`, `has_severe_accident`) VALUES 
('t_boss', MD5('123'), '王中层', 'TEACHER', '副教授', '学院中层干部', 0);

-- 样例4：教职事故人员，用来测“教学事故一票否决”
INSERT INTO `teacher` (`username`, `password`, `name`, `role`, `title`, `duty`, `has_severe_accident`) VALUES 
('t_bad', MD5('123'), '黑名单', 'TEACHER', '教授', '普通教师', 1);

-- 构造几门虚假的待申报教学计划放入池中
INSERT INTO `teaching_plan` (`semester`, `course_id`, `classes_id`, `total_hours`, `status`) VALUES 
('2026-秋季', 1, 1, 64, 'PENDING'),
('2026-秋季', 2, 1, 32, 'PENDING');
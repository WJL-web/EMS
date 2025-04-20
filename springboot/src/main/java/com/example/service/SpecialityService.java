package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Speciality;
import com.example.exception.CustomException;
import com.example.mapper.SpecialityMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 专业信息业务处理
 **/
@Service
public class SpecialityService {

    @Resource
    private SpecialityMapper specialityMapper;

    /**
     * 新增
     */
    public void add(Speciality speciality) {
        specialityMapper.insert(speciality);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        specialityMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            specialityMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Speciality speciality) {
        specialityMapper.updateById(speciality);
    }

    /**
     * 根据ID查询
     */
    public Speciality selectById(Integer id) {
        return specialityMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Speciality> selectAll(Speciality speciality) {
        return specialityMapper.selectAll(speciality);
    }

    /**
     * 分页查询
     */
    public PageInfo<Speciality> selectPage(Speciality speciality, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Speciality> list = specialityMapper.selectAll(speciality);
        return PageInfo.of(list);
    }



}

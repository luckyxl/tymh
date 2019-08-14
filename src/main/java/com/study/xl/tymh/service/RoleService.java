package com.study.xl.tymh.service;

import com.study.xl.tymh.entity.Role;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 14:41
 * @Version 1.0
 **/
public interface RoleService {

    List<Role> getRoleByUserId(Integer userId);
}

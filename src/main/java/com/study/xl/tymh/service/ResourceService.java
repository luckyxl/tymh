package com.study.xl.tymh.service;

import com.study.xl.tymh.entity.Resource;

import java.util.List;

/**
 * @ClassName ResourceService
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 14:34
 * @Version 1.0
 **/
public interface ResourceService {

    List<Resource> getResourceByUserId(Integer userId);

    List<Resource> getResourceByRoleIdList(List<Integer> roleIdList);
}

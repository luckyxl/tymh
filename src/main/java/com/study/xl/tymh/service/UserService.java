package com.study.xl.tymh.service;

import com.study.xl.tymh.common.ServiceResult;
import com.study.xl.tymh.entity.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 14:01
 * @Version 1.0
 **/
public interface UserService {

    User getUserByName(String username);

    ServiceResult addUser(User user);


}

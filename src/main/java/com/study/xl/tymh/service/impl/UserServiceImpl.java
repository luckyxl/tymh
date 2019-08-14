package com.study.xl.tymh.service.impl;

import com.study.xl.tymh.common.ServiceResult;
import com.study.xl.tymh.common.ServiceResultEnum;
import com.study.xl.tymh.dao.UserDao;
import com.study.xl.tymh.entity.User;
import com.study.xl.tymh.service.UserService;
import com.study.xl.tymh.util.Digests;
import com.study.xl.tymh.util.Encodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 15:22
 * @Version 1.0
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getUserByName(String username) {
        return userDao.selectByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult addUser(User user) {
        User userFromDb = getUserByName(user.getUsername());
        if (userFromDb != null) {
            return ServiceResult.fail(ServiceResultEnum.BUSINESS_ERROR.getCode(), "用户名重复");
        }
        byte[] salt = Digests.generateSalt(8);
        user.setSalt(Encodes.encodeHex(salt));
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, 1024);
        user.setPassword(Encodes.encodeHex(hashPassword));
        userDao.insertSelective(user);
        return ServiceResult.success();
    }
}

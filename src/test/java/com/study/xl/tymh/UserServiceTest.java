package com.study.xl.tymh;

import com.study.xl.tymh.common.ServiceResult;
import com.study.xl.tymh.entity.User;
import com.study.xl.tymh.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceTest
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 15:31
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TymhApplication.class)
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void addUserTest(){
        User userInsert = new User();
        userInsert.setUsername("lisi");
        userInsert.setNickname("李四");
        userInsert.setPassword("123456");
        ServiceResult serviceResult = userService.addUser(userInsert);
        assert serviceResult.isSuccess();
    }
}

package com.study.xl.tymh.controller;

import com.study.xl.tymh.common.ServiceResult;
import com.study.xl.tymh.common.ServiceResultEnum;
import com.study.xl.tymh.config.shiro.AuthRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author xule
 * @Date 2019/8/8 14:35
 * @Version 1.0
 **/
@Controller
@RequestMapping("login")
public class LoginController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("login")
    @ResponseBody
    public ServiceResult login(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return ServiceResult.fail(ServiceResultEnum.PARAM_ERROR.getCode(), "用户名为空");
        }
        if (StringUtils.isBlank(password)) {
            return ServiceResult.fail(ServiceResultEnum.PARAM_ERROR.getCode(), "密码为空");
        }
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        String error = null;
        try {
            user.login(token);
            if (user.isAuthenticated()) {
                ServiceResult success = ServiceResult.success();
                success.put("url", "/login/index");
                return success;
            }
        }catch (IncorrectCredentialsException e) {
            error = "登录密码错误.";
        } catch (ExcessiveAttemptsException e) {
            error = "登录失败次数过多";
        } catch (LockedAccountException e) {
            error = "帐号已被锁定.";
        } catch (DisabledAccountException e) {
            error = "帐号已被禁用.";
        } catch (ExpiredCredentialsException e) {
            error = "帐号已过期.";
        } catch (UnknownAccountException e) {
            error = "帐号不存在";
        } catch (UnauthorizedException e) {
            error = "您没有得到相应的授权！";
        }
        return ServiceResult.fail(ServiceResultEnum.BUSINESS_ERROR.getCode(), error);
    }
    @GetMapping("toLogin")
    public String toLogin() {
        return "login";
    }
    @GetMapping("index")
    @RequiresPermissions("index")
    public String index() {
        return "index";
    }
    @GetMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        AuthRealm.ShiroUser user = (AuthRealm.ShiroUser) subject.getPrincipal();
        subject.logout();
        stringRedisTemplate.delete("shiro:cache:com.study.xl.tymh.config.shiro.AuthRealm.authorizationCache:" + user.getUser().getId());
        stringRedisTemplate.delete("shiro:cache:shiro-activeSessionCache:" + user.getUser().getUsername());
        return "login";
    }





}

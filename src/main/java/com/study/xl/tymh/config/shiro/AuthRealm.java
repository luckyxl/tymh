package com.study.xl.tymh.config.shiro;

import com.google.common.base.Objects;
import com.study.xl.tymh.entity.Role;
import com.study.xl.tymh.entity.User;
import com.study.xl.tymh.service.ResourceService;
import com.study.xl.tymh.service.RoleService;
import com.study.xl.tymh.service.UserService;
import com.study.xl.tymh.util.Encodes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName AuthRealm
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 14:27
 * @Version 1.0
 **/
@Component
public class AuthRealm extends AuthorizingRealm {

    @Resource
    @Lazy
    private UserService userService;
    @Resource
    @Lazy
    private ResourceService resourceService;
    @Resource
    @Lazy
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        User user = shiroUser.getUser();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.getRoleByUserId(user.getId());
        if (roleList != null && !roleList.isEmpty()) {
            info.setRoles(roleList.stream().map(Role::getRolename).collect(Collectors.toSet()));
        }
        List<com.study.xl.tymh.entity.Resource> resourceList = resourceService.getResourceByUserId(user.getId());
        if (resourceList != null && !resourceList.isEmpty()) {
            info.setStringPermissions(resourceList.stream().map(com.study.xl.tymh.entity.Resource::getPermission).collect(Collectors.toSet()));
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String)token.getPrincipal();
        User user = userService.getUserByName(username);
        //没找到帐号
        if(user == null) {
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(user),
                //密码
                user.getPassword(),
                //salt
                ByteSource.Util.bytes(Encodes.decodeHex(user.getSalt())),
                //realm name
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {

        private static final long serialVersionUID = -8120826686741820295L;
        private User user;

        private Integer id;




        public User getUser() {
            return user;
        }

        public Integer getId() {
            return id;
        }

        public ShiroUser() {
        }

        public ShiroUser(User user) {
            this.user = user;
            this.id = user.getId();
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return user == null ? null : user.getNickname();
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(user == null ? null : user.getUsername());
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (user == null) {
                return other.user == null;
            } else {
                return user.getUsername().equals(other.getUser().getUsername());
            }
        }
    }
}

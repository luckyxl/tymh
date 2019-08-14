package com.study.xl.tymh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.study.xl.tymh.dao.RoleDao;
import com.study.xl.tymh.entity.Role;
import com.study.xl.tymh.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 15:58
 * @Version 1.0
 **/
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<Role> getRoleByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        String roleListStr = stringRedisTemplate.opsForValue().get("role:" + userId);
        if (StringUtils.isNotBlank(roleListStr)) {
            return JSONArray.parseArray(roleListStr, Role.class);
        }
        List<Role> roleList = roleDao.listByUserId(userId);
        if (roleList != null && !roleList.isEmpty()) {
            stringRedisTemplate.opsForValue().set("role:" + userId, JSONArray.toJSONString(roleList));
        }
        return roleList;
    }
}

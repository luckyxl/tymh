package com.study.xl.tymh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.study.xl.tymh.dao.ResourceDao;
import com.study.xl.tymh.entity.Resource;
import com.study.xl.tymh.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ResourceServiceImpl
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 15:58
 * @Version 1.0
 **/
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @javax.annotation.Resource
    private ResourceDao resourceDao;
    @javax.annotation.Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Resource> getResourceByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        String resourceListStr = stringRedisTemplate.opsForValue().get("resource:" + userId);
        if (StringUtils.isNotBlank(resourceListStr)) {
            return JSONArray.parseArray(resourceListStr, Resource.class);
        }
        List<Resource> resourceList = resourceDao.listByUserId(userId);
        if (resourceList != null && !resourceList.isEmpty()) {
            stringRedisTemplate.opsForValue().set("resource:" + userId, JSONArray.toJSONString(resourceList));
        }
        return resourceList;
    }

    @Override
    public List<Resource> getResourceByRoleIdList(List<Integer> roleIdList) {
        return resourceDao.listByRoleIdList(roleIdList);
    }

}

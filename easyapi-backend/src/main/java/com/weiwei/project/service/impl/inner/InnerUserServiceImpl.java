package com.weiwei.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weiwei.common.model.entity.User;
import com.weiwei.common.service.InnerUserService;
import com.weiwei.project.common.ErrorCode;
import com.weiwei.project.exception.BusinessException;
import com.weiwei.project.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现类
 *
 * @author weiwei
 */
@DubboService(version = "1.0.0")
@Slf4j
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(User::getAccessKey, accessKey);
        return userMapper.selectOne(LambdaQueryWrapper);
    }
}





package com.weiwei.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weiwei.common.model.entity.UserInterfaceInfo;
import com.weiwei.common.service.InnerUserInterfaceInfoService;
import com.weiwei.project.common.ErrorCode;
import com.weiwei.project.exception.BusinessException;
import com.weiwei.project.mapper.UserInterfaceInfoMapper;
import com.weiwei.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author 86177
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2023-06-15 16:41:49
 */
@DubboService(version = "1.0.0")
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    @Override
    public UserInterfaceInfo getById(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        queryWrapper.eq("userId", userId);
        return userInterfaceInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public int addDefault(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        userInterfaceInfo.setUserId(userId);
        userInterfaceInfo.setLeftNum(100);  //默认100次
        return userInterfaceInfoMapper.insert(userInterfaceInfo);
    }
}





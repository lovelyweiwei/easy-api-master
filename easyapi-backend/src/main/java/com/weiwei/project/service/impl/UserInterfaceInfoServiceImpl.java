package com.weiwei.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiwei.common.model.entity.UserInterfaceInfo;
import com.weiwei.project.common.ErrorCode;
import com.weiwei.project.exception.BusinessException;
import com.weiwei.project.mapper.UserInterfaceInfoMapper;
import com.weiwei.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 86177
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-06-15 16:41:49
*/
@Service("userInterfaceInfo")
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //校验
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        boolean update;
        //加锁防止高并发
        synchronized (this){
            updateWrapper.eq("interfaceInfoId", interfaceInfoId);
            updateWrapper.eq("userId", userId);
            updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
            update = this.update(updateWrapper);
        }
        return update;
    }

}





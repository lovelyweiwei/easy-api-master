package com.weiwei.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.weiwei.common.model.entity.UserInterfaceInfo;

/**
* @author 86177
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-06-15 16:41:49
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验
     *
     * @param userInterfaceInfo
     * @param add 是否为创建校验
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     *
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

}

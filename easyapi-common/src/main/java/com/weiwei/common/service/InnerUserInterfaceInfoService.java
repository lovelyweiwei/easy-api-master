package com.weiwei.common.service;

import com.weiwei.common.model.entity.UserInterfaceInfo;

/**
* @author 86177
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-06-15 16:41:49
*/
public interface InnerUserInterfaceInfoService{

    /**
     * 调用接口统计
     *
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 获取剩余调用次数
     *
     * @return
     */
    UserInterfaceInfo getById(long interfaceInfoId, long userId);

    /**
     *  添加调用接口默认数据
     *
     * @return
     */
    int addDefault(long interfaceInfoId, long userId);

}

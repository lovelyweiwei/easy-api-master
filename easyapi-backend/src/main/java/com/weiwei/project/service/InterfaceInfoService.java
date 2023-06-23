package com.weiwei.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weiwei.common.model.entity.InterfaceInfo;

/**
* @author 86177
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-06-10 15:50:55
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}


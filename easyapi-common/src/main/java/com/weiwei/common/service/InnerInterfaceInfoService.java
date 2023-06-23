package com.weiwei.common.service;


import com.weiwei.common.model.entity.InterfaceInfo;


/**
* @author 86177
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-06-10 15:50:55
*/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在
     * @param url
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String url, String method);
}


package com.weiwei.common.service;


import com.weiwei.common.model.entity.User;

/**
 * 用户服务
 *
 * @author weiwei
 */
public interface InnerUserService {
    /**
     * 从数据库中查找是否给用户分配密钥
     *
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}

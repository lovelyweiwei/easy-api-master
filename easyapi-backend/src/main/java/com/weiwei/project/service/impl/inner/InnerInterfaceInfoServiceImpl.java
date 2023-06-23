package com.weiwei.project.service.impl.inner;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weiwei.common.model.entity.InterfaceInfo;
import com.weiwei.common.service.InnerInterfaceInfoService;
import com.weiwei.project.common.ErrorCode;
import com.weiwei.project.exception.BusinessException;
import com.weiwei.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 86177
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-06-10 15:50:55
*/
@DubboService(version = "1.0.0")
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(InterfaceInfo::getUrl, url)
                .eq(InterfaceInfo::getMethod, method);
        return interfaceInfoMapper.selectOne(LambdaQueryWrapper);
    }
}





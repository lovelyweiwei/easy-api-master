package com.weiwei.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weiwei.common.model.entity.UserInterfaceInfo;

import java.util.List;


/**
* @author 86177
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2023-06-15 16:41:49
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}





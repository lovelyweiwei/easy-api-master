package com.weiwei.project.model.vo;

import com.weiwei.common.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口信息封装视图
 *
 * @Author weiwei
 * @Date 2023/6/16 21:37
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo implements Serializable {

    /**
     * 调用次数
     */
    private Integer totalNum;

}

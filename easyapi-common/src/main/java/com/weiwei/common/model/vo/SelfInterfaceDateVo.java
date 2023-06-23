package com.weiwei.common.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SelfInterfaceDateVo
 * @Description TODO
 * @Author lish
 * @Date 2023/5/8 2:35
 */
@Data
public class SelfInterfaceDateVo implements Serializable {

    private String interfaceName;

    private int totalNum;

    private int leftNum;
}

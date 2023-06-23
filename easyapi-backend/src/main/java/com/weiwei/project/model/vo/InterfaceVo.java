package com.weiwei.project.model.vo;

import com.weiwei.common.model.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName InterfaceVo
 * @Description TODO
 * @Author lish
 * @Date 2023/4/27 9:32
 */
@Data
public class InterfaceVo extends InterfaceInfo implements Serializable {

    /*调用次数*/
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}

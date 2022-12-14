package com.gxj.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {


    private static final long serialVersionUID = 573124234657491160L;
    private Integer code;
    private String msg;
    private T data;
}

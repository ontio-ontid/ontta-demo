package com.onchain.tademo;

import lombok.Data;

/**
 * @author zhouq
 * @version 1.0
 * @date 2018/4/9
 */
@Data
public class ONTTAException extends RuntimeException {

    private Integer code;

    private String msg;

    private Object result;

    public ONTTAException() {
        super();
    }

    public ONTTAException(String msg){
        super(msg);
    }

    public ONTTAException(Integer code, String msg, Object result) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.result = result;
    }


}

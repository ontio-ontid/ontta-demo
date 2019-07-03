package com.onchain.tademo.model;

/**
 * @author zhouq
 * @version 1.0
 * @date 2017/12/11
 */
public enum ErrorInfo {

    /**
     * success
     */
    SUCCESS(0, "SUCCESS", "成功"),

    /**
     * param error
     */
    PARAM_ERROR(61001, "FAIL, param error.", "参数错误"),

    /**
     * param error
     */
    ONTID_NOT_EXIST(61002, "FAIL, ONTID not exist.", "ONTID不存在"),

    /**
     * verify failed
     */
    SIG_VERIFY_FAILED(62006, "FAIL, verify signature fail.", "验签失败"),

    /**
     * verify failed
     */
    VERIFY_FAILED(62001,"FAIL, verify fail.", "身份校验失败"),

    /**
     * inner error
     */
    INNER_ERROR(63001, "FAIL, inner error.", "内部异常");

    private Integer errorCode;
    private String errorDescEN;
    private String errorDescCN;

    ErrorInfo(Integer errorCode, String errorDescEN, String errorDescCN) {
        this.errorCode = errorCode;
        this.errorDescEN = errorDescEN;
        this.errorDescCN = errorDescCN;
    }

    public Integer code() {
        return errorCode;
    }

    public String descEN() {
        return errorDescEN;
    }

    public String descCN() {
        return errorDescCN;
    }

}

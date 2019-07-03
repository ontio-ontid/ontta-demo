package com.onchain.tademo.model;

/**
 * @author zhouq
 * @version 1.0
 * @date 2017/12/27
 */
public class ConstantParam {

    //可信声明注销类型--存证合约
    public static final String ATTESTCONTRACT = "AttestContract";

    public static final String ISSUECLAIM_FAILED_DES = "node network communication error";

    public static final String SFP_VERIFYFAILED_REASON_OVERTIME = "verification overtime";

    public static final String SFP_VERIFYFAILED_EXCEPTION = "CA communicate failed";



    public static final String ST_VERIFYFAILED_REASON_ERROR01 = "图片认证与个人信息不一致";

    public static final String ST_VERIFYFAILED_REASON_EXCEPTION = "CA网络异常";

    /**
     * shuftipro认证结果标识
     */
    public static final String SFP_VERIFYRESULT_SUCCEED = "verification.accepted";

    public static final String SFP_VERIFYRESULT_FAILED = "verification.declined";


    public static final String CN = "CN";

    //商汤认证异常的特定的DB里的requestId
    public static final String ST_VERIFY_EXCEPTION_REQUESTID = "1111111111";

    /**
     * kafka
     */
    public static final String KAFKA_AUTHENTICATION_KYCINFO_TOPIC = "authentication.kycinfo";

    public static final String KAFKA_AUTHENTICATION_KYCRESULT_TOPIC = "authentication.kycresult";

    public static final String REDIS_AUTHENTICATION_KYCINFO_SUFFIX = "_kycinfo";

    public static final String REDIS_AUTHENTICATION_KYCRESULT_SUFFIX = "_kycresult";

}

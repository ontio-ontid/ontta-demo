package com.onchain.tademo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @author zhouq
 * @date 2017/11/23
 */

@Service("ConfigParam")
public class ParamsConfig {


    /**
     * 节点信息
     */
    @Value("${ontology.restfulUrl}")
    public String ONT_RESTFUL_URL;

    @Value("${claimRecord.codeHash}")
    public String CLAIMRECORD_CODEHASH;


    /**
     * claim存证资产账户=收款账户
     */
    @Value("${account.address}")
    public String ACCOUNT_ADDRESS;

    @Value("${account.wif}")
    public String ACCOUNT_WIF;


    /**
     * 商汤 身份账户
     */
    @Value("${sensetime.identity.ontId}")
    public String ST_IDENTITY_ONTID;

    @Value("${sensetime.identity.password}")
    public String ST_IDENTITY_PASSWORD;

    @Value("${sensetime.identity.salt}")
    public String ST_IDENTITY_SALT;

}
package com.onchain.tademo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SensetimeDto {

    private Integer id;

    /**
     * 用户的ONT ID
     */
    private String ownerOntid;

    /**
     * 认证请求方的ONT ID
     */
    private String arOntid;

    /**
     * 认证需求方的认证编号
     */
    private String authId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证编号
     */
    private String idNumber;

    /**
     * 提交时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0:待认证 1:认证成功 2:认证失败
     */
    private Integer status;

}

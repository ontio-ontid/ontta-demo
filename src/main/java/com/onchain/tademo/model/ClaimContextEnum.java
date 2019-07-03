package com.onchain.tademo.model;

/**
 * @author zhouq
 * @version 1.0
 * @date 2019/6/22
 */
public enum ClaimContextEnum {

    SENSETIME("claim:sensetime_authentication", "id_card", "我的商汤实名认证"),

    SHUFTIPRO_ID("claim:sfp_idcard_authentication", "id_card", "My Shuftipro IdCard Authentication"),

    SHUFTIPRO_PP("claim:sfp_passport_authentication", "passport", "My Shuftipro Passport Authentication"),

    SHUFTIPRO_DL("claim:sfp_dl_authentication", "driverlicense", "My Shuftipro DriverLicense Authentication");

    private String context;

    private String docType;

    private String des;

    ClaimContextEnum(String context, String docType, String des) {
        this.context = context;
        this.docType = docType;
        this.des = des;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static String getDesByContext(String claimContext){
        String des = "";
        if(ClaimContextEnum.SHUFTIPRO_DL.getContext().equals(claimContext)){
            des = ClaimContextEnum.SHUFTIPRO_DL.getDes();
        }else if(ClaimContextEnum.SHUFTIPRO_ID.getContext().equals(claimContext)){
            des = ClaimContextEnum.SHUFTIPRO_ID.getDes();
        }else if(ClaimContextEnum.SHUFTIPRO_PP.getContext().equals(claimContext)){
            des = ClaimContextEnum.SHUFTIPRO_PP.getDes();
        }else if(ClaimContextEnum.SENSETIME.getContext().equals(claimContext)){
            des = ClaimContextEnum.SENSETIME.getDes();
        }
        return des;
    }

}

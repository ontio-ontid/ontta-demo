package com.onchain.tademo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onchain.tademo.model.ClaimContextEnum;
import com.onchain.tademo.model.SensetimeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.LinkedHashMap;

@Slf4j
@Service
public class AuthenticationService {

    private ClaimService claimService;

    @Autowired
    AuthenticationService(ClaimService claimService) {
        this.claimService = claimService;
    }

    /**
     * 处理商汤认证成功的记录
     * @param senseTimeDto
     */
    public void hanldeSenseTimeVerifySucceedResult(SensetimeDto senseTimeDto) {

        JSONObject claimMetadataObj = makeSensetimeClaimMetadata(senseTimeDto);
        //签发claim
        String claimStr = "";
        try {
            claimStr = claimService.issueClaim(claimMetadataObj);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        // 存证交易hash
        String txHash = JSON.parseObject(new String(Base64.getDecoder().decode(claimStr.split("\\.")[3]))).getString("TxnHash");
    }

    /**
     * 构造sensetime认证成功的claim元数据
     * @param sensetimeDto
     * @return
     */
    public JSONObject makeSensetimeClaimMetadata(SensetimeDto sensetimeDto) {

        JSONObject clmObj = new JSONObject(new LinkedHashMap<>());
        clmObj.put("身份证号", sensetimeDto.getIdNumber());
        clmObj.put("姓名", sensetimeDto.getName());

        JSONObject claimMetadata = new JSONObject();
        claimMetadata.put("PersonalData", clmObj);
        claimMetadata.put("ClaimDesc", ClaimContextEnum.SENSETIME.getDes());
        claimMetadata.put("OwnerOntId", sensetimeDto.getOwnerOntid());
        claimMetadata.put("ClaimContext", ClaimContextEnum.SENSETIME.getContext());
        return claimMetadata;
    }

}

package com.onchain.tademo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onchain.tademo.Helper;
import com.onchain.tademo.ONTTAException;
import com.onchain.tademo.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ClaimService {

    private ParamsConfig paramsConfig;

    @Autowired
    ClaimService(ParamsConfig paramsConfig) {
        this.paramsConfig = paramsConfig;
    }

    private OntologySDKService sdkService;

    private synchronized void initSDK() {
        if (sdkService == null) {
            sdkService = OntologySDKService.getInstance(paramsConfig);
        }
    }

    /**
     * 签发可信声明
     * @param reqObj
     * @return
     * @throws Exception
     */
    public String issueClaim(JSONObject reqObj) throws Exception {

        String ownerOntId = reqObj.getString("OwnerOntId");
        String claimContext = reqObj.getString("ClaimContext");
        Map claimInfo = reqObj.getObject("PersonalData", Map.class);

        // CA的ontid身份相关参数，可以在配置文件中进行配置
        IssuerIdentityDto issuerIdentityDto = new IssuerIdentityDto();
        issuerIdentityDto.setOntid(paramsConfig.ST_IDENTITY_ONTID);
        issuerIdentityDto.setPassword(paramsConfig.ST_IDENTITY_PASSWORD);
        issuerIdentityDto.setSalt(paramsConfig.ST_IDENTITY_SALT);
        issuerIdentityDto.setName(TAInfoEnum.SENSETIME.myName());

        // 创建不带merkleproof的可信声明
        initSDK();
        claimInfo.put("IssuerName", issuerIdentityDto.getName());
        String withoutProofClaimStr = createWithoutProofClaim(sdkService, issuerIdentityDto, ownerOntId, claimContext, claimInfo);
        log.info("create withoutProofClaimStr result:{}", withoutProofClaimStr);

        // 创建claim失败
        if (Helper.isEmptyOrNull(withoutProofClaimStr)) {
            log.error("create withoutProofClaimStr failed...");
            throw new ONTTAException(ErrorInfo.INNER_ERROR.code(), ErrorInfo.INNER_ERROR.descEN(), false);
        }

        // 创建带merkleproof的可信声明，并发送存证交易到链上
        String completeClaim = createCompleteClaim(sdkService, issuerIdentityDto, withoutProofClaimStr, ownerOntId);

        // 最终完整的可信声明，以便于传给ONTPass和用户
        return completeClaim;
    }


    /**
     * 创建不带merkleproof的不完整可信声明
     * @param sdkService
     * @param issuerIdentityDto
     * @param ownerOntId
     * @param context
     * @param claimInfo
     * @return
     */
    private String createWithoutProofClaim(OntologySDKService sdkService,
                                           IssuerIdentityDto issuerIdentityDto,
                                           String ownerOntId,
                                           String context,
                                           Map<String, Object> claimInfo) {

        Map<String, Object> metaDataMap = new HashMap<>();
        metaDataMap.put("Issuer", issuerIdentityDto.getOntid());
        metaDataMap.put("Subject", ownerOntId);

        Map<String, Object> clvMap = new HashMap<String, Object>();
        clvMap.put("typ", ConstantParam.ATTESTCONTRACT);
        clvMap.put("addr", paramsConfig.CLAIMRECORD_CODEHASH);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        long expires = c.getTimeInMillis() / 1000L;

        //create claim without merkleproof
        String withoutProofClaimStr = sdkService.createClaim(issuerIdentityDto.getOntid(),
                                                             issuerIdentityDto.getPassword(),
                                                             issuerIdentityDto.getSalt(),
                                                             context,
                                                             claimInfo,
                                                             metaDataMap,
                                                             clvMap,
                                                             expires);
        return withoutProofClaimStr;
    }

    /**
     * 生成带merkleproof的完整可信声明
     * @param sdkService
     * @param issuerIdentityDto
     * @param withoutProofClaimStr
     * @param ownerOntId
     * @return
     * @throws Exception
     */
    public String createCompleteClaim(OntologySDKService sdkService,
                                      IssuerIdentityDto issuerIdentityDto,
                                      String withoutProofClaimStr,
                                      String ownerOntId) throws Exception {

        log.info("####{} run...", Helper.currentMethod());

        JSONObject payloadObj = JSON.parseObject(new String(Base64.getDecoder().decode(withoutProofClaimStr.split("\\.")[1])));
        String claimId = payloadObj.getString("jti");

        //发送存证交易
        String recordTxhash = sdkService.sendClaimRecordTx(issuerIdentityDto.getOntid(), issuerIdentityDto.getPassword(), issuerIdentityDto.getSalt(), ownerOntId, claimId);
        log.info("sendClaimRecordTx txhash:{}", recordTxhash);

        //等待区块链出块
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //根据存证交易hash获取merkleproof
        HashMap<String, String> tempProof = new HashMap<>();
        tempProof.put("TxnHash", recordTxhash);
        String proofStr = JSON.toJSONString(tempProof);
        try {
            Map proofMap = sdkService.getMerkleProofByTxhash(recordTxhash);
            proofStr = JSONObject.toJSONString(proofMap);
        } catch (Exception e) {
            log.error("sdkService.getMerkleProofByTxhash error,ownerOntId:{},txhash:{}...", ownerOntId, recordTxhash, e);
        }
        String base64ProofStr = Base64.getEncoder().encodeToString(proofStr.getBytes());
        //添加merkleproof，构造完整的可信声明
        String completeClaimStr = withoutProofClaimStr + "\\." + base64ProofStr;
        log.info("complete-claim:{}", completeClaimStr);
        return completeClaimStr;
    }


}

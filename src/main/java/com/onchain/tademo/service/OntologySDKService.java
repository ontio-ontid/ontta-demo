package com.onchain.tademo.service;


import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.onchain.tademo.model.ParamsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

/**
 * @author zhouq
 * @date 2018/03/01
 */
@Component
@Slf4j
public class OntologySDKService {

    private static OntologySDKService instance = null;

    private ParamsConfig paramsConfig;

    @Autowired
    void ontologySDKService(ParamsConfig paramsConfig) {
        this.paramsConfig = paramsConfig;
    }

    static synchronized OntologySDKService getInstance(ParamsConfig param) {
        if (instance == null) {
            instance = new OntologySDKService(param);
        }
        return instance;
    }

    public OntologySDKService(ParamsConfig param) {
        this.paramsConfig = param;
    }

    /**
     * 创建不带merkleproof的可信声明
     * @param issuerOntId
     * @param ontIdPassword
     * @param salt
     * @param context
     * @param claimInfo
     * @param metaData
     * @param clvMap
     * @param expires
     * @return
     */
    public String createClaim(String issuerOntId,
                              String ontIdPassword,
                              String salt,
                              String context,
                              Map claimInfo,
                              Map metaData,
                              Map clvMap,
                              long expires) {
        try {
            OntSdk ontSdk = getOntSdk();
            String claimStr = ontSdk.nativevm().ontId().createOntIdClaim(issuerOntId,
                                                                         ontIdPassword,
                                                                         Base64.getDecoder().decode(salt),
                                                                         context,
                                                                         claimInfo,
                                                                         metaData,
                                                                         clvMap,
                                                                         expires);
            return claimStr;
        } catch (Exception e) {
            log.error("createClaim error...", e);
        }
        return "";
    }

    public boolean verifyClaim(String claim) {

        try {
            OntSdk ontSdk = getOntSdk();
            return ontSdk.nativevm().ontId().verifyOntIdClaim(claim);
        } catch (Exception e) {
            log.error("verifyClaim error...", e);
        }
        return false;
    }


    /**
     * 根据交易hash，获取merkleproof
     * @param txnHash
     * @return
     * @throws Exception
     */
    public Map getMerkleProofByTxhash(String txnHash) throws Exception {
        int tryTime = 1;
        int maxTryTime = 10;
        OntSdk ontSdk = getOntSdk();
        while (true) {
            try {
                Map proof = (Map) ontSdk.nativevm().ontId().getMerkleProof(txnHash);
                int blockHeight = (Integer) ((Map) proof.get("Proof")).get("BlockHeight");
                if (blockHeight == 0 && tryTime <= maxTryTime) {
                    log.error("get proof error... blockheight = 0. wait 1 second");
                    Thread.sleep(1 * 1000);
                    tryTime++;
                    continue;
                } else if (tryTime > maxTryTime) {
                    log.error("get proof error... blockheight = 0 already wait {} seconds", maxTryTime);
                    return null;
                }
                Map map = (Map) proof.get("Proof");
                map.put("ContractAddr", paramsConfig.CLAIMRECORD_CODEHASH);
                //proof.put("Proof", map);
                return map;
            } catch (Exception e) {
                log.error("getMerkleProofByTxhash:{} errorstr:{}...", txnHash, e.getMessage(), e);
                JSONObject errObj = JSONObject.parseObject(e.getMessage());
                if (errObj.getInteger("Error") == 42002 && tryTime <= maxTryTime) {
                    log.error("get proof error... wait 1 second ");
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    tryTime++;
                    continue;
                }
                return null;
            }
        }

    }

    /**
     * 发送claim存证交易
     * @param issuerOntId
     * @param ontIdPassword
     * @param salt
     * @param subjectOntId
     * @param claimId
     * @return
     */
    public String sendClaimRecordTx(String issuerOntId,
                                    String ontIdPassword,
                                    String salt,
                                    String subjectOntId,
                                    String claimId) {
        try {
            OntSdk ontSdk = getOntSdk();

            // 存证交易的付款账号，可以在配置文件中进行配置WIF
            Account acct = new Account(Account.getPrivateKeyFromWIF(paramsConfig.ACCOUNT_WIF), ontSdk.defaultSignScheme);

            // 往链上发送存证交易
            String txnHash = ontSdk.neovm().claimRecord().sendCommit(issuerOntId, ontIdPassword, Base64.getDecoder().decode(salt), subjectOntId, claimId, acct, 20000, 500);
            return txnHash;
        } catch (Exception e) {
            log.error("sendClaimRecordTx error...", e);
        }
        return "";
    }


    private OntSdk getOntSdk() throws Exception {
        OntSdk wm = OntSdk.getInstance();
        wm.setRestful(paramsConfig.ONT_RESTFUL_URL);  // 设置区块链节点的restful接口（主网和测试网的地址不一样。）
        wm.openWalletFile("identity.json");  // 钱包文件
        wm.neovm().claimRecord().setContractAddress(paramsConfig.CLAIMRECORD_CODEHASH);  // 存证交易的智能合约地址（主网和测试网的不一样。）
        return wm;
    }

}
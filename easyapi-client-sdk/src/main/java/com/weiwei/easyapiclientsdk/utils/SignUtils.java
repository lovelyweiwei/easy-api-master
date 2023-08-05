package com.weiwei.easyapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @Author weiwei
 * @Date 2023/6/16 18:21
 * @Version 1.0
 */
public class SignUtils {
    /**
     * 生成签名
     * @param
     * @param secretKey
     * @return
     */
    public static String getSign(String body, String secretKey, String nonce, String timestamp) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey + "." + nonce + "." + timestamp;
        System.out.println(content);
        return md5.digestHex(content);
    }
}

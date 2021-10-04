package com.lp.gateway;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

@Slf4j
public class ConfigEncryptUtils {

    public static void main(String[] args) {
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        //生成秘钥的公钥
        config.setPassword("gateway-public-key");
        //应用配置
        encryptor.setConfig(config);
        //明文密码
        String plaintext = "microservices";
        //加密
        String cipherText = encryptor.encrypt(plaintext);
        log.info("{}加密后:{}",plaintext,cipherText);
        //解密 过程
        String pText = encryptor.decrypt(cipherText);
        log.info("{}解密后:{}",cipherText,plaintext);
    }
}

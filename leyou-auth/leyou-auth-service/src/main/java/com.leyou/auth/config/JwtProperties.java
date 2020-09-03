package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Mr.JK
 * @create 2020-05-27  19:52
 */
@ConfigurationProperties(prefix = "ly.jwt")
@Data
public class JwtProperties {

    private String secret;
    private String pubKeyPath;
    private String priKeyPath;
    private int expire;
    private String cookieName;

    private PublicKey publicKey;    // 公钥
    private PrivateKey privateKey;  // 私钥

    //类一旦加载，就一个读取公钥和私钥
    @PostConstruct
    public void init() throws Exception {
        //公钥私钥如果不存在，先生产
        File pubPath = new File(pubKeyPath);
        File priPath = new File(priKeyPath);
        if (!pubPath.exists() || !priPath.exists()){
            //生产公钥和私钥
            RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);

        }

        //读取公钥和私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

}

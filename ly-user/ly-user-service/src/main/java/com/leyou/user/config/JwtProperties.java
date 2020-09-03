package com.leyou.user.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author Mr.JK
 * @create 2020-05-27  19:52
 */
@ConfigurationProperties(prefix = "ly.jwt")
@Data
public class JwtProperties {

    private String pubKeyPath;
    private String cookieName;

    private PublicKey publicKey;    // 公钥

    //类一旦加载，就一个读取公钥和私钥
    @PostConstruct
    public void init() throws Exception {

        //读取公钥和私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }

}

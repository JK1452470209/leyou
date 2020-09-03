package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-16  11:46
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {

    private String baseUrl;
    private List<String> allowTypes;
}

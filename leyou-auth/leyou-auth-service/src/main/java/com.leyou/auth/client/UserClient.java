package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mr.JK
 * @create 2020-05-27  20:43
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}

package com.leyou.user.service;

import com.leyou.user.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author Mr.JK
 * @create 2020-05-27  12:45
 */

public interface UserService {

    Boolean checkData(String data, Integer type);

    void sendCode(String phone);

    void register(User user, String code);

    User queryUserByUsernameAndPassword(String username, String password);
}

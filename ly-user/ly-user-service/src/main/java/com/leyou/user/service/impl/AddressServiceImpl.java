package com.leyou.user.service.impl;

import com.leyou.auth.entity.UserInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.interceptor.UserInterceptor;
import com.leyou.user.mapper.AddressMapper;
import com.leyou.user.pojo.Address;
import com.leyou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-06-06  12:10
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public List<Address> queryAllAddressByUserId() {
        Long userId = getUserId();

        Address address = new Address();
        address.setUserId(userId);

        List<Address> addressList = addressMapper.select(address);


        return addressList;
    }

    @Override
    public void saveAddress(Address address) {
        Long userId = getUserId();
        address.setUserId(userId);

        int count = addressMapper.insert(address);
        if (count != 1){
            throw new LyException(ExceptionEnum.ADDRESS_SAVE_ERROR);
        }
    }

    @Override
    public Address queryAddressById(Long id) {

        Address address = addressMapper.selectByPrimaryKey(id);
        if (address == null) {
            throw new LyException(ExceptionEnum.ADDRESS_QUERY_ERROR);
        }

        return address;
    }

    @Override
    public void updateAddress(Address address) {
        int count = addressMapper.updateByPrimaryKey(address);
        if (count == 0){
            throw new LyException(ExceptionEnum.ADDRESS_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = new Address();
        address.setId(id);
        int count = addressMapper.delete(address);
        if (count != 1){
            throw new LyException(ExceptionEnum.ADDRESS_DELETE_ERROR);
        }
    }

    private Long getUserId() {
        UserInfo user = UserInterceptor.getUser();
        return user.getId();
    }


}

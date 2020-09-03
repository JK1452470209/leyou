package com.leyou.user.service;

import com.leyou.user.pojo.Address;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-06-06  12:10
 */
public interface AddressService {
    List<Address> queryAllAddressByUserId();

    void saveAddress(Address address);

    Address queryAddressById(Long id);

    void updateAddress(Address address);

    void deleteAddress(Long id);
}

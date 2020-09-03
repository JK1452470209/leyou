package com.leyou.user.web;

import com.leyou.user.pojo.Address;
import com.leyou.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-06-06  12:11
 */
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> queryAllAddressByUserId(){
        return ResponseEntity.ok(addressService.queryAllAddressByUserId());
    }


    @PostMapping
    public ResponseEntity<Void> saveAddress(@RequestBody Address address){
        addressService.saveAddress(address);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Address> queryAddressById(@PathVariable("id") Long id){
        return ResponseEntity.ok(addressService.queryAddressById(id));
    }

    @PutMapping
    public ResponseEntity<Void> updateAddress(@RequestBody Address address){
        addressService.updateAddress(address);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id){
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

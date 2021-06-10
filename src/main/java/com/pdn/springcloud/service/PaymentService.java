package com.pdn.springcloud.service;


import com.pdn.springcloud.entities.Payment;

public interface PaymentService {
    int insert(Payment record);
    Payment selectByPrimaryKey(Long id);
}

package com.pdn.springcloud.service.impl;


import com.pdn.springcloud.entities.Payment;
import com.pdn.springcloud.mapper.PaymentMapper;
import com.pdn.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentMapper mapper;
    @Override
    public int insert(Payment record) {
        return mapper.insert(record);
    }

    @Override
    public Payment selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }
}

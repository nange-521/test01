package com.pdn.springcloud.controller;



import com.pdn.springcloud.entities.CommonResult;
import com.pdn.springcloud.entities.Payment;
import com.pdn.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService service;
    @Value("${server.port}")
    private  String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/insert")
    public CommonResult insert(@RequestBody Payment payment){
        System.out.println(1);
        int result = service.insert(payment);
        log.info("*****插入结果："+result);
        if (result>0)
            return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,result);
        else
            return new CommonResult(444,"插入数据库失败",null);
    }

    @GetMapping(value = "/payment/select/{id}")
    public CommonResult select(@PathVariable("id") Long id){
        Payment payment = service.selectByPrimaryKey(id);
        log.info("*****插入结果："+payment);
        if (payment!=null)
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        else
            return new CommonResult(444,"查询失败",null);
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        services.forEach(service-> System.out.println("service:"+service));
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        instances.forEach(instance-> System.out.println(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri()));
        return discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping(value = "/payment/timout")
    public String timeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}

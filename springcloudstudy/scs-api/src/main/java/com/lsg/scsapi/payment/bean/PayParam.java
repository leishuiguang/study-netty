package com.lsg.scsapi.payment.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayParam {
    private String uid;
    private Integer money;
}

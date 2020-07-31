package com.bill.ChatroomAdvanced.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {

    private String status;
    private String crid;
    private String sender;
    private String sendTime;
    private String message;
}

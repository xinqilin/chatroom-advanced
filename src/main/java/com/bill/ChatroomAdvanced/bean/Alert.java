package com.bill.ChatroomAdvanced.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

    private String type;
    private String message;
    private String alertSender;
    private String alertTime;
}

package com.bill.ChatroomAdvanced.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
public class ChatroomContact {

    private String status;
    private String roomid;
    private String[] uids;
}

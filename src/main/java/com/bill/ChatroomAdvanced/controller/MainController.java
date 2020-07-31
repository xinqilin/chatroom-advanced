package com.bill.ChatroomAdvanced.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bill.ChatroomAdvanced.bean.Alert;
import com.bill.ChatroomAdvanced.bean.ChatroomContact;
import com.bill.ChatroomAdvanced.bean.Message;
import com.bill.ChatroomAdvanced.exception.AlertException;
import com.bill.ChatroomAdvanced.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	@Autowired
    MainService mainService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @MessageMapping("/connect")
    @SendTo("/topic/ChatroomContact")
    public ChatroomContact connectWs(Message Message) throws AlertException {
        String mrid = Message.getCrid();
        String uid = Message.getSender();
        mainService.connectChatRoom(mrid, uid);

        //alert user join
        Alert alert = mainService.createAlert("info", String.format("User[%s] join talk!", uid));
        messagingTemplate.convertAndSend("/topic/alert", alert);
        //message user join
        Message.setSender("sysinfo");
        Message.setMessage(uid + " join talk!");
        Message = mainService.sendMessage(Message);
        messagingTemplate.convertAndSend("/topic/Message", Message);

        return mainService.getAllContactByCrid(mrid);
    }

    @MessageMapping("/disconnect")
    @SendTo("/topic/ChatroomContact")
    public ChatroomContact disconnectWs(Message Message) throws AlertException {
        String mrid = Message.getCrid();
        String uid = Message.getSender();
        mainService.disconnectChatRoom(mrid, uid);

        //alert user exit
        Alert alert = mainService.createAlert("warning", String.format("User[%s] exit talk!", uid));
        messagingTemplate.convertAndSend("/topic/alert", alert);

        //message user exit
        Message.setSender("sysdanger");
        Message.setMessage(uid + " exit talk!");
        Message = mainService.sendMessage(Message);
        messagingTemplate.convertAndSend("/topic/Message", Message);

        return mainService.getAllContactByCrid(mrid);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/Message")
    public Message[] sendMrMessage(Message Message) throws AlertException {
        return new Message[] {mainService.sendMessage(Message)};
    }

    @MessageExceptionHandler
    @SendTo("/topic/alert")
    public Alert handleException(Throwable exception) {
        return ((AlertException)exception).getMsg();
    }

    @GetMapping(value = "/chatroom")
    public String viewDefaultMeetingRoomPage(Model model) {
        model.addAttribute("crid", mainService.getDefaultCrid());
        return "chatroom";
    }

    @GetMapping(value = "/chatroom/{crid}")
    public String viewMeetingRoomPage(Model model, @PathVariable("crid") String crid) {
        model.addAttribute("crid", crid);
        return "chatroom";
    }
}

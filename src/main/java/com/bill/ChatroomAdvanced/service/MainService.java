package com.bill.ChatroomAdvanced.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bill.ChatroomAdvanced.bean.Alert;
import com.bill.ChatroomAdvanced.bean.ChatroomContact;
import com.bill.ChatroomAdvanced.bean.Message;
import com.bill.ChatroomAdvanced.exception.AlertException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MainService {

	
	 private Set<String> cridSet = new HashSet<String>();
	    private Map<String, Set<String>> crIdSet = new HashMap();
	    private String defaultCrid = "default";
	    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	    @PostConstruct
	    public void initialize() {
	        cridSet.add(defaultCrid);
	        crIdSet.put(defaultCrid, new LinkedHashSet<>());
	    }

	    public String getDefaultCrid() {
	        return defaultCrid;
	    }

	    public Message connectChatRoom(String mrid, String userid) {
	        if (StringUtils.isEmpty(mrid)) {
	            mrid = defaultCrid;
	        }
	        Message Message = new Message();
	        if (!cridSet.contains(mrid)) {
	            Message.setStatus("fail");
	            Message.setSender("System");
	            Message.setSendTime(df.format(new Date()));
	            Message.setMessage(String.format("The Meeting Room[{}] not exist!", mrid));
	        } else if(crIdSet.get(mrid).contains(userid)) {
	            Message.setStatus("fail");
	            Message.setSender("System");
	            Message.setSendTime(df.format(new Date()));
	            Message.setMessage(String.format("The user id[{}] existed in Meeting Room[{}]!", userid, mrid));
	        } else {
	            Set<String> ids = crIdSet.get(mrid);
	            ids.add(userid);
	            Message.setStatus("success");
	            Message.setSender("System");
	            Message.setSendTime(df.format(new Date()));
	            Message.setMessage(String.format("Welcome to Meeting Room[{}]!", mrid));
	            log.info(String.format("connect ids size:%d", ids.size()));
	        }
	        return Message;
	    }

	    public Message disconnectChatRoom(String crid, String userid) {
	        if (StringUtils.isEmpty(crid)) {
	            crid = defaultCrid;
	        }
	        Message Message = new Message();
	        if (!cridSet.contains(crid)) {
	            Message.setStatus("fail");
	            Message.setSender("System");
	            Message.setSendTime(df.format(new Date()));
	            Message.setMessage(String.format("The Meeting Room[{}] not exist!", crid));
	        } else if(!crIdSet.get(crid).contains(userid)) {
	            Message.setStatus("fail");
	            Message.setSender("System");
	            Message.setSendTime(df.format(new Date()));
	            Message.setMessage(String.format("The user id[{}] not existed in Meeting Room[{}]!", userid, crid));
	        } else {
	            Set<String> ids = crIdSet.get(crid);
	            ids.remove(userid);
	            Message.setStatus("success");
	            Message.setSender("System");
	            Message.setSendTime(df.format(new Date()));
	            Message.setMessage(String.format("Bye!"));
	            log.info(String.format("disconnect ids size:%d", ids.size()));
	        }
	        return Message;
	    }

	    public ChatroomContact getAllContactByCrid(String crid) {
	        ChatroomContact ChatroomContact = new ChatroomContact();
	        ChatroomContact.setStatus("success");
	        ChatroomContact.setRoomid(crid);;
	        Set<String> ids = crIdSet.get(crid);
	        ChatroomContact.setUids(ids.toArray(new String[ids.size()]));
	        return ChatroomContact;
	    }

	    public Message sendMessage(Message Message) throws AlertException {
	        String uid = Message.getSender();
	        if (uid.equals("error")) {
	            // simulate exception and send alert to client
	            Alert mrAlert = createAlert("danger", "System error, please try again!");
	            throw new AlertException(mrAlert);
	        }

	        String crid = getDefaultCrid();
	        if (!StringUtils.isEmpty(Message.getCrid())) {
	            crid = Message.getCrid();
	        }

	        Message.setCrid(crid);
	        Message.setSendTime(df.format(new Date()));
	        Message.setStatus("Success");
	        log.info("service get message:{}", Message.getMessage());
	        return Message;
	    }

	    public Alert createAlert(String type, String msg) {
	        Alert alert = new Alert();
	        alert.setType(type);
	        alert.setMessage(msg);
	        alert.setAlertSender("sys");
	        alert.setAlertTime(df.format(new Date()));
	        return alert;
	    }
}

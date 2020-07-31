package com.bill.ChatroomAdvanced.config;

import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
		//給client 連接的url
		registry.addEndpoint("/bill-chatroom").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
		//被訂閱頻道的前綴
		registry.enableSimpleBroker("/topic");
		//傳送訊息的前綴
        registry.setApplicationDestinationPrefixes("/bill/room1");
	}

}

package com.xullent.servlet;

import java.util.Date;

import com.xullent.bean.TextMessage;
import com.xullent.util.MessageUtil;

public class Test {
	public static void main(String[] args) {
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName("xullent");
		textMessage.setToUserName("sun");
		textMessage.setMsgType("text");
		textMessage.setCreateTime(new Date().getTime() );
		textMessage.setContent("您发送的内容是 ：" + "123");
		String message = MessageUtil.textMessageToXml(textMessage);
		System.out.println(message);
	}
}

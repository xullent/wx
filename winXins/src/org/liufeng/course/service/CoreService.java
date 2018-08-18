package org.liufeng.course.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.liufeng.course.message.resp.TextMessage;
import org.liufeng.course.util.MessageUtil;
import org.liufeng.course.util.MySQLUtil;
import org.liufeng.course.util.TransUtil;
import org.liufeng.course.util.TulingApiUtil;
import org.liufeng.course.util.UnicodeUtil;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-12-02
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "发送任意文本，我们开始聊天吧！";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息创建时间
			String createTime = requestMap.get("CreateTime");
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if(MessageUtil.isNumeric(content)){
					respContent = Integer.parseInt(content) + 1 + "";
				}else if(content.contains("翻译")) {
					if(UnicodeUtil.isContainZh(content.split("翻译")[1])){
						respContent = TransUtil.getTransEnResult(content.split("翻译")[1]);
					}else{
						respContent = TransUtil.getTransZhResult(content.split("翻译")[1]);
					}
	            }else{
//	            	respContent = ChatService.chat(fromUserName, createTime, content);
	            	respContent = TulingApiUtil.getTulingResult(content);
	            }
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				respContent = MySQLUtil.getJoke();
			}
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setContent(respContent);
			// 将文本消息对象转换成xml
			respXml = MessageUtil.messageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
	
	public static void main(String[] args) {
		String respContent = ChatService.chat("123", null, "笑话");
		System.out.println(respContent);
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName("123");
		textMessage.setFromUserName("123");
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(respContent);
	}
}
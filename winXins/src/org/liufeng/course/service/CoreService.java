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
 * ���ķ�����
 * 
 * @author liufeng
 * @date 2013-12-02
 */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml��ʽ����Ϣ����
		String respXml = null;
		// Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "���������ı������ǿ�ʼ����ɣ�";
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");
			// ��Ϣ����ʱ��
			String createTime = requestMap.get("CreateTime");
			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if(MessageUtil.isNumeric(content)){
					respContent = Integer.parseInt(content) + 1 + "";
				}else if(content.contains("����")) {
					if(UnicodeUtil.isContainZh(content.split("����")[1])){
						respContent = TransUtil.getTransEnResult(content.split("����")[1]);
					}else{
						respContent = TransUtil.getTransZhResult(content.split("����")[1]);
					}
	            }else{
//	            	respContent = ChatService.chat(fromUserName, createTime, content);
	            	respContent = TulingApiUtil.getTulingResult(content);
	            }
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				respContent = MySQLUtil.getJoke();
			}
			// �ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setContent(respContent);
			// ���ı���Ϣ����ת����xml
			respXml = MessageUtil.messageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
	
	public static void main(String[] args) {
		String respContent = ChatService.chat("123", null, "Ц��");
		System.out.println(respContent);
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName("123");
		textMessage.setFromUserName("123");
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(respContent);
	}
}
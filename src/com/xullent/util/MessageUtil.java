package com.xullent.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.xullent.bean.TextMessage;

public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";

	public static final String EVENT_SUB = "subscribe";
	public static final String EVENT_UNSUB = "unsubscribe";
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";

	/**
	 * xml转为map集合
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws DocumentException, IOException {
		Map<String, String> map = new HashMap<String, String>();

		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();

		return map;

	}

	/**
	 * 消息对象转化为xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream stream = new XStream();
		stream.alias("xml", textMessage.getClass());
		return stream.toXML(textMessage);
	}

	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText(String fromUserName) {
		StringBuffer sb = new StringBuffer();
		sb.append(fromUserName + "您好 :" + "\n");
		sb.append("谢谢您的关注" + "\n\n");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();

		sb.append("敬请期待历史上的今天 ");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();

		sb.append("敬请期待幽默笑话");
		return sb.toString();
	}

	public static  boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println(MessageUtil.isNumeric("123"));;
	}
}

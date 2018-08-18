package com.xullent.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.xullent.bean.TextMessage;
import com.xullent.util.CheckUtil;
import com.xullent.util.MessageUtil;

public class WinXinServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
        try {
            Map<String,String> map = MessageUtil.xmlToMap(req);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");    

            String message = null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType))
            {	
            	if(MessageUtil.isNumeric(content)){
            		content = Integer.parseInt(content) + 1 + "";
            	}else{
            		content = "收到您的信息";
            	}
                message = MessageUtil.initText(toUserName, fromUserName, content);

            }else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
            	String mycontent = "你回复的是語音，暫時不能處理 " ;
                message = MessageUtil.initText(toUserName, fromUserName, mycontent);
            }  else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType = map.get("Event");
                if(MessageUtil.EVENT_SUB.equals(eventType)){
                    String mycontent = MessageUtil.menuText(fromUserName);
                    message = MessageUtil.initText(toUserName, fromUserName, mycontent);

                }
            }   
            out.print(message);

        } catch (Exception e) {
            // TODO: handle exception
            out.close();
        }   
	}
}

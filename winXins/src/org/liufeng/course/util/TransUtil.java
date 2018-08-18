package org.liufeng.course.util;

import java.io.UnsupportedEncodingException;


public class TransUtil {
	
	private static final String APP_ID = "20180313000135235";//账号
	private static final String SECURITY_KEY = "2_ug8SB9LpOjmpl6TMyZ";//密码
	private static TransApi api = new TransApi(APP_ID, SECURITY_KEY);
	/**
	 * 中文转英文
	 * @param content
	 * @return
	 */
	public static String getTransEnResult(String content)  {
		String content_result = "";
		try {
			content_result = api.getTransResult(content, "auto", "en");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
         return getCommon(content_result);
	}
	/**
	 * 英文转中文
	 * @param content
	 * @return
	 */
	public static String getTransZhResult(String content){
		String content_result = "";
		try {
			content_result = api.getTransResult(content, "auto", "zh");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
         return UnicodeUtil.decode(getCommon(content_result));
	}
	private static String getCommon(String str){
		 char [] content_result_temp = str.toCharArray();
		 str = "";//清空数据
         for(int i = content_result_temp.length-5;;i--) {
             if(content_result_temp[i] == '"') {
                 break;
             }
             str = content_result_temp[i] + str;
         }
         return str;
	}
	public static void main(String[] args) throws Exception {
//		String content = "翻译 你好";
//		if(content.contains("翻译")){
//			System.out.println(TransUtil.getTransResult(content.split("翻译")[1]));
//		}
		String str = "翻译 hello你";
//		if(str.contains("翻译")){
//			System.out.println(TransUtil.getTransZhResult(str.split("翻译")[1]));
//		}
		
		  System.out.println(UnicodeUtil.isContainZh(str));
	}
}

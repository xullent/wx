package org.liufeng.course.util;

import java.io.UnsupportedEncodingException;


public class TransUtil {
	
	private static final String APP_ID = "20180313000135235";//�˺�
	private static final String SECURITY_KEY = "2_ug8SB9LpOjmpl6TMyZ";//����
	private static TransApi api = new TransApi(APP_ID, SECURITY_KEY);
	/**
	 * ����תӢ��
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
	 * Ӣ��ת����
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
		 str = "";//�������
         for(int i = content_result_temp.length-5;;i--) {
             if(content_result_temp[i] == '"') {
                 break;
             }
             str = content_result_temp[i] + str;
         }
         return str;
	}
	public static void main(String[] args) throws Exception {
//		String content = "���� ���";
//		if(content.contains("����")){
//			System.out.println(TransUtil.getTransResult(content.split("����")[1]));
//		}
		String str = "���� hello��";
//		if(str.contains("����")){
//			System.out.println(TransUtil.getTransZhResult(str.split("����")[1]));
//		}
		
		  System.out.println(UnicodeUtil.isContainZh(str));
	}
}

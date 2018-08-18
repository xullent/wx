package org.liufeng.course.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * ����ͼ�������api�ӿڣ���ȡ���ܻظ�����
 * 
 * @author pamchen-1
 * 
 */
public class TulingApiUtil {
	private static String APIKEY = "6ced4c40f79e42c59f45ef1715b8bef4";

	public static String getTulingResult(String question) throws IOException {
		String INFO = URLEncoder.encode(question, "utf-8");
		String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.connect();

		// ȡ������������ʹ��Reader��ȡ
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();

		// �Ͽ�����
		connection.disconnect();
		char[] content_result_temp = sb.toString().toCharArray();
		String str = "";
		for (int i = content_result_temp.length - 3; i >= 0; i--) {
			if (content_result_temp[i] == '"') {
				break;
			}
			str = content_result_temp[i] + str;
		}
		return str;
	}

	public static void main(String[] args) throws IOException {
		String str = TulingApiUtil.getTulingResult("���");
//		char[] content_result_temp = str.toCharArray();
//		str = "";
//		for (int i = content_result_temp.length - 3; i >= 0; i--) {
//			if (content_result_temp[i] == '"') {
//				break;
//			}
//			str = content_result_temp[i] + str;
//		}
		System.out.println(str);
	}
}

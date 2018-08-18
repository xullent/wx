package org.liufeng.course.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ÿ��λ����һ���ֽ�
 * 
 * @author zhouzhian
 */
public class UnicodeUtil {

	/**
	 * �ַ��������Unicode����
	 */
	public static String encode(String src) throws Exception {
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < src.length(); i++) {
			c = src.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128)
				str.append("\\u" + strHex);
			else
				str.append("\\u00" + strHex); // ��λ��ǰ�油00
		}
		return str.toString();
	}

	/**
	 * Unicode������ַ���
	 * 
	 * @param src
	 * @return
	 */
	public static String decode(String src) {
		int t = src.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = src.substring(i * 6, (i + 1) * 6); // ÿ6λ����һ���ֽ�
			// ��λ��Ҫ����00��ת
			String s1 = s.substring(2, 4) + "00";
			// ��λֱ��ת
			String s2 = s.substring(4);
			// ��16���Ƶ�stringתΪint
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// ��intת��Ϊ�ַ�
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}
	/**
	 * �ж��ַ������Ƿ�������
	 * @param str
	 * @return
	 */
	public static boolean isContainZh(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
}

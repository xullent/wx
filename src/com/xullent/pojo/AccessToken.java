package com.xullent.pojo;
/**
 * ΢��ͨ�ýӿ�ƾ֤
 * @author xullent
 */
public class AccessToken {
	private String token;     // ��ȡ����ƾ֤
	private int expiresIn; //ƾ֤��Чʱ�䣬��λ��
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}

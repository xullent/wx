package com.xullent.menu;
/**
 * �Ӳ˵��� :û���Ӳ˵��Ĳ˵���п����Ƕ����˵��Ҳ�п����ǲ��������˵���һ���˵���
 * @author xullent
 */
public class CommonButton extends Button  {
	private String type;  //�˵�����Ӧ�������ͣ�view��ʾ��ҳ���ͣ�click��ʾ�������
	private String key;   //click�ȵ�����ͱ���	�˵�KEYֵ��������Ϣ�ӿ����ͣ�������128�ֽ�
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}

package com.xullent.menu;
/**
 * 子菜单项 :没有子菜单的菜单项，有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。
 * @author xullent
 */
public class CommonButton extends Button  {
	private String type;  //菜单的响应动作类型，view表示网页类型，click表示点击类型
	private String key;   //click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
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

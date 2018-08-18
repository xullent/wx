package org.liufeng.course.pojo;

/**
 * �ʴ�֪ʶmodel
 * 
 * @author liufeng
 * @date 2013-12-01
 */
public class Knowledge {
	private int id;
	private String question;
	private String answer;
	private int category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Knowledge [id=" + id + ", question=" + question + ", answer=" + answer + ", category=" + category + "]";
	}
	
}
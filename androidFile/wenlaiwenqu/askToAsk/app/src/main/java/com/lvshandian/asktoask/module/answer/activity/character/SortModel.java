package com.lvshandian.asktoask.module.answer.activity.character;

public class SortModel {

	private String name;   //��ʾ�����
	private String sortLetters;  //��ʾ���ƴ��������ĸ
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	@Override
	public String toString() {
		return "SortModel [name=" + name + ", sortLetters=" + sortLetters + "]";
	}
	
}

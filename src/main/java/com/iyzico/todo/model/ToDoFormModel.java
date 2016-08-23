package com.iyzico.todo.model;

import java.sql.Date;

import javax.validation.constraints.Size;

public class ToDoFormModel {
	
	@Size(min=4, max=35)
	private String title;
	
	@Size(min=4, max=35)
	private String subTitle;
	
	private String content;
	private Date startDate;
	private Date endDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
}

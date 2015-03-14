package com.tqe.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class EvalTable {
	
	private Integer id;
	private String title;
	private String note;
	private List<EvalItem> itemList = new ArrayList<EvalItem>(); 
	private List<EvalTableItem> tableItemList =new ArrayList<EvalTableItem>();
	private List<EvalItem> questionList =new ArrayList<EvalItem>();
	private Date createDate;	//创建时间
	private String jsonString;
	
	
	public static class EvalItem {
		private String context;
		private String ans;
		public EvalItem() {
		}

		public EvalItem(String context, String ans) {
			super();
			this.context = context;
			this.ans = ans;
		}
		
		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}
		public String getAns() {
			return ans;
		}
		public void setAns(String ans) {
			this.ans = ans;
		}
		
	}
	
	public static class EvalTableItem {
		private String context;
		private String level;
		private String ans;
		
		public EvalTableItem() {
			super();
		}

		public EvalTableItem(String context, String level, String ans) {
			super();
			this.context = context;
			this.level = level;
			this.ans = ans;
		}
		
		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public String getAns() {
			return ans;
		}
		public void setAns(String ans) {
			this.ans = ans;
		}
		
	}

	
	public EvalTable() {
		super();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<EvalItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<EvalItem> itemList) {
		this.itemList = itemList;
	}
	public List<EvalTableItem> getTableItemList() {
		return tableItemList;
	}
	public void setTableItemList(List<EvalTableItem> tableItemList) {
		this.tableItemList = tableItemList;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<EvalItem> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<EvalItem> questionList) {
		this.questionList = questionList;
	}
	
	public EvalTable json2Object(){
		EvalTable tem = JSON.parseObject(this.getJsonString(), EvalTable.class);
		this.setItemList(tem.getItemList());
		this.setQuestionList(tem.getQuestionList());
		this.setTableItemList(tem.getTableItemList());
		return this;
	}
	
}

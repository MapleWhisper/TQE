package com.tqe.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * 评教表
 */
public class EvalTable {
	
	private Integer id;
    private String type;    //评教表的类型
	private String title;	//评教表的标题
	private String note;	//评教须知
	private List<EvalItem> itemList = new ArrayList<EvalItem>();	//表单信息
	private List<EvalTableItem> tableItemList =new ArrayList<EvalTableItem>();	//打分表
	private List<EvalItem> questionList =new ArrayList<EvalItem>();	//问题表
	private Date createDate;	//创建时间
	private String jsonString;	//序列化表

	private String score;	//得分
	private String level;	//评价结果等级

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




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
			if(ans!=null && ans.length()>255){	//截取 数据库不能存放字数大于 255 的 答案
				ans = ans.substring(255);
			}
			this.ans = ans;
		}

        @Override
        public String toString() {
            return "EvalItem{" +
                    "context='" + context + '\'' +
                    ", ans='" + ans + '\'' +
                    '}';
        }
    }
	
	public static class EvalTableItem {


		private String context;     //文本
		private String level;       //等级

		private Integer ans;         //回答(得分)


        private Integer avgScore;   //平均得分
        private List<Integer> scoreLevelCnts;   //得分等级统计
        private Integer maxLevel;   //最大分
        private Integer percent;     //平均得分百分比



		public EvalTableItem() {
			super();
		}

		public EvalTableItem(String context, String level, Integer ans) {
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
		public Integer getAns() {
			return ans;
		}
		public void setAns(Integer ans) {
			this.ans = ans;
		}

        public Integer getAvgScore() {
            return avgScore;
        }

        public void setAvgScore(Integer avgScore) {
            this.avgScore = avgScore;
        }

        public List<Integer> getScoreLevelCnts() {
            return scoreLevelCnts;
        }

        public void setScoreLevelCnts(List<Integer> scoreLevelCnts) {
            this.scoreLevelCnts = scoreLevelCnts;
        }

        @Override
        public String toString() {
            return "EvalTableItem{" +
                    "context='" + context + '\'' +
                    ", level='" + level + '\'' +
                    ", ans=" + ans +
                    ", avgScore=" + avgScore +
                    ", scoreLevelCnts=" + scoreLevelCnts +
                    '}';
        }

        public Integer getMaxLevel() {
            return maxLevel;
        }

        public void setMaxLevel(Integer maxLevel) {
            this.maxLevel = maxLevel;
        }

        public Integer getPercent() {
            return percent;
        }

        public void setPercent(Integer percent) {
            this.percent = percent;
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

    /**
     * 将评教表中的JSON内容序列化成内存对象
     */
	public EvalTable json2Object(){
		EvalTable tem = JSON.parseObject(this.getJsonString(), EvalTable.class);
		this.setItemList(tem.getItemList());
		this.setQuestionList(tem.getQuestionList());
		this.setTableItemList(tem.getTableItemList());
		return this;
	}

    public static EvalTable json2Object(String jsonString){
        if(StringUtils.isNoneBlank(jsonString)){
            return JSON.parseObject(jsonString, EvalTable.class);
        }
        return null;
    }

	public void setAns(EvalTable src,EvalTable ans){
		for(int i=0;i<src.getItemList().size();i++){
			src.getItemList().get(i).setAns(ans.getItemList().get(i).getAns());
		}
		for(int i=0;i<src.getTableItemList().size();i++){
			src.getTableItemList().get(i).setAns(ans.getTableItemList().get(i).getAns());
		}
		for(int i=0;i<src.getQuestionList().size();i++){
			src.getQuestionList().get(i).setAns(ans.getQuestionList().get(i).getAns());
		}
	}





	public String getScore() {
		return score;
	}


	public void setScore(String score) {
		this.score = score;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "EvalTable{" +
				"id=" + id +
				", title='" + title + '\'' +
				", note='" + note + '\'' +
				", itemList=" + itemList +
				", tableItemList=" + tableItemList +
				", questionList=" + questionList +
				", createDate=" + createDate +
				", jsonString='" + jsonString + '\'' +
				", score='" + score + '\'' +
				", level='" + level + '\'' +
				'}';
	}
}

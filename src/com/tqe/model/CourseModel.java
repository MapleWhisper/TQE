package com.tqe.model;

import java.util.ArrayList;
import java.util.List;

import com.tqe.po.LeaTable;
import com.tqe.po.StuTable;
import com.tqe.po.TeaStuTable;
import com.tqe.po.TeaTable;

public class CourseModel {
	private List<Batches> batchesList = new ArrayList<Batches>();
	
	public static class Batches {
		
		private com.tqe.po.Batches batches;		//季度
		private  List<StuTable> stuTableList;	//学生评教表
		private  List<TeaTable> teaTableList;	//教师评教表
		private  List<LeaTable> leaTableList;	//领导评教表

		public  List<StuTable> getStuTableList() {
			return stuTableList;
		}

		public  void setStuTableList(List<StuTable> stuTableList) {
			this.stuTableList = stuTableList;
		}

		public com.tqe.po.Batches getBatches() {
			return batches;
		}

		public void setBatches(com.tqe.po.Batches batches) {
			this.batches = batches;
		}

		public List<TeaTable> getTeaTableList() {
			return teaTableList;
		}

		public void setTeaTableList(List<TeaTable> teaTableList) {
			this.teaTableList = teaTableList;
		}

		public List<LeaTable> getLeaTableList() {
			return leaTableList;
		}

		public void setLeaTableList(List<LeaTable> leaTableList) {
			this.leaTableList = leaTableList;
		}
		
		
	}

	public List<Batches> getBatchesList() {
		return batchesList;
	}

	public void setBatchesList(List<Batches> batchesList) {
		this.batchesList = batchesList;
	}
	


	
	
}

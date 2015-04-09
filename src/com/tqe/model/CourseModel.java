package com.tqe.model;

import java.util.ArrayList;
import java.util.List;

import com.tqe.po.Course;
import com.tqe.po.StuTable;

public class CourseModel {
	private List<Batches> batchesList = new ArrayList<Batches>();
	
	public static class Batches {
		private com.tqe.po.Batches batches;
		private  List<StuTable> stuTableList;

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
		
	}

	public List<Batches> getBatchesList() {
		return batchesList;
	}

	public void setBatchesList(List<Batches> batchesList) {
		this.batchesList = batchesList;
	}
	


	
	
}

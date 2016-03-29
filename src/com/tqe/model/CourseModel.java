package com.tqe.model;

import java.util.ArrayList;
import java.util.List;

import com.tqe.po.*;

public class CourseModel {

    private Course course;

	private List<Batches> batchesList = new ArrayList<Batches>();

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseModel(){

    }

    public CourseModel(Course course) {
        this.course = course;
    }

    public static class Batches {
		
		private com.tqe.po.Batches batches;		//季度
		private  List<StuResultTable> stuTableList;	//学生评教表
		private  List<TeaResultTable> teaTableList;	//教师评教表
		private  List<LeaResultTable> leaTableList;	//领导评教表
		private List<TeaStuResultTable> teaStuTableList;	//教师评价学生表

		public  List<StuResultTable> getStuTableList() {
			return stuTableList;
		}

		public  void setStuTableList(List<StuResultTable> stuTableList) {
			this.stuTableList = stuTableList;
		}

		public com.tqe.po.Batches getBatches() {
			return batches;
		}

		public void setBatches(com.tqe.po.Batches batches) {
			this.batches = batches;
		}

		public List<TeaResultTable> getTeaTableList() {
			return teaTableList;
		}

		public void setTeaTableList(List<TeaResultTable> teaTableList) {
			this.teaTableList = teaTableList;
		}

		public List<LeaResultTable> getLeaTableList() {
			return leaTableList;
		}

		public void setLeaTableList(List<LeaResultTable> leaTableList) {
			this.leaTableList = leaTableList;
		}

		public List<TeaStuResultTable> getTeaStuTableList() {
			return teaStuTableList;
		}

		public void setTeaStuTableList(List<TeaStuResultTable> teaStuTableList) {
			this.teaStuTableList = teaStuTableList;
		}
		
		
	}

	public List<Batches> getBatchesList() {
		return batchesList;
	}

	public void setBatchesList(List<Batches> batchesList) {
		this.batchesList = batchesList;
	}
	
	

	
	
}

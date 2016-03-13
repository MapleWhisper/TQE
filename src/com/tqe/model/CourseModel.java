package com.tqe.model;

import java.util.ArrayList;
import java.util.List;

import com.tqe.po.LeaResultTable;
import com.tqe.po.StuResultTable;
import com.tqe.po.TeaStuResultTable;
import com.tqe.po.TeaResultTable;

public class CourseModel {
	private List<Batches> batchesList = new ArrayList<Batches>();
	
	public static class Batches {
		
		private com.tqe.po.Batches batches;		//����
		private  List<StuResultTable> stuTableList;	//ѧ�����̱�
		private  List<TeaResultTable> teaTableList;	//��ʦ���̱�
		private  List<LeaResultTable> leaTableList;	//�쵼���̱�
		private List<TeaStuResultTable> teaStuTableList;	//��ʦ����ѧ����

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

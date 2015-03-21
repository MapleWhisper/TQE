package com.tqe.po;

import java.util.ArrayList;
import java.util.List;

public class Data<E> {
	private List<E> data =  null;
	public Data(List<E> data) {
		this.data = data;
	}
	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}
	
}

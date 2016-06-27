package com.tqe.po;

import java.io.Serializable;

public class Privilege implements  Serializable{

	private Integer id;
	private String url;
	private String name;
	private Integer stu;
	private Integer tea;
	private Integer adm;
	private Integer lea;
	private Integer editable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStu() {
		return stu;
	}
	public void setStu(Integer stu) {
		this.stu = stu;
	}
	public Integer getTea() {
		return tea;
	}
	public void setTea(Integer tea) {
		this.tea = tea;
	}
	public Integer getAdm() {
		return adm;
	}
	public void setAdm(Integer adm) {
		this.adm = adm;
	}
	public Integer getLea() {
		return lea;
	}
	public void setLea(Integer lea) {
		this.lea = lea;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privilege privilege = (Privilege) o;

        if (url != null ? !url.equals(privilege.url) : privilege.url != null) return false;
        if (name != null ? !name.equals(privilege.name) : privilege.name != null) return false;
        if (stu != null ? !stu.equals(privilege.stu) : privilege.stu != null) return false;
        if (tea != null ? !tea.equals(privilege.tea) : privilege.tea != null) return false;
        if (adm != null ? !adm.equals(privilege.adm) : privilege.adm != null) return false;
        if (lea != null ? !lea.equals(privilege.lea) : privilege.lea != null) return false;
        return editable != null ? editable.equals(privilege.editable) : privilege.editable == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (stu != null ? stu.hashCode() : 0);
        result = 31 * result + (tea != null ? tea.hashCode() : 0);
        result = 31 * result + (adm != null ? adm.hashCode() : 0);
        result = 31 * result + (lea != null ? lea.hashCode() : 0);
        result = 31 * result + (editable != null ? editable.hashCode() : 0);
        return result;
    }
}

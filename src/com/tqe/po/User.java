package com.tqe.po;


import com.tqe.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.Serializable;

public class User implements Serializable{

	private String id;
	private String username;
	private String password;
	private String type;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public void setPassWordConvertMD5(String originPwd){
        if(originPwd!=null){
            this.password = DigestUtils.md5DigestAsHex(originPwd.getBytes()).toLowerCase();
        }

    }
    public void setMd5Password(String md5Password){
        this.password = md5Password;
    }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + "***" + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

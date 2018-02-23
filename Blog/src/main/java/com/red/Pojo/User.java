package com.red.Pojo;

public class User {
private int id;
private String username;
private String password;
private String idCard;
private String age;
private Double weight;
private String phone;
private boolean sex;//0表示男  1表示女
private String chmod;
public int getId() {
	return id;
}

public String getPassword() {
	return password;
}
public String getPhone() {
	return phone;
}
public boolean isSex() {
	return sex;
}
public String getChmod() {
	return chmod;
}
public void setId(int id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public void setPassword(String password) {
	this.password = password;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public void setSex(boolean sex) {
	this.sex = sex;
}
public void setChmod(String chmod) {
	this.chmod = chmod;
}


}

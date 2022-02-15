package com.example.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.entity.User;

public interface UserDataDao extends Serializable{

//	public List<UserData> search(String userName, String gender);
	public List<User> search(String userNameKey, String gender, Date minDay, Date maxDay);


}

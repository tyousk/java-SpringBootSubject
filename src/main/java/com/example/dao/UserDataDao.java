package com.example.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.entity.User;
import com.example.entity.UserDivision;

public interface UserDataDao extends Serializable{

	public List<User> search(String userNameKey, String gender, Date minDay, Date maxDay);

	public List<UserDivision> search(Integer divisionId);


}

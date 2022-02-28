package com.example.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.entity.User;

import lombok.Data;
@Data
public class SignupDivisionForm implements Serializable{

	private Integer id;

	private String area;

	private Integer sales;

	private Integer client;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime updateTime;

	private Integer divisionId;

	private String divisionName;

	private boolean fire;

	private User user;


}

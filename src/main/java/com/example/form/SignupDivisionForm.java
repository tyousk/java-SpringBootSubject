package com.example.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class SignupDivisionForm implements Serializable{

	private String area;

	private Integer sales;

	private Integer client;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime updateTime;

	private Integer division_id;

	private String division_name;

	private boolean fire;

	private Integer userId;

}

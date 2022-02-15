package com.example.form;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SearchForm implements Serializable {

	private String userNameKey;
	private String gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date minDay;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date maxDay;
}

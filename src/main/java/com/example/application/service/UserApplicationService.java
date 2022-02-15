package com.example.application.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {
	@Autowired

	/** message.propertiesから値を取得するためにMessageSourceを使用*/
	private MessageSource messageSource;

	/** 性別のMapを生成する */
	public Map<String, String> getGenderMap() {
		Map<String, String> genderMap = new LinkedHashMap<>();

		/** MessageSurceのgetMessageメソッドを使い値を取得*/
		String male = messageSource.getMessage("male", null, Locale.JAPAN);
		String female = messageSource.getMessage("female", null, Locale.JAPAN);

		genderMap.put(male, "男性");
		genderMap.put(female, "女性");
		return genderMap;
	}
}
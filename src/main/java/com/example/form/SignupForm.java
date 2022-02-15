package com.example.form;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//ユーザー情報リクエストデータ
//画面入力内容とマッピングするフォームクラスを用意
@Data
public class SignupForm implements Serializable{

	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 1, max = 100, groups = ValidGroup2.class)

	private String userName;

	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 1, max = 100, groups = ValidGroup2.class)
	@Pattern(regexp = "^[ァ-ヶー]*$", groups = ValidGroup2.class, message="全角カタカナで入力してください")
	private String userNameKana;

	@NotBlank(groups = ValidGroup1.class)
	@Pattern(regexp = "^^0[789]0-[0-9]{4}-[0-9]{4}$", groups = ValidGroup2.class, message="携帯電話の番号を入力してください 例)999-9999-9999")
	private String phoneNumber;

	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String email;

//データ型にバインドするために@DataTimeFormatアノテーションを使用
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull(groups = ValidGroup1.class)
	private Date startDay;

	@NotNull(groups = ValidGroup1.class)
	@Min(value = 1, groups = ValidGroup2.class)
	@Max(value = 10000, groups = ValidGroup2.class)
	private Integer userId;

	@NotNull(groups = ValidGroup1.class)
	private String gender;
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private LocalDateTime updateTime;
}
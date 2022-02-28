package com.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

/**
 * ユーザー情報 Entity
 */
@Entity

//Lombokの@Dataは、getterとsetterをコンパイル時に自動生成する
@Data

@Table(name = "user")
public class User implements Serializable {

	/*//	一側のフィールドに、多側のエンティティのコレクションを保持
		@OneToMany(mappedBy = "user")
		private List<UserDivision> userDivisionList;*/

	/**
	 * 社員番号
	 */
	@Id
	@Column(name = "user_id")
	public Integer userId;

	/**
	 * 氏名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 氏名(カナ)
	 */
	@Column(name = "user_name_kana")
	private String userNameKana;

	/**
	 * 携帯電話番号
	 */
	@Column(name = "phone_number")
	private String phoneNumber;

	/**
	 * Email
	 */
	@Column(name = "email")
	private String email;

	/**
	 * 入社日
	 */
	@Column(name = "start_day")
	private Date startDay;

	/**
	 * 性別
	 */
	@Column(name = "gender")
	private String gender;

	/**
	 * データ更新日時
	 */
	@Column(name = "update_time")
	private LocalDateTime updateTime; //日付と時刻
	//	private Date updateTime;  （日付のみの場合）

	//	更新日時をセット
	@PreUpdate
	public void onPreUpdate() {
		setUpdateTime(LocalDateTime.now());
		//		setUpdateTime(new Date());  日付のみ
	}

	/**
	 * 退職者判定
	 */
	@Column(name = "fire")
	private boolean fire;


}
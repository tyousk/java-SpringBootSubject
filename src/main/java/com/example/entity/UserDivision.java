package com.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="user_division")
public class UserDivision implements Serializable{


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Id
	@Column(name = "id")
	private Integer id;

	/**
	 * 担当地区
	 */
	@Column(name = "area")
	private String area;

	/**
	 * 売上
	 */
	@Column(name = "sales")
	private Integer sales;

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
	 * 保有顧客数
	 */
	@Column(name = "client")
	private Integer client;

	/**
	 * 退職者判定
	 */
	@Column(name = "fire")
	private boolean fire;

	/**
	 * 課名
	 */
	@Column(name = "division_name")
	private String divisionName;

	/**
	 * 課ID
	 */
	@Column(name = "division_id")
	private Integer divisionId;

}

package com.example.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public class UserDataDaoImpl implements UserDataDao {

	//Entityを利用するために必要な機能を提供する
	@PersistenceContext
	private EntityManager entityManager;

	public UserDataDaoImpl() {
		super();
	}

	public UserDataDaoImpl(EntityManager manager) {
		this();
		entityManager = manager;
	}

	//Daoクラスで用意したsearchメソッドをオーバーライドする
	@Override

			public List<User> search(String userNameKey, String gender, Date minDay, Date maxDay) {


		//sDayに値が入力されていれば
		//String型sDayをDate型dateSDayに変換
//		if(!"".equals(sDay)) {
//		Date dateSDay = sdFormat.parse(sDay);
//
//		System.out.println(dateSDay);
//		}

		//StringBuilderでSQL文を連結する
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u From User u WHERE ");

		boolean userNameFlg = false;
		boolean genderFlg = false;
		boolean minDayFlg = false;
		boolean maxDayFlg = false;
		boolean andFlg = false;

		//userNameがブランクではなかった場合、sql変数にappendする
		//フラグをtrueにしとく
		if (!"".equals(userNameKey)) {
			sql.append("u.userName LIKE :userNameKey");
			userNameFlg = true;
			andFlg = true;
		}

		//genderがnullではなかった場合、sql変数にappendする
		//フラグをtrueにしとく
		if (!(gender==null)) {
			if (andFlg)
				sql.append(" AND ");
			sql.append("u.gender LIKE :gender");
			genderFlg = true;
			andFlg = true;
		}

		if (!(minDay==null)) {
			if (andFlg)
				sql.append(" AND ");
			sql.append(" u.startDay >= :minDay");
			minDayFlg = true;
			andFlg = true;
		}
		if (!(maxDay==null)) {
			if (andFlg)
				sql.append(" AND ");
			sql.append(" u.startDay <= :maxDay");
			maxDayFlg = true;
			andFlg = true;
		}


		/*
		QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
		entityManagerのcreateQueryメソッドを使用する
		sql変数を引数に渡すS
		*/
		Query query = entityManager.createQuery(sql.toString());

		//上記のif文でtrueになった場合は、各変数に値をセットする
		if (userNameFlg)
			query.setParameter("userNameKey", "%" + userNameKey + "%");
		if (userNameFlg)
			System.out.println("nameクエリー");
		if (genderFlg)
			query.setParameter("gender", "%" + gender + "%");
		if (genderFlg)
			System.out.println("genderクエリー");
		if (minDayFlg)
			query.setParameter("minDay", minDay);
		if (maxDayFlg)
			query.setParameter("maxDay", maxDay);
			System.out.println("sDayクエリー");

		System.out.println("クエリー");
		System.out.println(sql.append(userNameKey));
		System.out.println(sql.append(gender));

		return query.getResultList();
	}
}

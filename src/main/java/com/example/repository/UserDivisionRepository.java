package com.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.UserDivision;

@Repository
public interface UserDivisionRepository extends JpaRepository<UserDivision, Integer> {

	@Query(value = " SELECT division_id, division_name, COUNT(division_id) AS count, SUM(sales) AS sum,"
			+ " AVG(sales) AS avg"
			+ " FROM user_division WHERE fire=false "
			+ " GROUP BY division_id ORDER BY division_id ASC", nativeQuery = true)
	public ArrayList<String> getSumAvg();

	// Object[]をListに変換する。
	/*	default List<SumAvg> findUserDivision() {

			return getSumAvg().stream() //Streamメソッドで、Object[]をStreamに変換
					.map(SumAvg::new) //Object[]をSumAvgに流し込む
					.collect(Collectors.toList()); //collectメソッドで、StreamをListに変換

		}*/
}

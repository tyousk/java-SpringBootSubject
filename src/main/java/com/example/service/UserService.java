package com.example.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDataDaoImpl;
import com.example.entity.SumAvg;
import com.example.entity.User;
import com.example.entity.UserDivision;
import com.example.form.SignupDivisionForm;
import com.example.form.SignupForm;
import com.example.repository.UserDivisionRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//ユーザー情報 Service
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

	//ユーザー情報 Repository

	private final UserRepository userRepository;
	private final UserDivisionRepository userDivisionRepository;

	//ユーザー情報 全検索
	//@return 検索結果

	public List<User> searchAll() {
		return userRepository.findAll();
	}

	//ユーザー情報新規登録
	//@param user ユーザー情報

	public void create(SignupForm form) {
		userRepository.save(CreateUser(form));
	}

	//ユーザーTBLエンティティの生成
	//@param userRequest ユーザー情報リクエストデータ
	//@return ユーザーTBLエンティティ

	public User CreateUser(SignupForm form) {

		User user = new User();
		user.setUserId(form.getUserId());
		user.setUserName(form.getUserName());
		user.setUserNameKana(form.getUserNameKana());
		user.setPhoneNumber(form.getPhoneNumber());
		user.setEmail(form.getEmail());
		user.setStartDay((Date) form.getStartDay());
		user.setGender(form.getGender());
		return user;
	}

	/**
	 * ユーザー情報 主キー検索
	 * @return 検索結果
	 */
	public User findById(Integer userId) {
		return userRepository.findById(userId).get();
	}

	public void update(SignupForm form) {
		userRepository.save(UpdateUser(form));
	}

	/**
	 * ユーザー情報 更新
	 * @param user ユーザー情報
	 */
	public User UpdateUser(SignupForm form) {
		User user = findById(form.getUserId());
		user.setUserId(form.getUserId());
		user.setUserName(form.getUserName());
		user.setUserNameKana(form.getUserNameKana());
		user.setPhoneNumber(form.getPhoneNumber());
		user.setEmail(form.getEmail());
		user.setStartDay((Date) form.getStartDay());
		user.setGender(form.getGender());
		return user;
	}

	/**
	 * ユーザー情報 削除
	 */
	public void delete(Integer userId) {
		User user = findById(userId);
		userRepository.delete(user);
	}

	/**
	 * ユーザー検索
	 */
	private final UserDataDaoImpl userDataDaoImpl;

	public List<User> search(String userName, String gender, Date minDay, Date maxDay) {

		List<User> userlist = new ArrayList<User>();
		System.out.println(userName);
		System.out.println(gender);
		System.out.println(minDay);
		//すべて未入力だった場合は全件検索する

		if ("".equals(userName) && gender == null && minDay == null && maxDay == null) {

			System.out.println("全検索");
			userlist = userRepository.findAll();
		} else {
			System.out.println("個別検索");
			//上記以外の場合、UserDataDaoImplのメソッドを呼び出す
			userlist = userDataDaoImpl.search(userName, gender, minDay, maxDay);

		}
		return userlist;
	}

	public UserDivision CreateUserDivision(SignupDivisionForm form) {

		UserDivision userDivision = new UserDivision();

		userDivision.setId(form.getId());
		userDivision.setUser(form.getUser());
		userDivision.setArea(form.getArea());
		userDivision.setSales(form.getSales());
		userDivision.setClient(form.getClient());
		userDivision.setDivisionName(form.getDivisionName());
		userDivision.setDivisionId(form.getDivisionId());

		System.out.println(userDivision);
		return userDivision;
	}

	public ArrayList<SumAvg> getDivList() {
		System.out.println(userDivisionRepository.getSumAvg());
		System.out.println(userDivisionRepository.getSumAvg().getClass());
		var a = userDivisionRepository.getSumAvg();
		ArrayList<SumAvg> g = new ArrayList<SumAvg>();
		for(var b:a) {
			var c = b.split(",");
			Integer e = Integer.parseInt(c[0]);
			Integer f = Integer.parseInt(c[2]);
			Integer h = Integer.parseInt(c[3]);
			BigDecimal i = new BigDecimal(c[4]);
			SumAvg d = new SumAvg(e,c[1],f,h,i);
			g.add(d);
		}
		return g;
	}

//	public List<UserDivision> getDivisionList () {
//		return userDivisionRepository.byDivisionList();
//	}

	//部門別ユーザー情報 全検索
	//@return 検索結果

	public List<UserDivision> searchUserAll() {
		return userDivisionRepository.findAll();
	}

	public List<UserDivision> searchUserDivAll(Integer divisionId) {
		List<UserDivision> byEmployeeList = new ArrayList<UserDivision>();
		byEmployeeList = userDataDaoImpl.search(divisionId);
		return byEmployeeList;
	}

	/**
	 * ユーザー情報 主キー検索
	 * @return 検索結果
	 */

	public UserDivision findByUserDivisionId(Integer id) {
		return userDivisionRepository.findById(id).get();
	}

	/**
	 * ユーザー情報 更新
	 * @param user ユーザー情報
	 */
	public UserDivision UpdateUserDivision(SignupDivisionForm form) {
		UserDivision userDivision = findByUserDivisionId(form.getId());
		userDivision.setId(form.getId());
		userDivision.setArea(form.getArea());
		userDivision.setSales(form.getSales());
		userDivision.setClient(form.getClient());
		userDivision.setDivisionName(form.getDivisionName());
		userDivision.setDivisionId(form.getDivisionId());
		userDivision.setUser(form.getUser());
		System.out.println(userDivision);
		return userDivision;
	}

}
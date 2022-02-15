package com.example.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.entity.User;
import com.example.form.GroupOrder;
import com.example.form.SearchForm;
import com.example.form.SignupDivisionForm;
import com.example.form.SignupForm;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository repository;
	private final UserApplicationService userApplicationService;
	private final UserService userService;

	/**
	 * ユーザー情報一覧画面を表示
	 */

	@GetMapping("/list")
	public String displayList(Model model, @ModelAttribute SignupForm form, SearchForm searchForm) {
		// 性別を取得
		Map<String, String> genderMap = userApplicationService.getGenderMap();
		model.addAttribute("genderMap", genderMap);
		List<User> userlist = userService.searchAll();
		model.addAttribute("userlist", userlist);
		System.out.println("一覧表示");
		return "user/list";
	}

	/** ユーザー登録画面の表示 */

	@GetMapping("/signup")
	public String getSignup(Model model, @ModelAttribute SignupForm form) {
		// 性別を取得
		Map<String, String> genderMap = userApplicationService.getGenderMap();
		model.addAttribute("genderMap", genderMap);
		// 新規登録画面に遷移
		return "user/signup";
	}

	/** ユーザー登録フォームの送信*/

	@PostMapping("/signup")
	public String postSignup(Model model,
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult) {

		//入力チェック結果
		if (bindingResult.hasErrors()) {
			//エラーを出して新規登録画面に戻る
			return getSignup(model, form);
		}
		//フォームとエンティティを繋げる
		User user = userService.CreateUser(form);

		//リポジトリに登録
		repository.save(user);

		return "redirect:/user/list";
	}

	/**
	 * ユーザー編集画面を表示
	 */

	@GetMapping("/{userId}/edit")
	public String userEdit(@PathVariable Integer userId, Model model, @ModelAttribute SignupForm form) {
		// 性別を取得
		Map<String, String> genderMap = userApplicationService.getGenderMap();
		model.addAttribute("genderMap", genderMap);

		User user = userService.findById(userId);
		form.setUserId(user.getUserId());
		form.setUserName(user.getUserName());
		form.setUserNameKana(user.getUserNameKana());
		form.setPhoneNumber(user.getPhoneNumber());
		form.setEmail(user.getEmail());
		form.setStartDay(user.getStartDay());
		form.setGender(user.getGender());
		model.addAttribute("userEdit", form);
		return "user/edit";
	}

	/**
	 * ユーザー更新
	 */

	@PostMapping("/update")
	public String update(Model model,
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Integer userId) {

		//入力チェック結果
		if (bindingResult.hasErrors()) {

			//エラーを出して編集画面に戻る
			return userEdit(userId, model, form);
		}

		//フォームとエンティティを繋げる
		User user = userService.UpdateUser(form);

		//リポジトリに登録
		repository.save(user);

		return "redirect:/user/list";
	}

	/**
	 * ユーザー情報削除
	 */

	@GetMapping("/{userId}/delete")
	public String delete(@PathVariable Integer userId, Model model) {
		// ユーザー情報の削除
		userService.delete(userId);
		return "redirect:/user/list";
	}

	/**
	*検索結果の受け取り処理
	 * @throws ParseException
	*/

	@PostMapping("/list")
	public String select(@ModelAttribute SearchForm searchForm, User user, Model model){
		System.out.println(searchForm.getUserNameKey());
		System.out.println(searchForm.getGender());
		System.out.println(searchForm.getMinDay());
		System.out.println(searchForm.getMaxDay());
		Map<String, String> genderMap = userApplicationService.getGenderMap();
		model.addAttribute("genderMap", genderMap);
		//userのゲッターで各値を取得する
//		List<User> userlist = userService.search(searchForm.getUserName(), searchForm.getGender());
		List<User> userlist = userService.search(searchForm.getUserNameKey(), searchForm.getGender(),
				searchForm.getMinDay(), searchForm.getMaxDay());
		model.addAttribute("userlist", userlist);
		return "/user/list";
	}

	/** 部署情報登録画面の表示 */

	@GetMapping("/{userId}/signupDivision")
	public String getSignDivision(@PathVariable Integer userId, Model model, @ModelAttribute SignupDivisionForm form) {

		User user = userService.findById(userId);
		form.setUserId(user.getUserId());

		// 部署登録画面に遷移
		return "user/signupDivision";
	}

	/**
	 * 退職処理
	 */
	@GetMapping("/{userId}/fire")
	public String fire(@PathVariable Integer userId, Model model) {
		User user = userService.findById(userId);
//		fireカラムをtrueに変更
		user.setFire(true);
		repository.save(user);
		return "redirect:/user/list";
	}
}

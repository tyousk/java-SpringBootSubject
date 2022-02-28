package com.example.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.application.service.UserApplicationService;
import com.example.entity.SumAvg;
import com.example.entity.User;
import com.example.entity.UserDivision;
import com.example.form.GroupOrder;
import com.example.form.SearchForm;
import com.example.form.SignupDivisionForm;
import com.example.form.SignupForm;
import com.example.repository.UserDivisionRepository;
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
	private final UserDivisionRepository divisionRepository;

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
	public String select(@ModelAttribute SearchForm searchForm, User user, Model model) {
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
//	PathVariableから持ってくる変数はurlと同じ
	public String getSignDivision(@PathVariable Integer userId, User user, Model model, @ModelAttribute SignupDivisionForm form) {

		form.setUser(user);
		form.setId(userId);

//		課名のセレクトボックスを取得
		Map<Integer, String> divisionNameMap = userApplicationService.getDivisionNameMap(model);
		model.addAttribute("divisionName", divisionNameMap);
//		セレクトボックスのデフォルト表示
		model.addAttribute("divisionNameFirst", 1);

		// 部署登録画面に遷移
		return "user/signupDivision";
	}

	/** 部署情報登録情報の送信 */

	@PostMapping("/divisionSignup")
	public String postSignup(Model model,
			@ModelAttribute SignupDivisionForm form) {

		//フォームとエンティティを繋げる
		UserDivision userDivision = userService.CreateUserDivision(form);

		//リポジトリに登録
		divisionRepository.save(userDivision);

		return "redirect:/user/userDivisionList";
	}

	/**
	 * 部門情報一覧画面を表示
	 */
	@GetMapping("/byDivisionList")
	public String displayDivList(Model model) {

		ArrayList<SumAvg> byDivisionList = userService.getDivList();
		System.out.println(userService.getDivList());
		model.addAttribute("byDivisionList", byDivisionList);
		System.out.println("部門情報一覧表示");
		System.out.println(byDivisionList);
		return "user/byDivisionList";
	}

	/**
	 * 部門別ユーザー情報一覧画面を表示
	 */

	@GetMapping("/{divisionId}/byEmployeeList")
	public String displayUserDivList(Model model, @PathVariable Integer divisionId) {

		List<UserDivision> byEmployeeList = userService.searchUserDivAll(divisionId);
		int a =divisionId;
		System.out.println(a);
		model.addAttribute("divisionIdTitle",a);
		model.addAttribute("byEmployeeList", byEmployeeList);
		System.out.println("社員一覧表示");
		return "user/byEmployeeList";
	}

	/**
	 * ユーザー詳細編集画面を表示
	 */

	@GetMapping("/{id}/editDivision")
	public String userEdit(@PathVariable Integer id, Model model, @ModelAttribute SignupDivisionForm form) {
		// 性別を取得
//		課名のセレクトボックスを取得
		Map<Integer, String> divisionNameMap = userApplicationService.getDivisionNameMap(model);
		model.addAttribute("divisionName", divisionNameMap);

		UserDivision userDivision = userService.findByUserDivisionId(id);
		form.setUser(userDivision.getUser());
		form.setArea(userDivision.getArea());
		form.setClient(userDivision.getClient());
		form.setSales(userDivision.getSales());
		form.setDivisionName(userDivision.getDivisionName());
		form.setDivisionId(userDivision.getDivisionId());
		form.setId(userDivision.getId());
		model.addAttribute("userDivisionEdit", form);
		return "user/editDivision";
	}

	/**
	 * ユーザー詳細情報の更新
	 */

	@PostMapping("divisionUpdate")
	public String updateDivision(Model model,RedirectAttributes redirectAttributes,
			@ModelAttribute SignupDivisionForm form, Integer id) {

//		//入力チェック結果
//		if (bindingResult.hasErrors()) {
//
//			//エラーを出して編集画面に戻る
//			return userEdit(userId, model, form);
//		}

		//フォームとエンティティを繋げる
		UserDivision userDivision = userService.UpdateUserDivision(form);

		//リポジトリに登録
		divisionRepository.save(userDivision);
		int divisionId = form.getDivisionId();
		//redirectAttributes: リダイレクト先に変数を挿入する
		redirectAttributes.addAttribute("divId", divisionId);

		return "redirect:/user/{divId}/byEmployeeList";
	}

	/**
	 * 退職処理
	 */
	@GetMapping("/{id}/fire")
	public String fire(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		UserDivision userDivision = userService.findByUserDivisionId(id);
		//		fireカラムをtrueに変更
		userDivision.setFire(true);
		divisionRepository.save(userDivision);
		Integer divisionId = userDivision.getDivisionId();
		System.out.println(divisionId);
		//redirectAttributes: リダイレクト先に変数を挿入する
		redirectAttributes.addAttribute("divId", divisionId);
		return "redirect:/user/{divId}/byEmployeeList";
	}
}

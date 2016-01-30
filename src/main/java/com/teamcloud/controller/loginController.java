package com.teamcloud.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.teamcloud.model.service.OauthService;
import com.teamcloud.model.vo.UserVO;

@Controller
@SessionAttributes("userInfo")

public class LoginController {

	@Autowired
	private OauthService oauthService;

	@RequestMapping(value="/", produces="text/plain;charset=UTF-8")
	public String index(HttpSession session){
		String url = "forward:/login.est";
		try{
			System.out.println("user info :" + session.getAttribute("userInfo") );
			if(session.getAttribute("userInfo") != null){
				url = "index";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("url : " + url);
		return url;
	}

	@RequestMapping(value="/login.est", produces="text/plain;charset=UTF-8" )
	public String login() {
		String loginPage = oauthService.getUriPath().toString();
		return "redirect:" + loginPage;
	}

	@RequestMapping(value="/authorization.est", produces="text/plain;charset=UTF-8")
	public String authorization( @RequestParam("code") String code, Model model ) {
		String url = "forward:/login.est";
		try{
			JSONObject tokenInfo = oauthService.getToken(code);
			if(tokenInfo != null){
				UserVO user = oauthService.getUserInfo(tokenInfo);
				System.out.println(user.toString());
				model.addAttribute("userInfo", user);
				url = "index";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	@RequestMapping(value="/logout.est", produces="text/plain;charset=UTF-8")
	public String authorization(HttpSession session, Model model) {
		session.invalidate();
		model.asMap().clear();
		return "forward:/";
	}
}
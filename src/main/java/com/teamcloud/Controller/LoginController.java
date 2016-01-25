package com.teamcloud.Controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.teamcloud.model.service.OauthService;
import com.teamcloud.model.vo.UserVO;

@SessionAttributes("userInfo")
@Controller
public class LoginController {

	@Autowired
	private OauthService oauthService;

	@RequestMapping(value="/", produces="text/plain;charset=UTF-8")
	public String index(HttpSession session){
		String url = "redirect:/login.est";
		try{
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
		System.out.println("로그인 페이지 !");
		return "redirect:" + loginPage;
	}

	@RequestMapping(value="/authorization.est", produces="text/plain;charset=UTF-8")
	public String authorization( @RequestParam("code") String code, Model model ) {
		String url = "redirect:/login.est";
		try{
			JSONObject tokenInfo = oauthService.getToken(code);
			if(tokenInfo != null){
				UserVO user = oauthService.getUserInfo(tokenInfo);
				model.addAttribute("userInfo", user);
				url = "index";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	@RequestMapping(value="/logout.est", produces="text/plain;charset=UTF-8")
	public String authorization(SessionStatus session) {
		session.setComplete();
		return "redirect:/";
	}
}
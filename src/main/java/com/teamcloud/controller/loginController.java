package com.teamcloud.controller;

import com.teamcloud.model.vo.UserVO;
import com.teamcloud.service.DataService;
import com.teamcloud.service.OauthService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;


@Controller
@SessionAttributes({"userInfo","list", "path", "parentDirectory"})
@PropertySource( value = { "classpath:application.properties" })
public class LoginController {

	@Autowired
	private OauthService oauthService;

	@Autowired
	private DataService dataService;

	@Autowired
	private Environment environment;

	@RequestMapping(value="/")
	public String index(HttpSession session, Model model) {
		String url = "redirect:" + oauthService.getUriPath().toString();
		try{
			if(session.getAttribute("userInfo") != null){
				String absolutePath = environment.getRequiredProperty("ABSOLUTE_PATH");
				model.addAttribute("path", absolutePath);
				model.addAttribute("list", dataService.getFile_Folder_List(absolutePath));
				model.addAttribute("parentDirectory", "");
				url = "cloud";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	@RequestMapping(value="/authorization")
	public String authorization(@RequestParam("code") String code, Model model) {
		String url = "redirect:/";

		try{
			JSONObject tokenInfo = oauthService.getToken(code);
			if(tokenInfo != null){
				UserVO user = oauthService.getUserInfo(tokenInfo);
				model.addAttribute("userInfo", user);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		String url = "redirect:/";

		try{
			session.invalidate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

}
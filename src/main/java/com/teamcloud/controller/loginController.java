package com.teamcloud.controller;

import com.teamcloud.model.vo.UserVO;
import com.teamcloud.service.DataService;
import com.teamcloud.service.OauthService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SessionAttributes({"userInfo","list", "path", "parentDirectory", "pageList"})
@PropertySource( value = { "classpath:application.properties" })
public class LoginController {

	@Autowired
	private OauthService oauthService;

	@Autowired
	private DataService dataService;

	@Autowired
	private Environment environment;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/")
	public String index(HttpSession session, Model model,
						@RequestParam(required = false, defaultValue = "1", value = "currentPage") int currentPage) {
		String url = "redirect:" + oauthService.getUriPath().toString();

		try {
			if(session.getAttribute("userInfo") != null){
				String absolutePath = environment.getRequiredProperty("ABSOLUTE_PATH");
				model.addAttribute("path", absolutePath);
				model.addAttribute("list", dataService.getFileFolderList(absolutePath, currentPage));
				model.addAttribute("parentDirectory", "");
				url = "cloud";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return url;
	}

	@RequestMapping(value="/authorization")
	public String authorization(@RequestParam("code") String code, Model model) {
		String url = "redirect:/";

		try {
			JSONObject tokenInfo = oauthService.getToken(code);
			if(tokenInfo != null){
				UserVO user = oauthService.getUserInfo(tokenInfo);
				model.addAttribute("userInfo", user);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return url;
	}

	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		String url = "redirect:/";

		try {
			session.invalidate();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return url;
	}
}
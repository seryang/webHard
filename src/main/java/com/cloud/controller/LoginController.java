package com.cloud.controller;

import com.cloud.model.vo.Token;
import com.cloud.model.vo.UserVO;
import com.cloud.service.DataService;
import com.cloud.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private OauthService oauthService;

	@Autowired
	private DataService dataService;

	@Autowired
	private Environment environment;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/")
	public String index(HttpSession session,
						@RequestParam(required = false, defaultValue = "1", value = "currentPage") int currentPage) {
		String url = "redirect:" + oauthService.getUriPath().toString();

		try {
			if(session.getAttribute("userInfo") != null){
				String absolutePath = environment.getRequiredProperty("absolute.path");
				session.setAttribute("path", absolutePath);
				session.setAttribute("list", dataService.getFileFolderList(absolutePath, currentPage));
				session.setAttribute("parentDirectory", "");
				url = "cloud";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return url;
	}

	@RequestMapping(value="/authorization")
	public String authorization(@RequestParam("code") String code, HttpSession session) {
		String url = "redirect:/";

		try {
			Token tokenInfo = oauthService.getToken(code);
			if(tokenInfo != null){
				UserVO user = oauthService.getUserInfo(tokenInfo);
				session.setAttribute("userInfo", user);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return url;
	}

	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		String url = "redirect:/";
		Cookie[] cookies = request.getCookies();

		for(int i = 0 ; i < cookies.length ; i++){
			Cookie cookie = cookies[i];
			if(cookie.getName().equals("tmid")){
				cookie.setMaxAge(0);
				cookie.setDomain(".tmup.com");
				response.addCookie(cookie);
			}
		}

		try {
			HttpSession session = request.getSession();
			session.removeAttribute("userInfo");
			session.invalidate();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return url;
	}
}

package com.thinkgem.jeesite.modules.alipay;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.util.AlipaySubmit;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
public class AlipayController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/alipayIndex", method = RequestMethod.GET)
	public String alipayIndex( HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String buildForm = AlipaySubmit.BuildForm();
			model.addAttribute("alipayurl", buildForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/alipay/index";
	}
	
	@RequestMapping(value = "${adminPath}/alipayNotify", method = RequestMethod.GET)
	public String alipayNotify( HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/alipay/alipay_notify";
	}
	
	@RequestMapping(value = "${adminPath}/alipayReturn", method = RequestMethod.GET)
	public String alipayReturn( HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/alipay/alipay_return";
	}
	
}

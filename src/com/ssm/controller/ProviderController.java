package com.ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ssm.pojo.Bill;
import com.ssm.pojo.Provider;
import com.ssm.pojo.User;
import com.ssm.service.BillService;
import com.ssm.service.ProviderService;

@Controller
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	@Autowired
	private BillService billService;

	@RequestMapping("/toProvideradd")
	public String toProvideradd() {
		return "provideradd";
	}
	
	@RequestMapping("/toProviderlist")
	public String toProviderlist(HttpServletRequest request) {
		List<Provider> list = providerService.selectProviderList();
		request.setAttribute("providerList", list);
		return "providerlist";
	}
	
	@RequestMapping("/toProvidermodify")
	public String toProvidermodify(HttpServletRequest request) {
		Provider provider = providerService.selectProviderCode(request.getParameter("proCode"));
		if(provider != null)
		{
			request.setAttribute("provider", provider);
			return "providermodify";
		}
		return "error";
	}
	
	/*显示供应商信息*/
	@RequestMapping("/toProviderview")
	public String toProviderview(HttpServletRequest request) {
		Provider provider = providerService.selectProviderCode(request.getParameter("proCode"));
		if(provider != null)
		{
			request.setAttribute("provider", provider);
			return "providerview";
		}
		return "error";
	}
	
	/*ajax下拉菜单*/
	@RequestMapping("/selectProviderCode")
	@ResponseBody
	public String selectProviderCode(@RequestParam(value="proCode") String proCode) {
		Provider provider = new Provider();
		provider = providerService.selectProviderCode(proCode);
		JSONObject jo = new JSONObject();
		if(provider == null) {
			jo.put("proCode", "notexist");
		}else {
			jo.put("proCode", "exist");
		}
		return jo.toString();
	}
	
	/*添加供应商*/
	@RequestMapping("/insertProvider")
	public String insertProvider(HttpServletRequest request, HttpSession session) {
		Provider provider = new Provider();
		provider.setProCode(request.getParameter("proCode"));
		provider.setProName(request.getParameter("proName"));
		provider.setProContact(request.getParameter("proContact"));
		provider.setProPhone(request.getParameter("proPhone"));
		provider.setProAddress(request.getParameter("proAddress"));
		provider.setProFax(request.getParameter("proFax"));
		provider.setProDesc(request.getParameter("proDesc"));
		User user = (User) session.getAttribute("USER_SESSION");
		if(user == null)	return "error";
		provider.setCreatedBy(user.getId());
		int rows = providerService.insertProvider(provider);
		if(rows > 0) {
			return "redirect:/toProviderlist";
		}
		else {
			return "error";
		}
	}
	
	/*修改供应商信息*/
	@RequestMapping("/updateProvider")
	public String updateProvider(HttpServletRequest request, HttpSession session) {
		Provider provider = new Provider();
		provider.setProCode(request.getParameter("proCode"));
		provider.setProName(request.getParameter("proName"));
		provider.setProContact(request.getParameter("proContact"));
		provider.setProPhone(request.getParameter("proPhone"));
		provider.setProAddress(request.getParameter("proAddress"));
		provider.setProFax(request.getParameter("proFax"));
		provider.setProDesc(request.getParameter("proDesc"));
		User user = (User) session.getAttribute("USER_SESSION");
		if(user == null)	return "error";
		provider.setModifyBy(user.getId());
		int rows = providerService.updateProvider(provider);
		if(rows > 0) {
			return "redirect:/toProviderlist";
		}
		else {
			return "error";
		}
		
	}
	
	/*删除供应商*/
	@RequestMapping("/deleteProvider")
	@ResponseBody
	public String deleteProvider(@RequestParam(value="proCode") String proCode) {
		Provider provider = providerService.selectProviderCode(proCode);
		JSONObject jo = new JSONObject();
		if(provider == null) {
			jo.put("delResult","notexist");
			return jo.toString();
		}
		List<Bill> list = billService.selectByProviderId(provider.getId());
		if(list.size() == 0) {
			int rows = providerService.deleteProvider(proCode);
			if(rows > 0) {
				jo.put("delResult","true");
			} else {
				jo.put("delResult","false");
			}
		}else {
			jo.put("delResult",list.size());
		}
		return jo.toString();
	}
	
	/*多条件查询*/
	@RequestMapping("/selectProviderByCondition")
	public String selectProviderByCondition(HttpServletRequest request) {
		Provider provider = new Provider();
		String proCode = request.getParameter("queryProCode");
		String proName = request.getParameter("queryProName");
		provider.setProCode(proCode);
		provider.setProName(proName);
		List<Provider> list = providerService.selectProviderByCondition(provider);
		request.setAttribute("providerList", list);
		request.setAttribute("queryProCode",proCode);
		request.setAttribute("queryProName",proName);
		return "providerlist";
		
	}
	
}


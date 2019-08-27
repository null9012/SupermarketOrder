package com.ssm.controller;

import java.math.BigDecimal;
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
public class BillController {
	
	@Autowired
	private BillService billService;
	@Autowired
	private ProviderService providerService;
	
	@RequestMapping("/billList")
	public String billList(HttpServletRequest request) {
		List<Bill> billList = billService.billList();
		List<Provider> providerList = providerService.providerList();
		request.setAttribute("billList", billList);
		request.setAttribute("providerList", providerList);
		return "billlist";
	}
	
	/*查看详细信息*/
	@RequestMapping("/billview")
	public String BillView(HttpServletRequest request) {
		String billCode = request.getParameter("billcc");
		Bill bill = billService.selectBillById(billCode);
		request.setAttribute("bill", bill);
		return "billview";
	}
	
	@RequestMapping("/tobilladd")
	public String toBilladd(HttpServletRequest request) {
		return "billadd";
	}
	
	/*ajax下拉菜单*/
	@RequestMapping("/selectBillCode")
	@ResponseBody
	public String selectBillCode(@RequestParam(value="billCode") String billCode) {
		Bill bill = new Bill();
		bill = billService.selectBillCode(billCode);
		JSONObject jo = new JSONObject();
		if(bill == null) {
			jo.put("billCode", "notexist");
		}else {
			jo.put("billCode", "exist");
		}
		return jo.toString();
	}
	
	/*新增订单*/
	@RequestMapping("/billadd")
	public String billAdd(HttpServletRequest request,HttpSession session) {
		Bill bill = new Bill();
		User user = (User)session.getAttribute("USER_SESSION");
		if(user == null)	return "error";
		bill.setBillCode(request.getParameter("billCode"));
		bill.setProductName(request.getParameter("productName"));
		bill.setProductUnit(request.getParameter("productUnit"));
		bill.setProductCount(new BigDecimal(request.getParameter("productCount")));
		bill.setTotalPrice(new BigDecimal(request.getParameter("totalPrice")));
		bill.setProviderId(Integer.valueOf(request.getParameter("providerId")));
		bill.setIsPayment(Integer.valueOf(request.getParameter("isPayment")));
		bill.setCreatedBy(user.getId());
		int rows = billService.createBill(bill);
		if(rows > 0) {
			return "redirect:/billList";
		}else {
			return "error";
		}
	}
	
	/*获取所有供应商ID*/
	@RequestMapping("/selectProviderId")
	@ResponseBody
	public List<Provider> selectProviderId(){
		List<Provider> provider = providerService.providerList();
		return provider;
	}
	
	@RequestMapping("/tobillmodify")
	public String tobillModify(HttpServletRequest request) {	
		String billCode = request.getParameter("billcc");
		Bill bill = billService.selectBillById(billCode);
		request.setAttribute("bill", bill);
		return "billmodify";		
	}
	
	/*修改订单*/
	@RequestMapping("/billmodify")
	public String billModify(HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("USER_SESSION");
		if(user == null)	return "error";
		Bill bill = new Bill();
		bill.setBillCode(request.getParameter("billCode"));
		bill.setProductName(request.getParameter("productName"));
		bill.setProductUnit(request.getParameter("productUnit"));
		bill.setProductCount(new BigDecimal(request.getParameter("productCount")));
		bill.setTotalPrice(new BigDecimal(request.getParameter("totalPrice")));
		bill.setProviderId(Integer.valueOf(request.getParameter("providerId")));
		bill.setIsPayment(Integer.valueOf(request.getParameter("isPayment")));
		bill.setModifyBy(user.getId());
		int rows = billService.updateBill(bill);
		if(rows > 0) {
			return "redirect:/billList";
		}else {
			return "error";
		}
	}
	
	/*删除订单*/
	@RequestMapping("/billdelete")
	@ResponseBody
	public boolean billDelete(@RequestParam("billcode")String billcc) {
		Bill bill = new Bill();
		bill.setBillCode(billcc);
		int rows = billService.deleteBill(bill);
		if(rows > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/*多条件查询*/
	@RequestMapping("/selectbill")
	public String select(HttpServletRequest request) {
		Bill bill = new Bill();
		String queryProductName = request.getParameter("queryProductName");
		String queryProviderId = request.getParameter("queryProviderId");
		String queryIsPayment = request.getParameter("queryIsPayment");
		bill.setProductName(queryProductName);
		bill.setProviderId(Integer.valueOf(queryProviderId));
		bill.setIsPayment(Integer.valueOf(queryIsPayment));
		List<Bill> billList = billService.select(bill);
		List<Provider> providerList = providerService.providerList();
		request.setAttribute("billList", billList);
		request.setAttribute("providerList", providerList);
		request.setAttribute("queryProductName", queryProductName);
		request.setAttribute("queryProviderId", queryProviderId);
		request.setAttribute("queryIsPayment", queryIsPayment);
		return "billlist";
	}
}

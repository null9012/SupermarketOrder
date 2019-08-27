package com.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ssm.pojo.Role;
import com.ssm.pojo.User;
import com.ssm.service.RoleService;
import com.ssm.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/toLogin")
	public String toLogin(HttpSession session) {
		session.removeAttribute("USER_SESSION");
		return "login";
	}
	
	@RequestMapping("/toUseradd")
	public String toUseradd() {
		return "useradd";
	}
	
	@RequestMapping("/toUserlist")
	public String toUserlist(HttpServletRequest request) {
		List<User> list = userService.selectUserlist();
		List<User> userList = null;
		List<Role> rolelist = roleService.selectRoleName();
		int pageIndex = 1;
		if(request.getParameter("pageIndex")!=null)
		    pageIndex = Integer.parseInt((String) request.getParameter("pageIndex"));
		userList = userService.selectUserlist(pageIndex, 5);
		for(User u :userList) {
			u.setAge(u.getBirthday());
			u.setUserRoleName(u.getUserRole());
		}
		request.setAttribute("userList", userList);
		request.setAttribute("roleList", rolelist);
		request.setAttribute("totalCount", list.size());
		request.setAttribute("pageIndex",request.getParameter("pageIndex"));
		request.setAttribute("totalPageCount", (int)Math.ceil((double)list.size()/(double)5));
		return "userlist";
	}
	
	@RequestMapping("/toUsermodify")
	public String toUsermodify(HttpServletRequest request) {
		User user = userService.selectUserCode(request.getParameter("userCode"));
		if(user != null)
		{
			user.setAge(user.getBirthday());
			user.setUserRoleName(user.getUserRole());
			request.setAttribute("user", user);
			return "usermodify";
		}
		return "error";
	}
	
	/*显示用户信息*/
	@RequestMapping("/toUserview")
	public String toUserview(HttpServletRequest request) {
		User user = userService.selectUserCode(request.getParameter("userCode"));
		if(user != null)
		{
			user.setAge(user.getBirthday());
			user.setUserRoleName(user.getUserRole());
			request.setAttribute("user", user);
			return "userview";
		}
		return "error";
	}
	
	@RequestMapping("/toPwdmodify")
	public String toPwdmodify() {
		return "pwdmodify";
	}
	
	/*用户登录*/
	@RequestMapping("/loginUser")
	public String loginUser(HttpServletRequest request, HttpSession session, Model model) {
		User user = new User();
		String userCode = request.getParameter("userCode");
		String userPassword = request.getParameter("userPassword");
		user = userService.selectUserCode(userCode);
		if(user != null) {
			String pw = user.getUserPassword();
			if(pw != null && userPassword.equals(pw))
			{
				session.setAttribute("USER_SESSION", user);
				return "frame";
			}
		}	
		model.addAttribute("error", "用户名或密码错误！");
		return "login";
	}
	
	/*判断userCode是否重名*/
	@RequestMapping("/selectUserCode")
	@ResponseBody
	public String selectUserCode(@RequestParam(value="userCode") String userCode) {
		User user = new User();
		user = userService.selectUserCode(userCode);
		JSONObject jo = new JSONObject();
		if(user == null) {
			jo.put("userCode", "notexist");
		}else {
			jo.put("userCode", "exist");
		}
		return jo.toString();
	}
	
	/*添加用户*/
	@RequestMapping("/insertUser")
	public String insertUser(HttpServletRequest request, HttpSession session) throws ParseException {
		User u = (User) session.getAttribute("USER_SESSION");
		if(u == null)	return "error";
		User user = new User();
		user.setUserCode(request.getParameter("userCode"));
		user.setUserName(request.getParameter("userName"));
		user.setUserPassword(request.getParameter("userPassword"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = format.parse(request.getParameter("birthday"));
		java.sql.Date date = new java.sql.Date(d.getTime());
		user.setBirthday(date);
		user.setPhone(request.getParameter("phone"));
		user.setAddress(request.getParameter("address"));
		user.setUserRole(request.getParameter("userRole"));
		user.setCreatedBy(u.getId());
		int rows = userService.insertUser(user);
		if(rows > 0) {
			return "redirect:/toUserlist";
		}
		else {
			return "error";
		}
	}
	
	/*修改用户*/
	@RequestMapping("/updateUser")
	public String updateUser(HttpServletRequest request, HttpSession session) throws ParseException {
		User u = (User) session.getAttribute("USER_SESSION");
		if(u == null)	return "error";
		User user = new User();
		user.setUserCode(request.getParameter("userCode"));
		user.setUserName(request.getParameter("userName"));
		user.setUserPassword(request.getParameter("userPassword"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = format.parse(request.getParameter("birthday"));
		java.sql.Date date = new java.sql.Date(d.getTime());
		user.setBirthday(date);
		user.setPhone(request.getParameter("phone"));
		user.setAddress(request.getParameter("address"));
		user.setUserRole(request.getParameter("userRole"));
		user.setModifyBy(u.getId());
		int rows = userService.updateUser(user);
		if(rows > 0) {
			return "redirect:/toUserlist"; 
		}
		else {
			return "error";
		}
	}
	
	/*修改密码*/
	@RequestMapping("/updatePwd")
	public String updatePwd(HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("USER_SESSION");
		if(user == null)	return "error";
		user.setUserPassword(request.getParameter("newpassword"));
		user.setModifyBy(user.getId());
		int rows = userService.updatePwd(user);
		if(rows > 0) {
			return "redirect:/toLogin"; 
		}
		else {
			return "error";
		}
	}
	
	/*验证密码*/
	@RequestMapping("/verifyPwd")
	@ResponseBody
	public String verifyPwd(@RequestParam(value="userPassword") String userPassword, HttpSession session) {
		JSONObject jo = new JSONObject();
		if(userPassword == ""){
			jo.put("result", "error");
			return jo.toString();
		}
		User user = (User) session.getAttribute("USER_SESSION");
		if(user != null && userPassword != null)
		{
			if(userPassword.equals(user.getUserPassword())) {
				jo.put("result", "true");
			}else {
				jo.put("result", "false");
			}
		}else {
			jo.put("result", "sessionerror");
		}
		return jo.toString();
	}
	
	/*删除用户*/
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(@RequestParam(value="userCode") String userCode) {
		JSONObject jo = new JSONObject();
		int rows = userService.deleteUser(userCode);
		if(rows > 0) {
			jo.put("delResult","true");
		}
		else {
			jo.put("delResult","false");
		}
		return jo.toString();
		
	}
	
	/*多条件查询*/
	@RequestMapping("/selectUserByCondition")
	public String selectUserByCondition(HttpServletRequest request) {
		User user = new User();
		int pageSize = 5;
		String queryname = request.getParameter("queryname");
		String queryUserRole = request.getParameter("queryUserRole");
		if(queryUserRole.equals("0")) queryUserRole = "";
		user.setUserCode(queryname);
		user.setUserRole(queryUserRole);
		List<User> list = userService.selectUserByCondition(user);
		List<Role> rolelist = roleService.selectRoleName();
		List<User> userList = userService.selectUserByCondition(user,Integer.parseInt(request.getParameter("pageIndex")), pageSize);	
		for(User u :userList) {
			u.setAge(u.getBirthday());
			u.setUserRoleName(u.getUserRole());
		}
		request.setAttribute("queryUserRole", queryUserRole);
		request.setAttribute("userList", userList);
		request.setAttribute("roleList", rolelist);
		request.setAttribute("pageIndex", request.getParameter("pageIndex"));
		request.setAttribute("totalCount", list.size());
		request.setAttribute("totalPageCount", (int)Math.ceil((double)list.size()/(double)pageSize));
		return "userlist";

	}

}

package com.ssm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.pojo.Role;
import com.ssm.service.RoleService;

@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	/*获取用户角色名称*/
	@RequestMapping("/selectRoleName")
	@ResponseBody
	public List<Role> selectRoleName() {
		List<Role> role = roleService.selectRoleName();
		return role;
	}
}

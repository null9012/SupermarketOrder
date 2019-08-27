package com.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.mapper.RoleMapper;
import com.ssm.pojo.Role;
import com.ssm.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	@Autowired
	public RoleMapper roleMapper;

	@Override
	public List<Role> selectRoleName() {
		// TODO Auto-generated method stub
		return this.roleMapper.selectRoleName();
	}

}

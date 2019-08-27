package com.ssm.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.pojo.Role;

@Repository("roleMapper")
public interface RoleMapper {
	public List<Role> selectRoleName();
}

package com.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.mapper.UserMapper;
import com.ssm.pojo.User;
import com.ssm.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int insertUser(User user) {
		return this.userMapper.insertUser(user);
	}

	@Override
	public User selectUserCode(String userCode) {
		return this.userMapper.selectUserCode(userCode);
	}

	@Override
	public List<User> selectUserlist() {
		return this.userMapper.selectUserlist();
	}
	
	@Override
	public List<User> selectUserByCondition(User user) {
		return this.userMapper.selectUserByCondition(user);
	}
	
	@Override
	public int updateUser(User user) {
		return this.userMapper.updateUser(user);
	}

	@Override
	public int updatePwd(User user) {
		return this.userMapper.updatePwd(user);
	}

	@Override
	public int deleteUser(String userCode) {
		return this.userMapper.deleteUser(userCode);
	}

	@Override
	public List<User> selectUserlist(int currentPage, int pageSize) {
		List<User> userList = userMapper.selectUserlist();
		int firstIndex = (currentPage - 1) * pageSize;
		int lastIndex = currentPage * pageSize;
		if(lastIndex > userList.size()) lastIndex = userList.size();
		return userList.subList(firstIndex, lastIndex);
	}

	@Override
	public List<User> selectUserByCondition(User user,int currentPage, int pageSize) {
		List<User> userList = userMapper.selectUserByCondition(user);
		int firstIndex = (currentPage - 1) * pageSize;
		int lastIndex = currentPage * pageSize;
		if(lastIndex > userList.size()) lastIndex = userList.size();
		return userList.subList(firstIndex, lastIndex);
	}


	
	

}

package com.ssm.service;

import java.util.List;

import com.ssm.pojo.User;

public interface UserService {

	public int insertUser(User user);
	public User selectUserCode(String userCode);
	public List<User> selectUserlist();
	public List<User> selectUserlist(int currentPage,int pageSize);
	public List<User> selectUserByCondition(User user);
	public List<User> selectUserByCondition(User user,int currentPage,int pageSize);
	public int updateUser(User user);
	public int updatePwd(User user);
	public int deleteUser(String userCode);
}

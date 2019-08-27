package com.ssm.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.pojo.User;

@Repository("userMapper")
public interface UserMapper {
	
	public int insertUser(User user);
	public User selectUserCode(String userCode);
	public List<User> selectUserlist();
	public List<User> selectUserByCondition(User user);
	public int updateUser(User user);
	public int updatePwd(User user);
	public int deleteUser(String userCode);
}

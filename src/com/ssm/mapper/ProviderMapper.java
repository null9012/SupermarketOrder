package com.ssm.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.pojo.Provider;

@Repository("providerMapper")
public interface ProviderMapper {
	
	public Provider selectProviderCode(String proCode);
	public List<Provider> selectProviderList();
	public List<Provider> selectProviderByCondition(Provider provider);
	public int insertProvider(Provider provider);
	public int updateProvider(Provider provider);
	public int deleteProvider(String proCode);
	public List<Provider> providerList();
	
}

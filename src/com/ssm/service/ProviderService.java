package com.ssm.service;

import java.util.List;

import com.ssm.pojo.Provider;

public interface ProviderService {

	public Provider selectProviderCode(String proCode);
	public List<Provider> selectProviderList();
	public List<Provider> selectProviderByCondition(Provider provider);
	public int insertProvider(Provider provider);
	public int updateProvider(Provider provider);
	public int deleteProvider(String proCode);
	public List<Provider> providerList();
	
}

package com.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.mapper.ProviderMapper;
import com.ssm.pojo.Provider;
import com.ssm.service.ProviderService;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService{
	@Autowired
	private ProviderMapper providerMapper;

	@Override
	public Provider selectProviderCode(String proCode) {
		return this.providerMapper.selectProviderCode(proCode);
	}
	
	@Override
	public List<Provider> selectProviderList() {
		return this.providerMapper.selectProviderList();
	}

	@Override
	public int insertProvider(Provider provider) {
		return this.providerMapper.insertProvider(provider);
	}

	@Override
	public int updateProvider(Provider provider) {
		return this.providerMapper.updateProvider(provider);
	}

	@Override
	public int deleteProvider(String proCode) {
		return this.providerMapper.deleteProvider(proCode);
	}

	@Override
	public List<Provider> selectProviderByCondition(Provider provider) {
		return this.providerMapper.selectProviderByCondition(provider);
	}

	@Override
	public List<Provider> providerList() {
		// TODO Auto-generated method stub
		return providerMapper.providerList();
	}
	
	
}

package com.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.mapper.BillMapper;
import com.ssm.pojo.Bill;
import com.ssm.service.BillService;

@Service("billService")
public class BillServiceImpl implements BillService{
	@Autowired
	private BillMapper billMapper;
	
	@Override
	public List<Bill> selectByProviderId(int providerId) {
		return this.billMapper.selectByProviderId(providerId);
	}
	
	@Override
	public List<Bill> billList() {
		return billMapper.billList();
	}

	@Override
	public Bill selectBillById(String billCode) {
		return billMapper.selectBillById(billCode);
	}

	@Override
	public int createBill(Bill bill) {
		return billMapper.createBill(bill);
	}

	@Override
	public int updateBill(Bill bill) {
		return billMapper.updateBill(bill);
	}

	@Override
	public int deleteBill(Bill bill) {
		return billMapper.deleteBill(bill);
	}

	@Override
	public List<Bill> select(Bill bill) {
		return billMapper.select(bill);
	}

	@Override
	public Bill selectBillCode(String billCode) {
		// TODO Auto-generated method stub
		return billMapper.selectBillCode(billCode);
	}
	

}

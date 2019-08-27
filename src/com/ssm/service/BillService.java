package com.ssm.service;

import java.util.List;

import com.ssm.pojo.Bill;

public interface BillService {
	
	public List<Bill> selectByProviderId(int providerId);
	public List<Bill> billList();
	public Bill selectBillById(String billCode);
	public int createBill(Bill bill);
	public int updateBill(Bill bill);
	public int deleteBill(Bill bill);
	public List<Bill> select(Bill bill);
	public Bill selectBillCode(String billCode);
}

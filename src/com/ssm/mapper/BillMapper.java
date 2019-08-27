package com.ssm.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssm.pojo.Bill;

@Repository("billMapper")
public interface BillMapper {
	public List<Bill> selectByProviderId(int providerId);
	public List<Bill> billList();
	public Bill selectBillById(String billCode);
	public int createBill(Bill bill);  
	public int updateBill(Bill bill);
	public int deleteBill(Bill bill);
	public List<Bill> select(Bill bill);
	public Bill selectBillCode(String billCode);
}

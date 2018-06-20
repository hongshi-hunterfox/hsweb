package com.uclee.fundation.data.mybatis.mapping;
import java.util.List;

import com.uclee.fundation.data.mybatis.model.Account;
public interface AccountMapper {

	List<Account> getAccount(String account);
	
	List<Account> getPassword(String password);
}

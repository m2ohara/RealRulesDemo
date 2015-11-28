package com.realrules.data;

import java.sql.ResultSet;
import java.util.List;

public interface IExecuter<E> {
	
	void setSql(String sql);
	
	List<E> execute(ResultSet result); 

}

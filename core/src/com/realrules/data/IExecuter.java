package com.realrules.data;

import java.sql.ResultSet;
import java.util.List;

public interface IExecuter<E> {
	
	void setSql();
	
	List<E> getResult(ResultSet result); 

}

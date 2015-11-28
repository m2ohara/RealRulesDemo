package com.realrules.data;

import java.sql.ResultSet;
import java.util.List;

public class GetPlayerState<E> extends BaseExecuter<E> {

	@Override
	public void setSql(String sql) {
		this.sql = "SELECT * FROM PLAYER;"
		
	}

	@Override
	public List<E> execute(ResultSet result) {
		while(result.next()) {
			
		}
	}
	

}

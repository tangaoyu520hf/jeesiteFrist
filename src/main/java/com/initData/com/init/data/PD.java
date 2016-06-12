package com.initData.com.init.data;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.initData.util.DBUtil;

public class PD {
	@Test
	public void initData(){
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());	//sqlServer用
		List<Map<String, Object>> excuteQuery2 = sqlConnectionDB.excuteQuery("select * from dbo.PD_MXB order by PDJH;", null);
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery("select * from PD_MXB_CK where itmid!=''", null);
		
/*		for(Map<String, Object> map : excuteQuery2 ){
			System.out.println(map);
		}*/
		
		for(Map<String, Object> map : excuteQuery ){
			String itmid = map.get("ITMID")+"";
			for(Map<String, Object> map2 : excuteQuery2){
				String itmid2 = map2.get("ITMID")+"";
				if(itmid.equals(itmid2)){
					System.out.println(map);
				}
			}
		}
	}
}

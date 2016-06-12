package com.initData.com.init.data;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.initData.util.DBUtil;

/**
 * 初始化类型
 * @author tangaoyu
 *
 */
public class InitData2 {

	public static void main(String[] args) throws SQLException {
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnection());
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery("select codeid,parentid from STANDARD_TYPE where parentid !='0'", null);
		for(Map<String, Object> map :excuteQuery){
			StringBuffer sql1 = new StringBuffer("select codeid,parentid from STANDARD_TYPE start with codeid in (?)");
			sql1.append("connect by prior  PARENTID  =  codeid");
			List<Map<String, Object>> excuteQuery2 = sqlConnectionDB.excuteQuery(sql1.toString(), new Object[]{map.get("CODEID")});
			Map<String, Object> map2 = excuteQuery2.get(excuteQuery2.size()-1);
			String newCode = map2.get("CODEID")+"00"+map.get("CODEID");
			String pCode = "";
			if((map2.get("CODEID")+"").indexOf("Z")!=-1){
				pCode = map2.get("CODEID")+"";
			}else
				pCode= map2.get("CODEID")+"00"+map.get("PARENTID");
			//sqlConnectionDB.executeUpdate("update STANDARD_TYPE set codeid=?,CODE_TYPE=?,parentid=? where codeid =?" ,  new Object[]{newCode,newCode,pCode,map.get("CODEID")});
		}
		
		sqlConnectionDB.closeAll();
		
/*		StringBuffer sql = new StringBuffer();
		sql.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		sql.append("values(?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		Connection orcaleConnection = DBUtil.getOrcaleConnection();
		PreparedStatement createStatement = orcaleConnection.prepareStatement(sql.toString());
		System.out.println(excuteQuery.size());
		for(Map<String, Object> map :excuteQuery){
			createStatement.setObject(1, map.get("FLID"));
			createStatement.setObject(2, map.get("FLID"));
			createStatement.setObject(3, map.get("FLMC"));
			createStatement.setObject(4, "");
			createStatement.setObject(5, map.get("FJID"));
			createStatement.setObject(6,"admin");
			createStatement.setObject(7,"admin");
			createStatement.setObject(8,0);
			createStatement.setObject(9, 1);
			createStatement.setObject(10, 0);
			createStatement.setObject(11, map.get("IS_RECIPIENT"));
			createStatement.addBatch();
		}
		createStatement.executeBatch();
		createStatement.clearBatch();*/
	}
}

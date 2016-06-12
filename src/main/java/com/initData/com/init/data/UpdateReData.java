package com.initData.com.init.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.initData.util.DBUtil;

public class UpdateReData {
	public static void main(String[] args) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select rde.useid");
		sql.append(" from recipient_de rde");
		sql.append(" inner join recipient re");
		sql.append("   on re.useid = rde.useid");
		sql.append("  and re.bill_status = '2'");
		sql.append(" group by rde.useid");
		sql.append(" having count(*) > 15");
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnectionByZhengshi());
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery(sql.toString(), null);
		
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append(" select * from (");
		sql2.append(" select row_.*, rownum rownum_ from (");
		sql2.append(" select * from recipient_de rde  inner join recipient re");
		sql2.append("   on re.useid = rde.useid where rde.useid=? ORDER BY rde.itmid)row_ where rownum <= 99999) where rownum_>15");
		
		
		
		
/*		StringBuffer insertMoveLog = new StringBuffer();
		insertMoveLog.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		insertMoveLog.append(" values (?, sysdate, ?,?, 'Z81', ?, ?, ?, ?, '', '', 'admin', sysdate, 'admin', sysdate, '0')");
		
		Connection orcaleInsertConnection = DBUtil.getOrcaleConnectionByZhengshi();
		PreparedStatement sepcStatement = orcaleInsertConnection.prepareStatement(insertMoveLog.toString());*/
		
		
		
		
		
		Connection orcaleInsertConnection1 = DBUtil.getOrcaleConnectionByZhengshi();
		PreparedStatement sepcStatement1 = orcaleInsertConnection1.prepareStatement("update stock_pr set STOCKQTY = STOCKQTY-?,STOCKSUM = STOCKSUM-? where itmid=? and dept = ?");
		
		//PreparedStatement sepcStatement2 = orcaleInsertConnection1.prepareStatement("update stock_pr set ATP = ATP-? where itmid=? and dept = ? and STOCKLOC= ? ");
		
		
		
		for(Map<String, Object> map : excuteQuery){
			List<Map<String, Object>> excuteQuery2 = sqlConnectionDB.excuteQuery(sql2.toString(), new Object[]{map.get("USEID")});
			if(excuteQuery2.size()>0){
			for(Map<String, Object> map2 : excuteQuery2){
				String supplyDept = map2.get("SUPPLY_DEPT")+"";
				String supplyDeptPlace = map2.get("SUPPLY_DEPT_PLACE")+"";
				String imtid = map2.get("ITMID")+"";
				String useqyt =  map2.get("USEQTY")+"";
				String delipr = map2.get("DELIPR")+"";
				
				if("0003004597".equals(imtid)&&"5054".equals(supplyDept)){
					System.out.println();
				}
				
				Double valueOf = Double.valueOf(useqyt);
				Double valueOf2 = Double.valueOf(delipr);
				Double sum = valueOf*valueOf2;
				BigDecimal valueOf3 = BigDecimal.valueOf(sum);
				
				BigDecimal setScale = valueOf3.setScale(2, BigDecimal.ROUND_HALF_UP);
				
				sepcStatement1.setObject(1, useqyt);
				sepcStatement1.setObject(2, setScale);
				sepcStatement1.setObject(3, imtid);
				sepcStatement1.setObject(4, supplyDept);
				sepcStatement1.addBatch();
/*				sepcStatement.setObject(1, DBUtil.getSequence());
				sepcStatement.setObject(2, supplyDept);
				sepcStatement.setObject(3, supplyDeptPlace);
				sepcStatement.setObject(4, imtid);
				sepcStatement.setObject(5, useqyt);
				sepcStatement.setObject(6, delipr);
				sepcStatement.setObject(7, setScale);
				sepcStatement.addBatch();*/
			}
			}
		}
		//sepcStatement1.executeBatch();
		sqlConnectionDB.closeAll();
	}
}

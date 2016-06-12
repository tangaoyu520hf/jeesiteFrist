package com.initData.com.init.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.initData.util.DBUtil;


/**
 * 初始化移动记录
 * @author tangaoyu
 *
 */
public class InitData5 {

	public static void main(String[] args) throws SQLException {
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT temp.*,CAST((temp.movpr*temp.qty) AS DECIMAL(38,2))sum FROM ( ");
		sql.append("SELECT CAST(MAX(VZK.ZZJJ) AS DECIMAL(38,2)) movpr,CAST(sum(VZK.KCSL)AS DECIMAL(38,2)) qty,NS.itmid,BM.ORGID FROM V_ZCML_KCB VZK ");
		sql.append("INNER JOIN NEW_SPMC ns ON VZK.SPID=ns.SPID ");
		sql.append("INNER JOIN BMGX BM ON BM.KWID=VZK.KWID ");
		sql.append("WHERE VZK.KCSL!=0 AND VZK.KWID!=1 AND BM.ORGID != '0001' GROUP BY NS.itmid,BM.ORGID) temp ");
		
		
		
		
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery(sql.toString(), null);
		
		
		StringBuffer insertMoveLog = new StringBuffer();
		insertMoveLog.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		insertMoveLog.append(" values (?, sysdate, ?, '0001', '561', ?, ?, ?, ?, '', '', 'admin', sysdate, 'admin', sysdate, '1')");
		
		
		Connection orcaleInsertConnection = DBUtil.getOrcaleConnectionByZhengshi();
		PreparedStatement sepcStatement = orcaleInsertConnection.prepareStatement(insertMoveLog.toString());
		
		for(Map<String, Object> spec :excuteQuery){
			sepcStatement.setObject(1, DBUtil.getSequence());
			sepcStatement.setObject(2, spec.get("ORGID"));
			sepcStatement.setObject(3, spec.get("itmid"));
			sepcStatement.setObject(4, spec.get("qty"));
			sepcStatement.setObject(5, spec.get("movpr"));
			sepcStatement.setObject(6, spec.get("sum"));
			sepcStatement.addBatch();
		}
		//sepcStatement.executeBatch();
	}
	
	@Test
	public void initItmid() throws Exception{
		ConnectionDB oraConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnectionByZhengshi());
		
		StringBuffer sql = new StringBuffer();
		sql.append("select itmid,spid from SPECIFICATION where SPID is not null");
		String updateItimid="update NEW_SPMC set itmid=? where SPID=?";
		Connection sqlInsertConnection = DBUtil.getSqlConnection();
		PreparedStatement sepcStatement = sqlInsertConnection.prepareStatement(updateItimid.toString());
		
		List<Map<String, Object>> excuteQuery = oraConnectionDB.excuteQuery(sql.toString(), null);
		for(Map<String,Object> map:excuteQuery){
			sepcStatement.setString(1, map.get("ITMID")+"");
			sepcStatement.setString(2, map.get("SPID")+"");
			sepcStatement.addBatch();
		}
		sepcStatement.executeBatch();
		oraConnectionDB.closeAll();
		sqlInsertConnection.close();
	}
}

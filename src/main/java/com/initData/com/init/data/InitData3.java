package com.initData.com.init.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.initData.util.CodeType;
import com.initData.util.DBUtil;

/**
 * 初始化通用名
 * @author tangaoyu
 *
 */
public class InitData3 {

	public static void main(String[] args) throws SQLException {
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());
		ConnectionDB oraConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnection());
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT TYM,MAX(ZLMC),MAX(newFLID)newFLID FROM (");
		sql.append("SELECT NS.TYM,TF.FLID NEWFLID,NS.ZLMC  FROM (");
		sql.append(" SELECT TYM,ZLMC FROM NEW_SPMC GROUP BY TYM,ZLMC) NS");
		sql.append(" LEFT JOIN T_FLB TF ON TF.FLMC=NS.ZLMC AND LX='3' AND LX!='0'");
		sql.append(" )TEMP GROUP BY TYM");
		
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into GENERIC (GENID, ITMTYP, GENNAM, COURSE, SPELL, APPRFINI, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG, BILL_STATUS)");
		insertSql.append("values (?, ?, ?, '51701001', ?, '1', 'admin', sysdate, '', '', '0', '2')");
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery(sql.toString(), null);
		Connection orcaleInsertConnection = DBUtil.getOrcaleConnection();
		PreparedStatement createStatement = orcaleInsertConnection.prepareStatement(insertSql.toString());
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		CodeType sType=CodeType.Z011;
		int i=1;
		String sMaxCode=null;
		
		for(Map<String, Object> map :excuteQuery){
			String sql2 = "select * from standard_type where instr(codeid,'"+map.get("newFLID")+"')>0";
			List<Map<String, Object>> newTypeMapList = oraConnectionDB.excuteQuery(sql2, null);
			String str="";
			if(null==newTypeMapList||newTypeMapList.size()<1){
				str =map.get("newFLID")+""+map.get("ZLMC")+map.get("TYM");
				System.out.println(str);
				maps.add(map);
				continue;
			}
			i++;
			String sCode=sType.getHeadCode()+String.format("%06d", i);
			Map<String, Object> newTypeMap = newTypeMapList.get(0);
			String codeType = newTypeMap.get("CODEID")+"";
			System.out.println(""+newTypeMap.get("CODEID"));
			createStatement.setObject(1, sCode);
			createStatement.setObject(2, codeType);
			createStatement.setObject(3, ((map.get("TYM"))+"").trim());
			createStatement.setObject(4, DBUtil.converterToSpell(((map.get("TYM"))+"").trim()));
			createStatement.addBatch();
			sMaxCode=sCode;
				//throw new RuntimeException("空"+);
		}
		System.out.println(i);
		System.out.println(maps.size());
		int j = i;
		for(Map<String, Object> map :maps){
			j++;
			String sCode=sType.getHeadCode()+String.format("%05d", j);
			createStatement.setObject(1, sCode);
			createStatement.setObject(2, "Z003000594");
			createStatement.setObject(3, ((map.get("TYM"))+"").trim());
			createStatement.setObject(4, DBUtil.converterToSpell(((map.get("TYM")))+"".trim()));
			createStatement.addBatch();
		}
		createStatement.executeBatch();
		if(sMaxCode!=null){
			
			//DBUtil.setBaseCode(orcaleInsertConnection, sType, sMaxCode);
		}
		System.out.println(j+i);
		oraConnectionDB.closeAll();
		sqlConnectionDB.closeAll();
		orcaleInsertConnection.close();
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

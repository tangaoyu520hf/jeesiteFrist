package com.initData.com.init.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.initData.util.CodeType;
import com.initData.util.DBUtil;

public class JXinitData {
	
	public static void main(String[] args) {
		
	}
	
	
	/**
	 * 初始化公司及库存信息
	 * 
	 */
	public void initMoveLog() throws SQLException{
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());//sqlServer用
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery("select * from jx_pdb", null);
		
		StringBuffer insertconpanySql = new StringBuffer();
		insertconpanySql.append("insert into STANDARD_COMPANY (UUID, COMPID, ACTDAT, ITMID, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG)");
		insertconpanySql.append(" values (?, 'CK_JX_01', sysdate, ?, 'jx_admin', sysdate, '', '', '0')");
		
		StringBuffer insertMoveLog = new StringBuffer();
		insertMoveLog.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		insertMoveLog.append(" values (?, sysdate, ?, '0001', '561', ?, ?, ?, ?, '', '', 'jx_admin', sysdate, 'jx_admin', sysdate, '1')");
		
		Connection orcaleInsertConnection = DBUtil.getOrcaleConnectionByZhengshi();
		PreparedStatement conpanyStatement = orcaleInsertConnection.prepareStatement(insertconpanySql.toString());
		
		PreparedStatement moveLogStatement = orcaleInsertConnection.prepareStatement(insertMoveLog.toString());
		
		for(Map<String, Object> map : excuteQuery){
			String itmid = (map.get("SPGGID")+"").trim();
			conpanyStatement.setObject(1, DBUtil.getSequence());
			conpanyStatement.setObject(2, itmid);
			conpanyStatement.addBatch();
			int stockqty = Integer.valueOf((map.get("SL")+"").trim());
			BigDecimal price = BigDecimal.valueOf(Double.valueOf((map.get("JG")+"").trim()));
			if(stockqty!=0){
				moveLogStatement.setObject(1, DBUtil.getSequence());
				moveLogStatement.setObject(2, "CK_JX_01");
				moveLogStatement.setObject(3, map.get("SPGGID"));
				moveLogStatement.setObject(4, stockqty);
				moveLogStatement.setObject(5, price);
				
				BigDecimal insertStockqty =  BigDecimal.valueOf(Double.valueOf((map.get("SL")+"").trim()));
				BigDecimal sum = price.multiply(insertStockqty);
				sum = sum.setScale(5,   BigDecimal.ROUND_HALF_UP); 
				moveLogStatement.setObject(6, sum);
				moveLogStatement.addBatch();
			}
		}
		moveLogStatement.executeBatch();
		conpanyStatement.executeBatch();
		sqlConnectionDB.closeAll();
		orcaleInsertConnection.close();
	}
	
	/**
	 * 初始化商品规格信息
	 * @throws SQLException 
	 */
	public void initALLData() throws SQLException{
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());//sqlServer用
		ConnectionDB oraConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnectionByZhengshi());//查询用
		Connection orcaleInsertConnection = DBUtil.getOrcaleConnectionByZhengshi();
		
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery("select * from jx_pdb", null);
		
		
		StringBuffer insertSpecSql = new StringBuffer();
		insertSpecSql.append("insert into SPECIFICATION (UUID, GENID, ITMID, BRAND, STD, MODAL, MANUF, SPELL, APPRFINI, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG, GEN_SPE_NAME, BILL_STATUS,SPID)");
		insertSpecSql.append(" values (?, ?, ?, ?, ?, ?, ?, ?, '1', 'jx_admin', sysdate, '', '', '0',?, '2',?)");
		
		StringBuffer insertUnitSql = new StringBuffer();
		insertUnitSql.append("insert into STANDARD_UNIT (UUID, ITMID, MAINEAN, PACKSTUF, UNIT, DIVI, EAN, LENGTH, WEIGHT, HEIGHT, LENUNIT, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG)");
		insertUnitSql.append(" values (?, ?, '1','',?, '', '', '', '', '', '', 'jx_admin', sysdate, 'jx_admin', sysdate, '0')");
		
		PreparedStatement sepcStatement = orcaleInsertConnection.prepareStatement(insertSpecSql.toString());
		PreparedStatement unitOrclInsertStantement = orcaleInsertConnection.prepareStatement(insertUnitSql.toString());
		
		
		int i=6537;
		for(Map<String, Object> map : excuteQuery){
			String genid = (map.get("TYMID")+"").trim();
			String itmid = (map.get("SPGGID")+"").trim();
			List<Map<String, Object>> excuteQuery2 = oraConnectionDB.excuteQuery("select * from generic where genid=?", new Object[]{genid});
			if(null==excuteQuery2||excuteQuery2.size()<1){
				throw new RuntimeException("有通用名id不存在的数据");
			}
			//增加商品规格数据
			if(itmid == null || itmid.length() == 0){
				String gennam =  map.get("SPTYMC")+"";
				String genType = (map.get("SPFLID")+"").substring(0,4);
				CodeType codeType = CodeType.valueOf(genType);
				String sCode=codeType.getHeadCode()+String.format("%06d", i);
				String spmc = (map.get("PP")+"").trim()==null||(map.get("PP")+"").trim().length()==0?(map.get("SPTYMC")+"").trim():(map.get("PP")+"").trim();
				String gg = (map.get("GG")+"").trim();
				String lxh = (map.get("XH")+"").trim();	
				String sccj = (map.get("CJ")+"").trim();
				
				//规格
				sepcStatement.setObject(1, DBUtil.getSequence());
				sepcStatement.setObject(2, genid);
				sepcStatement.setObject(3, sCode);
				sepcStatement.setObject(4, spmc);
				sepcStatement.setObject(5, gg);
				sepcStatement.setObject(6, lxh);
				sepcStatement.setObject(7, sccj);
				sepcStatement.setObject(8,combinationSpellName(spmc, sccj));
				sepcStatement.setObject(9,gennam+" "+spmc+" "+gg+" "+lxh+" "+sccj);
				sepcStatement.setObject(10, "");
				sepcStatement.addBatch();
				
				//规格单位
				unitOrclInsertStantement.setObject(1, DBUtil.getSequence());
				unitOrclInsertStantement.setObject(2, sCode);
				unitOrclInsertStantement.setObject(3, "11");
				unitOrclInsertStantement.addBatch();
				
				i++;
				sqlConnectionDB.executeUpdate("update JX_PDB set SPGGID=? where uuid=?", new Object[]{sCode,map.get("UUID")});
			}else{
				List<Map<String, Object>> excuteQuery3 = oraConnectionDB.excuteQuery("select * from specification where itmid=?", new Object[]{itmid});
				if(null==excuteQuery3||excuteQuery3.size()<1){
					throw new RuntimeException("有规格id不存在的数据");
				}
			}
		}
		sepcStatement.executeBatch();
		unitOrclInsertStantement.executeBatch();
		oraConnectionDB.closeAll();
		sqlConnectionDB.closeAll();
		orcaleInsertConnection.close();
	}
	/**
	 * 初始化江苏通用名
	 * @throws SQLException 
	 */
	public void initTYMData() throws SQLException{
		//通用名插入语句
				StringBuffer insertSql = new StringBuffer();
				insertSql.append("insert into GENERIC (GENID, ITMTYP, GENNAM, COURSE, SPELL, APPRFINI, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG, BILL_STATUS)");
				insertSql.append("values (?, ?, ?, '51701001', ?, '1', 'jx_admin', sysdate, '', '', '0', '2')");
				
				String updateTYMID = "update JX_PDB set tymid=? where sptymc=?";
				
				ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());//sqlServer用
				ConnectionDB oraConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnectionByZhengshi());//查询用
				Connection orcaleInsertConnection = DBUtil.getOrcaleConnectionByZhengshi();
				PreparedStatement createStatement = orcaleInsertConnection.prepareStatement(insertSql.toString());
				List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery("select sptymc,spflid from jx_pdb where tymid = '' group by sptymc,spflid", null);
				
				int i=3881;
				CodeType sType=CodeType.Z011;
				
				for(Map<String, Object> map : excuteQuery){
					String sptymc = (map.get("sptymc")+"").trim();
					List<Map<String, Object>> excuteQuery2 = oraConnectionDB.excuteQuery("select * from generic where gennam=?", new Object[]{sptymc});
					String tymid="";
					//新增通用名
					if(null==excuteQuery2||excuteQuery2.size()<1){
						String sCode=sType.getHeadCode()+String.format("%06d", i);
						createStatement.setObject(1, sCode);
						createStatement.setObject(2, (map.get("spflid")));
						createStatement.setObject(3, sptymc);
						createStatement.setObject(4, DBUtil.converterToSpell(sptymc));
						createStatement.addBatch();
						tymid = sCode;
						i++;
					}else{
						Map<String, Object> map2 = excuteQuery2.get(0);
						tymid =  map2.get("GENID")+"";
					}
					sqlConnectionDB.executeUpdate(updateTYMID, new Object[]{tymid,sptymc});
				}
				createStatement.executeBatch();
				oraConnectionDB.closeAll();
				sqlConnectionDB.closeAll();
				orcaleInsertConnection.close();
				System.out.println(i);
	}
	
	public static String combinationSpellName(String spmc,String sccj){
		StringBuffer genSpeSpell = new StringBuffer();
		genSpeSpell.append(DBUtil.converterToSpell(spmc)).append(" ")
		.append(DBUtil.converterToSpell(sccj)).append(" ");
		return genSpeSpell.toString();
	}
	
	public static String combinationName(String spmc,String sccj){
		StringBuffer genSpeSpell = new StringBuffer();
		genSpeSpell.append(DBUtil.converterToSpell(spmc)).append(" ")
		.append(DBUtil.converterToSpell(sccj)).append(" ");
		return genSpeSpell.toString();
	}
	
}

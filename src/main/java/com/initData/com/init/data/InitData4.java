package com.initData.com.init.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.initData.util.CodeType;
import com.initData.util.DBUtil;

/**
 * 初始化商品规格及单位
 * @author tangaoyu
 *
 */
public class InitData4 {

	public static void main(String[] args) throws SQLException {
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());
		ConnectionDB oraConnectionDB = new ConnectionDB(DBUtil.getOrcaleConnection());
		ConnectionDB oraConnectionDB2 = new ConnectionDB(DBUtil.getOrcaleConnection());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ns.TYM,ts.SPID,ts.SPMC,ts.JBDW,ts.SCCJ,ts.GG,ts.LXH FROM NEW_SPMC ns INNER JOIN T_SPXX ts ON ns.SPID=ts.SPID");
		
		String unitSql = "select dict_name as name,dict_code as value from sys_dict@INVER_LINK where deletelable='0' and type_code ='unit'  order by dict_orders";
		
		List<Map<String, Object>> excuteQuery = sqlConnectionDB.excuteQuery(sql.toString(), null);
		List<Map<String, Object>> unitListMap = oraConnectionDB.excuteQuery(unitSql, null);
		
		int i=1;
		String sMaxCode=null;
		
		StringBuffer insertSpecSql = new StringBuffer();
		insertSpecSql.append("insert into SPECIFICATION (UUID, GENID, ITMID, BRAND, STD, MODAL, MANUF, SPELL, APPRFINI, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG, GEN_SPE_NAME, BILL_STATUS,SPID)");
		insertSpecSql.append(" values (?, ?, ?, ?, ?, ?, ?, ?, '1', 'admin', sysdate, '', '', '0',?, '2',?)");
		
		StringBuffer insertUnitSql = new StringBuffer();
		insertUnitSql.append("insert into STANDARD_UNIT (UUID, ITMID, MAINEAN, PACKSTUF, UNIT, DIVI, EAN, LENGTH, WEIGHT, HEIGHT, LENUNIT, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG)");
		insertUnitSql.append(" values (?, ?, '1','',?, '', '', '', '', '', '', 'admin', sysdate, 'admin', sysdate, '0')");
		
		StringBuffer insertconpanySql = new StringBuffer();
		insertconpanySql.append("insert into STANDARD_COMPANY (UUID, COMPID, ACTDAT, ITMID, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DELETE_FLAG)");
		insertconpanySql.append(" values (?, 'CK_CS_01', sysdate, ?, 'admin', sysdate, '', '', '0')");
		
		
		Set<String> set = new HashSet<String>();
		
		
		Connection orcaleInsertConnection = DBUtil.getOrcaleConnection();
		Connection unitOrclInsertConnection = DBUtil.getOrcaleConnection();
		Connection conpanyOrclInsertConnection = DBUtil.getOrcaleConnection();
		PreparedStatement sepcStatement = orcaleInsertConnection.prepareStatement(insertSpecSql.toString());
		PreparedStatement unitOrclInsertStantement = unitOrclInsertConnection.prepareStatement(insertUnitSql.toString());
		PreparedStatement conpanyStatement = conpanyOrclInsertConnection.prepareStatement(insertconpanySql.toString());
		
		for(Map<String, Object> spec :excuteQuery){
			String gennameSql = "select * from generic where GENNAM = ?";
			List<Map<String, Object>> genListMap = oraConnectionDB2.excuteQuery(gennameSql, new Object[]{(spec.get("TYM")+"").trim()});
			if(genListMap==null||genListMap.size()<1){
				System.out.println(spec.get("TYM")+""+spec.get("SPID"));
				continue;
			}
			i++;
			Map<String, Object> geninfo = genListMap.get(0);
			String genid = geninfo.get("GENID")+"";
			String gennam =  geninfo.get("GENNAM")+"";
			String genType = (geninfo.get("ITMTYP")+"").substring(0,4);
			CodeType codeType = CodeType.valueOf(genType);
			String sCode=codeType.getHeadCode()+String.format("%06d", i);
			String spid = (spec.get("SPID")+"").trim();
			String spmc = (spec.get("SPMC")+"").trim();
			String gg = (spec.get("GG")+"").trim();
			String lxh = (spec.get("LXH")+"").trim();	
			String sccj = (spec.get("SCCJ")+"").trim();
			String newJbdw = "";
			String specOldUnitName = (spec.get("JBDW")+"").trim();
			for(Map<String, Object> unitMap:unitListMap){
				String unitName = (unitMap.get("NAME")+"").trim();
				if(unitName.equals(specOldUnitName)){
					newJbdw=unitMap.get("VALUE")+"";
					break;
				}
			}
			if("".equals(newJbdw)){
				newJbdw = specOldUnitName;
				System.out.println(newJbdw);
			}
			sepcStatement.setObject(1, DBUtil.getSequence());
			sepcStatement.setObject(2, genid);
			sepcStatement.setObject(3, sCode);
			sepcStatement.setObject(4, spmc);
			sepcStatement.setObject(5, gg);
			sepcStatement.setObject(6, lxh);
			sepcStatement.setObject(7, sccj);
			sepcStatement.setObject(8,combinationSpellName(spmc, sccj));
			sepcStatement.setObject(9,gennam+" "+spmc+" "+gg+" "+lxh+" "+sccj);
			sepcStatement.setObject(10, spid);
			sepcStatement.addBatch();
			
			unitOrclInsertStantement.setObject(1, DBUtil.getSequence());
			unitOrclInsertStantement.setObject(2, sCode);
			unitOrclInsertStantement.setObject(3, newJbdw);
			unitOrclInsertStantement.addBatch();
			
			conpanyStatement.setObject(1, DBUtil.getSequence());
			conpanyStatement.setObject(2, sCode);
			conpanyStatement.addBatch();
		}
		System.out.println(i++);
		sepcStatement.executeBatch();
		conpanyStatement.executeBatch();
		unitOrclInsertStantement.executeBatch();
/*		StringBuffer unitUstore  = new StringBuffer();
		unitUstore.append("insert into SYS_DICT (DICT_ID, TYPE_CODE, DICT_CODE, DICT_NAME, DICT_LEVEL, DELETELABLE, DICT_ORDERS)");
		unitUstore.append(" values (?,'unit', ?, ?, '1','0',15)");
		
		Connection unitUstoreConnection = DBUtil.getOrcaleConnection2();
		PreparedStatement unitStoreStatement = unitUstoreConnection.prepareStatement(unitUstore.toString());
		int c=40;
		for(String str : set){
			unitStoreStatement.setObject(1, DBUtil.getSequence());
			unitStoreStatement.setObject(2, ++c);
			unitStoreStatement.setObject(3, str.trim());
			unitStoreStatement.addBatch();
		}
		unitStoreStatement.executeBatch();*/
/*		for(Map<String, Object> map :excuteQuery){
			String sql2 = "select * from standard_type where instr(codeid,'"+map.get("newFLID")+"')>0";
			List<Map<String, Object>> newTypeMapList = oraConnectionDB.excuteQuery(sql2, null);
			String str="";
			if(null==newTypeMapList||newTypeMapList.size()<1){
				str =map.get("newFLID")+""+map.get("ZLMC")+map.get("TYM");
				System.out.println(str);
				continue;
			}
			i++;
			String sCode=sType.getHeadCode()+String.format("%05d", i);
			Map<String, Object> newTypeMap = newTypeMapList.get(0);
			String codeType = newTypeMap.get("CODEID")+"";
			System.out.println(""+newTypeMap.get("CODEID"));
			sepcStatement.setObject(1, sCode);
			sepcStatement.setObject(2, codeType);
			sepcStatement.setObject(3, map.get("TYM"));
			sepcStatement.setObject(4, DBUtil.converterToSpell(map.get("TYM")+""));
			sepcStatement.addBatch();
			sMaxCode=sCode;
				//throw new RuntimeException("空"+);
		}
		sepcStatement.executeBatch();
		if(sMaxCode!=null){
			DBUtil.setBaseCode(orcaleInsertConnection, sType, sMaxCode);
		}*/
/*		oraConnectionDB.closeAll();
		sqlConnectionDB.closeAll();
		orcaleInsertConnection.close();*/
/*		StringBuffer sql = new StringBuffer();
		sql.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		sql.append("values(?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		Connection orcaleConnection = DBUtil.getOrcaleConnection();
		PreparedStatement sepcStatement = orcaleConnection.prepareStatement(sql.toString());
		System.out.println(excuteQuery.size());
		for(Map<String, Object> map :excuteQuery){
			sepcStatement.setObject(1, map.get("FLID"));
			sepcStatement.setObject(2, map.get("FLID"));
			sepcStatement.setObject(3, map.get("FLMC"));
			sepcStatement.setObject(4, "");
			sepcStatement.setObject(5, map.get("FJID"));
			sepcStatement.setObject(6,"admin");
			sepcStatement.setObject(7,"admin");
			sepcStatement.setObject(8,0);
			sepcStatement.setObject(9, 1);
			sepcStatement.setObject(10, 0);
			sepcStatement.setObject(11, map.get("IS_RECIPIENT"));
			sepcStatement.addBatch();
		}
		sepcStatement.executeBatch();
		sepcStatement.clearBatch();*/
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

package com.initData.com.init.data;
import java.sql.CallableStatement;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import com.initData.util.DBUtil;

/** 
 * 数据库连接类 
 * 说明:封装了 无参，有参，存储过程的调用 
 * @author iflytek 
 * 
 */  
public class ConnectionDB {  
  
    /** 
     * 创建数据库连接对象 
     */  
    private Connection connnection = null;  
  
    public Connection getConnnection() {
		return connnection;
	}

	public void setConnnection(Connection connnection) {
		this.connnection = connnection;
	}
	/** 
     * 创建PreparedStatement对象 
     */  
    private PreparedStatement preparedStatement = null;  
      
    /** 
     * 创建CallableStatement对象 
     */  
    private CallableStatement callableStatement = null;  
  
    /** 
     * 创建结果集对象 
     */  
    private ResultSet resultSet = null;  
  
/*    static {  
        try {  
            // 加载数据库驱动程序  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
            System.out.println("加载驱动错误");  
            System.out.println(e.getMessage());  
        }  
    }  */
  
    
    public ConnectionDB (Connection connection){
        // 获取连接  
    	this.connnection=connection;
    }
  
    /** 
     * insert update delete SQL语句的执行的统一方法 
     * @param sql SQL语句 
     * @param params 参数数组，若没有参数则为null 
     * @return 受影响的行数 
     */  
    public int executeUpdate(String sql, Object[] params) {  
        // 受影响的行数  
        int affectedLine = 0;  
          
        try {  
            // 获得连接  
            // 调用SQL   
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 参数赋值  
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 执行  
            affectedLine = preparedStatement.executeUpdate();  
  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // 释放资源  
           // closeAll();  
        }  
        return affectedLine;  
    }  
  
    /** 
     * SQL 查询将查询结果直接放入ResultSet中 
     * @param sql SQL语句 
     * @param params 参数数组，若没有参数则为null 
     * @return 结果集 
     */  
    private ResultSet executeQueryRS(String sql, Object[] params) {  
        try {  
            // 获得连接  
              
            // 调用SQL  
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 参数赋值  
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 执行  
            resultSet = preparedStatement.executeQuery();  
  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }   
  
        return resultSet;  
    }  
      
    /** 
     * SQL 查询将查询结果：一行一列 
     * @param sql SQL语句 
     * @param params 参数数组，若没有参数则为null 
     * @return 结果集 
     */  
    public Object executeQuerySingle(String sql, Object[] params) {  
        Object object = null;  
        try {  
            // 获得连接  
              
            // 调用SQL  
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 参数赋值  
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 执行  
            resultSet = preparedStatement.executeQuery();  
  
            if(resultSet.next()) {  
                object = resultSet.getObject(1);  
            }  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
           // closeAll();  
        }  
  
        return object;  
    }  
  
    /** 
     * 获取结果集，并将结果放在List中 
     *  
     * @param sql 
     *            SQL语句 
     * @return List 
     *                       结果集 
     */  
    public List<Map<String, Object>> excuteQuery(String sql, Object[] params) {  
        // 执行SQL获得结果集  
        ResultSet rs = executeQueryRS(sql, params);  
          
        // 创建ResultSetMetaData对象  
        ResultSetMetaData rsmd = null;  
          
        // 结果集列数  
        int columnCount = 0;  
        try {  
            rsmd = rs.getMetaData();  
              
            // 获得结果集列数  
            columnCount = rsmd.getColumnCount();  
        } catch (SQLException e1) {  
            System.out.println(e1.getMessage());  
        }  
  
        // 创建List  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
  
        try {  
            // 将ResultSet的结果保存到List中  
            while (rs.next()) {  
                Map<String, Object> map = new HashMap<String, Object>();  
                for (int i = 1; i <= columnCount; i++) {  
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));  
                }  
                list.add(map);  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // 关闭所有资源  
            // 关闭PreparedStatement对象  
            if (preparedStatement != null) {  
                try { 
                    preparedStatement.close();  
                } catch (SQLException e) {  
                    System.out.println(e.getMessage());  
                }  
            }  
              
            // 关闭CallableStatement 对象  
            if (callableStatement != null) {  
                try {  
                    callableStatement.close();  
                } catch (SQLException e) {  
                    System.out.println(e.getMessage());  
                }  
            }  
        }  
  
        return list;  
    }  
      
    /** 
     * 存储过程带有一个输出参数的方法 
     * @param sql 存储过程语句 
     * @param params 参数数组 
     * @param outParamPos 输出参数位置 
     * @param SqlType 输出参数类型 
     * @return 输出参数的值 
     */  
    public Object excuteQuery(String sql, Object[] params,int outParamPos, int SqlType) {  
        Object object = null;  
        try {  
            // 调用存储过程  
            callableStatement = connnection.prepareCall(sql);  
              
            // 给参数赋值  
            if(params != null) {  
                for(int i = 0; i < params.length; i++) {  
                    callableStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 注册输出参数  
            callableStatement.registerOutParameter(outParamPos, SqlType);  
              
            // 执行  
            callableStatement.execute();  
              
            // 得到输出参数  
            object = callableStatement.getObject(outParamPos);  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            // 释放资源  
           // closeAll();  
        }  
          
        return object;  
    }  
  
    /** 
     * 关闭所有资源 
     */  
    void closeAll() {  
        // 关闭结果集对象  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        // 关闭PreparedStatement对象  
        if (preparedStatement != null) {  
            try { 
                preparedStatement.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
          
        // 关闭CallableStatement 对象  
        if (callableStatement != null) {  
            try {  
                callableStatement.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        // 关闭Connection 对象  
        if (connnection != null) {  
            try {  
                connnection.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }     
    }
    public static void main(String[] args) throws Exception {
		ConnectionDB sqlConnectionDB = new ConnectionDB(DBUtil.getSqlConnection());
		StringBuffer buffer = new StringBuffer();
		/*buffer.append(" SELECT     a.SPID, b.SPMC '商品名称',case CHARINDEX(' ',b.SPMC) when 0 then b.SPMC ");
		buffer.append("   else SUBSTRING(b.SPMC,0,CHARINDEX(' ',b.SPMC)) end '新商品通用名称（参考）',");
		buffer.append("                    b.ZCMC '资产名称', b.DLMC '大类名称', b.XLMC '细类名称',b.ZLMC '子类名称'");
		buffer.append("FROM         dbo.T_ZCMLB AS a INNER JOIN");
		buffer.append("                     dbo.V_SPXX_FLB AS b ON a.SPID = b.SPID LEFT OUTER JOIN");
		buffer.append("                     dbo.T_GYXX AS c ON a.GYID = c.GYID GROUP BY a.SPID,b.SPMC,b.BMMC, b.ZCMC, b.DLMC, b.XLMC,b.ZLMC");*/
		//List<Object> excuteQuery = sqlConnectionDB.excuteQuery(buffer.toString(), null);
		sqlConnectionDB.closeAll();
		StringBuffer sql = new StringBuffer();
		/*sql.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		sql.append("values('40287eb651566bd9015158a153db0049', null, 'CK_CS_01', '0001', '101', '0001000023', 5000, 0, 0, '4100000052', '1', '谢会军', null, null, null, '1')");*/
		sql.append("insert into MOVE_LOG (UUID, MOVDAT, DEPT, STOCKLOC, MOVTYP, ITMID, MOVQTY, MOVPR, MOVSUM, REFERID, REFERPOS, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RELEA_STORAGE)");
		sql.append("values(?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		Connection orcaleConnection = DBUtil.getOrcaleConnection();
		PreparedStatement createStatement = orcaleConnection.prepareStatement(sql.toString());
		for(int i=1;i<=16;i++){
			if(i==2||i==15||i==13){
				createStatement.setObject(i, "");
			}else if(i==7||i==8||i==9){
				createStatement.setInt(i,i);
			}
			else
				createStatement.setObject(i, i);
		}
		System.out.println(orcaleConnection);
		createStatement.addBatch();
		createStatement.executeBatch();
		orcaleConnection.close();
	}
}  
package preparedstatement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import 数据库课程设计.ConnectionTest;
import 数据库课程设计.JDBCUtils;

public class updata {
	
//	
	public void testInsert(){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties pros =new Properties();
			
			pros.load(is);
			
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			String url = pros.getProperty("url");
			String driverClass = pros.getProperty("driverClass");
			//加载驱动
			Class.forName(driverClass);
			
			//获取连接
			conn = DriverManager.getConnection(url,user,password);
			
			String sql="insert into student(id,sname,ssex,scollege,smajor"
					+ ",sclass,sage,sphone,native_place,state)values(?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, 6);
			ps.setString(2, "小王");
			ps.setString(3, "小王");
			ps.setString(4, "小王");
			ps.setString(5, "小王");
			ps.setString(6, "小王");
			ps.setInt(7, 16);
			ps.setString(8, "小王");
			ps.setString(9, "小王");
			ps.setString(10, "小王");
			
			//执行sql语句
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (conn!=null) {
					conn.close();
				}
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	//public void testUpdate() 
	public static void main(String[] args){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn =JDBCUtils.getConnection();
			String sql = "update student set sname = ?  where id = ?";
			ps=conn.prepareStatement(sql);
			ps.setObject(1,"修改的数据");
			ps.setObject(2,6);
			
			ps.execute();
			
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps);
		}
	}
	
	
	
}

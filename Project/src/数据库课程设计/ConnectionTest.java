package 数据库课程设计;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
	public static void main(String[] args) throws Exception{
		
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
		Connection conn = DriverManager.getConnection(url,user,password);
		System.out.println(conn);
	}
}

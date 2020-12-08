package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.atguigu.p2.CustomerList;

import main.basic.MyPanel;
import main.basic.MyWindows;
import 数据库课程设计.ConnectionTest;

public class ForthPanel extends MyPanel{

	public ForthPanel(final MyWindows windows) 
	{
		super(windows, "删除用户");
		this.setLayout(null);
		JButton buttondel =new JButton("删除");
		JButton buttonback=new JButton("back");
		final JTextField textfield=new JTextField(3);
		JLabel label=new JLabel("删除的客户：");

		textfield.setBounds(150, 80, 100, 30);
		label.setBounds(220, 40, 100, 30);
		buttondel.setBounds(250, 80, 100, 30);
		buttonback.setBounds(150, 400, 100, 30);
		add(label);
		add(textfield);
		add(buttonback);
		add(buttondel);
		
		buttonback.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                windows.back();
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
		
		buttondel.addMouseListener(new MouseListener() {
			Connection conn=null;
    		PreparedStatement ps=null;
			public void mouseClicked(MouseEvent mouseEvent) 
			{
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
					

        			String sql="Delete from student where"
        					+"id= ?";
        			
        			ps.setObject(1, textfield.getText());
        			ps = conn.prepareStatement(sql);
        			
        			ps.execute();

					
					windows.back();
					System.out.println("删除成功");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			public void mousePressed(MouseEvent mouseEvent) {}
			public void mouseReleased(MouseEvent mouseEvent) {}
			public void mouseEntered(MouseEvent mouseEvent) {}
			public void mouseExited(MouseEvent mouseEvent) {}
		});
	}


	

}

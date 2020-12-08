package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

import main.basic.MyPanel;
import main.basic.MyWindows;
import 数据库课程设计.ConnectionTest;

public class SecondPanel extends MyPanel {

    SecondPanel(final MyWindows windows){
        super(windows,"新增用户");
        final ArrayList<JTextField> textList =new ArrayList<JTextField>();

    	ArrayList<JLabel> labelList =new ArrayList<JLabel>();
    	
        
        JButton buttonback=new JButton("返回");
        JButton buttonyes=new JButton("确认");
        buttonback.setBounds(150, 400, 100, 30);
        buttonyes.setBounds(250, 400, 100, 30);
		this.setLayout(null);

        final String[] shapeArr= {
				"sid",
				"日期",
				"地区",
				"接触史",
				"问题1：是否确诊",
				"问题2：是否与确诊患者有直接接触",
				"问题3：亲属是否确诊",
				};
		
		for(int i=0,y=60;i<shapeArr.length;i++){
		JLabel bl=new JLabel(shapeArr[i]);//创建按钮
		JTextField textField=new JTextField(15); 
		textList.add(textField);
		add(textField);
		textField.setBounds(250, y, 200, 30);
		bl.setBounds(40,y-10,200,50);
		labelList.add(bl);
		y=y+40;
		add(bl);//
		}
		//add(buttonList.get(0), BorderLayout.CENTER);
		
        
        buttonback.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                windows.back();
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        
        buttonyes.addMouseListener(new MouseListener() 
        {
			            public void mouseClicked(MouseEvent mouseEvent) 
			            {
			                
			                
			            	final String sid=textList.get(0).getText();
			            	final String answer_date=textList.get(1).getText();
			            	final String region=textList.get(2).getText();
			            	final String touch_history=textList.get(3).getText();
			            	final String answer_q1=textList.get(4).getText();
			            	final  String answer_q2=textList.get(5).getText();
			            	final String answer_q3=textList.get(6).getText();
			                
			            	
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
			            			
			            			String sql="insert into info(sid,answer_date,region,touch_history,answer_q1"
			            					+ ",answer_q2,answer_q3) values(?,?,?,?,?,?,?)";
			            			ps = conn.prepareStatement(sql);
			            			
			            			ps.setObject(1, Integer.parseInt(sid));
			            			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			            			java.util.Date date = sdf.parse(answer_date);
			            			
			            			ps.setObject(2, new Date(date.getTime()));
			            			ps.setString(3, region);
			            			ps.setString(4, touch_history);
			            			ps.setObject(5, Integer.parseInt(answer_q1));
			            			ps.setObject(6, Integer.parseInt(answer_q2));
			            			ps.setObject(7, Integer.parseInt(answer_q3));
			            			
			            			//执行sql语句
			            			ps.execute();
					                windows.back();
					            	System.out.println("添加成功");
			            		} 
			            		
			            		catch(NumberFormatException e){
				            		if(sid.isBlank())
					                	System.out.println("请先输入sid");
				            		int isnumber=1;
				            		for (int i = 0; i < sid.length(); i++)
				            		{
				            			if(!Character.isDigit(sid.charAt(i)))
				            				isnumber=0;
				            		}
				            		if(isnumber==0)
					            		JOptionPane.showMessageDialog(null, "请输入正确sid","标题【出错啦】",  JOptionPane.ERROR_MESSAGE);
				            		else
				            		{
				            		if(Integer.parseInt(sid)<0&&Integer.parseInt(sid)>36)
					            		JOptionPane.showMessageDialog(null, "该sid不存在","标题【出错啦】",  JOptionPane.ERROR_MESSAGE);

					                if(answer_date.isBlank())
				            		JOptionPane.showMessageDialog(null, "请先输入日期","标题【输入缺失】",  JOptionPane.ERROR_MESSAGE);
					                if(region.isBlank())
				            		JOptionPane.showMessageDialog(null, "请先输入地区","标题【输入缺失】",  JOptionPane.ERROR_MESSAGE);
					                if(touch_history.isBlank())
				            		JOptionPane.showMessageDialog(null,"请先输入是否有接触史","标题【输入缺失】",  JOptionPane.ERROR_MESSAGE);
					                if(answer_q1.isBlank())
				            		JOptionPane.showMessageDialog(null,"请先输入是否确诊","标题【输入缺失】",  JOptionPane.ERROR_MESSAGE);
					                if(answer_q2.isBlank())
				            		JOptionPane.showMessageDialog(null, "请先输入是否与确诊患者接触","标题【输入缺失】",  JOptionPane.ERROR_MESSAGE);
					                if(answer_q3.isBlank())
				            		JOptionPane.showMessageDialog(null, "请先输入是否有亲属确诊","标题【输入缺失】",  JOptionPane.ERROR_MESSAGE);
				            		}
					                
				            	}
			            		catch (ParseException e) {
			            			JOptionPane.showMessageDialog(null, "请输入正确的时间（yyyy-MM-dd）","标题【输入格式错误】",  JOptionPane.ERROR_MESSAGE);
			            		}
			            		catch(SQLException e) {
//				            		if (e.getMessage()=="Cannot add or update a child row: "
//				            				+ "a foreign key constraint fails "
//				            				+ "(`mydatabase(kechengsheji)`.`info`, CONSTRAINT `fk_info` "
//				            				+ "FOREIGN KEY (`sid`) REFERENCES `all_name` (`id`))") 
			            			if(e.getErrorCode()==1452 && e.getErrorCode()!=1406 )
				            		{
				            			JOptionPane.showMessageDialog(null, "该学生不存在","标题【输入错误】",  JOptionPane.ERROR_MESSAGE);
									}
			            			if(e.getErrorCode()==1062)
			            			{
			            				JOptionPane.showMessageDialog(null, "该学生本日信息已存在","标题【信息重复】",  JOptionPane.ERROR_MESSAGE);
			            			}
			            			if(!answer_q1.contentEquals("1")&&!answer_q1.contentEquals("0"))
				            			JOptionPane.showMessageDialog(null, "请对问题1输入正确布尔型的回答（0或1）","标题【输入格式错误】",  JOptionPane.ERROR_MESSAGE);
					                if(!answer_q2.contentEquals("1")&&!answer_q2.contentEquals("0"))
				            			JOptionPane.showMessageDialog(null, "请对问题2输入正确布尔型的回答（0或1）","标题【输入格式错误】",  JOptionPane.ERROR_MESSAGE);
					                if(!answer_q3.contentEquals("1")&&!answer_q3.contentEquals("0"))
				            			JOptionPane.showMessageDialog(null, "请对问题3输入正确布尔型的回答（0或1）","标题【输入格式错误】",  JOptionPane.ERROR_MESSAGE);
				            				            		}
			            		catch (Exception e) {
					                System.out.println(e.getMessage());
					                
			            		}
			            		
			            		
			            		
			            		finally 
				            		{
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
				            				e.printStackTrace();
				            			}
				            		}
			            		
			            		
			            	
			            	}

			            	
			            
        			
		            public void mousePressed(MouseEvent mouseEvent) {}
		            public void mouseReleased(MouseEvent mouseEvent) {}
		            public void mouseEntered(MouseEvent mouseEvent) {}
		            public void mouseExited(MouseEvent mouseEvent) {}
		        });
        	
        add(buttonback);
        add(buttonyes);
    }
}

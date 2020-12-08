package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.basic.MyPanel;
import main.basic.MyWindows;
import 数据库课程设计.ConnectionTest;

public class ThirdPanel extends MyPanel {

	static int num;
	static String qname;
	static String qsex;
	static String qcollege;
	static String qmajor;
	static String qclass;
	static String qage;
	static String qphone;
	static String qnative_place;
	static String qstate;

	public ThirdPanel(final MyWindows windows) {

		super(windows, "修改数据");
		final MyPanel that = this;
		ArrayList<JLabel> labelList1 = new ArrayList<JLabel>();
		final ArrayList<JLabel> labelList2 = new ArrayList<JLabel>();
		final ArrayList<JTextField> textList = new ArrayList<JTextField>();

		this.setLayout(null);

		JButton button = new JButton("确认");
		JButton buttonback = new JButton("返回");
		JButton buttonyes = new JButton("修改");
		JButton buttondel = new JButton("删除");
		JLabel label = new JLabel("修改的客户：");
		label.setBounds(220, 40, 100, 30);
		button.setBounds(250, 80, 100, 30);
		final JTextField selecttextField = new JTextField();
		selecttextField.setBounds(150, 80, 100, 30);
		buttonback.setBounds(120, 400, 100 ,30);
		buttonyes.setBounds(215, 400, 100, 30);
		buttondel.setBounds(310, 400, 100, 30);

		final String[] shapeArr = { "名字", "性别", "年龄", "学院", "专业", "班级", "电话", "籍贯", "状态", };

		for (int i = 0, y = 100; i < shapeArr.length; i++) {
			JLabel bl = new JLabel(shapeArr[i]);

			JTextField textField = new JTextField(15);
			textList.add(textField);
			textField.setBounds(100, y + 13, 100, 25);

			bl.setBounds(40, y, 50, 50);
			labelList1.add(bl);
			y = y + 33;
			add(bl);
		}

		add(selecttextField);
		add(label);
		add(button);
		add(buttonyes);
		add(buttonback);
		add(buttondel);

		// 监听器：显示用户信息
		button.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseEvent) {

				Connection conn = null;
				PreparedStatement ps = null;
				try {
					num = Integer.parseInt(selecttextField.getText());

					InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
					Properties pros = new Properties();

					pros.load(is);

					String user = pros.getProperty("user");
					String password = pros.getProperty("password");
					String url = pros.getProperty("url");
					String driverClass = pros.getProperty("driverClass");
					// 加载驱动
					Class.forName(driverClass);

					// 获取连接
					conn = DriverManager.getConnection(url, user, password);
					String sql = "select all_name.full_name,student.* from "
							+ "all_name,student where all_name.id=student.sid and " + "sid=" + num + ";";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					

					rs.next();

					qname = rs.getString("all_name.full_name");
					qsex = rs.getString("student.ssex");
					qcollege = rs.getString("student.scollege");
					qmajor = rs.getString("student.smajor");
					qclass = rs.getString("student.sclass");
					qage = rs.getString("student.sage");
					qphone = rs.getString("student.sphone");
					qnative_place = rs.getString("student.native_place");
					qstate = rs.getString("student.state");

					ps = conn.prepareStatement(sql);
//	            			
//	            			ps.setObject(1, Integer.parseInt(sid));
//	            			java.util.Date date = sdf.parse(answer_date);
//	            			
//	            			ps.setObject(2, new Date(date.getTime()));
//	            			ps.setString(3, region);
//	            			ps.setString(4, touch_history);
//	            			ps.setObject(5, Integer.parseInt(answer_q1));
//	            			ps.setObject(6, Integer.parseInt(answer_q2));
//	            			ps.setObject(7, Integer.parseInt(answer_q3));

					// 执行sql语句
					ps.execute();

//		            		name=customers.getCustomer(num-1).getName();
//			            	gender=customers.getCustomer(num-1).getGender()+"";
//			            	age=customers.getCustomer(num-1).getAge()+"";
//			            	phone=customers.getCustomer(num-1).getPhone();
//			            	email=customers.getCustomer(num-1).getEmail();
					if (num < 0 || num > 36)
						System.out.println("没有这个学生");
					labelList2.add(new JLabel(qname));
					labelList2.add(new JLabel(qsex));
					labelList2.add(new JLabel(qage + ""));
					labelList2.add(new JLabel(qcollege));
					labelList2.add(new JLabel(qmajor));
					labelList2.add(new JLabel(qclass));
					labelList2.add(new JLabel(qphone));
					labelList2.add(new JLabel(qnative_place));
					labelList2.add(new JLabel(qstate));
					for (int i = 0, y = 100; i < shapeArr.length; i++) {
						labelList2.get(i).setBounds(100, y, 100, 50);
						add(labelList2.get(i));
						y = y + 33;
					}
					that.invalidate();
					that.repaint();

				}

				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "请输入数字", "标题【输入错误】", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "没有这个客户，请重新输入", "标题【输入错误】", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

			public void mousePressed(MouseEvent mouseEvent) {
			}

			public void mouseReleased(MouseEvent mouseEvent) {
			}

			public void mouseEntered(MouseEvent mouseEvent) {
			}

			public void mouseExited(MouseEvent mouseEvent) {
			}
		});

		// 监听器：选择修改的信息后的添加输入框
		for (int i = 0; i < shapeArr.length; i++) {
			final int j = i;
			final String[] arrtext = { "正在进行姓名修改", "正在进行性别修改", "正在进行年龄修改", "正在进行学院修改", "正在进行专业修改", "正在进行班级修改",
					"正在进行籍贯修改", "正在进行电话修改", "正在进行状态修改" };
			labelList1.get(i).addMouseListener(new MouseListener() {
				public void mouseEntered(MouseEvent e) {
					System.out.println(arrtext[j]);
					add(textList.get(j));
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mouseClicked(MouseEvent e) {
				}
			});
		}

		// 监听器返回按钮
		buttonback.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseEvent) {
				windows.back();
			}

			public void mousePressed(MouseEvent mouseEvent) {
			}

			public void mouseReleased(MouseEvent mouseEvent) {
			}

			public void mouseEntered(MouseEvent mouseEvent) {
			}

			public void mouseExited(MouseEvent mouseEvent) {
			}
		});

		// 监听器修改按钮
		buttonyes.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseEvent) {
				String name = textList.get(0).getText();
				String sex = textList.get(1).getText();
				String age = textList.get(2).getText();
				String college = textList.get(3).getText();
				String major = textList.get(4).getText();
				String sclass = textList.get(5).getText();
				String native_place = textList.get(6).getText();
				String state = textList.get(7).getText();

				//输入为空时则不改变该值
                if(name.equals(""))
                	{name=qname;
                	System.out.println("未修改姓名");}
                
                if(sex.equals(""))
                {sex=qsex;
                System.out.println("未修改姓名");}
                
                if(age.equals(""))
                {age=qage;
                System.out.println("未修改年龄");}
                
                
                if(college.equals(""))
                {college=qcollege;
                System.out.println("未修改学院");}
                
                if(major.equals(""))
                {major=qmajor;
                System.out.println("未修改专业");}
                
                if(sclass.equals(""))
                {sclass=qclass;
                System.out.println("未修改班级");}
                
                if(native_place.equals(""))
                {native_place=qnative_place;
                System.out.println("未修改籍贯");}
                
                if(state.equals(""))
                {state=qstate;
                System.out.println("未修改状态");}
                
				Connection conn = null;
				PreparedStatement ps = null;
				try {

					num = Integer.parseInt(selecttextField.getText());

					InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
					Properties pros = new Properties();

					pros.load(is);

					String user = pros.getProperty("user");
					String password = pros.getProperty("password");
					String url = pros.getProperty("url");
					String driverClass = pros.getProperty("driverClass");
					// 加载驱动
					Class.forName(driverClass);

					// 获取连接
					conn = DriverManager.getConnection(url, user, password);
					String sql = "update student, all_name set"
							+ " student.ssex= ?,"
							+" student.sage= ?,"
							+" student.scollege= ?,"
							+" student.smajor= ?,"
							+" student.sclass= ?,"
							+" student.native_place= ?,"
							+" student.state= ?,"
							+ " all_name.full_name= ?"
							+ " where student.sid= ? and student.sid=all_name.id;";
					
					ps=conn.prepareStatement(sql);
					ps.setObject(1, sex);
        			ps.setString(2, age);
        			ps.setString(3, college);
        			ps.setObject(4, major);
        			ps.setObject(5, sclass);
        			ps.setObject(6, native_place);
        			ps.setObject(7, state);
        			ps.setObject(8, name);
        			ps.setObject(9, num);
        			
        			
        			ps.execute();

					windows.back();
					System.out.println("修改成功");
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("输入错误");
				} catch (NumberFormatException e) {
					System.out.println("输入错误");
				}catch(Exception e) {
					System.out.println(e.getCause());
					System.out.println(e.getMessage());
				}
			}

			public void mousePressed(MouseEvent mouseEvent) {
			}

			public void mouseReleased(MouseEvent mouseEvent) {
			}

			public void mouseEntered(MouseEvent mouseEvent) {
			}

			public void mouseExited(MouseEvent mouseEvent) {
			}
		});
		
		buttondel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

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
        			String sql1="delete from student where student.sid=?";
        			String sql2="delete from info where sid=?";
        			String sql3="delete from all_name where id=?";
        			ps = conn.prepareStatement(sql1);

        			ps.setObject(1, num);

        			ps.execute();
        			ps = conn.prepareStatement(sql2);

        			ps.setObject(1, num);

        			ps.execute();
        			
        			ps = conn.prepareStatement(sql3);

        			ps.setObject(1, num);

        			ps.execute();
					windows.back();
					System.out.println("删除成功");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}catch(Exception e4) {
					System.out.println(e4.getMessage());
					System.out.println(e4.getCause());
				}

			
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {}
		});
		
	}
	
}

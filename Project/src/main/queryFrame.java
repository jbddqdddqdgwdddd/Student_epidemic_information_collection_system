package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class jdbc_properties {
	static public final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase(kechengsheji)?serverTimezone=GMT%2B8";
	static public final String JDBC_USER = "root";
	static public final String JDBC_PASSWORD = "cgzyqf";
}

//信息查询界面
class INFO_CON {// 学生界面：连接类
	static int first_data = 0;// 数据范围
	static int num_data = 10;
	static int num = 10;// 数据范围
	static int date = 16;// 日期
	static int q_num = 3;
	static public int input_sid[] = new int[num];
	static public String[] input_name = new String[num];
	static public String[] input_date = new String[num];
	static public String[] input_region = new String[num];
	static public String[] input_touch = new String[num];
	static public int[] input_q1 = new int[num];
	static public int[] input_q2 = new int[num];
	static public int[] input_q3 = new int[num];
	static public String[] questions = new String[q_num];

	static public void con_date() {
		date++;// 下一天
		first_data -= 10;
		con_next();
	}
	static public void back_date() {
		date--;// 下一天
		first_data -= 10;
		con_next();
	}

	static public void con_next() {
		try {
			System.out.println("调用next");
			int rownum;
			jdbc_properties jdbc_temp = new jdbc_properties();// 记录连接属性的类
			Class.forName("com.mysql.cj.jdbc.Driver");// 需要导入外部包
			Connection conn_temp = DriverManager.getConnection(jdbc_temp.JDBC_URL, jdbc_temp.JDBC_USER,
					jdbc_temp.JDBC_PASSWORD);
			Statement stmt_temp = conn_temp.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			String query_temp = "select all_name.full_name,info.* from info,all_name where info.sid=all_name.id and answer_date = \"2020-06-"
					+ date + "\";";
			ResultSet rs_temp = stmt_temp.executeQuery(query_temp);
			rs_temp.last();
			rownum = rs_temp.getRow();// 注意
			if (rownum - (first_data + num_data) >= 10) {
				first_data += 10;
			} else if (first_data < rownum - 10) {
				first_data += 10;
				num_data = rownum - first_data;
				num = num_data;
			} else {
				first_data = 0;
				num_data = 10;// 初始化
				num = num_data;
			}
			String query_temp2 = "select all_name.full_name,info.* from info,all_name where info.sid=all_name.id and answer_date = \"2020-06-"
					+ date + "\" LIMIT " + first_data + "," + num_data + ";";
			ResultSet rs_temp2 = stmt_temp.executeQuery(query_temp2);
			int input = 0;// 输入位置
			while (rs_temp2.next()) {// 记录数据
				input_date[input] = rs_temp2.getString("info.answer_date");
				input_sid[input] = rs_temp2.getInt("info.sid");
				input_name[input] = rs_temp2.getString("all_name.full_name");
				input_region[input] = rs_temp2.getString("info.region");
				input_touch[input] = rs_temp2.getString("info.touch_history");
				input_q1[input] = rs_temp2.getInt("info.answer_q1");
				input_q2[input] = rs_temp2.getInt("info.answer_q2");
				input_q3[input] = rs_temp2.getInt("info.answer_q3");
				input++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("进步函数：数据库连接失败！");
		}
	}

	INFO_CON() {
		try {
			jdbc_properties jdbc1 = new jdbc_properties();// 记录连接属性的类
			Class.forName("com.mysql.cj.jdbc.Driver");// 需要导入外部包
			Connection conn = DriverManager.getConnection(jdbc1.JDBC_URL, jdbc1.JDBC_USER, jdbc1.JDBC_PASSWORD);
			System.out.println(conn);
			Statement stmt = conn.createStatement();
			String query = "select all_name.full_name,info.* from info,all_name where info.sid=all_name.id and answer_date = \"2020-06-"
					+ date + "\" LIMIT " + first_data + "," + num_data + ";";
			ResultSet rs = stmt.executeQuery(query);
			int input = 0;// 输入位置
			while (rs.next()) {// 记录数据
				input_sid[input] = rs.getInt("info.sid");
				input_date[input] = rs.getString("info.answer_date");
				input_name[input] = rs.getString("all_name.full_name");
				input_region[input] = rs.getString("info.region");
				input_touch[input] = rs.getString("info.touch_history");
				input_q1[input] = rs.getInt("info.answer_q1");
				input_q2[input] = rs.getInt("info.answer_q2");
				input_q3[input] = rs.getInt("info.answer_q3");
				input++;
			}
			String query2 = "select question_content from questions";// 获取问题
			ResultSet rs2 = stmt.executeQuery(query2);
			int input2 = 0;
			while (rs2.next()) {// 记录问题
				questions[input2] = rs2.getString("question_content");
				input2++;
			}
		} catch (Exception e) {
			System.out.println("构造函数：数据库连接失败！");
		}
	}
}

class INFO_Panel extends JPanel {
	INFO_CON connection1;
	INFO_Frame frame;
	int first_num = connection1.num;
	// 对象别放在绘图函数中！会导致repaint无法覆盖！
	JLabel sidList[] = new JLabel[connection1.num];
	JLabel nameList[] = new JLabel[connection1.num];
	JLabel regionlist[] = new JLabel[connection1.num];
	JLabel touchlist[] = new JLabel[connection1.num];
	JLabel q1list[] = new JLabel[connection1.num];
	JLabel q2list[] = new JLabel[connection1.num];
	JLabel q3list[] = new JLabel[connection1.num];
	JLabel datelist[] = new JLabel[connection1.num];
	INFO_CON connection2 = new INFO_CON();
	String[] shapeArr = { "学号", "姓名", "所在地", "接触史", connection2.questions[0], // 添加问题需要手动修改
			connection2.questions[1], connection2.questions[2], "日期" };
	JLabel[] bl = new JLabel[shapeArr.length];
	
	INFO_Panel(INFO_Frame Frame) {
		frame=Frame;
		connection1 = new INFO_CON();
		for (int i = 0; i < connection1.num; i++) {
			String input1 = "", input2 = "", input3 = "", input4 = "";
			input1 += connection1.input_sid[i];// 处理数据
			input2 += connection1.input_q1[i];
			input3 += connection1.input_q2[i];
			input4 += connection1.input_q3[i];
			sidList[i] = new JLabel(input1);
			nameList[i] = new JLabel(connection1.input_name[i]);
			regionlist[i] = new JLabel(connection1.input_region[i]);
			touchlist[i] = new JLabel(connection1.input_touch[i]);
			q1list[i] = new JLabel(input2);
			q2list[i] = new JLabel(input3);
			q3list[i] = new JLabel(input4);
			datelist[i] = new JLabel(connection1.input_date[i]);
			
			for(int j=0;j<shapeArr.length;j++){
				bl[j]=new JLabel(shapeArr[j]);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 调用连接类，建立连接
		// 画图开始
		JButton buttonback = new JButton("关闭");
		JButton buttonbd = new JButton("上一天");
		JButton buttonnd = new JButton("下一天");
		JButton buttonnext = new JButton("下一页");
		this.setLayout(null);
		buttonback.setBounds(200, 400, 100, 30);
		buttonbd.setBounds(400, 400, 100, 30);
		buttonnext.setBounds(600, 400, 100, 30);
		buttonnd.setBounds(400, 450, 100, 30);
		add(buttonback);
		add(buttonnext);
		add(buttonbd);
		add(buttonnd);

		for (int i = 0, x = 40; i < shapeArr.length; i++) {
			if (i == 4 || i == 5 || i == 6) {
				x = x + 120;
				bl[i].setBounds(x, 30, 170, 50);
			} else if (i == 7) {
				x = x + 120;
				bl[i].setBounds(x, 30, 100, 50);
			} else {
				bl[i].setBounds(x, 30, 50, 50);
			}
			x = x + 50;
			add(bl[i]);
		}
		if (first_num == connection1.num) {// 正常情况
			for (int i = 0, y = 50; i < first_num; i++) {
				String input1 = "", input2 = "", input3 = "", input4 = "";
				input1 += connection1.input_sid[i];// 处理数据
				input2 += connection1.input_q1[i];
				input3 += connection1.input_q2[i];
				input4 += connection1.input_q3[i];
				sidList[i].setText(input1);
				nameList[i].setText(connection1.input_name[i]);
				regionlist[i].setText(connection1.input_region[i]);
				touchlist[i].setText(connection1.input_touch[i]);
				datelist[i].setText(connection1.input_date[i]);
				q1list[i].setText(input2);
				q2list[i].setText(input3);
				q3list[i].setText(input4);

				sidList[i].setBounds(40, y, 50, 50);
				nameList[i].setBounds(90, y, 50, 50);
				regionlist[i].setBounds(140, y, 50, 50);
				touchlist[i].setBounds(190, y, 170, 50);
				q1list[i].setBounds(360, y, 170, 50);
				q2list[i].setBounds(530, y, 170, 50);
				q3list[i].setBounds(700, y, 170, 50);
				datelist[i].setBounds(870, y, 100, 50);

				add(sidList[i]);
				add(nameList[i]);
				add(regionlist[i]);
				add(touchlist[i]);
				add(q1list[i]);
				add(q2list[i]);
				add(q3list[i]);
				add(datelist[i]);

				y = y + 25;
			}
		} else {// 数据小于10的情况
			for (int i = 0, y = 50; i < first_num; i++) {
				if (i < connection1.num) {
					String input1 = "", input2 = "", input3 = "", input4 = "";
					input1 += connection1.input_sid[i];// 处理数据
					input2 += connection1.input_q1[i];
					input3 += connection1.input_q2[i];
					input4 += connection1.input_q3[i];
					sidList[i].setText(input1);
					nameList[i].setText(connection1.input_name[i]);
					regionlist[i].setText(connection1.input_region[i]);
					touchlist[i].setText(connection1.input_touch[i]);
					q1list[i].setText(input2);
					q2list[i].setText(input3);
					q3list[i].setText(input4);
					datelist[i].setText(connection1.input_date[i]);
				} else {
					String input1 = "", input2 = "", input3 = "", input4 = "";// 清空
					sidList[i].setText(input1);
					nameList[i].setText("");
					regionlist[i].setText("");
					touchlist[i].setText("");
					q1list[i].setText(input2);
					q2list[i].setText(input3);
					q3list[i].setText(input4);
					datelist[i].setText("");
				}
				sidList[i].setBounds(40, y, 50, 50);
				nameList[i].setBounds(90, y, 50, 50);
				regionlist[i].setBounds(140, y, 50, 50);
				touchlist[i].setBounds(190, y, 150, 50);
				q1list[i].setBounds(340, y, 150, 50);
				q2list[i].setBounds(490, y, 150, 50);
				q3list[i].setBounds(640, y, 150, 50);
				datelist[i].setBounds(870, y, 100, 50);

				add(sidList[i]);
				add(nameList[i]);
				add(regionlist[i]);
				add(touchlist[i]);
				add(q1list[i]);
				add(q2list[i]);
				add(q3list[i]);
				add(datelist[i]);

				y = y + 25;
			}
		}
		buttonback.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				frame.close();
			}
		});
		buttonnext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				connection1.con_next();
				repaint();
			}
		});
		buttonbd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				connection1.back_date();
				repaint();
			}
		});
		buttonnd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				connection1.con_date();
				repaint();
			}
		});
	}
}

class INFO_Frame extends JFrame {
	INFO_Frame() {
		super("信息界面");
		this.setBounds(200, 200, 1000, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void setP(INFO_Frame Frame) {
		this.setContentPane(new INFO_Panel(Frame));
		this.setVisible(true);
	}
	public void close() {
		this.setVisible(false);
	}
}

//辅导员界面
class TU_CON {// 学生界面：连接类
	static int first_data = 0;// 数据范围
	static int num_data = 6;
	static int num = 6;// 数据范围
	static public int input_aid[] = new int[num];
	static public String[] input_name = new String[num];
	static public String[] input_sex = new String[num];
	static public String[] input_phone = new String[num];
	static public String[] input_dep = new String[num];
	static public String[] input_major = new String[num];

	static public void con_next() {
		try {
			int rownum;
			jdbc_properties jdbc_temp = new jdbc_properties();// 记录连接属性的类
			Class.forName("com.mysql.cj.jdbc.Driver");// 需要导入外部包
			Connection conn_temp = DriverManager.getConnection(jdbc_temp.JDBC_URL, jdbc_temp.JDBC_USER,
					jdbc_temp.JDBC_PASSWORD);
			Statement stmt_temp = conn_temp.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			String query_temp = "select administrator.aid,all_name.full_name,administrator.asex,administrator.aphone,administrator.adep,administrator_res.res_major from administrator_res,administrator,all_name where administrator_res.aid=administrator.aid and all_name.id = administrator.aid;";// 获得行数
			ResultSet rs_temp = stmt_temp.executeQuery(query_temp);
			rs_temp.last();
			rownum = rs_temp.getRow();// 注意
			if (rownum - (first_data + num_data) >= 10) {
				first_data += 10;
			} else if (first_data < rownum - 10) {
				first_data += 10;
				num_data = rownum - first_data;
				num = num_data;
			} else {
				first_data = 0;
				num_data = 6;// 初始化
				num = num_data;
			}
			String query_temp2 = "select administrator.aid,all_name.full_name,administrator.asex,administrator.aphone,administrator.adep,administrator_res.res_major from administrator_res,administrator,all_name where administrator_res.aid=administrator.aid and all_name.id = administrator.aid LIMIT "
					+ first_data + "," + num_data + ";";
			ResultSet rs_temp2 = stmt_temp.executeQuery(query_temp2);
			int input = 0;// 输入位置
			while (rs_temp2.next()) {// 记录数据
				input_aid[input] = rs_temp2.getInt("administrator.aid");
				input_name[input] = rs_temp2.getString("all_name.full_name");
				input_sex[input] = rs_temp2.getString("administrator.asex");
				input_major[input] = rs_temp2.getString("administrator_res.res_major");
				input_dep[input] = rs_temp2.getString("administrator.adep");
				input_phone[input] = rs_temp2.getString("administrator.aphone");
				input++;
			}
		} catch (Exception e) {
			System.out.println("进步函数：数据库连接失败！");
		}
	}

	TU_CON() {
		try {
			jdbc_properties jdbc1 = new jdbc_properties();// 记录连接属性的类
			Class.forName("com.mysql.cj.jdbc.Driver");// 需要导入外部包
			Connection conn = DriverManager.getConnection(jdbc1.JDBC_URL, jdbc1.JDBC_USER, jdbc1.JDBC_PASSWORD);
			Statement stmt = conn.createStatement();
			String query = "select administrator.aid,all_name.full_name,administrator.asex,administrator.aphone,administrator.adep,administrator_res.res_major from administrator_res,administrator,all_name where administrator_res.aid=administrator.aid and all_name.id = administrator.aid LIMIT "
					+ first_data + "," + num_data + ";";
			ResultSet rs = stmt.executeQuery(query);
			int input = 0;// 输入位置
			while (rs.next()) {// 记录数据
				input_aid[input] = rs.getInt("administrator.aid");
				input_name[input] = rs.getString("all_name.full_name");
				input_sex[input] = rs.getString("administrator.asex");
				input_major[input] = rs.getString("administrator_res.res_major");
				input_dep[input] = rs.getString("administrator.adep");
				input_phone[input] = rs.getString("administrator.aphone");
				input++;
			}
		} catch (Exception e) {
			System.out.println("构造函数：数据库连接失败！");
		}
	}
}

class TU_Panel extends JPanel {
	TU_CON connection1;
	TU_Frame frame;
	int first_num = connection1.num;
	// 对象别放在绘图函数中！会导致repaint无法覆盖！
	JLabel aidList[] = new JLabel[connection1.num];
	JLabel nameList[] = new JLabel[connection1.num];
	JLabel sexlist[] = new JLabel[connection1.num];
	JLabel phonelist[] = new JLabel[connection1.num];
	JLabel deplist[] = new JLabel[connection1.num];
	JLabel majorlist[] = new JLabel[connection1.num];
	final String[] shapeArr = { "id", "姓名", "性别", "电话", "院系", "专业" };
	JLabel[] bl = new JLabel[shapeArr.length];
	
	TU_Panel(TU_Frame Frame) {
		TU_CON connection1 = new TU_CON();
		frame=Frame;
		for (int i = 0; i < connection1.num; i++) {
			String input1 = "";
			input1 += connection1.input_aid[i];// 处理数据
			aidList[i] = new JLabel(input1);
			nameList[i] = new JLabel(connection1.input_name[i]);
			sexlist[i] = new JLabel(connection1.input_sex[i]);
			phonelist[i] = new JLabel(connection1.input_phone[i]);
			deplist[i] = new JLabel(connection1.input_dep[i]);
			majorlist[i] = new JLabel(connection1.input_major[i]);
			
			for(int j=0;j<shapeArr.length;j++){
				bl[j]=new JLabel(shapeArr[j]);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 调用连接类，建立连接
		// 画图开始
		JButton buttonback = new JButton("关闭");
		JButton buttonnext = new JButton("下一页");

		this.setLayout(null);
		buttonback.setBounds(200, 400, 100, 30);
		buttonnext.setBounds(500, 400, 100, 30);
		add(buttonback);
		add(buttonnext);
		for(int i=0,x=40;i<shapeArr.length;i++){
			bl[i].setBounds(x,30,50,50);
			if( i==3 || i==4 || i==5 ) {
				x=x+90;
			}
			x=x+50;
			add(bl[i]);
		}
		// JLabel sidList[]=new JLabel[connection1.num];
		if (first_num == connection1.num) {// 正常情况
			for (int i = 0, y = 50; i < first_num; i++) {
				String input1 = "";
				input1 += connection1.input_aid[i];// 处理数据
				aidList[i].setText(input1);
				;// 只能用setText！否则无法覆盖！
				nameList[i].setText(connection1.input_name[i]);
				sexlist[i].setText(connection1.input_sex[i]);
				phonelist[i].setText(connection1.input_phone[i]);
				majorlist[i].setText(connection1.input_major[i]);
				deplist[i].setText(connection1.input_dep[i]);

				aidList[i].setBounds(40, y, 50, 50);
				nameList[i].setBounds(90, y, 50, 50);
				sexlist[i].setBounds(140, y, 50, 50);
				phonelist[i].setBounds(190, y, 100, 50);
				deplist[i].setBounds(320, y, 150, 50);
				majorlist[i].setBounds(500, y, 50, 50);

				add(aidList[i]);
				add(nameList[i]);
				add(sexlist[i]);
				add(majorlist[i]);
				add(phonelist[i]);
				add(deplist[i]);
				y = y + 25;
			}
		} else {// 数据小于9的情况
			for (int i = 0, y = 50; i < first_num; i++) {
				if (i < connection1.num) {
					String input1 = "";
					input1 += connection1.input_aid[i];// 处理数据
					aidList[i].setText(input1);
					;// 只能用setText！否则无法覆盖！
					nameList[i].setText(connection1.input_name[i]);
					sexlist[i].setText(connection1.input_sex[i]);
					phonelist[i].setText(connection1.input_phone[i]);
					majorlist[i].setText(connection1.input_major[i]);
					deplist[i].setText(connection1.input_dep[i]);
				} else {
					aidList[i].setText("");
					;// 只能用setText！否则无法覆盖！
					nameList[i].setText("");
					sexlist[i].setText("");
					majorlist[i].setText("");
					phonelist[i].setText("");
					deplist[i].setText("");
				}
				aidList[i].setBounds(40, y, 50, 50);
				nameList[i].setBounds(90, y, 50, 50);
				sexlist[i].setBounds(140, y, 50, 50);
				phonelist[i].setBounds(190, y, 100, 50);
				deplist[i].setBounds(290, y, 100, 50);
				majorlist[i].setBounds(390, y, 50, 50);

				add(aidList[i]);
				add(nameList[i]);
				add(sexlist[i]);
				add(majorlist[i]);
				add(phonelist[i]);
				add(deplist[i]);
				y = y + 25;
			}
		}
		buttonnext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				connection1.con_next();
				repaint();
			}
		});
		buttonback.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				frame.close();
            }
		});
	}
}

class TU_Frame extends JFrame {
	TU_Frame() {
		super("辅导员信息界面");
		this.setBounds(200, 200, 900, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void setP(TU_Frame Frame) {
		this.setContentPane(new TU_Panel(Frame));
		this.setVisible(true);
	}
	public void close() {
		this.setVisible(false);
	}
}

//学生信息查询界面
class STU_CON {// 学生界面：连接类
	static int first_data = 0;// 数据范围
	static int num_data = 10;
	static int num = 10;// 数据范围
	static public int input_sid[] = new int[num];
	static public String[] input_name = new String[num];
	static public String[] input_sex = new String[num];
	static public String[] input_native_place = new String[num];
	static public int[] input_age = new int[num];
	static public String[] input_college = new String[num];
	static public String[] input_major = new String[num];
	static public String[] input_class = new String[num];
	static public String[] input_phone = new String[num];
	static public String[] input_state = new String[num];

	static public void con_next() {
		try {
			int rownum;
			jdbc_properties jdbc_temp = new jdbc_properties();// 记录连接属性的类
			Class.forName("com.mysql.cj.jdbc.Driver");// 需要导入外部包
			Connection conn_temp = DriverManager.getConnection(jdbc_temp.JDBC_URL, jdbc_temp.JDBC_USER,
					jdbc_temp.JDBC_PASSWORD);
			Statement stmt_temp = conn_temp.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// String query_temp= "select count(*) as rownum from student,all_name where
			// all_name.id=student.sid;";//获得行数
			String query_temp = "select * from student,all_name where all_name.id=student.sid;";// 获得行数
			ResultSet rs_temp = stmt_temp.executeQuery(query_temp);
			// rownum=rs_temp.getInt("rownum")+1;//注意
			rs_temp.last();
			rownum = rs_temp.getRow();// 注意
			if (rownum - (first_data + num_data) >= 10) {
				first_data += 10;
			} else if (first_data < rownum - 10) {
				first_data += 10;
				num_data = rownum - first_data;
				num = num_data;
			} else {
				first_data = 0;
				num_data = 10;// 初始化
				num = num_data;
			}
			String query_temp2 = "SELECT student.sid,all_name.full_name,student.ssex,student.native_place,student.sage,student.scollege,student.sclass,student.smajor,student.sphone,student.state from student,all_name where all_name.id=student.sid LIMIT "
					+ first_data + "," + num_data + ";";
			ResultSet rs_temp2 = stmt_temp.executeQuery(query_temp2);
			int input = 0;// 输入位置
			while (rs_temp2.next()) {// 记录数据
				input_sid[input] = rs_temp2.getInt("student.sid");
				input_name[input] = rs_temp2.getString("all_name.full_name");
				input_sex[input] = rs_temp2.getString("student.ssex");
				input_native_place[input] = rs_temp2.getString("student.native_place");
				input_age[input] = rs_temp2.getInt("student.sage");
				input_college[input] = rs_temp2.getString("student.scollege");
				input_major[input] = rs_temp2.getString("student.smajor");
				input_class[input] = rs_temp2.getString("student.sclass");
				input_phone[input] = rs_temp2.getString("student.sphone");
				input_state[input] = rs_temp2.getString("student.state");
				input++;
			}
		} catch (Exception e) {
			System.out.println("进步函数：数据库连接失败！");
		}
	}

	STU_CON() {
		try {
			jdbc_properties jdbc1 = new jdbc_properties();// 记录连接属性的类
			Class.forName("com.mysql.cj.jdbc.Driver");// 需要导入外部包
			Connection conn = DriverManager.getConnection(jdbc_properties.JDBC_URL, jdbc_properties.JDBC_USER,
					jdbc_properties.JDBC_PASSWORD);
			Statement stmt = conn.createStatement();
			String query = "SELECT student.sid,all_name.full_name,student.ssex,student.native_place,student.sage,student.scollege,student.sclass,student.smajor,student.sphone,student.state from student,all_name where all_name.id=student.sid LIMIT "
					+ first_data + "," + num_data + ";";
			ResultSet rs = stmt.executeQuery(query);
			int input = 0;// 输入位置
			while (rs.next()) {// 记录数据
				input_sid[input] = rs.getInt("student.sid");
				input_name[input] = rs.getString("all_name.full_name");
				input_sex[input] = rs.getString("student.ssex");
				input_native_place[input] = rs.getString("student.native_place");
				input_age[input] = rs.getInt("student.sage");
				input_college[input] = rs.getString("student.scollege");
				input_major[input] = rs.getString("student.smajor");
				input_class[input] = rs.getString("student.sclass");
				input_phone[input] = rs.getString("student.sphone");
				input_state[input] = rs.getString("student.state");
				input++;
			}
		} catch (Exception e) {
			System.out.println("构造函数：数据库连接失败！");
		}
	}
}

class STU_Panel extends JPanel {
	STU_CON connection1;
	STU_Frame frame;
	int first_num = connection1.num;
	// 对象别放在绘图函数中！会导致repaint无法覆盖！
	JLabel sidList[] = new JLabel[connection1.num];
	JLabel nameList[] = new JLabel[connection1.num];
	JLabel sexlist[] = new JLabel[connection1.num];
	JLabel nativelist[] = new JLabel[connection1.num];
	JLabel agelist[] = new JLabel[connection1.num];
	JLabel collegelist[] = new JLabel[connection1.num];
	JLabel majorlist[] = new JLabel[connection1.num];
	JLabel classlist[] = new JLabel[connection1.num];
	JLabel phonelist[] = new JLabel[connection1.num];
	JLabel statuslist[] = new JLabel[connection1.num];
	final String[] shapeArr = { "学号", "姓名", "性别", "籍贯", "年级", "院系", "专业", "班级", "电话", "状态" };
	JLabel[] bl = new JLabel[shapeArr.length];

	STU_Panel(STU_Frame Frame) {
		frame=Frame;
		STU_CON connection1 = new STU_CON();
		for(int i=0;i<connection1.num;i++) {
			String input1="",input2="";
			String input3;
			input1+=connection1.input_sid[i];//处理数据
			input2+=connection1.input_age[i];
			if(connection1.input_state[i] == "1") {
				input3 = "异常";
			}
			else {
				input3 = "正常";
			}
			sidList[i]=new JLabel(input1);
			nameList[i]=new JLabel(connection1.input_name[i]);
			sexlist[i]=new JLabel(connection1.input_sex[i]);
			nativelist[i]=new JLabel(connection1.input_native_place[i]);
			agelist[i]=new JLabel(input2);
			collegelist[i]=new JLabel(connection1.input_college[i]);
			majorlist[i]=new JLabel(connection1.input_major[i]);
			classlist[i]=new JLabel(connection1.input_class[i]);
			phonelist[i]=new JLabel(connection1.input_phone[i]);
			statuslist[i]=new JLabel(input3);
			for(int j=0;j<shapeArr.length;j++){
				bl[j]=new JLabel(shapeArr[j]);
			}
	}
	}
	protected void paintComponent( Graphics g ) {
		super.paintComponent(g);
		//调用连接类，建立连接
		//画图开始
		JButton buttonback=new JButton("关闭");
		JButton buttonnext=new JButton("下一页");
		this.setLayout(null);
		buttonback.setBounds(300, 400, 100, 30);
		buttonnext.setBounds(500, 400, 100, 30);
		add(buttonback);
		add(buttonnext);
		
		for(int i=0,x=40;i<shapeArr.length;i++){
			bl[i].setBounds(x,30,50,50);
			if( i==5 || i==6 || i==8 ) {
				x=x+90;
			}
			x=x+50;
			add(bl[i]);
		}
			//JLabel sidList[]=new JLabel[connection1.num];
		if(first_num == connection1.num) {//正常情况
			for(int i=0,y=50;i<first_num;i++)
			{
					String input1="",input2="";
					String input3;
					input1+=connection1.input_sid[i];//处理数据
					input2+=connection1.input_age[i];
					if(connection1.input_state[i] == "1") {
						input3 = "异常";
					}
					else {
						input3 = "正常";
					}
					sidList[i].setText(input1);;//只能用setText！否则无法覆盖！
					nameList[i].setText(connection1.input_name[i]);
					sexlist[i].setText(connection1.input_sex[i]);
					nativelist[i].setText(connection1.input_native_place[i]);
					agelist[i].setText(input2);
					collegelist[i].setText(connection1.input_college[i]);
					majorlist[i].setText(connection1.input_major[i]);
					classlist[i].setText(connection1.input_class[i]);
					phonelist[i].setText(connection1.input_phone[i]);
					statuslist[i].setText(input3);
					sidList[i].setBounds(40,y,50,50);
					nameList[i].setBounds(90,y,50,50);
					sexlist[i].setBounds(140,y,50,50);
					nativelist[i].setBounds(190,y,50,50);
					agelist[i].setBounds(240,y,50,50);
					collegelist[i].setBounds(290,y,150,50);
					majorlist[i].setBounds(430,y,150,50);
					classlist[i].setBounds(580,y,50,50);
					phonelist[i].setBounds(630,y,150,50);
					statuslist[i].setBounds(780,y,50,50);
					//labelListid.add(blidList);
					add(sidList[i]);
					add(nameList[i]);
					add(sexlist[i]);
					add(nativelist[i]);
					add(agelist[i]);
					add(collegelist[i]);
					add(majorlist[i]);
					add(classlist[i]);
					add(phonelist[i]);
					add(statuslist[i]);
					y=y+25;
				}
		}
	else {//数据小于10的情况
		for(int i=0,y=50;i<first_num;i++)
		{
			if( i < connection1.num ) {
				String input1="",input2="";
				String input3;
				input1+=connection1.input_sid[i];//处理数据
				input2+=connection1.input_age[i];
				if(connection1.input_state[i] == "1") {
					input3 = "异常";
				}
				else {
					input3 = "正常";
				}
				sidList[i].setText(input1);;//只能用setText！否则无法覆盖！
				nameList[i].setText(connection1.input_name[i]);
				sexlist[i].setText(connection1.input_sex[i]);
				nativelist[i].setText(connection1.input_native_place[i]);
				agelist[i].setText(input2);
				collegelist[i].setText(connection1.input_college[i]);
				majorlist[i].setText(connection1.input_major[i]);
				classlist[i].setText(connection1.input_class[i]);
				phonelist[i].setText(connection1.input_phone[i]);
				statuslist[i].setText(input3);
				
			}
			else {
				sidList[i].setText("");;//只能用setText！否则无法覆盖！
				nameList[i].setText("");
				sexlist[i].setText("");
				nativelist[i].setText("");
				agelist[i].setText("");
				collegelist[i].setText("");
				majorlist[i].setText("");
				classlist[i].setText("");
				phonelist[i].setText("");
				statuslist[i].setText("");
			}
			sidList[i].setBounds(40,y,50,50);
			nameList[i].setBounds(90,y,50,50);
			sexlist[i].setBounds(140,y,50,50);
			nativelist[i].setBounds(190,y,50,50);
			agelist[i].setBounds(240,y,50,50);
			collegelist[i].setBounds(290,y,150,50);
			majorlist[i].setBounds(430,y,150,50);
			classlist[i].setBounds(580,y,50,50);
			phonelist[i].setBounds(630,y,150,50);
			statuslist[i].setBounds(780,y,50,50);
			add(sidList[i]);
			add(nameList[i]);
			add(sexlist[i]);
			add(nativelist[i]);
			add(agelist[i]);
			add(collegelist[i]);
			add(majorlist[i]);
			add(classlist[i]);
			add(phonelist[i]);
			add(statuslist[i]);
			y=y+25;
		}
	}
	
					
					
			/*buttonback.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent mouseEvent) {
	            	
	            }
	            
	            public void mousePressed(MouseEvent mouseEvent) {}
	            public void mouseReleased(MouseEvent mouseEvent) {}
	            public void mouseEntered(MouseEvent mouseEvent) {}
	            public void mouseExited(MouseEvent mouseEvent) {}
	        });*/
			buttonnext.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent mouseEvent) {
					connection1.con_next();
					repaint();
	            }
			});
			//未完成
			buttonback.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent mouseEvent) {
					frame.close();
	            }
			});
			
	}
}

class STU_Frame extends JFrame {
	STU_Frame() {
		super("学生信息界面");
		this.setBounds(200, 200, 900, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void setP(STU_Frame Frame) {
		this.setContentPane(new STU_Panel(Frame));
		this.setVisible(true);
	}
	public void close() {
		this.setVisible(false);
	}
}

public class queryFrame {
	public static void main(String[] args) {
		new TU_Frame();
	}
}

package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


import main.basic.MyPanel;
import main.basic.MyWindows;

public class FifthPanel extends MyPanel
{
	static int num;
	static String name;
	static String gender;
	static String phone;
	static  String age;
	static String email;
	
	public FifthPanel(final MyWindows windows) 
	{
		super(windows, "显示所有用户");
		
		JButton buttonback=new JButton("返回");
		JButton button1=new JButton("查看所有学生信息");
		JButton button2=new JButton("查看所有教师信息");
		JButton button3=new JButton("查看疫情信息");

		
		this.setLayout(null);
		buttonback.setBounds(200, 400, 100, 30);
		button1.setBounds(150, 100, 200, 30);
		button2.setBounds(150, 200, 200, 30);
		button3.setBounds(150, 300, 200, 30);
		add(buttonback);
		add(button1);
		add(button2);
		add(button3);
		
	
		buttonback.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                windows.back();
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
		
		button1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				STU_Frame SFrame=new STU_Frame();
				SFrame.setP(SFrame);
			}
		});
		button2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				TU_Frame TFrame=new TU_Frame();
				TFrame.setP(TFrame);
				
			}
		});
		button3.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				INFO_Frame IFrame=new INFO_Frame();
				IFrame.setP(IFrame);
				
			}
		});
		
	}

}

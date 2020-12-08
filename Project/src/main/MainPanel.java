package main;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


import main.basic.MyPanel;
import main.basic.MyWindows;

public class MainPanel extends MyPanel {
	ArrayList<JButton> buttonList =new ArrayList<JButton>();
	
	
    MainPanel(final MyWindows windows){
        super(windows,"mainPanel");
    	
    	
    	JLabel lb1=new JLabel("---------------------------------请开始操作---------------------------------");
        String[] shapeArr= {
				"添 加 学 生 信 息",
				"删 改 学 生 信 息",
				"查 看 所 有 信 息",
				"退       出"};
        
		add(lb1);
		lb1.setBounds(100, -100, 600, 300);
		MyPanel[] PanelList=new MyPanel[5];
		this.setLayout(null);
		for(int i=0,y=100;i<shapeArr.length;i++){
			JButton btn=new JButton(shapeArr[i]);//创建按钮
			buttonList.add(btn);
			btn.setBounds(180,y,150,50);
			add(btn);//将按钮添加到窗体上
			y=y+70;
			add(btn, BorderLayout.CENTER);		
		}
        
		
		buttonList.get(0).addMouseListener(new MouseListener() 
		{
        	
            public void mouseClicked(MouseEvent mouseEvent) 
            {
            	System.out.println("进入增加用户界面");
                windows.setNewPanel(new SecondPanel(windows));
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        
		buttonList.get(1).addMouseListener(new MouseListener() 
		{
        	
            public void mouseClicked(MouseEvent mouseEvent) 
            {
            	System.out.println("进入删改用户界面");
                windows.setNewPanel(new ThirdPanel(windows));
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        
		
        
		buttonList.get(2).addMouseListener(new MouseListener() 
		{
        	
            public void mouseClicked(MouseEvent mouseEvent) 
            {
            	System.out.println("进入查询用户界面");
                windows.setNewPanel(new FifthPanel(windows));
            }
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
		buttonList.get(3).addMouseListener(new MouseListener() 
		{
        	
            public void mouseClicked(MouseEvent mouseEvent) 
            {
            	System.out.println("是否退出");
                windows.setNewPanel(new sixthPanel(windows));
            }
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
    }

}

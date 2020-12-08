package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.basic.MyPanel;
import main.basic.MyWindows;

public class sixthPanel extends MyPanel{

	public sixthPanel(final MyWindows windows) {
		super(windows, "是否退出");
		JButton buttonyes=new JButton("确定");
		JButton buttonback=new JButton("返回");
		add(buttonyes);
		add(buttonback);
		
		buttonback.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                windows.back();
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
		
		buttonyes.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                windows.setVisible(false);
                
            }
            
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
		
	}
	

}

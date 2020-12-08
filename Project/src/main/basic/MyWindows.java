package main.basic;

import main.PanelStack;

import java.awt.Graphics;

import javax.swing.*;
public class MyWindows extends JFrame {
    PanelStack panelStack=new PanelStack();
    
    
    
    public void setNewPanel(MyPanel panel){
        panelStack.push(panel);
        setContentPane(panel);

    }

    public void back(){	
        setContentPane(panelStack.pop());
    }
   public MyWindows(String title){
        this.setTitle(title);
        setSize(500,500);
        this.setLocationRelativeTo(null);//窗口居中
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}

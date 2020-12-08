package main.basic;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyWindows windows;
    public MyPanel(MyWindows windows,String name){
        this.windows=windows;
        JLabel label=new JLabel(name);
        label.setLocation(10,10);
        add(label);
        setBounds(0,0,500,500);
        
    }
}

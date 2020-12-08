package main;

import main.basic.MyWindows;

public class MainWindows extends MyWindows {

    MainWindows(String title){
        super(title);
        setNewPanel(new MainPanel(this));
    }
}

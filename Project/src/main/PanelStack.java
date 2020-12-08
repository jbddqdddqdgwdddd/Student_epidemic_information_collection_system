package main;

import main.basic.MyPanel;

import java.util.Stack;

public class PanelStack {
    Stack<MyPanel> panelStack = new Stack<>();

    public PanelStack() {

    }

    public void push(MyPanel panel) {
        panelStack.push(panel);
    }

    public MyPanel pop() {
        panelStack.pop();
        if (panelStack.size() > 0)
            return panelStack.get(panelStack.size() - 1);
        return null;
    }
}

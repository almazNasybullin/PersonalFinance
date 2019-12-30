package gui.handler;

import gui.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract public class Handler implements ActionListener {

    protected final MainFrame frame;


    public Handler(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        frame.refresh();
    }
}

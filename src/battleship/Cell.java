package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell extends JComponent implements ActionListener{
    static int UNKNOWN = 0;
    static int MISS = 1;
    static int FIRED = 2;
    static int DEAD = 3;

    String name;
    int status;

    public Cell(String name){
        this.name = name;
        this.status = UNKNOWN;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(name + " was clicked");
    }

    @Override
    protected void paintComponent(Graphics g){

    }
}

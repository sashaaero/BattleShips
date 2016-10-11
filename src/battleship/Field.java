package battleship;

import javax.swing.*;
import java.awt.*;

public class Field extends JComponent{
    Cell[][] field1 = new Cell[Game.FIELD_SIZE][Game.FIELD_SIZE];

    public Field(){
        this.setLayout(new GridLayout(Game.FIELD_SIZE, Game.FIELD_SIZE));
        for(int i = 0; i < Game.FIELD_SIZE; i++){
            for(int j = 0; j < Game.FIELD_SIZE; j++){
                String name = Character.toString((char) ((int) 'a' + i));
                name += Integer.toString(j);
                field1[i][j] = new Cell(name);
                this.add(field1[i][j]);
            }
        }
    }
}

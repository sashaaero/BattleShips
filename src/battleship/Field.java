package battleship;

import javax.swing.*;
import java.awt.*;

class Field extends JPanel{
    private Cell[][] field = new Cell[Game.FIELD_SIZE][Game.FIELD_SIZE];

    Field(boolean player){
        JPanel cells = new JPanel(new GridLayout(Game.FIELD_SIZE + 1, Game.FIELD_SIZE + 1));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        String letters[] = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к"};

        cells.add(new JLabel());

        for(int i = 0; i < Game.FIELD_SIZE; i++){
            JLabel label = new JLabel(Integer.toString(i + 1));
            label.setHorizontalAlignment(JLabel.CENTER);
            cells.add(label);
        }

        for(int i = 0; i < Game.FIELD_SIZE; i++){
            JLabel label = new JLabel(letters[i]);
            label.setHorizontalAlignment(JLabel.CENTER);
            cells.add(label);
            for(int j = 0; j < Game.FIELD_SIZE; j++){
                String name = letters[i];
                name += Integer.toString(j + 1);
                field[i][j] = new Cell(name, player);
                cells.add(field[i][j]);
            }
        }
        cells.setSize(Game.CELL_SIZE * (Game.FIELD_SIZE + 1), Game.CELL_SIZE * (Game.FIELD_SIZE + 1));
        cells.setBackground(Color.white);
        this.add(cells, BorderLayout.CENTER);
        JLabel label = new JLabel(player ? "Вы" : "Компьютер");
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label, BorderLayout.NORTH);
    }

    public Cell get(int x, int y){
        return field[x-1][y-1];
    }
}

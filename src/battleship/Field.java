package battleship;

import javax.swing.*;
import java.awt.*;

class Field extends JPanel{
    private Cell[][] field = new Cell[Game.FIELD_SIZE][Game.FIELD_SIZE];
    private Army army;

    Field(boolean player){
        // GUI PART
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

        // Game part
        army = new Army();
    }

    boolean fieldIsReady(){
        // Быстрая проверка
        int counter = 0;
        for(int i = 0; i < Game.FIELD_SIZE; i++){
            for(int j = 0; j < Game.FIELD_SIZE; j++){
                counter += field[i][j].ship ? 1 : 0;
            }
        }
        if (counter != Army.POINTS_AMOUNT) {
            return false; //недостаточно клеток (или перебор)

        } else { //Начинаем проверку
            for(int i = 0; i < Game.FIELD_SIZE; i++){
                for(int j = 0; j < Game.FIELD_SIZE; j++){
                    if (isShipHere(i, j)) {
                        if (army.alreadySeen(i, j))
                            continue;

                        Ship curr = new Ship(this, i, j);
                        if (curr.valid){
                            army.addShip(curr);
                            curr.callSelf();
                        } else {
                            army.clear();
                            return false;
                        }
                    }
                }
            }
        }
        return false; //TODO
    }

    boolean isShipHere(int x, int y){
        return y >= 0 && y < Game.FIELD_SIZE && x >= 0 && x < Game.FIELD_SIZE && field[x][y].ship;
    }

    Cell get(int x, int y){
        return field[x][y];
    }
}

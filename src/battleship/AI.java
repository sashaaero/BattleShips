package battleship;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class AI implements Runnable{
    Army army;
    Thread thread;
    private Field field;
    private Field enemyField;

    private final int UP = 0;
    private final int RIGHT = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private int ways[] = {UP, RIGHT, DOWN, LEFT};

    AI(Field field, Field enemyField){
        this.field = field;
        this.enemyField = enemyField;
        army = field.army;
        setup();
        thread = new Thread(this);
    }

    void setup(){
        Random random = ThreadLocalRandom.current();
        int x, y;
        for(int len = 4; len > 0; len--){
            for(int i = 0, tries = 0; i < 5 - len; i++, tries++) {
                if (tries == 1000)
                    System.exit(1);

                // Выбираем случайную точку
                x = random.nextInt(Game.FIELD_SIZE);
                y = random.nextInt(Game.FIELD_SIZE);

                if (army.engaged(x, y)){
                    i--;
                    continue;
                }

                // Выбираем направление
                int currWay = findDir(x, y, len);
                if (currWay == -1){
                    i--;
                    continue;
                }

                setShip(currWay, x, y, len);
            }
        }
    }

    private void setShip(int dir, int x, int y, int len){
        switch (dir){
            case UP:
                for (int i = 0; i < len; i++){
                    Cell currCell = field.get(x - i, y);
                    currCell.ship = true;
                }
                army.addShip(new Ship(field, x - len + 1, y));
                break;
            case RIGHT:
                for (int i = 0; i < len; i++){
                    Cell currCell = field.get(x, y + i);
                    currCell.ship = true;
                }
                army.addShip(new Ship(field, x, y));
                break;
            case DOWN:
                for (int i = 0; i < len; i++){
                    Cell currCell = field.get(x + i, y);
                    currCell.ship = true;
                }
                army.addShip(new Ship(field, x, y));
                break;
            case LEFT:
                for (int i = 0; i < len; i++){
                    Cell currCell = field.get(x, y - i);
                    currCell.ship = true;
                }
                army.addShip(new Ship(field, x, y - len + 1));
                break;
        }
    }

    private int findDir(int x, int y, int len){
        shuffle(ways);
        //for(int j = 0; j < ways.length; j++){
        for (int j : ways) {
            // В цикле идем по ПЕРЕМЕШАННЫМ направлениям и проверяем, можно ли куда-нибудь засунуть корабль
            int begin;
            switch (j){
                case UP:
                    begin = x;
                    if (begin - len < 0)
                        continue;

                    while (!army.engaged(begin - 1, y) && begin >= 0)
                        begin--;

                    if (x - begin + 1 >= len)
                        return UP;

                    break;
                case RIGHT:
                    begin = y;
                    if (begin + len > Game.FIELD_SIZE)
                        continue;

                    while (!army.engaged(x, begin + 1) && begin < Game.FIELD_SIZE)
                        begin++;

                    if (begin - y + 1 >= len)
                        return RIGHT;

                    break;
                case DOWN:
                    begin = x;
                    if (begin + len > Game.FIELD_SIZE)
                        continue;

                    while (!army.engaged(begin + 1, y) && begin < Game.FIELD_SIZE)
                        begin++;

                    if (begin - x + 1 >= len)
                        return DOWN;

                    break;
                case LEFT:
                    begin = y;
                    if (begin - len + 1 < 0)
                        continue;

                    while (!army.engaged(x, begin - 1) && begin >= 0)
                        begin--;

                    if (y - begin + 1 >= len)
                        return LEFT;

                    break;
            }
        }
        return -1;
    }

    private void shuffle(int[] array){
        int index, temp;
        Random random = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void shoot(){
        try {
            Thread.sleep((int) (Math.random() * 500  + 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random r = ThreadLocalRandom.current();

        int x = r.nextInt(Game.FIELD_SIZE);
        int y = r.nextInt(Game.FIELD_SIZE);

        while (field.get(x, y).wasAttacked){
            x = r.nextInt(Game.FIELD_SIZE);
            y = r.nextInt(Game.FIELD_SIZE);
        }

        int code = enemyField.army.getShot(x, y);
        if (code == Ship.MISS){
            Game.getInstance().playerTurn = true;
            Game.getInstance().infoLabel.setText("Ходит игрок!");
        }
    }

    @Override
    public void run() {
        while(Game.getInstance().state != Game.OVER){
            if(!Game.getInstance().playerTurn){ // Ходим мы
                try {
                    Thread.sleep((int) (Math.random() * 500  + 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shoot();
            }
        }
    }
}

package battleship;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AI {
    Army army;
    Field field;

    private final int UP = 0;
    private final int RIGHT = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private int ways[] = {UP, RIGHT, DOWN, LEFT};

    AI(Field field){
        field = field;
        army = field.army;
    }

    void setup(){
        Random random = ThreadLocalRandom.current();
        int x, y;
        for(int len = 4; len > 0; len--){
            for(int i = 0; i < 5 - len; i++) {
                boolean wayFound = false;
                // Выбираем случайную точку
                x = random.nextInt(Game.FIELD_SIZE);
                y = random.nextInt(Game.FIELD_SIZE);

                if (!army.engaged(x, y)){
                    i--;
                    continue;
                }

                // Выбираем направление
                int currWay = findDir(x, y, len);
                if (currWay == -1){
                    i--;
                    continue;
                }

            }
        }
    }

    private int findDir(int x, int y, int len){
        shuffle(ways);
        for(int j = 0; j < ways.length; j++){
            // В цикле идем по ПЕРЕМЕШАННЫМ направлениям и проверяем, можно ли куда-нибудь засунуть корабль
            int begin;
            switch (ways[j]){
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
}

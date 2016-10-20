package battleship;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

class Ship{
    int length;
    boolean alive;
    List<Point> coordinates = new LinkedList<>();
    List<Point> around = new LinkedList<>();

    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;

    boolean valid;

    Ship(Field field, int x, int y){
        // Проверяем, действительно ли тут есть что-то
        if (!field.isShipHere(x, y)){
            valid = false;
            return; // Если ничего нет -- уходим
        }
        // Теперь нужно определить в какую сторону идет корабль
        boolean way = field.isShipHere(x - 1, y) || field.isShipHere(x + 1, y);
        // Проверяем, вертикально ли идет корабль, если false, получается что это горизонталь
        int begin, end;
        if (way == HORIZONTAL){
            begin = end = y;
            while (field.isShipHere(x, begin - 1))
                begin--;

            while (field.isShipHere(x, end + 1))
                end++;

            length = end - begin + 1;
            if(!(length >= 1 && length <= 4)){
                valid = false;
                return;
            }
            valid = true;
            for(int i = begin; i < end + 1; i++){
                coordinates.add(new Point(x, i));
                field.get(x, i).background = Color.lightGray; //DEV COLORING
                field.get(x, i).repaint();
            }

            // TODO CHECK AROUND
        }
        if (way == VERTICAL){
            begin = end = x;
            while (field.isShipHere(begin - 1, y))
                begin--;

            while (field.isShipHere(end + 1, y))
                end++;

            length = end - begin + 1;
            if(!(length >= 1 && length <= 4)){
                valid = false;
                return;
            }

            valid = true;
            for(int i = begin; i < end + 1; i++){
                coordinates.add(new Point(i, y));
                field.get(i, y).background = Color.lightGray; //DEV COLORING
                field.get(i, y).repaint();
            }

            // TODO CHECK AROUND
        }
    }

    // DEVMETHOD
    public void callSelf(){

        Point begin = coordinates.get(0), end = coordinates.get(coordinates.size() - 1);
        System.out.printf("Найден корабль длиной %d\n", length);
        String letters[] = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к"};
        System.out.printf("Координаты: (%s%d; %s%d)\n\n", letters[begin.x], begin.y + 1, letters[end.x], end.y + 1);
    }
}

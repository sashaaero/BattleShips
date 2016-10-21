package battleship;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

class Ship{
    private int length;
    boolean alive;
    List<Point> coordinates = new LinkedList<>();
    List<Point> around = new LinkedList<>();
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    boolean valid = true;
    private Field field;

    Ship(Field field, int x, int y){
        this.field = field;
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
            while (field.isShipHere(x, begin - 1)) // Находим начало корабля
                begin--;

            while (field.isShipHere(x, end + 1)) // Конец
                end++;

            // Проверка на длину
            length = end - begin + 1;
            if(!(length >= 1 && length <= 4)){ // Если корабль неверной длины
                valid = false; // Помечаем ошибку
                return; // Выходим
            }

            // Проверка окружения
            // begin & end
            for (int i = x - 1; i <= x + 1; i++) { // Если пусто там где должно быть
                if (!field.isShipHere(i, begin - 1) && !field.isShipHere(i, end + 1)) {
                    operateEmpty(field, i, begin - 1); // Добавляем в список окружения
                    operateEmpty(field, i, end + 1);
                } else {
                    valid = false;
                    return;
                }
            }
            // Все на протяжении корабля
            for (int i = begin; i <= end; i++){
                if (!field.isShipHere(x - 1, i) && !field.isShipHere(x + 1, i)){
                    operateEmpty(field, x - 1, i);
                    operateEmpty(field, x + 1, i);
                } else {
                    valid = false;
                    return;
                }
            }

            // Запись самого корабля
            for(int i = begin; i < end + 1; i++){
                coordinates.add(new Point(x, i));
                repaintCell(field.get(x, i), Color.lightGray);
            }
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

            // Проверка окружения
            // begin & end
            for (int i = y - 1; i <= y + 1; i++) { // Если пусто там где должно быть
                if (!field.isShipHere(begin - 1, i) && !field.isShipHere(end + 1, i)) {
                    operateEmpty(field, begin - 1, i); // Добавляем в список окружения
                    operateEmpty(field, end + 1, i);
                } else {
                    valid = false;
                    return;
                }
            }
            // Все на протяжении корабля
            for (int i = begin; i <= end; i++){
                if (!field.isShipHere(i, y - 1) && !field.isShipHere(i, y + 1)){
                    operateEmpty(field, i, y - 1);
                    operateEmpty(field, i, y + 1);
                } else {
                    valid = false;
                    return;
                }
            }

            for(int i = begin; i < end + 1; i++){
                coordinates.add(new Point(i, y));
                repaintCell(field.get(i, y), Color.lightGray);
                // callSelf();
            }
        }
    }

    private void operateEmpty(Field field, int x, int y){
        Cell cell = field.get(x, y);
        if (cell != null){
            around.add(new Point(x, y));
            repaintCell(field.get(x, y), Color.PINK);
        }
    }

    boolean getHit(int x, int y){
        Point p = new Point(x, y);
        if (!coordinates.contains(p))
            return false;

        coordinates.remove(p);
        if(coordinates.size() == 0){
            for(Point point: around){
                field.get(point.x, point.y).wasAttacked = true;
            }
        }
        field.repaintAll();
        return true;
    }

    private void repaintCell(Cell cell, Color color){
        if (!Main.DEV) return;
        cell.background = color;
        cell.repaint();
    }

    public void callSelf(){
        if (!Main.DEV) return;
        Point begin = coordinates.get(0), end = coordinates.get(coordinates.size() - 1);
        System.out.printf("Найден корабль длиной %d\n", length);
        String letters[] = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к"};
        System.out.printf("Координаты: (%s%d; %s%d)\n\n", letters[begin.x], begin.y + 1, letters[end.x], end.y + 1);
    }
}

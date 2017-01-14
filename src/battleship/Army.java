package battleship;

import java.util.*;
import java.util.List;

class Army {
    static final int POINTS_AMOUNT = 20; // Количество точек, которое обязано быть на поле
    static final int SHIPS_AMOUNT = 10;

    private List<Ship> ships = new LinkedList<>();
    private Field field;

    Army(Field field){
        this.field = field;
    }

    void addShip(Ship ship){
        if(ships.size() >= SHIPS_AMOUNT) {
            System.out.println("Слишком много кораблей");
            return;
        }
        ships.add(ship);
    }

    void clear(){
        ships.clear();
    }

    boolean engaged(int x, int y){
        for (Ship ship: ships){
            for (Cell cell: ship.coordinates){
                if (cell.x == x && cell.y == y)
                    return true;
            }
            for (Cell cell: ship.around){
                if (cell.x == x && cell.y == y)
                    return true;
            }
        }
        return false;
    }

    int getShot(int x, int y){
        if (field.isShipHere(x, y)){
            for (Ship ship: ships){
                int res = ship.getHit(x, y);
                if (res == Ship.KILL){
                    ships.remove(ship);
                    if (ships.size() == 0){
                        Game.getInstance().over(Game.getInstance().playerTurn);
                    }
                    return res;
                } else if (res == Ship.HIT){
                    return res;
                }
            }
            return Ship.MISS;
        } else {
            field.get(x, y).attacked();
            return Ship.MISS;
        }
    }
}

package battleship;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class Army {
    static final int POINTS_AMOUNT = 20; // Количество точек, которое обязано быть на поле
    static final int SHIPS_AMOUNT = 10;

    private List<Ship> ships = new LinkedList<>();

    private boolean playerArmy;

    Army(boolean playerArmy){
        this.playerArmy = playerArmy;
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

    void getShot(int x, int y){
        Iterator<Ship> it = ships.iterator();
        while (it.hasNext()){
            Ship ship = it.next();
            if (ship.getHit(x, y) == Ship.KILL) {
                it.remove();
                if (ships.isEmpty())
                    Game.over(!playerArmy);
            }
        }
    }
}

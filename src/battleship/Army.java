package battleship;

import java.awt.*;
import java.util.*;
import java.util.List;

class Army {
    public static final int POINTS_AMOUNT = 20; // Количество точек, которое обязано быть на поле
    public static final int SHIPS_AMOUNT = 10;

    private List<Ship> ships = new LinkedList<>();

    Army(){}

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
            for (Point point: ship.coordinates){
                if (point.x == x && point.y == y)
                    return true;
            }
            for (Point point: ship.around){
                if (point.x == x && point.y == y)
                    return true;
            }
        }
        return false;
    }

    boolean getShot(int x, int y){
        for(Ship ship: ships){
            if (ship.getHit(x, y)){
                return true;
            }
        }
        Game.playerTurn = !Game.playerTurn;
        if(!Game.playerTurn){
            Game.ai.shoot();
        }
        return false;
    }

    
}

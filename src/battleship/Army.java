package battleship;

class Army {
    public static final int POINTS_COUNTER = 20; // Количество точек, которое обязано быть на поле
    public static final int SHIPS_COUNTER = 10;

    private int pointer;
    private Ship ships[];

    Army(){
        pointer = 0;
        ships = new Ship[SHIPS_COUNTER];
    }

    public void addShip(Ship ship){
        if(pointer == SHIPS_COUNTER - 1) {
            System.out.println("Слишком много кораблей");
        }
        ships[pointer] = ship;
        pointer++;
    }
}

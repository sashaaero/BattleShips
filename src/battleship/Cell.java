package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends JComponent{
    /*
        Константы
            UNKNOWN - для отображения нетронутой ячейки (или пустой для игрока)
            MISS - для отображения ячейки с промахом (или технически недоступные ячейки)
            FIRED - для отображения ячейки, в которую попали
            SHIP - для отображения ячейки с кораблем (только для поля игрока)
            TODO DOCUMENTATION
     */
    public static final int UNKNOWN = 0;
    public static final int MISS = 1;
    public static final int FIRED = 2;
    public boolean SHIP = false;
    public boolean EMPTY = !SHIP;

    private String name;
    private int visualStatus;
    private boolean gameStatus;
    private boolean player;
    Color color;

    public Cell(String name, boolean player){
        super();
        this.setToolTipText(name);
        this.name = name;
        this.player = player;
        this.visualStatus = UNKNOWN;
        this.gameStatus = EMPTY;
        this.color = player ? Color.blue : Color.red;
        enableInputMethods(true);
        addMouseListener(new CellsMouseAdapter());

        //DEV PART
        if(name.startsWith("а")){
            this.gameStatus = SHIP;
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(color);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    protected void paintBorder(Graphics graphics){
        super.paintBorder(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        switch (visualStatus){
            case UNKNOWN:
                return;
            case MISS:
                g.drawLine(5, 5, getWidth() - 5, getHeight() - 5);
                g.drawLine(getWidth() - 5, 5, 5, getHeight() - 5);
                break;
            case FIRED:
                int xPoints_a[] = {5, getWidth() - 5, getWidth() / 2};
                int yPoints_a[] = {10, 10, getHeight() - 5};
                g.fillPolygon(xPoints_a, yPoints_a, 3);
                int xPoints_b[] = {getWidth() / 2, getWidth() - 5, 5};
                int yPoints_b[] = {5, getHeight() - 10, getHeight() - 10};
                g.fillPolygon(xPoints_b, yPoints_b, 3);
                break;
        }
    }

    private void operate(){
        if (Game.state == Game.GAME_FINISHED){
            return;
        }

        if (Game.state == Game.GAME_IN_PROCESS && player){
            return;
        }

        if (visualStatus == UNKNOWN){
            if(gameStatus == EMPTY){
                visualStatus = MISS;
            } else {
                visualStatus = FIRED;
            }
        }
        this.repaint();
    }

    public Dimension getPreferredSize(){
        return new Dimension(Game.CELL_SIZE, Game.CELL_SIZE);
    }

    public Dimension getMinimumSize(){
        return new Dimension(Game.CELL_SIZE, Game.CELL_SIZE);
    }

    public Dimension getMaximumSize(){
        return new Dimension(Game.CELL_SIZE, Game.CELL_SIZE);
    }

    private class CellsMouseAdapter extends MouseAdapter{
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            System.out.print(name + " was clicked");
            System.out.println(" status: " + Integer.toString(visualStatus));
            operate();
        }
    }
}

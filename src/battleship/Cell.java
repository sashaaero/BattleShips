package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Cell extends JComponent{
    /*
        Константы
            UNKNOWN - для отображения нетронутой ячейки (или пустой для игрока)
            MISS - для отображения ячейки с промахом (или технически недоступные ячейки)
            FIRED - для отображения ячейки, в которую попали
            SHIP - для отображения ячейки с кораблем (только для поля игрока)
            TODO DOCUMENTATION
     */

    private int x;
    private int y;
    private String name;
    boolean ship;
    boolean wasAttacked;
    private boolean playerField;
    private Color color;
    Color background;
    private Field field;

    Cell(Field field, int x, int y, boolean player){
        super();
        this.x = x;
        this.y = y;
        this.field = field;
        this.setToolTipText(name);
        String letters[] = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к"};
        this.name = letters[x] + Integer.toString(y + 1);
        this.playerField = player;
        this.ship = false;
        this.wasAttacked = false;
        this.color = player ? Color.blue : Color.red;
        this.background = Color.white;
        enableInputMethods(true);
        addMouseListener(new CellsMouseAdapter());
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    protected void paintBorder(Graphics graphics){
        super.paintBorder(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);

        if (playerField){
            if (ship) {
                g.fillRect(10, 10, 10, 10);
                if (wasAttacked) {
                    g.drawLine(5, 5, getWidth() - 5, getHeight() - 5);
                    g.drawLine(getWidth() - 5, 5, 5, getHeight() - 5);
                }
            } else if (wasAttacked) {
                g.fillOval(getWidth() / 2 - 2, getHeight() / 2 - 2, 4, 4);
            }
        } else {
            if (wasAttacked) {
                System.out.printf("(%d; %d) marked as attacked\n", x, y);
                if (ship) {
                    g.drawLine(5, 5, getWidth() - 5, getHeight() - 5);
                    g.drawLine(getWidth() - 5, 5, 5, getHeight() - 5);
                } else {
                    g.fillOval(getWidth() / 2 - 2, getHeight() / 2 - 2, 4, 4);
                }
            }
        }
    }

    private void operate(){
        //if (!Game.playerTurn) return;
        switch (Game.state){
            case Game.GAME_NOT_STARTED:
                if (playerField){ //Значит мы выставляем кораблики
                    this.ship = !this.ship;
                }
                break;
            case Game.GAME_IN_PROCESS:
                if (!playerField) {
                    this.wasAttacked = true;
                    boolean res = field.army.getShot(x, y);
                    Game.ai.shoot();
                }
                break;
            case Game.GAME_FINISHED:
                break;
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

    private class CellsMouseAdapter extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            operate();
        }
    }
}

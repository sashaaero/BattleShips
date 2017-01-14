package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Cell extends JComponent{
    final int x;
    final int y;
    String name;
    boolean ship;
    boolean wasAttacked;
    boolean playerField;
    Color color;
    Color background;
    Field field;

    Cell(Field field, int x, int y, boolean player){
        super();
        this.x = x;
        this.y = y;
        this.field = field;
        String letters[] = {"а", "б", "в", "г", "д", "е", "ж", "з", "и", "к"};
        this.name = letters[x] + Integer.toString(y + 1);
        this.setToolTipText(name);
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

        if (Game.getInstance().state == Game.IN_PROCESS || Game.getInstance().state == Game.NOT_STARTED) {

            if (playerField) {
                if (ship) {
                    paintShip(g);
                    if (wasAttacked) {
                        paintHit(g);
                    }
                } else if (wasAttacked) {
                    paintMiss(g);
                }
            } else {
                if (wasAttacked) {
                    if (ship) {
                        paintHit(g);
                    } else {
                        paintMiss(g);
                    }
                }
            }
        } else if (Game.getInstance().state == Game.OVER){
            if (ship) {
                paintShip(g);
                if (wasAttacked) {
                    paintHit(g);
                }
            } else if (wasAttacked){
                paintMiss(g);
            }
        }
    }

    public void attacked(){
        this.wasAttacked = true;
        this.repaint();
    }

    private void paintShip(Graphics g){
        g.fillRect(10, 10, 10, 10);
    }

    private void paintHit(Graphics g){
        g.drawLine(5, 5, getWidth() - 5, getHeight() - 5);
        g.drawLine(getWidth() - 5, 5, 5, getHeight() - 5);
    }

    private void paintMiss(Graphics g){
        g.fillOval(getWidth() / 2 - 2, getHeight() / 2 - 2, 4, 4);
    }

    void clicked(){
        switch (Game.getInstance().state){
            case Game.NOT_STARTED:{
                if (playerField)
                    ship = !ship;
                break;
            }
            case Game.IN_PROCESS: {
                if (Game.getInstance().playerTurn && !playerField){
                    if (wasAttacked) return;
                    attacked();
                    int res = Game.getInstance().enemyField.army.getShot(x, y);
                    if(res == Ship.MISS){
                        Game.getInstance().playerTurn = false;
                        Game.getInstance().infoLabel.setText("Ходит бот!");
                    }
                }
                break;
            }
            case Game.OVER: {
                // Нечего делать
                break;
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

    private class CellsMouseAdapter extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            //if (Game.playerTurn)
                clicked();
        }
    }
}

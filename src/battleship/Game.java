package battleship;

import javax.swing.*;
import java.awt.*;

public class Game {
    static final int FIELD_SIZE = 10;
    static final int CELL_SIZE = 30;
    private final String PUT_SHIPS = "Расставьте корабли, и нажмите \"Начать\" для начала игры";
    static int GAME_NOT_STARTED = 0;
    static int GAME_IN_PROCESS = 1;
    static int GAME_FINISHED = 2;

    static int state;

    private JFrame window;
    private Field playerField;
    private Field enemyField;
    private JPanel fieldsPanel;
    private JPanel lowerPanel;
    private JButton newGame;
    private JButton surrender;
    private JButton start;
    private JLabel infoLabel;

    private Game(){
        initGUI();

        this.window.setVisible(true);
    }


    private void initGUI(){
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        window.pack();
        window.setSize(840, 540);
        window.getContentPane().setBackground(Color.white);
        window.setTitle("Морской Бой - Даниил Евсеев");

        infoLabel = new JLabel(PUT_SHIPS);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setPreferredSize(new Dimension(window.getWidth(), 90));

        newGame = new JButton("Новая игра");
        surrender = new JButton("Сдаться");
        start = new JButton("Начать");
        newGame.addActionListener(e -> newGame());
        surrender.addActionListener(e -> surrender());
        start.addActionListener(e -> start());
        newGame.setEnabled(false);
        surrender.setEnabled(false);
        lowerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets.top = 30;
        c.insets.bottom = 30;
        c.insets.left = window.getWidth() / 9;
        c.insets.right = window.getWidth() / 9;
        c.gridx = 1;
        lowerPanel.add(start, c);
        c.gridx = 2;
        lowerPanel.add(newGame, c);
        c.gridx = 3;
        lowerPanel.add(surrender, c);
        lowerPanel.setPreferredSize(new Dimension(window.getWidth(), 90));
        lowerPanel.setBackground(Color.white);

        playerField = new Field(true);
        enemyField = new Field(false);
        fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.white);
        c.insets.top = 1;
        c.insets.left = 0;
        c.insets.right = 40;
        c.gridx = 1;
        fieldsPanel.add(playerField, c);
        c.insets.left = 40;
        c.insets.right = 0;
        c.gridx = 2;
        fieldsPanel.add(enemyField, c);

        window.add(infoLabel, BorderLayout.NORTH);
        window.add(fieldsPanel, BorderLayout.CENTER);
        window.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void newGame(){

    }

    private void start(){
        /*
            Проверяем на валидность расстановки
            Запускаем игру
         */
    }

    private void surrender(){
        //TODO
    }

    //private boolean isFieldValid(){

    //}

    public static void main(String[] args) {
        new Game();
    }
}

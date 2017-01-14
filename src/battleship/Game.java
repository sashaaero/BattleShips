package battleship;

import javax.swing.*;
import java.awt.*;

public class Game {
    // Константы
    static final int FIELD_SIZE = 10;
    static final int CELL_SIZE = 30;
    private String PUT_SHIPS = "Расставьте корабли, и нажмите \"Начать\" для начала игры";
    private String WRONG_SHIPS = "Ваши корабли выставлены неверно";
    private String YOUR_TURN = "Ваш ход";
    private String ENEMY_TURN = "Ходит противник";
    static final int NOT_STARTED = 0;
    static final int IN_PROCESS = 1;
    static final int OVER = 2;

    // Поля игры
    volatile int state;
    volatile boolean playerTurn;
    public AI ai;

    // Поля интернфейса
    private JFrame window;
    public Field playerField;
    public Field enemyField;
    private JPanel fieldsPanel;
    private JPanel lowerPanel;
    private JButton newGame;
    private JButton surrender;
    private JButton start;
    JLabel infoLabel;

    private static Game gameInstance = null;

    private Game(){
        state = NOT_STARTED;
        playerTurn = true;
        playerField = new Field(true);
        enemyField = new Field(false);
        initGUI();
        this.window.setVisible(true);
    }

    public static Game getInstance(){
        if (gameInstance == null){
            gameInstance = new Game();
        }
        return gameInstance;
    }


    private void initGUI(){
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        window.pack();
        window.setSize(840, 540);
        window.getContentPane().setBackground(Color.white);
        window.setTitle("Морской Бой - Даниил Евсеев");
        window.setLocationRelativeTo(null);

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
        enemyField.clear();
        playerField.clear();
        enemyField.repaintAll();
        playerField.repaintAll();
        state = NOT_STARTED;
        infoLabel.setText("Расставьте корабли и нажмите Начать игру");

        newGame.setEnabled(false);
        surrender.setEnabled(false);
        start.setEnabled(true);
    }

    private void start(){
        if(playerField.fieldIsReady()){
            infoLabel.setText("Ваш выстрел");
            ai = new AI(enemyField, playerField);
            ai.thread.start();
            start.setEnabled(false);
            newGame.setEnabled(true);
            surrender.setEnabled(true);
            state = IN_PROCESS;
        } else {
            infoLabel.setText("Ваши корабли выставлены неверно");
        }
    }

    private void surrender(){
        over(false);
    }

    void over(boolean playerWon){
        if (playerWon) {
            infoLabel.setText("Победил игрок!");
        } else {
            infoLabel.setText("Победил компьютер");
        }
        state = OVER;
        enemyField.repaintAll();
        playerField.repaintAll();
    }
}

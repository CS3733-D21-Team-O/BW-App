package edu.wpi.cs3733.teamO.DinoGame;

import edu.wpi.cs3733.teamO.DinoGame.components.Dino;
import edu.wpi.cs3733.teamO.DinoGame.components.Ground;
import edu.wpi.cs3733.teamO.DinoGame.components.Obstacles;
import java.awt.*;
import java.awt.event.*;
import javafx.embed.swing.JFXPanel;
import javax.swing.*;

public class GamePanel extends JFXPanel implements KeyListener, Runnable {

  public static int WIDTH;
  public static int HEIGHT;
  private Thread animator;

  private boolean running = false;
  private boolean gameOver = false;

  Ground ground;
  Dino dino;
  Obstacles obstacles;

  private int score;

  public GamePanel() {
    WIDTH = 200;
    HEIGHT = 150;

    ground = new Ground(HEIGHT);
    dino = new Dino();
    obstacles = new Obstacles((int) (WIDTH * 1.5));

    score = 0;

    setSize(WIDTH, HEIGHT);
    setVisible(true);
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.setFont(new Font("Courier New", Font.BOLD, 25));
    g.drawString(Integer.toString(score), getWidth() / 2 - 5, 100);
    ground.create(g);
    dino.create(g);
    obstacles.create(g);
  }

  public void run() {
    running = true;

    while (running) {
      updateGame();
      repaint();
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void updateGame() {
    score += 1;

    ground.update();
    // dino.update();
    obstacles.update();

    if (obstacles.hasCollided()) {
      dino.die();
      repaint();
      running = false;
      gameOver = true;
    }
    // game complete condition
  }

  public void reset() {
    score = 0;

    obstacles.resume();
    gameOver = false;
  }

  public void keyTyped(KeyEvent e) {
    //
    if (e.getKeyChar() == ' ') {
      if (gameOver) reset();
      if (animator == null || !running) {

        animator = new Thread(this);
        animator.start();
        dino.startRunning();
      } else {
        dino.jump();
      }
    }
  }

  public void keyPressed(KeyEvent e) {
    //
  }

  public void keyReleased(KeyEvent e) {
    //
  }
}

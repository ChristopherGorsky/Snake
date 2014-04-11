
package snakes;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import snakes.Snake.Direction;

public class MainGame implements KeyEventDispatcher{
   
    private Canvas canvas;
    private Snake snake;
    private JFrame window;
    private Graphics2D g;  
    private Food food;
    
    private int updateRate = 8;
    private int diffuclty = 1;
        
    private final int SCREEN_WIDTH = 640;
    private final int SCREEN_HEIGHT = 480;
    
    private Clock clock;
    private Thread clockThread;
    
    private boolean gameOver = false;
    private boolean keyPressed = false;
    private boolean devMode = false;
            
    
    public MainGame() {
        
        CreateGameWindow();
        
        canvas.createBufferStrategy(2);
        g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
        
        clock = new Clock();
        clockThread = new Thread(clock);
        clockThread.start();
        
        snake = new Snake( g, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 );
        food = new Food( snake, g, SCREEN_WIDTH, SCREEN_HEIGHT );
        
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        
        runGame();
    } 
    
    private void resetGame() {
        snake.resetSnake();
        food.resetFood();
        clock.resetClock();
        runGame();
    }
    
    private void CreateGameWindow( ) {
      
        canvas = new Canvas();
        canvas.setBackground(Color.DARK_GRAY);
        canvas.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        
        window = new JFrame();
        window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        window.setBackground(Color.BLACK);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Snakes Game");
        window.setLocationRelativeTo(null);
        window.setFocusable(true);
        window.add(canvas);
        window.setVisible(true);
    }
   
    private void runGame() {
        
        long startTime;
        long sleepTime;
        
        diffuclty = 1;
        gameOver = false;
        keyPressed = false;
        
        while ( !gameOver ) {
            
            startTime = System.currentTimeMillis();
            
            if (keyPressed) {
                update();
                
            } else {
                g.setColor(Color.WHITE);
                g.drawString("Press Any Key", (SCREEN_WIDTH / 2) - 40, SCREEN_HEIGHT - 50);
            }  
            
            draw();
            //draw the buffer to the screen
            canvas.getBufferStrategy().show();
            g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            
            sleepTime = (1000L/ updateRate) - (System.currentTimeMillis() - startTime);
            
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch(Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
        
        JOptionPane.showMessageDialog( window, "Snake Is Dead \nFinal Score: " + food.getFoodEaten()
                + "\nMinutes: " + clock.getMinutes() + "\nSeconds: " + clock.getSeconds() );
        
        g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        resetGame();
    }
    
    
    private void update() {
        
        if ( keyPressed ) {
            
            g.setColor(Color.WHITE);
            g.drawString("Points: " + food.getFoodEaten(), 4, 16);
            g.setColor(Color.WHITE);
            g.drawString( "Game Time: " + clock.getTime(), 4, 16 * 2);
        }
            
        if ( food.getFoodEaten() == (5 * diffuclty) ) {
            updateRate++;
            diffuclty++;
        } 
        
        this.snake.updateSnake();
        this.food.update();
        
        if (!snake.isAlive()) {
            gameOver = true;
        }                 
    }
    
    private void draw() {
        this.food.draw();
        this.snake.drawSnake();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        
        keyPressed = true;
        
        switch ( e.getKeyCode()) { 
            
            case 38:
              this.snake.moveSnake(Direction.UP);
            break;
                
            case 40:
                this.snake.moveSnake(Direction.DOWN);
            break;
                
            case 37:
              this.snake.moveSnake(Direction.LEFT);
            break;
                
            case 39:
                this.snake.moveSnake(Direction.RIGHT);
            break;
                
            case 27:
                if ( devMode ) 
                    devMode = false;
                else 
                    devMode = true;
            break;
                           
        }
        return false;
    }
    
}

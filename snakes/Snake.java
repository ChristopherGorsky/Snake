package snakes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;

public class Snake {
    
    public enum Direction {UP,DOWN,LEFT,RIGHT,NONE};
    
    private LinkedList <Point> points;
    
    private Direction currentDirection;
    
    private Graphics2D graphics;
    
    private int actualX = 0;
    private int actualY = 0;
    
    private int segments = 0;
    
    private boolean isAlive = true;
    private final int SCREEN_WIDTH = 640;
    private final int SCREEN_HEIGHT = 480;
    private final int SNAKE_WIDTH = 16;
    private final int SNAKE_HEIGHT = 16;
    private final int SPAWN_X;
    private final int SPAWN_Y;
            
    Snake( Graphics2D g, int x, int y ) {
        
        this.points = new LinkedList();
        this.graphics = g;
        this.actualX = x;
        this.actualY = y;
        this.SPAWN_X = x;
        this.SPAWN_Y = y;
        
        resetSnake();
    }
    
    public void resetSnake() {
        
        isAlive = true;
        segments = 0;
        this.actualX = SPAWN_X;
        this.actualY = SPAWN_Y;
        this.currentDirection = Direction.DOWN;
        this.points.clear();
        
        points.add( new Point( actualX, actualY ) );
        segments++;
        
        for ( int i = 0; i < 5; i++ ) {
           addOneBody();
        }
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void addOneBody() {
        
        Point p = points.getLast();
        int x = p.x;
        int y = p.y;
        
            switch ( currentDirection ) {

                case UP:
                    x += SNAKE_HEIGHT;
                break;

                case DOWN:
                    y -= SNAKE_HEIGHT;
                break;

                case LEFT:
                    x += SNAKE_WIDTH;
                break;

                case RIGHT:
                    x -= SNAKE_WIDTH;
                break;           

         }
            
        points.add( new Point( x, y ) );
        segments++;
        
    }
    
    public void drawSnake() {
        
        boolean isHead = true;
        ListIterator <Point> iter = points.listIterator();
        
        while( iter.hasNext() ) {

                Point p = iter.next();
                graphics.setColor(Color.GREEN);
                
                if ( isHead ) {
                    graphics.drawString("("+actualX+","+actualY+")", 4 , 50);
                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(p.x, p.y, SNAKE_WIDTH, SNAKE_HEIGHT);
                    
                    graphics.setColor(Color.BLACK);
                    
                    switch ( currentDirection ) {
                        
                        case UP:
                            graphics.fillRect(p.x + 2, p.y + 4, 4, 4);
                            graphics.fillRect(p.x + SNAKE_WIDTH - 6, p.y + 4, 4, 4);
                            graphics.setColor(Color.RED);
                            graphics.fillRect(p.x + (SNAKE_WIDTH / 2) - 3, p.y - 8, 6, 8);
                        break;
                            
                        case DOWN:
                            graphics.fillRect(p.x + 2, p.y + SNAKE_HEIGHT - 8, 4, 4);
                            graphics.fillRect(p.x + SNAKE_WIDTH - 6, p.y + SNAKE_HEIGHT - 8, 4, 4); 
                            graphics.setColor(Color.RED);
                            graphics.fillRect(p.x + (SNAKE_WIDTH / 2) - 3, p.y + SNAKE_HEIGHT, 6, 8);
                        break; 
                            
                        case LEFT:
                            graphics.fillRect(p.x + 4, p.y + 2, 4, 4);
                            graphics.fillRect(p.x + 4, p.y + 10, 4, 4); 
                            graphics.setColor(Color.RED);
                            graphics.fillRect(p.x - 8, p.y + 4, 8, 6);
                        break;
                            
                        case RIGHT:
                            graphics.fillRect(p.x + SNAKE_WIDTH - 6, p.y + 2, 4, 4);
                            graphics.fillRect(p.x + SNAKE_WIDTH - 6, p.y + 10, 4, 4);  
                            graphics.setColor(Color.RED);
                            graphics.fillRect(p.x + SNAKE_WIDTH, p.y + 4, 8, 6);
                        break;
                    }

                    isHead = false;
                    
                } else {
                    graphics.fillRect(p.x, p.y, SNAKE_WIDTH, SNAKE_HEIGHT);
                }  
            }
    }
    
    public void updateSnake() {
        
        switch ( currentDirection ) {

                case UP:
                    if ( this.actualY - 16 >= 0 ) {
                        actualY -= SNAKE_HEIGHT;
                    } else {
                        killSnake();
                    }
                break;

                case DOWN:
                    if ( this.actualY + SNAKE_HEIGHT * 3 <= SCREEN_HEIGHT ) {
                        actualY += SNAKE_HEIGHT;
                    } else {
                        killSnake();
                    }
                break;

                case LEFT:
                    if ( this.actualX - 16 >= 0 ) {
                        actualX -= SNAKE_WIDTH;
                    } else {
                        killSnake();
                    }
                break;
                    

                case RIGHT:
                    if ( this.actualX + SNAKE_WIDTH * 2 <= SCREEN_WIDTH ) {
                        actualX += SNAKE_WIDTH;
                    } else {
                        killSnake();
                    }
                break;           

         }
 
        for ( int i = points.size() - 1; i > 0; i-- ) {

            Point sender = points.get(i-1);
            Point receiver = points.get(i);
            receiver.x = sender.x;
            receiver.y = sender.y;
        }
        
        Point receiver = points.getFirst();
        receiver.x = actualX;
        receiver.y = actualY;
      
    }
    
    public Point getSnakeSegment( int i ) {
        return points.get(i);
    }
    
    public Point getHead() {
        return points.getFirst();
    }
    
    public int getSegmentCount() {
        return points.size();
    }    
    
    public void killSnake() {
        isAlive = false;
    }
    
    public Direction getDirection() {
        return currentDirection;
    }
    
    public void forceLeft() {
        this.actualX -= 16;
    }
    
    public void forceUP() {
        this.actualY -= 16;
    }
    
    public void moveSnake( Direction d ) {
        
        if ( d == Direction.UP && currentDirection != Direction.DOWN ) {
            currentDirection = d;
        }
        
        if ( d == Direction.DOWN && currentDirection != Direction.UP ) {
            currentDirection = d;
        }
        
        if ( d == Direction.LEFT && currentDirection != Direction.RIGHT ) {
            currentDirection = d;
        }
        
        if ( d == Direction.RIGHT && currentDirection != Direction.LEFT ) {
            currentDirection = d;
        }        
    }
    
}

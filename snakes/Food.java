
package snakes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Food {
    
    private Graphics2D graphics;
    private Snake snake;
    private boolean foodSpawned = false;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private Point position;
    private int foodCount = 0;
    private int size = 24;
    
    public Food( Snake s, Graphics2D g, int x, int y ) {
  
        snake = s;
        graphics = g;
        SCREEN_WIDTH = x;
        SCREEN_HEIGHT = y;
        
        position = new Point();
        position.x = 0;
        position.y = 0;
        
    }
    
    public void resetFood() {
        foodSpawned = false;
        foodCount = 0;
    }
    
    private void spawnFood() {
        
        boolean spawnableLocation = false;
        boolean errorFound = false;
        Random rand = new Random(System.currentTimeMillis());
        int errorCounter = 0;
        
        while ( !spawnableLocation && errorCounter < 5 ) {
            
            this.size = 24;    
            this.position.x = rand.nextInt(SCREEN_WIDTH - 40);
            this.position.y = rand.nextInt(SCREEN_HEIGHT - 40);
            
            System.out.println("Max ("+ (SCREEN_WIDTH - 40) + "," + (SCREEN_HEIGHT - 40) + ")" );
            System.out.printf( "Size: %d\n(%d,%d)\n", this.size, this.position.x, this.position.y );
            
            for ( int i = 0; i < snake.getSegmentCount(); i++ ) {
               
                Point temp = this.snake.getSnakeSegment(i);
                
                if ( isCollision( this.position.x, this.position.y, this.size, this.size, temp.x, temp.y, 16, 16 ) ) {
                    errorFound = true;
                    errorCounter++;
                    
                } 
            }
            
            if ( !errorFound || errorCounter >= 5 ) {
                System.out.println("Found a position.");
                spawnableLocation = true;
                foodSpawned = true;   
            } else {
                System.err.println("Error - Point Blocked!");
            }

        }
    }
    
    public void update( ) {
       
        if ( !foodSpawned ) {
            spawnFood();
        } else {
                   
            Point temp = this.snake.getSnakeSegment(0);
            
            if ( isCollision( this.position.x, this.position.y, this.size, this.size, temp.x, temp.y, 16, 16 ) ) {
                foodSpawned = false;
                snake.addOneBody();
                foodCount++;
            }    
        }                
    }
    
    public int getFoodEaten() {
        return foodCount;
    }
    
    public void draw() {
        if ( foodSpawned ) {
            graphics.setColor(Color.cyan);
            graphics.fillOval( position.x, position.y, this.size, this.size );   
        }
    }
    
    private boolean isCollision( int x1, int y1,int w1,int h1, int x2,int y2,int w2,int h2 ) {
        //The sides of the rectangles
        int leftA, leftB;
        int rightA, rightB;
        int topA, topB;
        int bottomA, bottomB;

        //Calculate the sides of rect A
        leftA = x1;
        rightA = x1 + w1;
        topA = y1;
        bottomA = y1 + h1;

        //Calculate the sides of rect B
        leftB = x2;
        rightB = x2 + w2;
        topB = y2;
        bottomB = y2 + h2;

        //If any of the sides from A are outside of B
        if( bottomA <= topB )
        {
            return false;
        }

        if( topA >= bottomB )
        {
            return false;
        }

        if( rightA <= leftB )
        {
            return false;
        }

        if( leftA >= rightB )
        {
            return false;
        }

        //If none of the sides from A are outside B
        return true;
    }

}

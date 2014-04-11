/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakes;

public class Clock implements Runnable {
    
    private int minutes = 0;
    private int seconds = 0;   
    private boolean paused;
    
    public Clock() {
        paused = false;
    }
    
    public void resetClock() {
        minutes = 0;
        seconds = 0;
    }
    
    public void setPaused ( boolean b ) {
        paused = b;
    }
    
    private void tick() throws InterruptedException {
       
        while ( true ) {
            
            if ( !paused ) {
                    
                seconds++;

                if ( seconds == 60 ) {
                    minutes++;
                    seconds = 0;
                } 

                Thread.sleep(1000);
            }
        }
    }
    
    public String getTime() {
        return minutes + ":" + seconds;
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public int getSeconds() {
        return seconds;
    }
    
    @Override
    public void run() {
        try {
            tick();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
}

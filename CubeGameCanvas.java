/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author patri
 */
public class CubeGameCanvas  extends JPanel{

 
        private int initialY = 130;
        private boolean update = true; // allows the score to update if true
        private  int peakLocation = 80; // peak jump location of the cube
        private int cubeY = initialY; // y coordinate of the cube
        private int cubeWidth = 10;
        private int intitialCubeHeight = 10;
        private int cubeHeight = 10;
	private Color cubeColor = Color.white; //the size of the player cube
        private int cubeX = 20;
        private int objectX = 100;
  
        private int objectY = initialY;
      
      
        private int objectHeight = 20;
       private int objectWidth = 10;
        private int score = 0;
        private boolean gameOver = false;
        private boolean showInstructions = true;
	private int objectSpeed = 50;
	private int delta = 5;
        private int jumpSpeed = 2;
	private int xDir = +1;
	private int yDir = -1;
        private boolean ascend;
        private boolean peak = false;
	private int windowSize; 
                

	private boolean leftDown;
        private boolean jump;
        private int jumpTiming = 10;
	private boolean rightUp;
	private boolean rightDown;
        private boolean colorQ = false; 
       private Color objectColor = Color.green;
    private CubeGame animationInterface;  
    private boolean intructionsDisplayed = false; 
    private  ArrayList<Obstacle> obstacleArray = new ArrayList();
     private  ArrayList<Integer> obstacleXArray = new ArrayList();
      private  ArrayList<Integer> intialXArray = new ArrayList();
      private  ArrayList<Integer> heightArray = new ArrayList();
     private int numberOfObjects = 3; // at most 3sssss
     private  ArrayList<Integer> obstacleSpacing = new ArrayList();
     private boolean crouch; 
     private boolean uncrouch = false;
    
    public CubeGameCanvas(CubeGame animationInterface){
  
    this.animationInterface = animationInterface;
   
    
		setBackground(Color.black);
		setDoubleBuffered(true);

		setFocusable(true);
		requestFocus();
             
             
           initializeObjects(); // randomly generate initial oobjects 
             
 

//-------------------------User Controled Cube Logic--------------------------------------------     
             new Timer(jumpTiming, e -> {
               if(showInstructions){   //determine if the program has just been booted and display instructions if true 
                  animationInterface.showInstructions();
                 showInstructions = false;
                 jump = false;
               }
//--------------------- Jump Logic ---------------------------------------------------------------
                   if (jump){ // begin jump logic if user hits the jump key
                        ascend = true;                          
                    }
                    if(gameOver == true){ //cancel jump input command from the user if gameover and reset the score
                       ascend = false;
                       gameOver = false;
                       jump = false;    
                       score = 0;
                       }
                    else{
                    animationInterface.updateScore(); // update score while user has not died
                    score++;
                    }
                   if(ascend&&(!crouch||cubeY<initialY)){ //jump logic  make sure crouch is not being held while jumping or before jumping
                                                          // do not allow user to jump again while in air 
                       if (cubeY > peakLocation && !peak && jump){
                          cubeY += yDir * jumpSpeed;
                        }
                        else if (cubeY == peakLocation &&!peak || (!peak || (!jump && !peak))){ // begin descent if peak is reached 
                            peak =true;
                        }
                        else if(cubeY != initialY){
                            cubeY -= yDir * jumpSpeed;
                        }
                        else if(cubeY >initialY){
                            cubeY = initialY;
                        }
                        else{
                            ascend = false;
                           peak =false;
                        }   
                    }
 //---------------------------------------------------------------------------
  //---------------------------crouch Logic------------------------------------------------ 
         if(crouch && (!ascend||cubeY>initialY-jumpSpeed)){ //allow coruching abpve the number of pixels corespoding to jumpSpeed 
                                                            // decrease cube width by half when crouching and half the Y value to maintian orientation
            cubeHeight = intitialCubeHeight/2;
            cubeY = initialY + intitialCubeHeight/2;
           
        }else if (uncrouch){   // reset the cube height when crouch button is released 
            cubeHeight = intitialCubeHeight;
            cubeY = initialY;
            uncrouch = false;
        }          
                
            repaint();
            }).start();
//------------------------------------------------------------------------------

//--------------------------- Object Timer and logic -------------------------------------------
		new Timer(objectSpeed, e -> {
                  
                    if(!showInstructions){         // do not move objects while instructions are being shown
                        if(obstacleXArray.size() < obstacleArray.size()){
                            for(int i=0; i<obstacleArray.size();++i){
                            obstacleXArray.add(obstacleArray.get(i).getObjectX());
                        }
                            
                        }else{ // move objects 
                            for(int r=0; r<obstacleArray.size();++r){
                             obstacleXArray.set(r,obstacleArray.get(r).getObjectX()- xDir * delta);
                            }
                        }

                    windowSize = (int)getSize().getWidth(); // get the width of the window
                             
                        for(int u=0; u<obstacleArray.size();++u){ // determine whether an object has collided with the cube (game over is triggered if so)
                            obstacleArray.get(u).collide(u, obstacleArray, obstacleXArray.get(u) ,objectY, cubeX,  cubeY, cubeWidth, cubeHeight, windowSize, animationInterface, this);
                        }
		     repaint();                               
                    }

		}).start();
//-----------------------------------------------------------------------------------------------------------------------------------    
    //------------------detect key presses-------------------------------
  this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
                                if (key == KeyEvent.VK_S) {
					crouch = true;
				}

				if (key == KeyEvent.VK_Q) {
					colorQ = true;
				}

				if (key == KeyEvent.VK_SPACE) {
					jump = true;
				}
			}
          @Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				

				if (key == KeyEvent.VK_S) {
					crouch = false;
                                        uncrouch = true;
				}

				if (key == KeyEvent.VK_Q) {
					colorQ = false;
				}

				if (key == KeyEvent.VK_SPACE) {
					jump = false;
				}
			}
		});
            
          }   
    // paint objects and cube for animation 
    @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(cubeColor);       
                g.fillRect(cubeX, cubeY, cubeWidth, cubeHeight); 
                g.setColor(objectColor);
                
                for(int q=0; q<obstacleArray.size(); ++q){
                    paintObstacle( g, obstacleArray.get(q).getObjectX(),obstacleArray.get(q).getObjectHeight() ,obstacleArray.get(q).getObjectColor(),q);
                } 
	}
        /**
         * resets the game parameters
         */
        public void resetGame(){ // clears objects and re-initializes the game
            cubeColor= Color.white;
            colorQ = false;
            cubeY = initialY;
            jump = false;
            crouch = false;
            uncrouch = true;
            
            for(int i=0; i<numberOfObjects; ++i){
                 obstacleArray.remove(0);    
            }
            if(obstacleArray.size()==0){
                 System.out.println("Clear!");
                 System.out.println("Re-initializing");
                 initializeObjects();
                 }
        }

  /**
   * randomly initializes the objects in the the array
   */
        private void initializeObjects(){
                int objectRef = -999;// refferences the previous objects location note a value of -999 is imposible for already existing objects this is used to denote the first object is being generated
             for(int i=0; i<numberOfObjects; ++i){ 
                obstacleArray.add( new Obstacle() );
                obstacleArray.get(i).randomize(objectRef, initialY);
                objectRef = obstacleArray.get(i).getObjectX();
             }
        }
    /**
     * paints the obstacles
     * @param g the graphic variable
     * @param objectX object x coordinate 
     * @param objectHeight the object height
     * @param objectColor the object color 
     * @param objectNumber the object number
     */
    private void paintObstacle(Graphics g, int objectX, int objectHeight, Color objectColor, int objectNumber) {       
            g.setColor(objectColor);
            g.fillRect(objectX, obstacleArray.get(objectNumber).getInitialY() - objectHeight + intitialCubeHeight, objectWidth , objectHeight);
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
    
    public int getNumberOfObjects() {
        return numberOfObjects;
    }
    
    public void setCubeColor(Color cubeColor) {
        this.cubeColor = cubeColor;
    }

    public void setCubeHeight(int cubeHeight) {
        this.cubeHeight = cubeHeight;
    }

    public void setCrouch(boolean crouch) {
        this.crouch = crouch;
    }

    public void setUncrouch(boolean uncrouch) {
        this.uncrouch = uncrouch;
    }

    public void setCubeY(int cubeY) {
        this.cubeY = cubeY;
    }

    public void setObjectX(int objectX) {
        this.objectX = objectX;
    }

    public int getScore() {
        return score;
    }

    public void setShowInstructions(boolean showInstructions) {
        this.showInstructions = showInstructions;
    }

    public ArrayList<Obstacle> getObstacleArray() {
        return obstacleArray;
    }

    public int getInitialY() {
        return initialY;
    }

    public int getCubeY() {
        return cubeY;
    }

    public boolean isUpdate() {
        return update;
    }
    
    public boolean isGameOver() {
      return gameOver;
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setxDir(int xDir) {
        this.xDir = xDir;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

}

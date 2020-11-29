/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author patri
 */
class Obstacle { // if you are in netbeans some times this will appeared underlined asking you to rename the file this is a false error (the file is correct and the code will run without changing this)
        private int  initialX;
        private int objectX = initialX;
        private Color objectColor = Color.green; 
        private int initialY = 130; // position on start up if set here
        private int objectY = initialY;       
        private int objectHeight = 20;
        private int objectWidth = 10; 
        private CubeGameCanvas gameAnimation;
        private CubeGame animationInterface;
        private static Random rng;
        
    Obstacle(){
        initialX = 100;
    }
    
    Obstacle(int objectX2 , int objectHeight) {
       objectX = objectX2;
       this.initialX = objectX2;  
       this.objectHeight = objectHeight;
    }
/**
 * randomizes an objects height spacing type (non crouch-able and crouch-able)
 * @param objectRef the x coordinate of the object in front o the object
 * @param cubeInitialY the initial y coordinate of the cube
 */
    public void randomize(int objectRef, int cubeInitialY){
       Obstacle.rng = new Random();
       int spaceRand=rng.nextInt(10)+1;
       int heightpRand=rng.nextInt(10)+1;
       int crouchRand=rng.nextInt(10)+1;
       int obstacleSpacing = 0;
       boolean crouchObstacle = false;
         
         
       //-------------crouchable object randomizer-------------------
          if(crouchRand < 3){
          initialY = 122;
          crouchObstacle = true;
          }
          else{
          initialY = cubeInitialY;
          crouchObstacle = false;
          }
       
       //---------------height randomizer----------------------------  
         if(crouchObstacle){
            objectHeight = 40; 
         }
         else if(heightpRand < 5){
           objectHeight = 20;
          }
          else if(heightpRand < 10){
           objectHeight = 30;
          }
      //---------------spacing randomizer----------------------------       
          if(objectRef == -999){
              objectRef = 285; //first object spacing 
          }
          else if(spaceRand < 5 ){
              obstacleSpacing = 90;
          }else{
              obstacleSpacing = 80;
          }
       //------------------------------------------------------------
       
       
          objectX = objectRef + obstacleSpacing;
          initialX = objectRef + obstacleSpacing;
    }
    
    /**
     * detects if an object collides with the cube if triggers a game over
     * @param objectNumber the number the object is 
     * @param obstacleArray an array containing  various objects 
     * @param objecX x coordinate of the object
     * @param objctY y coordinate of the object 
     * @param cubeX x coordinate of the cube 
     * @param cubeY y coordinate of the cube 
     * @param cubeWidth width of the cube
     * @param cubeHeight height of the cube 
     * @param windowSize the window size 
     * @param animationInterface the game interface instance 
     * @param gameAnimation  the Cube Game Canvas instance
     */
    public void collide(int objectNumber, ArrayList<Obstacle> obstacleArray, int objecX , int objctY, int cubeX, int cubeY, int cubeWidth, int cubeHeight,int windowSize,CubeGame animationInterface, CubeGameCanvas gameAnimation){
        this.animationInterface = animationInterface;
        this.gameAnimation = gameAnimation;    
        this.objectX = objecX;              
        this.objectHeight = objectHeight;
        this.objectWidth = objectWidth;  
        
      
             
                  if(gameAnimation.isGameOver()){ // reset objects if an earlier object triggered a gameOver
                                        objectX = initialX; // call intitial set up
                                        cubeY = initialY;
                                        gameAnimation.setxDir(+1);
                                        gameAnimation.setGameOver(true);                                               
                  }                                                                                                                          

                                                                                                                                                 
                    else if(((cubeX <=  objectX +objectWidth && objectX <= cubeX+cubeWidth)||(cubeX < objectX && objectX <= cubeX+cubeWidth))&& ((initialY - objectHeight + cubeHeight <= cubeY+cubeHeight)) && (cubeY<=initialY + cubeHeight)){//detect if the cube overlaps the object
                                      
                                        objectColor = Color.red; 
                                        gameAnimation.setJump(false);
                                        gameAnimation.setUpdate(false);
                                        animationInterface.gameOver();
                                        gameAnimation.setUpdate(true);
                                        gameAnimation.setScore(0);
                                        objectX = initialX; // call intitial set up
                                        cubeY = initialY;
                                        gameAnimation.setxDir(+1);
                                        gameAnimation.setGameOver(true);
                                    
                    }
                    else{
                      objectColor = Color.blue;                
                    }
                                
                                if (objectX <= -objectWidth) {   // loop object back around to the right plane
                                    int previousObject;
                                    int cubeIntY;
                                    cubeIntY =   gameAnimation.getInitialY();
                                    if(objectNumber != obstacleArray.size()-1){ 
                                        previousObject = obstacleArray.get(objectNumber+1).getObjectX();    
                                    }
                                    else{
                                        previousObject = obstacleArray.get(obstacleArray.size()-1).getObjectX();
                                    }
                                   obstacleArray.get(objectNumber).randomize(previousObject,cubeIntY);
					
                                   
                                   objectX = windowSize;
                                        
                                        
                                    if(objectHeight < 2 || objectWidth < 2){ // reset shrunken items
                                    objectHeight = 20;
                                    objectWidth = 10;
                                    objectY = initialY;
                                    }
				}
    } 

    public void setObjectX(int objectX) {
        this.objectX = objectX;
    }

    public int getInitialY() {
        return initialY;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getObjectX() {
        return objectX;
    }

    public Color getObjectColor() {
        return objectColor;
    }

    public int getObjectY() {
        return objectY;
    }

    public int getObjectHeight() {
        return objectHeight;
    }

    public int getObjectWidth() {
        return objectWidth;
    }
    }
            




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 *
 * @author patri
 */

public class CubeGame  extends JFrame{
  /**
     * @param args the command line arguments
     */
    private CubeGameCanvas gameAnimation = new CubeGameCanvas(this);
    private JLabel scoreLabel;
    private int tempScore =0;
    private boolean start = true;
    private boolean allowPause = true;  /// ????????????????
    
    public CubeGame(){
        super("Cube Game");
        this.setLayout(new BorderLayout());
        this.getContentPane().add(gameAnimation);
        
        JPanel scorePanel = new JPanel();
	scorePanel.setLayout(new GridLayout(1, 2));
        scoreLabel = new JLabel("Score : " + gameAnimation.getScore());
        JButton button = new JButton("Show Instructions");
        button.setPreferredSize(new Dimension(10, 20));
        button.setFocusable(false);
        scorePanel.add(scoreLabel);
        scorePanel.add(button);
        
       if(allowPause){  /// ????????????????
        button.addActionListener(e -> {
            gameAnimation.setShowInstructions(true);  
            allowPause =false;
        });
       } 
       
        this.add(scorePanel, BorderLayout.SOUTH); 	
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 200); 
    }

   
    /**
     * Displays the instructions to to the user 
     */
    public void showInstructions(){ 
       JOptionPane.showMessageDialog(this, ("Instructions:\nAvoid running into objects\nand survive as long as possible!!\n-------------Controls-------------\njump --- space bar\ncrouch --- s"));	
      
    }
    /**
     *  updates the score and prints it to the score window 
     */
    public void updateScore() {
        if(gameAnimation.isUpdate()){
        tempScore = gameAnimation.getScore();
        scoreLabel.setText("Score : " + tempScore);
        }
        else{
        scoreLabel.setText("Final Score: " + (tempScore + 1)); // 1 is added to the temp score as 1 timer tic will be missed when the function is called in this case
        }
    }
    /**
     *triggers the game over screen
     */
    public void gameOver() {   
        int decision = JOptionPane.showConfirmDialog(this,"You Died!\n Your score is: "+ gameAnimation.getScore() + "\n\nContinue?", "Game Over", JOptionPane.YES_NO_OPTION);

       if(decision == JOptionPane.NO_OPTION){
         System.exit(0);
       }

            ArrayList<Obstacle> obstacleArray = gameAnimation.getObstacleArray(); // reset objects
                gameAnimation.resetGame();      
	}
    
    public static void main(String[] args) {
        // TODO code application logic here
            SwingUtilities.invokeLater(new Runnable() {
                public void run(){ 
                new CubeGame().setVisible(true);
            }
      }); 
    }   
}

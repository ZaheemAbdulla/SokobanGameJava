/**
 * Setting the GUI.
 * To make and implement the level maps.
 * To take in the input of the user.
 *
 * @author (Zaheem Abdulla)
 * @version (12 June 2021)
 */

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Sokoban extends JFrame //Inheriting from the JFrame class
{
	private final int OFFSET = 145;

	
	/**
	 * Constructor
	 */
    public Sokoban() 
    {
        try 
        {
			GUI();//Calling Method
		} 
        
        catch (IOException e) 
        {
			e.printStackTrace();
		}
    }

    /**
     * Creates a JPannel as the main layout or the GUI of the game.
     * 
     * @throws IOException
     */
    private void GUI() throws IOException 
    { 
    	
        SokobanGame board = new SokobanGame();
        add(board);//adding the board method
        setTitle("Sokoban Game - Zaheem Abdulla");//setting the headline of the game GUI (JFrame)
        
        setSize(board.getsokoWidth() + OFFSET, board.getsokoHeight() + OFFSET);//Setting the h & w of the GUI(JFrame)
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
    
    /**
     * this is the main class
     * @param args
     */
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(() -> { Sokoban game = new Sokoban(); game.setVisible(true); });
    }
}
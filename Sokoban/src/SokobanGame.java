/**
 * To load and provides access to the map and read the map.
 * To read and access the next level after the previous level is completed.
 * After the completion of fifth level the maps get reset to the first level.
 * The algorithm checks the Ware-House Keeper if he is going through the wall or crates.
 * The algorithm checks the Crate if it is above the Diamond, it also checks if it is in a corner of the walls or if it is in front of an another crate.
 *  
 * @author (Zaheem Abdulla)
 * @version (15 June 2021)
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SokobanGame extends JPanel 
{
	private static final String EXIT_ON_CLOSE = null;
	private final int OFFSET = 30;//Distance between the game layout and the border of the window
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;
    
	private String level[] = new String[6];
    private int levelCount = 1;
	private int moveCount = 0;

    private int h = 0;
    private int w = 0;
    private boolean isComp = false;
    private WK soko;
    
    private ArrayList<Wall> walls;//The walls are special containers which holds all the walls of the game.
    private ArrayList<Crate> crates;//The crate are special containers which holds all the crate of the game.
    private ArrayList<Diamond> diamonds;//The diamonds are special containers which holds all the diamonds of the game.
	
    
    /**
     * Constructor
     * @throws IOException
     */
    SokobanGame() throws IOException 
	{
        sokoGUI();//Calling Method
    }

    private void sokoGUI() throws IOException
    {
        addKeyListener(new TAdapter());
        setFocusable(true);
        worldGUI();//Calling Method
    }
    
    /**
     * Saves sokoban's width
     * @return w 
     */
    public int getsokoWidth()
    {
        return this.w;
    }
    
    /**
     * Saves sokoban's height
     * @return h
     */
    public int getsokoHeight() 

    {
        return this.h;
    }

    /**
     * This is a method in which which initiates the GUI of the game.
     * It goes through the level string and updates the array lists.
     * @throws IOException
     */
    private void worldGUI() throws IOException 
    {
    	
        walls = new ArrayList<>();
        crates = new ArrayList<>();
        diamonds = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;//calling the object
        Crate crate;//calling the object
        Diamond diamond;//calling the object
        
        level[levelCount] = new String ( Files.readAllBytes( Paths.get("levels/level"+levelCount+".txt") ) );
        for (int i = 0; i < level[levelCount].length(); i++) 
        {

            char item = level[levelCount].charAt(i);
            switch (item) 
            {
                case '\n'://line character (\n) starts a new row of the world.
                    y += SPACE;
                    if (this.w < x) 
                    {
                        this.w = x;
                    }
                    x = OFFSET;
                    break;

                case 'w'://stands for the wall picture and helps the program to read the map
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case 'C'://stands for the crate picture and helps the program to read the map
                    crate = new Crate(x, y);
                    crates.add(crate);
                    x += SPACE;
                    break;

                case 'D'://stands for the diamond picture and helps the program to read the map
                    diamond = new Diamond(x, y);
                    diamonds.add(diamond);
                    x += SPACE;
                    break;

                case 'P'://stands for the WK character picture and helps the program to read the map
                    soko = new WK(x, y);
                    x += SPACE;
                    break;

                case 'F'://Stands for free space
                    x += SPACE;
                    break;

                default:
                    break;
            }
            h = y;
        }
    }
    
    /**
     * The generateWorld() method draws the game on the GUI using graphics class.
     * @param g (consists of the components to generate the game)
     */
    private void generateWorld(Graphics g) 
    {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<MapElement> world = new ArrayList<>();//Creating a GUI list which includes all objects of the game.

        world.addAll(walls);//contains the element of the game
        world.addAll(diamonds);//contains the element of the game
        world.addAll(crates);//contains the element of the game
        world.add(soko);//contains the element of the game

        g.setColor (new Color(255, 0, 0));
		g.drawString ("Moves Count:" + moveCount, 25, 325);//Creating the move count statement which counts each time the WK Actor moves in a given direction.
		g.setColor (new Color(0, 0, 255));
		g.drawString ("Level:" + levelCount, 300, 325);//Creating the level count statement which shows the level which you currently in.

		
        for (int i = 0; i < world.size(); i++) 
        {
            MapElement item = world.get(i);
            if (item instanceof WK || item instanceof Crate) 
            {
                g.drawImage(item.getImg(), item.x(), item.y(), this);
            } 
            
            else 
            {  
                g.drawImage(item.getImg(), item.x(), item.y(), this);
            }
        }
    }

    /**
     * adds the key components to the GUI
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        generateWorld(g);
    }
    
    /**
     * Checks if the arrow key is pressed. 
     * Inside this method it checks if the WK satisfy the conditions for the movement when given the arrow keys are pressed.
     */
    class TAdapter extends KeyAdapter 
    {

    	Graphics g;
    	
		public void keyPressed(KeyEvent e)  
        {
            if (isComp) 
            {
                return;
            }

            int key = e.getKeyCode();

            switch (key) 
            {
                case KeyEvent.VK_LEFT:
                    
                    if (checkWallCollision(soko, LEFT_COLLISION))//Check is there is a wall colliding the path
                    {
                        return;
                    }
                    
                    if (checkCrateCollision(LEFT_COLLISION))//Check is there is a crate colliding the path 
                    {
                        return;
                    }
                    
                    soko.move(-SPACE, 0);
                    moveCount++;//To update when ever the left key is pressed and the WK moves. 
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    if (checkWallCollision(soko, RIGHT_COLLISION))//Check is there is a wall colliding the path
                    {
                        return;
                    }
                    
                    if (checkCrateCollision(RIGHT_COLLISION))//Check is there is a crate colliding the path  
                    {
                        return;
                    }
                    
                    soko.move(SPACE, 0);
                    moveCount++;//To update when ever the left key is pressed and the WK moves.
                    break;
                    
                case KeyEvent.VK_UP:
                    
                    if (checkWallCollision(soko, TOP_COLLISION))//Check is there is a wall colliding the path 
                    {
                        return;
                    }
                    
                    if (checkCrateCollision(TOP_COLLISION))//Check is there is a crate colliding the path  
                    {
                        return;
                    }
                    
                    soko.move(0, -SPACE);
                    moveCount++;//To update when ever the left key is pressed and the WK moves.
                    break;
                    
                case KeyEvent.VK_DOWN:
                    
                    if (checkWallCollision(soko, BOTTOM_COLLISION))//Check is there is a wall colliding the path 
                    {
                        return;
                    }
                    
                    if (checkCrateCollision(BOTTOM_COLLISION))//Check is there is a crate colliding the path  
                    {
                        return;
                    }
                    
                    soko.move(0, SPACE);
                    moveCount++;//To update when ever the left key is pressed and the WK moves.
                    break;
                    
                case KeyEvent.VK_SPACE://This is to reset the level of the game by pressing the space bar.
				try 
				{
					resetLevel();
				}
				
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
                    
				moveCount = 0;//To reset the move counter to 0 when the space bar is pressed
                    
                default:
                    break;
            }
            repaint();
        }
    }

    /**
     * Checks if the WK is against the wall. if there is wall near to the crate or the WK.
     * @param coord (consists of all the data)
     * @param type (holds integer data)
     * @return false
     * @return true
     */
    private boolean checkWallCollision(MapElement coord, int type) 
    {
    	switch (type) 
    	{
        	case LEFT_COLLISION:
        		for (int i = 0; i < walls.size(); i++) 
        		{
        			Wall wall = walls.get(i);
	                if (coord.isLeftCollision(wall)) 
	                {
	                    return true;
	                }
	            }
	            return false;
	            
	        case RIGHT_COLLISION:
	            for (int i = 0; i < walls.size(); i++) 
	            { 
	                Wall wall = walls.get(i);
	                
	                if (coord.isRightCollision(wall)) 
	                {
	                    return true;
	                }
	            }
	            
	            return false;
	            
	        case TOP_COLLISION:
	            
	            for (int i = 0; i < walls.size(); i++) 
	            {
	                Wall wall = walls.get(i);
	                if (coord.isTopCollision(wall)) 
	                {  
	                    return true;
	                }
	            }
	            
	            return false;
	            
	        case BOTTOM_COLLISION:
	            
	            for (int i = 0; i < walls.size(); i++) 
	            {
	                Wall wall = walls.get(i);
	                if (coord.isBottomCollision(wall)) 
	                {
	                    return true;
	                }
	            }
	            
	            return false;
	        	default:
	        		break;
	    }
	    return false;
	}
   
    /**
     * Checks if the WK is pushing the Crate. 
     * Inside this method it checks if the Crate meets the requirement for the being pushed.
     * @param type (holds integer data)
     * @return false
     * @return true 
     */
    private boolean checkCrateCollision(int type) 
    {

        switch (type) 
        {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < crates.size(); i++) 
                {

                	Crate box = crates.get(i);

                    if (soko.isLeftCollision(box)) 
                    {

                        for (int j = 0; j < crates.size(); j++) 
                        {
                            
                        	Crate item = crates.get(j);
                            
                            if (!box.equals(item)) 
                            {
                                 
                                if (box.isLeftCollision(item)) 
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(box, LEFT_COLLISION)) 
                            {
                            	JOptionPane.showMessageDialog(null, "Your Crate is being Pushed to a wall which is not possible.");
                                return true;
                            }
                        }
                        
                        box.move(-SPACE, 0);
                        try 
                        {
							isCompleted();
						} 
                        catch (IOException e) 
                        {
							e.printStackTrace();
						}
                    }
                }
                
                return false;
                
            case RIGHT_COLLISION:
                
                for (int i = 0; i < crates.size(); i++) 
                {

                	Crate crate = crates.get(i);
                    
                    if (soko.isRightCollision(crate)) 
                    {
                        
                        for (int j = 0; j < crates.size(); j++) 
                        {

                        	Crate item = crates.get(j);
                            
                            if (!crate.equals(item)) 
                            {
                                
                                if (crate.isRightCollision(item)) 
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(crate, RIGHT_COLLISION)) 
                            {
                            	JOptionPane.showMessageDialog(null, "Your Crate is being Pushed to a wall which is not possible.");
                                return true;
                            }
                        }
                        
                        crate.move(SPACE, 0);
                        try 
                        {
							isCompleted();
						} 
                        catch (IOException e) 
                        {
							e.printStackTrace();
						}
                    }
                }
                return false;
                
            case TOP_COLLISION:
                
                for (int i = 0; i < crates.size(); i++) 
                {

                	Crate crate = crates.get(i);
                    
                    if (soko.isTopCollision(crate)) 
                    {
                        
                        for (int j = 0; j < crates.size(); j++) 
                        {

                        	Crate item = crates.get(j);

                            if (!crate.equals(item)) 
                            {
                                
                                if (crate.isTopCollision(item)) 
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(crate, TOP_COLLISION)) 
                            {
                            	JOptionPane.showMessageDialog(null, "Your Crate is being Pushed to a wall which is not possible.");
                                return true;
                            }
                        }
                        
                        crate.move(0, -SPACE);
                        try 
                        {
							isCompleted();
						} 
                        catch (IOException e) 
                        {
							e.printStackTrace();
						}
                    }
                }

                return false;
                
            case BOTTOM_COLLISION:
                
                for (int i = 0; i < crates.size(); i++) 
                {
                	Crate crate = crates.get(i);
                    if (soko.isBottomCollision(crate)) 
                    {
                        for (int j = 0; j < crates.size(); j++) 
                        {
                        	Crate item = crates.get(j);
                            if (!crate.equals(item)) 
                            {
                                
                                if (crate.isBottomCollision(item)) 
                                {
                                    return true;
                                }
                            }
                            if (checkWallCollision(crate,BOTTOM_COLLISION)) 
                            {
                            	JOptionPane.showMessageDialog(null, "Your Crate is being Pushed to a wall which is not possible.");
                                return true;
                            }
                        }
                        
                        crate.move(0, SPACE);
                        try 
                        {
							isCompleted();
						}
                        
                        catch (IOException e) 
                        {
							e.printStackTrace();
						}
                    }
                }
            default:
                break;
        }
        return false;
    }

    /**
     * This checks if the level has been completed
     * If completed then it will move towards the next level. 
     * @throws IOException
     */
    public void isCompleted() throws IOException 
    {
        int nOfcrates = crates.size();
        int finishedCrates = 0;

        for (int i = 0; i < nOfcrates; i++) 
        {
        	Crate bag = crates.get(i);
            for (int j = 0; j < nOfcrates; j++) 
            {
                Diamond diamond =  diamonds.get(j);
                if (bag.x() == diamond.x() && bag.y() == diamond.y()) 
                {
                	finishedCrates += 1;
                }
            }
        }

        if (finishedCrates == nOfcrates)//Checks if the crates are above the diamond.
        {
        	isComp= true;
            if (levelCount <5)
            {
            	levelCount++;//After the all the crates are above the diamond the level increases.
            }
            
            else
            {
            	levelCount=1;//After the all the levels are completed the level starts back to the 1st level.
            }
            resetLevel();
            moveCount = -1;//After each level the move counter changes its value to 0
            repaint();
        }
    }

    /**
     * resetting all the crates, diamonds, walls and the GUI after the reset button. 
     * @throws IOException
     */
    private void resetLevel() throws IOException 
    {
        diamonds.clear();
        crates.clear();
        walls.clear();

        worldGUI();

        if (isComp) 
        {
            isComp = false;
        }
    }
}
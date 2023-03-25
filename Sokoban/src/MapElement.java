/**
 * Connects to MapElement through inheritance.
 * It is used for the implementation of its position and setting the image.
 * 
 * @author (Zaheem Abdulla)
 * @version (17 June 2021)
 */

import java.awt.Image;

public class MapElement
{
    private final int SPACE = 20;
    
    private boolean canBePushed;
	private boolean finalDestination;
	private boolean obstacle;
	
	private String symb;
	private String picFileName;
	private Image pic;
    private MapElement underneath;
    
	private int x;
    private int y;
    
    /**
     * Constructor
     * @param x
     * @param y
     */
    public MapElement(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }
    
	/**
	 * takes image from all the class Wall, Crate, Diamond and WK
	 * @return pic
	 */
    public Image getImg() 
    {
        return pic;
    }

    /**
     * Set the image from all the class Wall, Crate, Diamond and WK
     * @param img
     */
    public void setImg(Image img) 
    {
        pic = img;
    }

    public int x() 
    {  
        return x;
    }

    public int y() 
    {  
        return y;
    }

    /**
     * set X(value)
     * @param x
     */
    public void setX(int x) 
    {
        this.x = x;
    }

    /**
     * setY(value)
     * @param y
     */
    public void setY(int y) 
    {
        this.y = y;
    }

    /**
     * Check Left Collision
     * @param coord
     * @return x() - SPACE == coord.x() && y() == coord.y()	
     */
    public boolean isLeftCollision(MapElement coord) 
    {
        return x() - SPACE == coord.x() && y() == coord.y();
    }

    /**
     * Check Right Collision
     * @param coord
     * @return x() + SPACE == coord.x() && y() == coord.y()
     */
    public boolean isRightCollision(MapElement coord) 
    {
        return x() + SPACE == coord.x() && y() == coord.y();
    }

    /**
     * Check Top Collision
     * @param coord
     * @return y() - SPACE == coord.y() && x() == coord.x()
     */
    public boolean isTopCollision(MapElement coord) 
    { 
        return y() - SPACE == coord.y() && x() == coord.x();
    }

    /**
     * Check Bottom Collision
     * @param coord
     * @return y() + SPACE == coord.y() && x() == coord.x()
     */
    public boolean isBottomCollision(MapElement coord) 
    {
        return y() + SPACE == coord.y() && x() == coord.x();
    }

}
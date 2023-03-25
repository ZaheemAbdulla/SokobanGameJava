/**
 * Connects to MapElement through inheritance.
 * It is used for the implementation of its position and setting the image.
 * 
 * @author (Zaheem Abdulla)
 * @version (13 June 2021)
 */

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends MapElement 
{
    private Image picture;

    /**
     * Constructor
	 * @param x coordinate
	 * @param y coordinate
     */
    public Wall(int x, int y) 
    {
        super(x, y);
        wallGUI();//Calling Method
    }
    
    /**
     * setting the GUI for the wall(linking the image)
     */
    private void wallGUI() 
    {
        ImageIcon pic = new ImageIcon("Image/Wall.jpg");//Specifying the image location
        picture = pic.getImage();
        setImg(picture);//setting the image
    }
}
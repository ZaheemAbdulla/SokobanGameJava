/**
 * Connects to MapElement through inheritance.
 * It is used for the implementation of its position and setting the image.
 * 
 * @author (Zaheem Abdulla)
 * @version (13 June 2021)
 */

import java.awt.Image;
import javax.swing.ImageIcon;

public class Diamond extends MapElement 
{
	/**
	 * Constructor
	 * @param x coordinate
	 * @param y coordinate
	 */
    public Diamond(int x, int y) 
    {
        super(x, y);
        diamondGUI();//Calling Method
    }
    
    /**
     * setting the GUI for the Diamond(linking the image)
     */
    private void diamondGUI() 
    {
        ImageIcon pic = new ImageIcon("Image/diamond1.png");//Specifying the image location
        Image image = pic.getImage();
        setImg(image);//setting the image
    }
}
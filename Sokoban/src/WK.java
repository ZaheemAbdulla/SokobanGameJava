/**
 * Connects to MapElement through inheritance.
 * It is used for the implementation of its position and setting the image.
 * 
 * @author (Zaheem Abdulla)
 * @version (13 June 2021)
 */
import java.awt.Image;
import javax.swing.ImageIcon;

public class WK extends MapElement 
{
	/**
	 * Constructor
	 * @param x coordinate
	 * @param y coordinate
	 */
    public WK(int x, int y) 
    {
        super(x, y);
        WKGUI();//Calling Method
    }
    
    /**
     * setting the GUI for the WK(linking the image)
     */
    private void WKGUI()
    {
        ImageIcon pic = new ImageIcon("Image/WK.png");//Specifying the image location
        Image image = pic.getImage();
        setImg(image);//setting the image
    }

    /**
     * Stores the position of the WK and sets its self.
     * It is responsible for the movement of the WK in the virtual world
	 * @param x coordinate
	 * @param y coordinate
     */
    public void move(int x, int y) 
    {
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}
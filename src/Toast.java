import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is an implementation of a Toast class. The toast class is used to
 * display messages to the user on the GUI.
 *
 * @author Colton Donkersgoed
 */
public class Toast {
    /** int for the x,y coordinates of the toast **/
    private int x, y;
    /** String representing the type of the toast **/
    private final String type;
    /** Color representing the color to be used for the toast **/
    private Color color;
    /** String representing the text to write for the toast **/
    private String text;
    /** ints representing the lifetime and the current time of the toast **/
    private int lifetime = 5;
    private int current = 0;

    /**
     * Initializes an instance of the Toast class with given x,y coordinates,
     * and the type of Toast that it is.
     * @param x: int representing the x-coordinate for the toast.
     * @param y: int representing the y-coordinate for the toast.
     * @param type: the String representing the type of Toast.
     */
    public Toast(int x, int y, String type){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Initializes an instance of the Toast class with given x,y coordinates,
     * the type that it is, the text to be displayed, and the color of the Toast.
     *
     * @param x: int representing the x-coordinate of the toast.
     * @param y: int representing the y-coordinate of the toast.
     * @param type: String representing the type of the toast.
     * @param text: String representing the text to be displayed with the text.
     * @param color: Color representing which color the message will be written with.
     */
    public Toast(int x, int y, String type, String text, Color color){
        this.x = x;
        this.y = y;
        this.type = type;
        this.text = text;
        this.color = color;
    }

    /**
     * Draws the toast on the given GraphicsContext
     * @param gc: GraphicsContext representing the context with which to draw the toast.
     */
    public void draw(GraphicsContext gc){
        if (type == "Text"){
            gc.setStroke(this.color);
            gc.strokeText(this.text, this.x, this.y);
            gc.setStroke(Color.BLACK);
            this.y -= 1;
            current += 1;
        }
    }

    /**
     * Returns if the toast is expired or not.
     * @return boolean representing whether the toast should be deleted
     */
    public boolean getExpired(){
        return (getCurrent() >= getLifetime());
    }

    /**
     * Returns the lifetime of the toast.
     * @return int representing the lifetime of the toast.
     */
    public int getLifetime(){
        return this.lifetime;
    }

    /**
     * Returns the current time elapsed of the toast.
     * @return int representing the current time of the toast.
     */
    public int getCurrent(){
        return this.current;
    }
}

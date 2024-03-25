import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is a basic class for our more specific workers to draw from.
 *
 * This class will track the amount that the worker mines, the number of workers,
 * and the size of the drawing.
 */
public class Worker {
    /** Below are the variables for the Worker class**/
    /** A tracker for the total amount mined by the workers **/
    private static int totalMined;
    /** The x and y position of the worker **/
    private int x, y;
    /** The size of the worker**/
    private int size;
    /** The amount of income generated by the base worker **/
    private int amount;
    private String type;


    /**
     * The constructor for the basic worker class, predefining the size and the amount
     * to 10 and 1 respectively.
     * @param x: an integer representing the x coordinate to draw the worker
     * @param y: an integer representing the y coordinate to draw the worker
     */
    public Worker(int x, int y){
        this.x = x;
        this.y = y;
        this.size = 10;
        this.amount = 1;
        this.type = "Worker";
    }

    /**
     * The draw method for the worker, draws the worker onto the canvas provided.
     * @param gc: The GraphicsContext with which to draw the worker
     */
    public void draw(GraphicsContext gc){
        gc.setFill(Color.PURPLE);
        gc.fillOval(x, y, this.size, this.size);
        gc.setFill(Color.BLACK);
    }

    /**
     * The command calls for the worker to mine a given amount.
     * @return integer value of the amount mined by the worker
     */
    public int mine(){
        totalMined += this.amount;
        return this.amount;

    }

    /**
     * Returns the x value of the worker
     * @return integer value representing the x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns the y value of the worker
     * @return integer value representing the y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Returns the size of the worker, as an integer
     * @return integer representing the size of the worker
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Returns the type of the worker, defaulting to "Worker"
     * @return String representing the type of the worker
     */
    public String getType(){
        return this.type;
    }

    /**
     * Sets the type of the Worker
     * @param type: a String representing the type of the worker to change to
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Returns the income amount generated by the worker
     * @return integer representing the worker value
     */
    public int getAmount(){
        return this.amount;
    }

    /**
     * Sets the income amount the worker generates
     * @param amount: integer representing the amount generated by the worker
     */
    public void setAmount(int amount){
        this.amount = amount;
    }

}

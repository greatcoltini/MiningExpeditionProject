import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *  An implementation of a basic Miner class. This Miner extends from the Worker superclass
 *  and has its own implementation of the draw method.
 *
 * @author Colton Donkersgoed
 */
public class Miner extends Worker{

    /**
     * Creates an instance of a Miner class, the lowest tier of workers for the factory.
     *
     * @param x: the x coordinate at which to draw the miner.
     * @param y: the y coordinate at which to draw the miner.
     */
    public Miner(int x, int y){
        super(x, y);
        this.setType("Miner");

    }

    /**
     * Draws the Miner onto the given GraphicsContext.
     *
     * @param gc: The GraphicsContext with which to draw the worker
     */
    public void draw(GraphicsContext gc){
        gc.setFill(Color.PURPLE);
        gc.fillOval(getX(), getY(), getSize(), getSize());
        gc.setFill(Color.BLACK);
    }

}

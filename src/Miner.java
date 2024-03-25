import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Colton Donkersgoed
 *
 * TODO: DOCSTRING
 */
public class Miner extends Worker{

    /**
     *
     * @param x
     * @param y
     */
    public Miner(int x, int y){
        super(x, y);

    }

    /**
     *
     * @param gc: The GraphicsContext with which to draw the worker
     */
    public void draw(GraphicsContext gc){
        gc.setFill(Color.PURPLE);
        gc.fillOval(getX(), getY(), getSize(), getSize());
        gc.setFill(Color.BLACK);
    }

    /**
     *
     * @return
     */
    public String getType(){
        return "Miner";
    }

}

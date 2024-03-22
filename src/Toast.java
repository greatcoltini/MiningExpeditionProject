import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Toast {
    private int x, y;
    private final String type;
    private Color color;
    private String text;
    private int lifetime = 5;
    private int current = 0;

    public Toast(int x, int y, String type){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Toast(int x, int y, String type, String text, Color color){
        this.x = x;
        this.y = y;
        this.type = type;
        this.text = text;
        this.color = color;
    }

    public void draw(GraphicsContext gc){
        if (type == "Text"){
            gc.setStroke(this.color);
            gc.strokeText(this.text, this.x, this.y);
            gc.setStroke(Color.BLACK);
            this.y -= 1;
            current += 1;
        }
    }

    public boolean getExpired(){
        return (getCurrent() >= getLifetime());
    }

    public int getLifetime(){
        return this.lifetime;
    }

    public int getCurrent(){
        return this.current;
    }
}

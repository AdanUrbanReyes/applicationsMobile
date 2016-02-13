package buttons;
import javax.microedition.lcdui.*;
public class Pressed extends Canvas{
    private Alert alert;
    private Display display;
    public Pressed(Display d){
        display=d;
    }
    public void paint(Graphics g){
        g.setColor(255,255,255);
        g.fillRect(0,0,getWidth(),getHeight());
    }
    public void keyPressed(int i){
        switch(getGameAction(i)){
            case Canvas.DOWN:
                alert=new Alert("down "+i);
            break;
            case Canvas.UP:
                alert=new Alert("up "+i);
            break;
            case Canvas.RIGHT:
                alert=new Alert("rigth "+i);
            break;
            case Canvas.LEFT:
                alert=new Alert("left "+i);
            break;
        }
        alert.setTimeout(250);
        display.setCurrent(alert);
    }
}

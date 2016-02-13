package buttons;
import javax.microedition.lcdui.*;
class Window extends Canvas{
    //private Alert alert;
    private int x,y,flatx,flaty;
    public Window(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void paint(Graphics g){
        g.setColor(255,255,255);
        g.fillArc(flatx,flaty,11,11,0,360);
        //g.drawArc(flatx,flaty,1,1,0,360);
        g.setColor(255,0,0);
        g.fillArc(x, y,11,11,0,360);
//        g.drawArc(x,y,1,1,0,360);
    }
    public void keyPressed(int i){
        switch(getGameAction(i)){
            case Canvas.DOWN:
                flaty=y-1;
                flatx=x;
                y++;
            break;
            case Canvas.UP:
                flaty=y+1;
                flatx=x;
                y--;
            break;
            case Canvas.RIGHT:
                flatx=x-1;
                flaty=y;
                x++;
            break;
            case Canvas.LEFT:
                flatx=x+1;
                flaty=y;
                x--;
            break;    
        }
        repaint();
    }

}

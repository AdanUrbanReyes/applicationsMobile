package buttons;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class PixelMove extends MIDlet{
    private Display display;
    private Form form;
    //private Command stepOne;
    private Window window;
    public PixelMove(){
        display=Display.getDisplay(this);
        form=new Form("draw anything");
        window=new Window(0,0);
        
    }
    public void startApp(){
        display.setCurrent(window);
    }
    public void pauseApp(){
        ;
    }
    public void destroyApp(boolean unconditional) {;
    }
}

package buttons;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Buttons extends MIDlet{
    private Display display;
    private Pressed pressed;
    public Buttons(){
        display=Display.getDisplay(this);
        pressed=new Pressed(display);
    }
    public void startApp() {
        display.setCurrent(pressed);
    }
    
    public void pauseApp() {;
    }
    
    public void destroyApp(boolean unconditional) {;
    }
}

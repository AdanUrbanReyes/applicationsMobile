package imagenes;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Imagen extends MIDlet{
    private Display display;
    private Command stepOne;
    private Pintar figuras;
    public Imagen(){
        display=Display.getDisplay(this);
        figuras=new Pintar(display);
    }
    public void startApp(){
        display.setCurrent(figuras);
    }
    public void pauseApp() {
    ;
    }
    public void destroyApp(boolean unconditional) {
    ;
    }
}

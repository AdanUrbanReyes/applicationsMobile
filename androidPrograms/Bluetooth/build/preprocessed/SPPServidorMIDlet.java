import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class SPPServidorMIDlet extends MIDlet implements CommandListener {
	public static SPPServidorMIDlet SPPs= null;
	public static Display display;
	private SPPServidor s =null;
	public SPPServidorMIDlet() {
		SPPs = this;
	}
	public void startApp() {
		display = Display.getDisplay(this);
		s = new SPPServidor();
		s.inicializar();
		display.setCurrent(s);
	}
	public void pauseApp() {}
	public void destroyApp(boolean unconditional) {}
	public void salir(){
		SPPs.destroyApp(true);
		SPPs.notifyDestroyed();
		SPPs = null;
	}
	public void commandAction(Command c, Displayable d) {
		if (d == s && c.getLabel().equals("Salir")) {
			try{
				s.fin = true;
				s.servidor.close();
			}catch(Exception e){}
			salir();
		}
	}
}

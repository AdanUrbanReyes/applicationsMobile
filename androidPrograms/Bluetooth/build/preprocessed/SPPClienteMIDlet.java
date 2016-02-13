import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
import java.util.*;
public class SPPClienteMIDlet extends MIDlet implements CommandListener {
	public static SPPClienteMIDlet SPPc= null;
	public static Display display;
	private SPPCliente c =null;
	private Mensaje msg = null;
	public LocalDevice dispositivoLocal;
	public DiscoveryAgent da;
	public static Vector dispositivos_encontrados = new Vector();
	public static Vector servicios_encontrados = new Vector();
	public static int dispositivo_seleccionado = -1;
	public SPPClienteMIDlet(){
		SPPc = this;
	}
	public void startApp() {
		display = Display.getDisplay(this);
		c = new SPPCliente();
		msg = new Mensaje();
		c.mostrarDispositivos();
		display.setCurrent(c);
	}
	public void pauseApp() {}
	public void destroyApp(boolean unconditional) {}
	public static void salir(){
		SPPc.destroyApp(true);
		SPPc.notifyDestroyed();
		SPPc = null;
	}
	public void mostrarAlarma(Exception e, Screen s, int tipo){
		Alert alerta=null;
		if(tipo == 0){
			alerta = new Alert("Excepcion","Se ha producido la excepcion "+e.getClass().getName(), null, AlertType.ERROR);
		}else if(tipo==1){
			alerta = new Alert("Error","No ha seleccionado un dispositivo ", null, AlertType.ERROR);
		}else if(tipo==2){
			alerta = new Alert("Informacion","El mensaje ha sido enviado ", null, AlertType.INFO);
		}
		alerta.setTimeout(Alert.FOREVER);
		display.setCurrent(alerta,s);
	}
	public void commandAction(Command co, Displayable d){
		if(d==c && co.getLabel().equals("Busqueda")){
			dispositivos_encontrados.removeAllElements();
			servicios_encontrados.removeAllElements();
			try{
				dispositivoLocal = LocalDevice.getLocalDevice();
				dispositivoLocal.setDiscoverable(DiscoveryAgent.GIAC);
				da = dispositivoLocal.getDiscoveryAgent();
				da.startInquiry(DiscoveryAgent.GIAC,new Listener());
				c.escribirMensaje("Por favor espere...");
			}catch(BluetoothStateException be){
				mostrarAlarma(be,c, 0);
			}
		}else if(d==c && co.getLabel().equals("Enviar")){
			dispositivo_seleccionado = c.getSelectedIndex();
			if(dispositivo_seleccionado == -1 || dispositivo_seleccionado >= dispositivos_encontrados.size()){
				mostrarAlarma(null, c,1);
				return;
			}
			display.setCurrent(msg);
		}else if(d==c && co.getLabel().equals("Salir")){
			salir();
		}else if(d==msg && co.getLabel().equals("OK")){
			servicios_encontrados.removeAllElements();
			RemoteDevice dispositivo_remoto =(RemoteDevice)dispositivos_encontrados.elementAt(dispositivo_seleccionado);
			try{
				da.searchServices(null,new UUID[]{new UUID(0x1101)},dispositivo_remoto,new Listener());
			}catch(BluetoothStateException be){
				mostrarAlarma(be, c, 0);
			}
		}
	}
	public void enviarMensaje(String msg){
		ServiceRecord sr = (ServiceRecord)servicios_encontrados.elementAt(0);
		String URL = sr.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT,false);
		try{
			StreamConnection con = (StreamConnection)Connector.open(URL);
			DataOutputStream out = con.openDataOutputStream();
			out.writeUTF(msg);
			out.flush();
			out.close();
			con.close();
			mostrarAlarma(null, c, 2);
		}catch(Exception e){
			mostrarAlarma(e, c, 0);
		}
	}
	public class Listener implements DiscoveryListener{
		public void deviceDiscovered(RemoteDevice dispositivoRemoto, DeviceClass clase){
			System.out.println("Se ha encontrado un dspositivo Bluetooth");
			dispositivos_encontrados.addElement(dispositivoRemoto);
		}
		public void inquiryCompleted(int completado){
			System.out.println("Se ha completado la busqueda de dispositivos");
			if(dispositivos_encontrados.size()==0){
				Alert alerta = new Alert("Problema","No se ha encontrado dispositivos",null, AlertType.INFO);
				alerta.setTimeout(3000);
				c.escribirMensaje("Presione descubrir dispositivos");
				display.setCurrent(alerta,c);
			}else{
				c.mostrarDispositivos();
				display.setCurrent(c);
			}
		}
		public void servicesDiscovered(int transID, ServiceRecord[] servRecord){
			System.out.println("Se ha encontrado un servicio remoto");
			for(int i=0;i<servRecord.length;i++){
				ServiceRecord record = servRecord[i];
				servicios_encontrados.addElement(servRecord);
			}
		}
		public void serviceSearchCompleted(int transID, int respCode){
			System.out.println("Terminada la busqueda de servicios");
			if(servicios_encontrados.size()>0){
				enviarMensaje(msg.getString());
			}else{
				c.mostrarDispositivos();
				display.setCurrent(c);
			}
		}
	}
}

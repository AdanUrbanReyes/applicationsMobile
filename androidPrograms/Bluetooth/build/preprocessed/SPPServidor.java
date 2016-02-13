import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;
public class SPPServidor extends List implements Runnable{
	public LocalDevice dispositivoLocal;
	public DiscoveryAgent da;
	public boolean fin = false;
	public StreamConnectionNotifier servidor;
	public SPPServidor(){
		super("Servidor SPP",List.EXCLUSIVE);
		addCommand(new Command("Salir",Command.EXIT,1));
		setCommandListener(SPPServidorMIDlet.SPPs);
	}
	public void mostrarAlarma(Exception e, Screen s, int tipo){
		Alert alerta;
		if(tipo == 0){
			alerta = new Alert("Excepcion:","Se ha producido la excepcion "+e.getClass().getName(), null, AlertType.ERROR);
		}else{
			alerta = new Alert("Error:","No ha seleccionado un dispositivo ", null, AlertType.ERROR);
		}
		alerta.setTimeout(Alert.FOREVER);
		SPPServidorMIDlet.display.setCurrent(alerta,s);
	}
	public void inicializar(){
		try{
			dispositivoLocal = LocalDevice.getLocalDevice();
			dispositivoLocal.setDiscoverable(DiscoveryAgent.GIAC);
			Thread hilo = new Thread(this);
			hilo.start();
		}catch(BluetoothStateException be){
			System.out.println("Se ha producido un error al inicializar el hilo servidor");
		}
	}
	public void run(){
		String nombre = "Ejemplo SPP";
		UUID uuid = new UUID(0xABCD);
		servidor = null;
		StreamConnection sc = null;
		RemoteDevice rd = null;
		try{
			servidor = (StreamConnectionNotifier)Connector.open("btspp://localhost:"+uuid.toString()+";name="+nombre);
			ServiceRecord rec = dispositivoLocal.getRecord(servidor);
			DataElement e1 = new DataElement(DataElement.DATSEQ);
			DataElement e2 = new DataElement(DataElement.DATSEQ);
			e2.addElement(new DataElement(DataElement.UUID,new UUID(0x1101)));
			e2.addElement(new DataElement(DataElement.INT_8,1));
			e1.addElement(e2);
			rec.setAttributeValue(0x0009,e1);
		}catch(Exception e){
			System.out.println("Se ha producido un error al lanzar el hilo servidor");
			mostrarAlarma(e, this,0);
			return;
		}
		while(!fin){
			try{
				append("Esperando mensaje:",null);
				sc = servidor.acceptAndOpen();
				rd = rd.getRemoteDevice(sc);
				DataInputStream in = sc.openDataInputStream();
				append(in.readUTF(),null);
				sc.close();
			}catch(Exception e){
				mostrarAlarma(e,this, 0);
				return;
			}
		}
	}
}

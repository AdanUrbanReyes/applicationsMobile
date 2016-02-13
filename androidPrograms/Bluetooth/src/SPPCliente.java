import javax.microedition.lcdui.*;
import javax.bluetooth.*;
public class SPPCliente extends List{
	public SPPCliente(){
		super("Cliente SPP",List.EXCLUSIVE);
		addCommand(new Command("Busqueda",Command.SCREEN, 1));
		addCommand(new Command("Enviar",Command.SCREEN, 1));
		addCommand(new Command("Salir",Command.EXIT, 1));
		this.setCommandListener(SPPClienteMIDlet.SPPc);
	}
	public void escribirMensaje(String str){
		for(int i=0;i<this.size();i++) delete(i);
		append(str,null);
	}
	public void mostrarDispositivos(){
		for(int i=0;i<this.size();i++) delete(i);
		if(SPPClienteMIDlet.dispositivos_encontrados.size()>0){
			for(int i=0;i<SPPClienteMIDlet.dispositivos_encontrados.size();i++){
				try{
					RemoteDevice dispositivoRemoto = (RemoteDevice)
					SPPClienteMIDlet.dispositivos_encontrados.elementAt(i);
					append(dispositivoRemoto.getFriendlyName(false),null);
				}catch(Exception e){
					System.out.println("Se ha producido una excepcion");
				}
			}
		}
		else append("Pulse Busqueda",null);
	}
}
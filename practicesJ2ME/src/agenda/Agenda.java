package agenda;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
public class Agenda extends MIDlet implements CommandListener{
    private Display window;
    private Form formMain,comodin;
    private Command addRegister,removeRegister,changeRegister,listRegister,back,save,remove,change;
    private TextField name,phone,email,idRemoveChange;
    private StringItem siname,siphone,siemail;
    private RecordStore rs=null;
    private String nameBaseDatos="agenda";
    private Contacto contacto;
    private Alert alert;
    private byte []bufer=null;
    private int idRegister;
    public Agenda(){
        window=Display.getDisplay(this);
        formMain=new Form("form main");
        comodin=new Form("comodin");
        addRegister=new Command("addRegister",Command.OK,2);
        removeRegister=new Command("removerRegister",Command.OK,2);
        changeRegister=new Command("changeRegister",Command.OK,2);
        listRegister=new Command("listRegister",Command.OK,2);
        formMain.addCommand(addRegister);
        formMain.addCommand(removeRegister);
        formMain.addCommand(changeRegister);
        formMain.addCommand(listRegister);
        formMain.setCommandListener(this);
        
        back=new Command("back",Command.BACK,2);
        
        name=new TextField("enter name","",30,TextField.ANY);
        phone=new TextField("enter phone","",10,TextField.NUMERIC);
        email=new TextField("enter email","",30,TextField.EMAILADDR);
        save=new Command("save",Command.OK,2);
        alert=new Alert("");
        alert.setTimeout(3000);
        
        idRemoveChange=new TextField("enter id of register to delete","",10,TextField.NUMERIC);
        remove=new Command("remove",Command.OK,2);
        
        change=new Command("changeOne",Command.OK,2);
        try{
            rs=RecordStore.openRecordStore(nameBaseDatos,true);
        }catch(Exception e){alert.setString("error tring open data base :S");window.setCurrent(alert);}
    }
    public void startApp(){
        window.setCurrent(formMain);
    }
    public void pauseApp(){
        ;
    }
    public void destroyApp(boolean b){
        ;
    }
    public void listRegister(){
        try{
            ByteArrayInputStream bais;
            DataInputStream dis;
            RecordEnumeration registros=rs.enumerateRecords(null,null,false);//filtro,comparacion,false solo lectura true modo edicion
            comodin.deleteAll();
            comodin.removeCommand(remove);
            comodin.removeCommand(change);
            while(registros.hasNextElement()){//recorro todos los registros
                idRegister=registros.nextRecordId();//obtengo los id de los registros
                bufer=rs.getRecord(idRegister);
                bais=new ByteArrayInputStream(bufer);
                dis=new DataInputStream(bais);
                contacto=new Contacto(dis.readUTF().toString(),dis.readUTF().toString(),dis.readUTF().toString());
                siname=new StringItem(idRegister+"   name",contacto.getname());
                siphone=new StringItem("phone",contacto.getphone());
                siemail=new StringItem("email",contacto.getemail());
                comodin.append(siname);
                comodin.append(siphone);
                comodin.append(siemail);
            }
            comodin.addCommand(back);
            comodin.setCommandListener(this);
            window.setCurrent(comodin);
        }catch(Exception e){alert.setString("\nerror in listar "+e.getMessage());window.setCurrent(alert);}
    }
    public void saveRegister(){
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            DataOutputStream dos=new DataOutputStream(baos);
            contacto=new Contacto(name.getString(),phone.getString(),email.getString());
            dos.writeUTF(contacto.getname());
            dos.writeUTF(contacto.getphone());
            dos.writeUTF(contacto.getemail());
            bufer=baos.toByteArray();
            idRegister=rs.addRecord(bufer,0,bufer.length);
            alert=new Alert("save thats ok :D "+idRegister);
            alert.setTimeout(2000);
            window.setCurrent(alert);
            window.setCurrent(formMain);
        }catch(Exception e){alert.setString("\nerro data ouput stream "+e.getMessage());window.setCurrent(alert);}    
    }
    public void removeRegister(){
        try{
            rs.deleteRecord(Integer.parseInt(idRemoveChange.getString()));
            alert.setString("register remove ok :D");
            window.setCurrent(alert);
            window.setCurrent(formMain);
        }catch(Exception e){alert.setString("error at tring remove register "+e.getMessage());window.setCurrent(alert);}
    }
    public void changeRegister(){
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            DataOutputStream dos=new DataOutputStream(baos);
            contacto=new Contacto(name.getString(),phone.getString(),email.getString());
            dos.writeUTF(contacto.getname());
            dos.writeUTF(contacto.getphone());
            dos.writeUTF(contacto.getemail());
            bufer=baos.toByteArray();
            rs.setRecord(Integer.parseInt(idRemoveChange.getString()),bufer,0,bufer.length);
            alert.setString("register change ok :D");
            window.setCurrent(alert);
            window.setCurrent(formMain);
        }catch(Exception e){alert.setString("error at tring change register "+e.getMessage());window.setCurrent(alert);}        
    }
    public void formAddRegister(){
        comodin.deleteAll();
        comodin.append(name);
        comodin.append(phone);
        comodin.append(email);
        comodin.addCommand(save);
        comodin.addCommand(back);
        comodin.setCommandListener(this);
        window.setCurrent(comodin);
    }
    public void formRemoveRegister(){
        listRegister();
        comodin.append(idRemoveChange);
        comodin.addCommand(remove);
    }
    public void formChangeRegister(){
        listRegister();
        idRemoveChange=new TextField("enter id register to change","",10,TextField.NUMERIC);
        comodin.append(idRemoveChange);
        comodin.append(name);
        comodin.append(phone);
        comodin.append(email);
        comodin.addCommand(change);
    }
    public void commandAction(Command co,Displayable d){
        if(d==formMain){
            if(co==addRegister)
                formAddRegister();
            else
                if(co==removeRegister)
                    formRemoveRegister();
                else
                    if(co==changeRegister)
                        formChangeRegister();
                    else
                        if(co==listRegister)
                            listRegister();
        }
        if(d==comodin){
            if(co==back)
                window.setCurrent(formMain);
            else 
                if(co==save)
                    saveRegister();
                else
                    if(co==remove)
                        removeRegister();
                    else
                        if(co==change)
                            changeRegister();
        }
    }
}

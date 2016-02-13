package practicas;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class FirstTextField extends MIDlet implements CommandListener{
    private Display display;
    private Form form;
    private Command stepOne;
    private TextField fullName,phoneNumber,email,address;
    public FirstTextField(){
        display=Display.getDisplay(this);
        form=new Form("agenda");
        stepOne=new Command("stepOne",Command.OK,2);
        fullName=new TextField("enter full name","",70,TextField.ANY);
        phoneNumber=new TextField("enter phone number","",11,TextField.NUMERIC);
        email=new TextField("enter email","",70,TextField.ANY);
        address=new TextField("enter addres","",70,TextField.ANY);
        form.append(fullName);
        form.append(phoneNumber);
        form.append(email);
        form.append(address);
        form.addCommand(stepOne);
        form.setCommandListener(this);
    }
    public void startApp() {
        display.setCurrent(form);
    }
    
    public void pauseApp() {;
    }
    
    public void destroyApp(boolean unconditional) {;
    }
    public void commandAction(Command co,Displayable d){
        if(co==stepOne){
            form.deleteAll();
            fullName=new TextField("name:",fullName.getString(),70,TextField.ANY);
            phoneNumber=new TextField("phone number:",phoneNumber.getString(),70,TextField.ANY);
            email=new TextField("email:",email.getString(),70,TextField.ANY);
            address=new TextField("address:",address.getString(),70,TextField.ANY);
            form.append(fullName);
            form.append(phoneNumber);
            form.append(email);
            form.append(address);
            form.removeCommand(stepOne);
        }
    }
}

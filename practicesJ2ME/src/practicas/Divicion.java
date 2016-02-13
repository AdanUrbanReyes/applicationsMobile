package practicas;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Divicion extends MIDlet implements CommandListener{
    private Display display;
    private Form form;
    private Command stepOne;
    private TextField numerador,denominador;
    private TextBox resultado;
    public Divicion(){
        display=Display.getDisplay(this);
        form=new Form("divicion");
        stepOne=new Command("stepOne",Command.OK,2);
        numerador=new TextField("enter numerador","",512,TextField.NUMERIC);
        denominador=new TextField("enter denominador","",10,TextField.NUMERIC);
        resultado=new TextBox("","",512,TextField.ANY);
        form.append(numerador);
        form.append(denominador);
        form.addCommand(stepOne);
        form.setCommandListener(this);
    }
    public void startApp(){
        display.setCurrent(form);
    }
    public void pauseApp() {
        ;
    }
    public void destroyApp(boolean unconditional) {
        ;
    }
    public String makeDivicion(String numerador,String denominador){
      int i;
      int flat=0;
      String residuo="",result="";
      double d=Double.parseDouble(denominador);
      double n;
      for(i=0;i<numerador.length();i++){
         if(flat>=1)result+="0";
         residuo+=numerador.charAt(i);
         n=Double.parseDouble(residuo);
         flat++;
         if(n>=d){//si se puede aser la divicion
            residuo=""+(int)(n%d);
            result+=(int)(n/d);
            flat=0;
         }
      }
      return ("\nresultado = "+result+"     resuido "+residuo);
   }
   public void commandAction(Command c,Displayable d){
        if(c==stepOne){
            resultado.setString(makeDivicion(numerador.getString(),denominador.getString()));
            display.setCurrent(resultado);
        }
    }
}

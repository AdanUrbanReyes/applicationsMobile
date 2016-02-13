package practicas;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Numeros extends MIDlet implements CommandListener{
    private Display display;
    private Form form;
    private Command stepOne;
    private TextField number;
    private TextBox progress;
    private String aux="";
    public Numeros(){
        display=Display.getDisplay(this);
        form=new Form("number beautiful");
        stepOne=new Command("stepOne",Command.OK,2);
        number=new TextField("enter number ","",10,TextField.NUMERIC);
        progress=new TextBox("","",512,TextField.ANY);
        form.append(number);
        form.addCommand(stepOne);
        form.setCommandListener(this);
    }
    public void startApp(){
        display.setCurrent(form);
    }
    public void pauseApp(){
        ;
    }
    public void destroyApp(boolean unconditional) {
        ;
    }
    public void numberBeautiful(int number){
        if(number<1){
            aux+="\nno es un numero maravilloso\n";
            return;
        }
        while(number!=1){
            aux+=" "+number+" ";
            if(number%2==0)
                number/=2;
            else
                number=number*3+1;
        }
        aux+="\tsi fue numbero maravilloso\n";
    }
    public boolean isPrimo(int number){
        int i;
        for(i=2;i<number;i++)
            if(number%i==0)
                return false;
        return true;
    }
    public void numbersPrimos(int tope){
        int number;
        aux+="\nlos primeros "+tope+" numeros primos\n";
        for(number=1;tope>0;number++){
            if(isPrimo(number)){
                aux+=" "+number+" ";
                tope--;
            }
        }
    }
    public int fibonacci(int tope){
        aux+=tope;
        if(tope<2)
            return 1;
        return fibonacci(tope-1)+fibonacci(tope-2);
    }
    public void commandAction(Command c,Displayable d){
        if(c==stepOne){
            numberBeautiful(Integer.parseInt(number.getString()));
            numbersPrimos(5);
            //aux+="\nserie de fibonacci\n";
            //fibonacci(5);
            progress.setString(aux);
            display.setCurrent(progress);
        }
    }
}

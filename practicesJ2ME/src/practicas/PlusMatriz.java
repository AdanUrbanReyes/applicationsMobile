package practicas;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class PlusMatriz extends MIDlet implements CommandListener{
    private Display display;
    private Form form;
    private Command stepOne,stepTwo,stepTree;
    private TextField n;
    private TextField [][]matriz;
    private TextBox resultado;
    private int lenghtMatriz;//tamano de la matris
    private int [][]sumandoOne,sumandoTwo;
    public PlusMatriz(){
        display=Display.getDisplay(this);
        form=new Form("suma de matrizes");
        stepOne=new Command("stepOne",Command.OK,2);
        stepTwo=new Command("stepTwo",Command.OK,2);
        stepTree=new Command("stepTree",Command.OK,2);
        n=new TextField("enter the size of matrizes","",10,TextField.NUMERIC);
        resultado=new TextBox("resultado de la suma \n","",512,TextField.ANY);
        form.append(n);
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
    public void setValueOfMatriz(){
        int r,c;
        for(r=0;r<lenghtMatriz;r++){
            for(c=0;c<lenghtMatriz;c++){
                matriz[r][c]=new TextField("["+r+"]["+c+"]","",10,TextField.NUMERIC);
                form.append(matriz[r][c]);
            }
        }
    }
    public void plusMatrizes(){
        int r,c;
        for(r=0;r<lenghtMatriz;r++)
            for(c=0;c<lenghtMatriz;c++)
                sumandoOne[r][c]+=sumandoTwo[r][c];
    }
    public void commandAction(Command co,Displayable d){
        int r,c;
        //Alert a=new Alert("entro step One");
        if(co==stepOne){
            lenghtMatriz=Integer.parseInt(n.getString());
            matriz=new TextField[lenghtMatriz][lenghtMatriz];
            sumandoOne=new int[lenghtMatriz][lenghtMatriz];
            sumandoTwo=new int[lenghtMatriz][lenghtMatriz];
            form.deleteAll();
            form.removeCommand(stepOne);
            setValueOfMatriz();
            form.addCommand(stepTwo);
            form.setCommandListener(this);
        }else{
            if(co==stepTwo){
                for(r=0;r<lenghtMatriz;r++)
                    for(c=0;c<lenghtMatriz;c++)
                        sumandoOne[r][c]=Integer.parseInt(matriz[r][c].getString());
                form.deleteAll();
                setValueOfMatriz();
                form.removeCommand(stepTwo);
                form.addCommand(stepTree);
                form.setCommandListener(this);
            }else{
                if(co==stepTree){
                    String aux="";
                    for(r=0;r<lenghtMatriz;r++)
                        for(c=0;c<lenghtMatriz;c++)
                            sumandoTwo[r][c]=Integer.parseInt(matriz[r][c].getString());
                    plusMatrizes();
                    form.deleteAll();
                    for(r=0;r<lenghtMatriz;r++){
                        for(c=0;c<lenghtMatriz;c++)
                            aux+=sumandoOne[r][c]+"   ";
                            //resultado.setString(""+sumandoOne[r][c]+"\t");
                        aux+="\n";
                        //resultado.setString("\n");
                    }
                    resultado.setString(aux);
                    form.removeCommand(stepTree);
                    display.setCurrent(resultado);
                }
            }
        }
    }
}
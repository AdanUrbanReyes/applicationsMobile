package practicas;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Determinante extends MIDlet implements CommandListener{
    private Display display;
    private Form form;
    private Command stepOne,stepTwo;
    private TextField n;
    private TextField [][]matrizTF;
    private TextBox resultado;
    private int [][]matriz;
    private int ni,determinante;
    public Determinante(){
        display=Display.getDisplay(this);
        stepOne=new Command("stepOne",Command.EXIT,2);
        stepTwo=new Command("stepTwo",Command.EXIT,2);
        form=new Form("calculo determinante matriz n*n");
        n=new TextField("ingrese el valor de n ","",10,TextField.NUMERIC);
        resultado=new TextBox("","",512,TextField.ANY);
        form.append(n);
        form.addCommand(stepOne);
        form.setCommandListener(this);
    }
    public void startApp(){
        display.setCurrent(form);
    }
    public void pauseApp() {;
    }
    public void destroyApp(boolean unconditional) {;
    }
    public void pideValuesOfMatriz(int n){
        int r,c;
        matrizTF=new TextField[n][n];
        for(r=0;r<n;r++){
            for(c=0;c<n;c++){
                matrizTF[r][c]=new TextField("["+(r+1)+"]["+(c+1)+"]","",10,TextField.NUMERIC);
                form.append(matrizTF[r][c]);
            }
        }
    }
    public void setValuesinMatriz(int n){
        int r,c;
        matriz=new int[n][n];
        for(r=0;r<n;r++){
            for(c=0;c<n;c++){
                matriz[r][c]=Integer.parseInt(matrizTF[r][c].getString());
            }
        }
    }
    public int calculaDeterminante(int [][]matriz,int n){
        if(n==2)
            return matriz[0][0]*matriz[1][1]-matriz[0][1]*matriz[1][0];
        int c,i,j,k,cta=0;
        for(c=0;c<n;c++){
            int [][]submatriz=new int [n-1][n-1];
            for(i=1;i<n;i++){
                for(j=0,k=0;j<n;j++){
                    if(j!=c)
                        submatriz[i-1][k++]=matriz[i][j];
                }
            }
            if(c%2==0)
                cta+=matriz[0][c]*calculaDeterminante(submatriz,n-1);
            else
                cta-=matriz[0][c]*calculaDeterminante(submatriz,n-1);
        }
        return cta;
    }
    public void commandAction(Command c,Displayable d){
        if(c==stepOne){
            form.deleteAll();
            ni=Integer.parseInt(n.getString());
            pideValuesOfMatriz(ni);
            form.addCommand(stepTwo);
            form.setCommandListener(this);
        }else{
            if(c==stepTwo){
                setValuesinMatriz(ni);
                determinante=calculaDeterminante(matriz,ni);
                resultado.setString("\ndeterminante = "+determinante);
                display.setCurrent(resultado);
            }
        }
    }
}

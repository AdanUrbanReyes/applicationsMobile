package reproductor;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Alert;
import javax.microedition.media.Player;
import javax.microedition.media.Manager;
import javax.microedition.media.control.VolumeControl;
import java.io.InputStream;
public class Music extends MIDlet implements CommandListener{
    private Display window;
    private Form fmain;
    private Alert alert;
    private Command play;
    public Music(){
        window=Display.getDisplay(this);
        fmain=new Form("reproductor");
        play=new Command("play",Command.OK,2);
        fmain.addCommand(play);
        fmain.setCommandListener(this);
        alert=new Alert("");
        alert.setTimeout(3000);
    }
    public void startApp() {
        window.setCurrent(fmain);
    }
    
    public void pauseApp() {;
    }
    
    public void destroyApp(boolean unconditional) {;
    }
    public void openFileAudio(String route){
        Player musicPlayer=null;
        try{
            /*InputStream is = getClass().getResourceAsStream(route);
            musicPlayer = Manager.createPlayer(is, "audio/x-wav");
            /*
                Ficheros "wave audio": audio/x-wav
                Ficheros AU:audio/basic
                Ficheros MP3:audio/mpeg
                Ficheros MIDI:audio/midi
                Secuencias de tonos simples:audio/x-tone-seq
            
           // VolumeControl vc=(VolumeControl)musicPlayer.getControl("VolumeControl");
           // if(vc!=null)vc.setLevel(100);
            musicPlayer.prefetch();
            //musicPlayer.setLoopCount(-1);
            musicPlayer.start();*/
            Manager.playTone(68,7000,50);
        }catch(Exception e){
            alert.setString("\nerror in openFileAudio "+e.getMessage());
            window.setCurrent(alert);
        }
        //musicPlayer.close();
    }
    public void commandAction(Command co,Displayable d){
        if(d==fmain){
            if(co==play){//weAreYoungEchosmith.mp3
                openFileAudio("/tono.wav");
            }
        }
    }
}
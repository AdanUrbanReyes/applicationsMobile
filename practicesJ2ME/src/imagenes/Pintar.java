package imagenes;
import javax.microedition.lcdui.*;
public class Pintar extends Canvas{
    Image image=null;
    Alert alert=null;
    public Pintar(Display d){
        alert=new Alert("");
        alert.setTimeout(3000);
        try{///home/ayan/photo/gaara.jpg
            image=Image.createImage("/genio.gif");
        }catch(Exception e){System.out.print("\nerro in load image -> "+e.getMessage());alert.setString("error in load image "+e.getMessage());d.setCurrent(alert);}
    }
    public void paint(Graphics g){
        g.setColor(255,255,255);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(255,0,0);
        g.drawImage(image,30,30,Graphics.TOP|Graphics.LEFT);
        g.drawArc(30+image.getWidth(),30+image.getHeight(),30,30,0,360);
        g.drawLine(20,70+image.getHeight(),90,70+image.getHeight());
        g.drawLine(20,70+image.getHeight(),20+35,70+image.getHeight()-61);
        g.drawLine(20+70,70+image.getHeight(),55,70+image.getHeight()-61);
    }
}

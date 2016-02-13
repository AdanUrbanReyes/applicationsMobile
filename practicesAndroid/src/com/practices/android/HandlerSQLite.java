package com.practices.android;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.LinkedList;
import static android.provider.BaseColumns._ID;
public class HandlerSQLite extends SQLiteOpenHelper{
    public HandlerSQLite(Context context){
        super(context,"mobiles",null,1);
    }
    public void onCreate(SQLiteDatabase database){
        String query="CREATE TABLE contactos(name char(23) not null,phone char(23) not null,email char(23) not null,primary key(phone));";
        database.execSQL(query);
    }
    public void onUpgrade(SQLiteDatabase database,int versionOld,int versionNew){
        database.execSQL("DROP TABLE IF EXISTS contactos;");
        onCreate(database);
    }
    public void deleteContact(String phone){
        ;
    }
    public void insertNewRegister(Contacto contacto){
        ContentValues values=new ContentValues();//values that i will insert
        values.put("name",contacto.getname());//first parameter is the column of table the data base and second paramenter is value
        values.put("phone",contacto.getphone());//columna phone and the value is contacto.getphone()
        values.put("email",contacto.getemail());
        this.getWritableDatabase().insert("contactos",null,values);//first paramenter is table where i will insert the values 
    }
    public LinkedList<Contacto> readRegisters(){
        LinkedList<Contacto> list=new LinkedList<Contacto>();
        String []columns={"name","phone","email"};
        Cursor c=this.getReadableDatabase().query("contactos",columns,null,null,null,null,null);//first parameter is name of table and second is columns that i like geting
        int in,ip,ie;
        in=c.getColumnIndex("name");//get index for get name after
        ip=c.getColumnIndex("phone");
        ie=c.getColumnIndex("email");
        c.moveToFirst();
        list.add(new Contacto(c.getString(in),c.getString(ip),c.getString(ie)));
        while(c.moveToNext())
            list.add(new Contacto(c.getString(in),c.getString(ip),c.getString(ie)));
        return list;
    }
    public boolean removeRegister(String phone){
        String query="DELETE FROM contactos WHERE phone='"+phone+"';";
        this.getWritableDatabase().execSQL(query);
        return true;
    }
    public void openDataBase(){
        this.getWritableDatabase();
    }
    public void closeDataBase(){
        this.close();
    }
}

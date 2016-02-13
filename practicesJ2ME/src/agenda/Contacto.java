package agenda;
public class Contacto {
    private String name,phone,email;
    public Contacto(String name,String phone,String email){
        this.name=name;
        this.phone=phone;
        this.email=email;
    }
    public String getname(){
        return name;
    }
    public String getphone(){
        return phone;
    }
    public String getemail(){
        return email;
    }
}

class animal{
    
    public void sonido(){
        System.out.println("animal hace sonido");
    
    }
}

class perro extends animal{
    
     public void sonido(){
        System.out.println("guau guau guau guau");
}
}
class gato extends animal{
    
     public void sonido(){
        System.out.println("miau miau miau miau");
}      
}
public class polimorfismo {
    
    public static void main(String[] args){
        
        animal lulu = new animal();
        
        lulu.sonido();
        
        lulu = new perro();
        
        lulu.sonido();
        
        lulu = new gato();
        
        lulu.sonido();
    }
}

import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        
        int num1, num2, suma;
        
        System.out.println("ingresa el valor 1");
        num1 = in.nextInt();
        
        System.out.println("ingresa el valor 2");
        num2 = in.nextInt();
        
        suma = num1 + num2;
        
        System.out.println("la suma = "+ suma);
        
        if (suma >10) {
             System.out.println("suma mayor que 10");
        
        } else {
         System.out.println("suma menor que 10");
        }
    }
}

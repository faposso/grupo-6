import java.util.Scanner;

abstract class Seguro {
    private String nombreTomador;
    private int edad;
    private String genero;
    private double valorAsegurado;
    private int duracion;

    public Seguro(String nombreTomador, int edad, String genero, double valorAsegurado, int duracion) {
        this.nombreTomador = nombreTomador;
        this.edad = edad;
        this.genero = genero;
        this.valorAsegurado = valorAsegurado;
        this.duracion = duracion;
    }


    public String getNombreTomador() {
        return nombreTomador;
    }
    public void setNombreTomador(String nombreTomador) {
        this.nombreTomador = nombreTomador;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public double getValorAsegurado() {
        return valorAsegurado;
    }
    public void setValorAsegurado(double valorAsegurado) {
        this.valorAsegurado = valorAsegurado;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public abstract double calcularTotal();
    public abstract void mostrarDetalle();
}

class SeguroVida extends Seguro {
    private String cobertura;

    public SeguroVida(String nombreTomador, int edad, String genero, double valorAsegurado, int duracion, String cobertura) {
        super(nombreTomador, edad, genero, valorAsegurado, duracion);
        this.cobertura = cobertura;
    }

    @Override
    public double calcularTotal() {
        double base = getValorAsegurado() * 0.03;
        double adicionalDuracion = getValorAsegurado() * 0.01 * getDuracion();
        double adicionalCobertura = 0;
        switch (cobertura.toUpperCase()) {
            case "A":
                adicionalCobertura = getValorAsegurado() * 0.10;
                break;
            case "B":
                adicionalCobertura = getValorAsegurado() * 0.15;
                break;
            case "C":
                adicionalCobertura = getValorAsegurado() * 0.20;
                break;
        }
        return base + adicionalDuracion + adicionalCobertura;
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("Tipo de seguro: Vida");
        System.out.println("Nombre del cliente: " + getNombreTomador());
        System.out.println("Edad: " + getEdad());
        System.out.println("Valor asegurado: " + getValorAsegurado());
        System.out.println("Duración: " + getDuracion() + " año(s)");
        System.out.println("Cobertura: " + cobertura);
        System.out.println("Total a pagar: " + calcularTotal());
    }
}

class SeguroVehiculo extends Seguro {
    private String marca;
    private String tipoVehiculo;

    public SeguroVehiculo(String nombreTomador, int edad, String genero, double valorAsegurado, String marca, String tipoVehiculo) {
        super(nombreTomador, edad, genero, valorAsegurado, 1);
        this.marca = marca;
        this.tipoVehiculo = tipoVehiculo;
    }

    @Override
    public double calcularTotal() {
        double base = getValorAsegurado() * 0.05;
        double adicionalTipo = 0;
        if (tipoVehiculo.equalsIgnoreCase("moto")) {
            adicionalTipo = getValorAsegurado() * 0.15;
        } else if (tipoVehiculo.equalsIgnoreCase("carro")) {
            adicionalTipo = getValorAsegurado() * 0.10;
        }
        return base + adicionalTipo;
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("Tipo de seguro: Vehículo");
        System.out.println("Nombre del cliente: " + getNombreTomador());
        System.out.println("Edad: " + getEdad());
        System.out.println("Valor asegurado: " + getValorAsegurado());
        System.out.println("Duración: 1 año");
        System.out.println("Marca: " + marca);
        System.out.println("Tipo de vehículo: " + tipoVehiculo);
        System.out.println("Total a pagar: " + calcularTotal());
    }
}

public class taller {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al sistema de gestión de seguros");
        System.out.println("Seleccione el tipo de seguro:");
        System.out.println("1. Seguro de Vida");
        System.out.println("2. Seguro de Vehículo");
        int opcion = sc.nextInt();
        sc.nextLine();
        Seguro seguro = null;
        if (opcion == 1) {
            System.out.print("Nombre del cliente: ");
            String nombre = sc.nextLine();
            System.out.print("Edad: ");
            int edad = sc.nextInt();
            sc.nextLine();
            System.out.print("Género: ");
            String genero = sc.nextLine();
            System.out.print("Valor asegurado: ");
            double valor = sc.nextDouble();
            System.out.print("Duración (años): ");
            int duracion = sc.nextInt();
            sc.nextLine();
            System.out.print("Cobertura (A/B/C): ");
            String cobertura = sc.nextLine();
            seguro = new SeguroVida(nombre, edad, genero, valor, duracion, cobertura);
        } else if (opcion == 2) {
            System.out.print("Nombre del cliente: ");
            String nombre = sc.nextLine();
            System.out.print("Edad: ");
            int edad = sc.nextInt();
            sc.nextLine();
            System.out.print("Género: ");
            String genero = sc.nextLine();
            System.out.print("Valor asegurado: ");
            double valor = sc.nextDouble();
            sc.nextLine();
            System.out.print("Marca: ");
            String marca = sc.nextLine();
            System.out.print("Tipo de vehículo (moto/carro): ");
            String tipo = sc.nextLine();
            seguro = new SeguroVehiculo(nombre, edad, genero, valor, marca, tipo);
        } else {
            System.out.println("Opción no válida.");
            System.exit(0);
        }
        System.out.println("\n--- Detalle de la venta ---");
        seguro.mostrarDetalle();
        sc.close();
    }
}

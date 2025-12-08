import java.util.*;

public class sistembanco {

    // Clase Banco 
    static class Banco {
        private ArrayList<Cuenta> cuentas;
        private int proximoNumeroCuenta;

        public Banco(String nombre) {
            this.cuentas = new ArrayList<>();
            this.proximoNumeroCuenta = 1001;
        }

        public void crearCuenta(String usuario, String tipo) {
            Cuenta nuevaCuenta = null;
            
            if (tipo.equalsIgnoreCase("ahorro")) {
                nuevaCuenta = new CuentaAhorro(proximoNumeroCuenta++, usuario);
            } else if (tipo.equalsIgnoreCase("corriente")) {
                nuevaCuenta = new CuentaCorriente(proximoNumeroCuenta++, usuario);
            } else if (tipo.equalsIgnoreCase("empresarial")) {
                nuevaCuenta = new CuentaEmpresarial(proximoNumeroCuenta++, usuario);
            }
            
            if (nuevaCuenta != null) {
                cuentas.add(nuevaCuenta);
                System.out.println("✓ Cuenta creada exitosamente. Número: " + nuevaCuenta.getNumeroCuenta());
            }
        }

        public Cuenta buscarCuenta(int numeroCuenta) {
            for (Cuenta c : cuentas) {
                if (c.getNumeroCuenta() == numeroCuenta) {
                    return c;
                }
            }
            return null;
        }

        public void mostrarCuentas() {
            if (cuentas.isEmpty()) {
                System.out.println("No hay cuentas registradas.");
                return;
            }
            System.out.println("\n" + "=".repeat(80));
            System.out.printf("%-12s %-25s %-15s %-18s %-10s\n", "N° Cuenta", "Usuario", "Tipo", "Saldo", "Estado");
            System.out.println("=".repeat(80));
            for (Cuenta c : cuentas) {
                System.out.printf("%-12d %-25s %-15s $%-17.2f %-10s\n", 
                    c.getNumeroCuenta(), c.getUsuario(), c.getTipo(), c.getSaldo(), c.getEstado());
            }
            System.out.println("=".repeat(80));
        }

        public void cerrarCuenta(int numeroCuenta) {
            Cuenta c = buscarCuenta(numeroCuenta);
            if (c != null) {
                c.setEstado("inactiva");
                System.out.println("✓ Cuenta cerrada exitosamente.");
            } else {
                System.out.println("✗ Cuenta no encontrada.");
            }
        }

        public ArrayList<Cuenta> getCuentas() {
            return cuentas;
        }
    }

    // Clase base: Cuenta
    static class Cuenta {
        protected int numeroCuenta;
        protected String usuario;
        protected String tipo;
        protected double saldo;
        protected String estado;

        public Cuenta(int numeroCuenta, String usuario, String tipo) {
            this.numeroCuenta = numeroCuenta;
            this.usuario = usuario;
            this.tipo = tipo;
            this.saldo = 0;
            this.estado = "activa";
        }

        public int getNumeroCuenta() {
            return numeroCuenta;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getTipo() {
            return tipo;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String consultarSaldo() {
            return "Saldo actual: $" + String.format("%.2f", saldo);
        }

        public void depositar(double monto) {
            saldo += monto;
        }

        public void retirar(double monto) {
            saldo -= monto;
        }
    }

    // Clase CuentaAhorro (hereda de Cuenta)
    static class CuentaAhorro extends Cuenta {
        private int retirosMes;
        private double saldoMinimo = 20000;

        public CuentaAhorro(int numeroCuenta, String usuario) {
            super(numeroCuenta, usuario, "Ahorro");
            this.retirosMes = 0;
        }

        @Override
        public void retirar(double monto) {
            if (saldo - monto < saldoMinimo) {
                System.out.println("✗ No se puede retirar. Saldo mínimo requerido: $" + saldoMinimo);
                return;
            }

            double comision = 0;
            if (retirosMes >= 3) {
                comision = monto * 0.02; // 2% de comisión
                System.out.println("   (Comisión por retiro adicional: $" + String.format("%.2f", comision) + ")");
            }

            saldo = saldo - monto - comision;
            retirosMes++;
            System.out.println("✓ Retiro realizado. Monto: $" + String.format("%.2f", monto) + " Nuevo saldo: $" + String.format("%.2f", saldo));
        }

        @Override
        public void depositar(double monto) {
            saldo += monto;
            System.out.println("✓ Depósito realizado. Monto: $" + String.format("%.2f", monto) + " Nuevo saldo: $" + String.format("%.2f", saldo));
            
            // Aplicar bonificación si el saldo supera $1.000.000
            if (saldo > 1000000) {
                double bono = saldo * 0.01;
                saldo += bono;
                System.out.println("✓ Bono de fidelidad aplicado: $" + String.format("%.2f", bono));
            }
        }

        public void resetearRetirosMes() {
            retirosMes = 0;
        }
    }

    // Clase CuentaCorriente (hereda de Cuenta)
    static class CuentaCorriente extends Cuenta {
        private double sobregiroPermitido = 500000;
        private double interesSobregiro = 0.03;

        public CuentaCorriente(int numeroCuenta, String usuario) {
            super(numeroCuenta, usuario, "Corriente");
        }

        @Override
        public void retirar(double monto) {
            if (saldo + sobregiroPermitido < monto) {
                System.out.println("✗ Límite de sobregiro excedido.");
                return;
            }

            double comision = monto * 0.01; // 1% de comisión
            saldo = saldo - monto - comision;
            System.out.println("✓ Retiro realizado. Monto: $" + String.format("%.2f", monto) + 
                             " Comisión: $" + String.format("%.2f", comision) + 
                             " Nuevo saldo: $" + String.format("%.2f", saldo));
        }

        @Override
        public void depositar(double monto) {
            saldo += monto;
            System.out.println("✓ Depósito realizado. Monto: $" + String.format("%.2f", monto) + " Nuevo saldo: $" + String.format("%.2f", saldo));
        }

        @Override
        public String consultarSaldo() {
            String info = super.consultarSaldo();
            if (saldo < 0) {
                double interes = Math.abs(saldo) * interesSobregiro;
                info += "\nSobregiro: $" + String.format("%.2f", Math.abs(saldo)) + 
                       "\nInterés por sobregiro (3%): $" + String.format("%.2f", interes);
            }
            return info;
        }
    }

    // Clase CuentaEmpresarial (hereda de Cuenta)
    static class CuentaEmpresarial extends Cuenta {
        private double saldoMinimo = 500000;
        private int retirosDia = 0;
        private double comisionRetiro = 5000;

        public CuentaEmpresarial(int numeroCuenta, String usuario) {
            super(numeroCuenta, usuario, "Empresarial");
        }

        @Override
        public void retirar(double monto) {
            if (retirosDia >= 2) {
                System.out.println("✗ Límite de retiros diarios (2) alcanzado.");
                return;
            }

            if (saldo - monto - comisionRetiro < saldoMinimo) {
                System.out.println("✗ No se puede retirar. Saldo mínimo empresarial requerido: $" + saldoMinimo);
                return;
            }

            saldo = saldo - monto - comisionRetiro;
            retirosDia++;
            System.out.println("✓ Retiro realizado. Monto: $" + String.format("%.2f", monto) + 
                             " Comisión: $" + String.format("%.2f", comisionRetiro) + 
                             " Nuevo saldo: $" + String.format("%.2f", saldo));
        }

        @Override
        public void depositar(double monto) {
            if (monto < 100000) {
                System.out.println("✗ Depósito rechazado. Monto mínimo: $100.000");
                return;
            }

            saldo += monto;
            System.out.println("✓ Depósito realizado. Monto: $" + String.format("%.2f", monto) + " Nuevo saldo: $" + String.format("%.2f", saldo));
            
            // Aplicar bonificación si supera $10.000.000
            if (saldo > 10000000) {
                double bonificacion = saldo * 0.02;
                saldo += bonificacion;
                System.out.println("✓ Bonificación por alta liquidez aplicada: $" + String.format("%.2f", bonificacion));
            }
        }

        public void resetearRetirosDia() {
            retirosDia = 0;
        }
    }

    // Método Main - Sistema de menú
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco("Banco Futuro");

        boolean ejecutando = true;

        while (ejecutando) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearCuenta(scanner, banco);
                    break;
                case 2:
                    registrarDeposito(scanner, banco);
                    break;
                case 3:
                    registrarRetiro(scanner, banco);
                    break;
                case 4:
                    consultarCuentas(banco);
                    break;
                case 5:
                    cerrarCuenta(scanner, banco);
                    break;
                case 6:
                    System.out.println("\n✓ Gracias por usar Banco Futuro. ¡Hasta luego!");
                    ejecutando = false;
                    break;
                default:
                    System.out.println("✗ Opción inválida.");
            }
            System.out.println();
        }
        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        BANCO FUTURO - SIMULADOR BANCARIO");
        System.out.println("=".repeat(50));
        System.out.println("1. Crear nueva cuenta");
        System.out.println("2. Registrar depósito");
        System.out.println("3. Registrar retiro");
        System.out.println("4. Consultar cuentas y saldos");
        System.out.println("5. Cerrar una cuenta");
        System.out.println("6. Salir");
        System.out.println("=".repeat(50));
    }

    static void crearCuenta(Scanner scanner, Banco banco) {
        System.out.println("\n--- CREAR NUEVA CUENTA ---");
        System.out.print("Nombre del usuario: ");
        String usuario = scanner.nextLine();
        
        System.out.println("Tipo de cuenta:");
        System.out.println("1. Ahorro");
        System.out.println("2. Corriente");
        System.out.println("3. Empresarial");
        System.out.print("Seleccione tipo: ");
        
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        String tipoCuenta = "";
        switch (tipo) {
            case 1:
                tipoCuenta = "ahorro";
                break;
            case 2:
                tipoCuenta = "corriente";
                break;
            case 3:
                tipoCuenta = "empresarial";
                break;
            default:
                System.out.println("✗ Tipo inválido.");
                return;
        }
        
        banco.crearCuenta(usuario, tipoCuenta);
    }

    static void registrarDeposito(Scanner scanner, Banco banco) {
        System.out.println("\n--- REGISTRAR DEPÓSITO ---");
        System.out.print("Número de cuenta: ");
        int numeroCuenta = scanner.nextInt();
        
        Cuenta cuenta = banco.buscarCuenta(numeroCuenta);
        if (cuenta == null) {
            System.out.println("✗ Cuenta no encontrada.");
            return;
        }
        
        if (!cuenta.getEstado().equals("activa")) {
            System.out.println("✗ Cuenta inactiva. No se puede realizar depósitos.");
            return;
        }
        
        System.out.print("Monto a depositar: $");
        double monto = scanner.nextDouble();
        
        if (monto <= 0) {
            System.out.println("✗ El monto debe ser mayor a 0.");
            return;
        }
        
        cuenta.depositar(monto);
    }

    static void registrarRetiro(Scanner scanner, Banco banco) {
        System.out.println("\n--- REGISTRAR RETIRO ---");
        System.out.print("Número de cuenta: ");
        int numeroCuenta = scanner.nextInt();
        
        Cuenta cuenta = banco.buscarCuenta(numeroCuenta);
        if (cuenta == null) {
            System.out.println("✗ Cuenta no encontrada.");
            return;
        }
        
        if (!cuenta.getEstado().equals("activa")) {
            System.out.println("✗ Cuenta inactiva. No se puede realizar retiros.");
            return;
        }
        
        System.out.print("Monto a retirar: $");
        double monto = scanner.nextDouble();
        
        if (monto <= 0) {
            System.out.println("✗ El monto debe ser mayor a 0.");
            return;
        }
        
        cuenta.retirar(monto);
    }

    static void consultarCuentas(Banco banco) {
        System.out.println("\n--- CONSULTAR CUENTAS ---");
        banco.mostrarCuentas();
        
        System.out.print("\n¿Desea ver el saldo detallado de una cuenta? (s/n): ");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Número de cuenta: ");
            int numeroCuenta = scanner.nextInt();
            
            Cuenta cuenta = banco.buscarCuenta(numeroCuenta);
            if (cuenta != null) {
                System.out.println("\n" + cuenta.consultarSaldo());
            } else {
                System.out.println("✗ Cuenta no encontrada.");
            }
        }
        scanner.close();
    }

    static void cerrarCuenta(Scanner scanner, Banco banco) {
        System.out.println("\n--- CERRAR CUENTA ---");
        System.out.print("Número de cuenta a cerrar: ");
        int numeroCuenta = scanner.nextInt();
        
        banco.cerrarCuenta(numeroCuenta);
    }
}

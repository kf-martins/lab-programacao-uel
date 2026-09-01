import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        int opcao;
        double a, b, resultado;

        do {
            System.out.println("\n===== CALCULADORA =====");
            System.out.println("0 - Sair");
            System.out.println("1 - Somar");
            System.out.println("2 - Subtrair");
            System.out.println("3 - Multiplicar");
            System.out.println("4 - Dividir");
            System.out.print("Escolha uma opção: ");

            opcao = s.nextInt();

            if (opcao == 0) {
                System.out.println("Encerrando a calculadora...");
                break;
            }

            if (opcao < 1 || opcao > 4) {
                System.out.println("Opção inválida!");
                continue;
            }

            System.out.print("Digite o primeiro número: ");
            a = s.nextDouble();

            System.out.print("Digite o segundo número: ");
            b = s.nextDouble();

            switch (opcao) {
                case 1 -> resultado = Calc.somar(a, b);
                case 2 -> resultado = Calc.subtrair(a, b);
                case 3 -> resultado = Calc.multiplicar(a, b);
                case 4 -> resultado = Calc.dividir(a, b); // a class calc ja faz a verificcao da divisao por 0 
                default -> {
                    continue;
                }
            }

            System.out.println("Resultado: " + resultado);

        } while (true);

        s.close();
    }
}
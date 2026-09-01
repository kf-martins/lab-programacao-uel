import com.kauafm.interfaces.*;
import com.kauafm.tipos.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        ArrayList<Ligavel> dispositivos = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n===== CASA INTELIGENTE =====");
            System.out.println("1 - Cadastrar Lâmpada");
            System.out.println("2 - Cadastrar TV");
            System.out.println("3 - Cadastrar Aspirador Robô");
            System.out.println("4 - Listar dispositivos");
            System.out.println("5 - Ligar dispositivo");
            System.out.println("6 - Desligar dispositivo");
            System.out.println("7 - Recarregar aspirador");
            System.out.println("8 - Ver bateria do aspirador");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = s.nextInt();
            s.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Cômodo: ");
                    String comodo = s.nextLine();

                    System.out.print("Estado inicial (1-Ligado / 0-Desligado): ");
                    boolean estadoLamp = s.nextInt() == 1;

                    dispositivos.add(new Lampada(comodo, estadoLamp));
                    System.out.println("Lâmpada cadastrada.");
                    break;

                case 2:
                    System.out.print("Marca da TV: ");
                    String marca = s.nextLine();

                    System.out.print("Estado inicial (1-Ligado / 0-Desligado): ");
                    boolean estadoTV = s.nextInt() == 1;

                    dispositivos.add(new Televisao(marca, estadoTV));
                    System.out.println("TV cadastrada.");
                    break;

                case 3:
                    System.out.print("Nível inicial da bateria: ");
                    int bateria = s.nextInt();

                    System.out.print("Estado inicial (1-Ligado / 0-Desligado): ");
                    boolean estadoAsp = s.nextInt() == 1;

                    dispositivos.add(new AspiradorRobo(bateria, estadoAsp));
                    System.out.println("Aspirador cadastrado.");
                    break;

                case 4:
                    if (dispositivos.isEmpty()) {
                        System.out.println("Nenhum dispositivo cadastrado.");
                    } else {
                        System.out.println("\n--- DISPOSITIVOS ---");
                        for (int i = 0; i < dispositivos.size(); i++) {
                            Ligavel d = dispositivos.get(i);

                            System.out.print(i + " - ");

                            if (d instanceof Lampada) {
                                Lampada l = (Lampada) d;
                                System.out.print("Lâmpada (" + l.getComodo() + ")");
                            } else if (d instanceof Televisao) {
                                Televisao tv = (Televisao) d;
                                System.out.print("TV (" + tv.getMarca() + ")");
                            } else if (d instanceof AspiradorRobo) {
                                AspiradorRobo a = (AspiradorRobo) d;
                                System.out.print("Aspirador (" + a.getNivelBateria() + "% bateria)");
                            }

                            System.out.println(" - " +
                                    (d.estaLigado() ? "Ligado" : "Desligado"));
                        }
                    }
                    break;

                case 5:
                    System.out.print("Índice do dispositivo: ");
                    int ligar = s.nextInt();
                    dispositivos.get(ligar).ligar();
                    System.out.println("Dispositivo ligado.");
                    break;

                case 6:
                    System.out.print("Índice do dispositivo: ");
                    int desligar = s.nextInt();
                    dispositivos.get(desligar).desligar();
                    System.out.println("Dispositivo desligado.");
                    break;

                case 7:
                    System.out.print("Índice do aspirador: ");
                    int idx = s.nextInt();

                    if (dispositivos.get(idx) instanceof AspiradorRobo) {
                        AspiradorRobo a = (AspiradorRobo) dispositivos.get(idx);

                        System.out.print("Tempo de recarga (minutos): ");
                        int minutos = s.nextInt();

                        a.recarregar(minutos);
                        System.out.println("Bateria: " + a.getNivelBateria() + "%");
                    } else {
                        System.out.println("Esse dispositivo não é um aspirador.");
                    }
                    break;

                case 8:
                    System.out.print("Índice do aspirador: ");
                    idx = s.nextInt();

                    if (dispositivos.get(idx) instanceof AspiradorRobo) {
                        AspiradorRobo a = (AspiradorRobo) dispositivos.get(idx);
                        System.out.println("Bateria: " + a.getNivelBateria() + "%");
                    } else {
                        System.out.println("Esse dispositivo não é um aspirador.");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        s.close();
    }
}
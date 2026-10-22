public class App {

    public static void main(String[] args) {

        Funcionario f1 = new Administrador("Ana", "A1", 8000);
        Funcionario f2 = new Desenvolvedor("Bruno", "D2", 6000, 1500);
        Funcionario f3 = new Estagiario("Carlos", "E3", 1800);

        Funcionario[] funcionarios = {f1, f2, f3};

        System.out.println("=== Funcionários da Empresa ===");

        for (Funcionario f : funcionarios) {
            System.out.println("Nome: " + f.getNome());
            System.out.println("Matrícula: " + f.getMatricula());
            System.out.println("Salário anual: R$ " + f.calcularSalarioAnual());
            System.out.println("----------------------------");
        }
    }
}
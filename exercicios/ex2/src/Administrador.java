public class Administrador extends Funcionario {

    private double salarioMensal;

    public Administrador(String nome, String matricula, double salarioMensal) {
        super(nome, matricula);
        this.salarioMensal = salarioMensal;
    }

    @Override
    public double calcularSalarioAnual() {
        return salarioMensal * 12;
    }
}
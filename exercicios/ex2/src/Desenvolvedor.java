public class Desenvolvedor extends Funcionario {

    private double salarioBase;
    private double bonusMensal;

    public Desenvolvedor(String nome, String matricula, double salarioBase, double bonusMensal) {
        super(nome, matricula);
        this.salarioBase = salarioBase;
        this.bonusMensal = bonusMensal;
    }

    @Override
    public double calcularSalarioAnual() {
        return (salarioBase + bonusMensal) * 12;
    }
}
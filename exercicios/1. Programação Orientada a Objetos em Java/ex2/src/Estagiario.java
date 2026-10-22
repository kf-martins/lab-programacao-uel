public class Estagiario extends Funcionario {

    private double bolsaMensal;

    public Estagiario(String nome, String matricula, double bolsaMensal) {
        super(nome, matricula);
        this.bolsaMensal = bolsaMensal;
    }

    @Override
    public double calcularSalarioAnual() {
        return bolsaMensal * 12;
    }
}
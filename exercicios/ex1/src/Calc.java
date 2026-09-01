public class Calc {
    public static double somar(double a, double b) {
        return a+b;
    }

    public static double subtrair(double a, double b){
        return a-b;
    }

    public static double multiplicar(double a, double b) {
        return a*b;
    }

    public static double dividir(double a, double b) {
        if(b != 0)
            return a/b;
        
        System.out.println("[ERRO] O segundo parâmetro é zero!");
        return 0;
    }


}

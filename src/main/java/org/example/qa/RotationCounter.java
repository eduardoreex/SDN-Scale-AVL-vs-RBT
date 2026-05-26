package java.org.example.qa;

public class RotationCounter {

    private int totalRotacoesAVL = 0;
    private int totalRotacoesRBT = 0;

    public void registrarRotacaoAVL() {
        totalRotacoesAVL++;
    }

    public void registrarRotacaoRBT() {
        totalRotacoesRBT++;
    }

    public int getTotalRotacoesAVL() {
        return totalRotacoesAVL;
    }

    public int getTotalRotacoesRBT() {
        return totalRotacoesRBT;
    }
}
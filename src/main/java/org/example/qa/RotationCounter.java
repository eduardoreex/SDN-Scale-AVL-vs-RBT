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
public void resetar() {
    totalRotacoesAVL = 0;
    totalRotacoesRBT = 0;
}

public void exibirResumo() {
    System.out.println("=== Resumo de Rotações ===");
    System.out.println("AVL  - Total de rotações: " + totalRotacoesAVL);
    System.out.println("RBT  - Total de rotações: " + totalRotacoesRBT);

    if (totalRotacoesAVL > totalRotacoesRBT) {
        System.out.println("Resultado: RBT realizou MENOS rotações.");
    } else if (totalRotacoesRBT > totalRotacoesAVL) {
        System.out.println("Resultado: AVL realizou MENOS rotações.");
    } else {
        System.out.println("Resultado: Ambas realizaram o mesmo número de rotações.");
    }
}
}
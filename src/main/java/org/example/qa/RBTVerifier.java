package java.org.example.qa;

public class RBTVerifier {

    private static final boolean VERMELHO = true;
    private static final boolean PRETO    = false;

    public static boolean verificar(Object raiz) {
        if (raiz == null) return true;

        try {
            // Propriedade 1: raiz deve ser preta
            var campoCor = raiz.getClass().getDeclaredField("cor");
            campoCor.setAccessible(true);
            boolean corRaiz = (boolean) campoCor.get(raiz);

            if (corRaiz == VERMELHO) {
                System.out.println("ERRO RBT: Raiz não é preta!");
                return false;
            }

            // Verifica as demais propriedades recursivamente
            int altura = verificarPropriedades(raiz);
            if (altura == -1) return false;

            System.out.println("RBT válida! Altura negra: " + altura);
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao verificar RBT: " + e.getMessage());
            return false;
        }
    }

    private static int verificarPropriedades(Object noObj) {
        if (noObj == null) return 1; // nó nulo é preto, conta 1

        try {
            var classe = noObj.getClass();

            var campoEsquerda = classe.getDeclaredField("esquerda");
            var campoDireita  = classe.getDeclaredField("direita");
            var campoCor      = classe.getDeclaredField("cor");

            campoEsquerda.setAccessible(true);
            campoDireita.setAccessible(true);
            campoCor.setAccessible(true);

            Object esquerda = campoEsquerda.get(noObj);
            Object direita  = campoDireita.get(noObj);
            boolean cor     = (boolean) campoCor.get(noObj);
}


        // Propriedade 2: nó vermelho não pode ter filho vermelho
        if (cor == VERMELHO) {
            if (esquerda != null) {
                var corEsq = esquerda.getClass().getDeclaredField("cor");
                corEsq.setAccessible(true);
                if ((boolean) corEsq.get(esquerda) == VERMELHO) {
                    System.out.println("ERRO RBT: Dois nós vermelhos consecutivos!");
                    return -1;
                }
            }
            if (direita != null) {
                var corDir = direita.getClass().getDeclaredField("cor");
                corDir.setAccessible(true);
                if ((boolean) corDir.get(direita) == VERMELHO) {
                    System.out.println("ERRO RBT: Dois nós vermelhos consecutivos!");
                    return -1;
                }
            }
        }

        int altEsq = verificarPropriedades(esquerda);
        int altDir = verificarPropriedades(direita);

        if (altEsq == -1 || altDir == -1) return -1;
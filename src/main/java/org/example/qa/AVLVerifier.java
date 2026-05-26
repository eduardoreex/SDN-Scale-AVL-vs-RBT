package java.org.example.qa;

public class AVLVerifier {

    public static boolean verificar(Object raiz) {
        return verificarNo(raiz);
    }

    private static boolean verificarNo(Object noObj) {
        if (noObj == null) return true;

        try {
            var classe = noObj.getClass();

            var campoEsquerda = classe.getDeclaredField("esquerda");
            var campoDireita  = classe.getDeclaredField("direita");
            var campoAltura   = classe.getDeclaredField("altura");

            campoEsquerda.setAccessible(true);
            campoDireita.setAccessible(true);
            campoAltura.setAccessible(true);

            Object esquerda = campoEsquerda.get(noObj);
            Object direita  = campoDireita.get(noObj);

            int altEsq = esquerda == null ? 0 : (int) campoAltura.get(esquerda);
            int altDir = direita  == null ? 0 : (int) campoAltura.get(direita);

            int fb = altEsq - altDir;

            if (Math.abs(fb) > 1) {
                System.out.println("ERRO AVL: Fator de Equilíbrio violado! FB = " + fb);
                return false;
            }

            return verificarNo(esquerda) && verificarNo(direita);

        } catch (Exception e) {
            System.out.println("Erro ao verificar nó AVL: " + e.getMessage());
            return false;
        }
    }
}
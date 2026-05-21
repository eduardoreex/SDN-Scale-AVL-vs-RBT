package org.example;

import org.example.avl.AVLRouterTree;
import org.example.model.PacketRule;
import org.example.rbt.RedBlackRouterTree;

public class Main {

    public static void main(String[] args) {

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();

        System.out.println("===== INSERINDO REGRAS =====");

        PacketRule[] regras = {
                new PacketRule(1, "192.168.0.1", "10.0.0.1", 50),
                new PacketRule(2, "192.168.0.2", "10.0.0.2", 30),
                new PacketRule(3, "192.168.0.3", "10.0.0.3", 70),
                new PacketRule(4, "192.168.0.4", "10.0.0.4", 20),
                new PacketRule(5, "192.168.0.5", "10.0.0.5", 90),
        };

        for (PacketRule r : regras) {
            avl.inserir(r);
            rbt.inserir(r);
            System.out.println("Inserida: " + r);
        }

        System.out.println("\n===== ALTURAS =====");
        System.out.println("Altura AVL: " + avl.getAltura());
        System.out.println("Altura RBT: " + rbt.getAltura());


        System.out.println("\n===== ROTAÇÕES =====");
        System.out.println("Rotações AVL: " + avl.getTotalRotacoes());
        System.out.println("Rotações RBT: " + rbt.getTotalRotacoes());

        System.out.println("\n===== BUSCA (prioridade 70) =====");
        System.out.println("AVL encontrou: " + avl.buscar(70));
        System.out.println("RBT encontrou: " + rbt.buscar(70));

        System.out.println("\n===== DELETANDO prioridade 30 =====");
        avl.deletar(30);
        System.out.println("AVL após deletar 30 → altura: " + avl.getAltura());
        System.out.println("Busca por 30 na AVL: " + avl.buscar(30));
    }
}
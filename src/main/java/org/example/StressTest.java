package org.example;

import org.example.avl.AVLRouterTree;
import org.example.rbt.RedBlackRouterTree;
import org.example.model.PacketRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StressTest {

    static final long SEED = 42L;
    static final int  PERCENTUAL_DELECAO = 20;

    static List<PacketRule> gerarDados(int quantidade, long seed) {
        Random rng = new Random(seed);
        List<PacketRule> lista = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            String ipSrc = rng.nextInt(256)+"."+rng.nextInt(256)+"."+rng.nextInt(256)+"."+rng.nextInt(256);
            String ipDst = rng.nextInt(256)+"."+rng.nextInt(256)+"."+rng.nextInt(256)+"."+rng.nextInt(256);
            lista.add(new PacketRule(i + 1, ipSrc, ipDst, i + 1));
        }
        return lista;
    }

    static class Resultado {
        long tempoInsercao, tempoBusca, tempoDelecao;
        int rotacoes, alturaFinal;
    }

    static Resultado testarAVL(List<PacketRule> dados) {
        AVLRouterTree avl = new AVLRouterTree();
        Resultado r = new Resultado();
        int qtdDeletar = (int)(dados.size() * PERCENTUAL_DELECAO / 100.0);

        long inicio = System.nanoTime();
        for (PacketRule p : dados) avl.inserir(p);
        r.tempoInsercao = System.nanoTime() - inicio;

        inicio = System.nanoTime();
        for (PacketRule p : dados) avl.buscar(p.getPrioridade());
        r.tempoBusca = System.nanoTime() - inicio;

        inicio = System.nanoTime();
        for (int i = 0; i < qtdDeletar; i++) avl.deletar(dados.get(i).getPrioridade());
        r.tempoDelecao = System.nanoTime() - inicio;

        r.rotacoes    = avl.getTotalRotacoes();
        r.alturaFinal = avl.getAltura();
        return r;
    }

    static Resultado testarRBT(List<PacketRule> dados) {
        RedBlackRouterTree rbt = new RedBlackRouterTree();
        Resultado r = new Resultado();
        int qtdDeletar = (int)(dados.size() * PERCENTUAL_DELECAO / 100.0);

        long inicio = System.nanoTime();
        for (PacketRule p : dados) rbt.inserir(p);
        r.tempoInsercao = System.nanoTime() - inicio;

        inicio = System.nanoTime();
        for (PacketRule p : dados) rbt.buscar(p.getPrioridade());
        r.tempoBusca = System.nanoTime() - inicio;

        inicio = System.nanoTime();
        for (int i = 0; i < qtdDeletar; i++) rbt.deletar(dados.get(i).getPrioridade());
        r.tempoDelecao = System.nanoTime() - inicio;

        r.rotacoes    = rbt.getTotalRotacoes();
        r.alturaFinal = rbt.getAltura();
        return r;
    }

    public static void main(String[] args) {
        int[] volumes = {1_000, 5_000, 10_000, 50_000, 100_000};

        System.out.println("============================================================");
        System.out.println("  SDN-Scale: AVL vs Red-Black Tree — Stress Test");
        System.out.println("  Seed: " + SEED + " | Delecao: " + PERCENTUAL_DELECAO + "%");
        System.out.println("  Tempos em nanossegundos (ns)");
        System.out.println("============================================================");
        System.out.printf("%-8s | %-14s %-14s %-14s %-8s %-6s | %-14s %-14s %-14s %-8s %-6s%n",
                "Volume",
                "AVL-Insert","AVL-Search","AVL-Delete","AVL-Rot","AVL-H",
                "RBT-Insert","RBT-Search","RBT-Delete","RBT-Rot","RBT-H");
        System.out.println("-".repeat(120));

        for (int vol : volumes) {
            List<PacketRule> dados = gerarDados(vol, SEED);
            Resultado avl = testarAVL(dados);
            Resultado rbt = testarRBT(dados);
            System.out.printf("%-8d | %-14d %-14d %-14d %-8d %-6d | %-14d %-14d %-14d %-8d %-6d%n",
                    vol,
                    avl.tempoInsercao, avl.tempoBusca, avl.tempoDelecao, avl.rotacoes, avl.alturaFinal,
                    rbt.tempoInsercao, rbt.tempoBusca, rbt.tempoDelecao, rbt.rotacoes, rbt.alturaFinal);
        }

        System.out.println("============================================================");
        System.out.println("Teste concluido. Enviar dados ao Integrante 3 para validacao.");
    }
}
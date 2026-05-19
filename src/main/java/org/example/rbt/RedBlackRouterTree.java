package org.example.rbt;

import org.example.model.PacketRule;

public class RedBlackRouterTree {

    private static final boolean VERMELHO = true;
    private static final boolean PRETO    = false;

    private static class No {
        PacketRule regra;
        No esquerda, direita, pai;
        boolean cor;

        No(PacketRule regra) {
            this.regra = regra;
            this.cor   = VERMELHO;
        }
    }

    private No raiz;
    private int totalRotacoes = 0;

    private boolean cor(No no) {
        return (no == null) ? PRETO : no.cor;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        x.direita = y.esquerda;

        if (y.esquerda != null) y.esquerda.pai = x;
        y.pai = x.pai;

        if (x.pai == null)              raiz = y;
        else if (x == x.pai.esquerda)  x.pai.esquerda = y;
        else                            x.pai.direita  = y;

        y.esquerda = x;
        x.pai = y;

        totalRotacoes++;
        return y;
    }
    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        y.esquerda = x.direita;

        if (x.direita != null) x.direita.pai = y;
        x.pai = y.pai;

        if (y.pai == null)             raiz = x;
        else if (y == y.pai.direita)   y.pai.direita  = x;
        else                           y.pai.esquerda = x;

        x.direita = y;
        y.pai = x;

        totalRotacoes++;
        return x;
    }

    public void inserir(PacketRule regra) {
        No novo = new No(regra);

        No pai  = null;
        No atual = raiz;

        while (atual != null) {
            pai = atual;
            if (novo.regra.getPrioridade() < atual.regra.getPrioridade())
                atual = atual.esquerda;
            else if (novo.regra.getPrioridade() > atual.regra.getPrioridade())
                atual = atual.direita;
            else return;
        }

        novo.pai = pai;

        if (pai == null)                                          raiz = novo;
        else if (novo.regra.getPrioridade() < pai.regra.getPrioridade()) pai.esquerda = novo;
        else                                                      pai.direita  = novo;

        corrigirInsercao(novo);
    }

    private void corrigirInsercao(No z) {
        while (z.pai != null && cor(z.pai) == VERMELHO) {
            if (z.pai == z.pai.pai.esquerda) {
                No tio = z.pai.pai.direita;

                if (cor(tio) == VERMELHO) {

                    z.pai.cor         = PRETO;
                    tio.cor           = PRETO;
                    z.pai.pai.cor     = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) {

                        z = z.pai;
                        rotacaoEsquerda(z);
                    }

                    z.pai.cor     = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                No tio = z.pai.pai.esquerda;

                if (cor(tio) == VERMELHO) {
                    z.pai.cor         = PRETO;
                    tio.cor           = PRETO;
                    z.pai.pai.cor     = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }
                    z.pai.cor     = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = PRETO;
    }

    public PacketRule buscar(int prioridade) {
        No atual = raiz;
        while (atual != null) {
            if (prioridade == atual.regra.getPrioridade())
                return atual.regra;
            else if (prioridade < atual.regra.getPrioridade())
                atual = atual.esquerda;
            else
                atual = atual.direita;
        }
        return null;
    }


    public int getTotalRotacoes() { return totalRotacoes; }

    public int getAltura() {
        return alturaNo(raiz);
    }

    private int alturaNo(No no) {
        if (no == null) return 0;
        return 1 + Math.max(alturaNo(no.esquerda), alturaNo(no.direita));
    }
}
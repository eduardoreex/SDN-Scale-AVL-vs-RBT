package org.example.avl;

import org.example.model.PacketRule;

public class AVLRouterTree {

    private static class No {
        PacketRule regra;
        No esquerda, direita;
        int altura;

        No(PacketRule regra) {
            this.regra = regra;
            this.altura = 1;
        }
    }

    private No raiz;
    private int totalRotacoes = 0;

    private int altura(No no) {
        return (no == null) ? 0 : no.altura;
    }

    private int fatorEquilibrio(No no) {
        return (no == null) ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private void atualizaAltura(No no) {
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        atualizaAltura(y);
        atualizaAltura(x);

        totalRotacoes++;
        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        atualizaAltura(x);
        atualizaAltura(y);

        totalRotacoes++;
        return y;
    }

    private No balancear(No no) {
        atualizaAltura(no);
        int fb = fatorEquilibrio(no);

        if (fb > 1 && fatorEquilibrio(no.esquerda) >= 0)
            return rotacaoDireita(no);

        if (fb > 1 && fatorEquilibrio(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (fb < -1 && fatorEquilibrio(no.direita) <= 0)
            return rotacaoEsquerda(no);

        if (fb < -1 && fatorEquilibrio(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void inserir(PacketRule regra) {
        raiz = inserirNo(raiz, regra);
    }

    private No inserirNo(No no, PacketRule regra) {
        if (no == null)
            return new No(regra);

        if (regra.getPrioridade() < no.regra.getPrioridade())
            no.esquerda = inserirNo(no.esquerda, regra);
        else if (regra.getPrioridade() > no.regra.getPrioridade())
            no.direita = inserirNo(no.direita, regra);
        else
            return no;

        return balancear(no);
    }

    public PacketRule buscar(int prioridade) {
        No resultado = buscarNo(raiz, prioridade);
        return (resultado != null) ? resultado.regra : null;
    }

    private No buscarNo(No no, int prioridade) {
        if (no == null || no.regra.getPrioridade() == prioridade)
            return no;

        if (prioridade < no.regra.getPrioridade())
            return buscarNo(no.esquerda, prioridade);
        else
            return buscarNo(no.direita, prioridade);
    }

    public void deletar(int prioridade) {
        raiz = deletarNo(raiz, prioridade);
    }

    private No menorNo(No no) {
        while (no.esquerda != null)
            no = no.esquerda;
        return no;
    }

    private No deletarNo(No no, int prioridade) {
        if (no == null)
            return null;

        if (prioridade < no.regra.getPrioridade())
            no.esquerda = deletarNo(no.esquerda, prioridade);
        else if (prioridade > no.regra.getPrioridade())
            no.direita = deletarNo(no.direita, prioridade);
        else {

            if (no.esquerda == null) return no.direita;
            if (no.direita == null)  return no.esquerda;

            No substituto = menorNo(no.direita);
            no.regra = substituto.regra;
            no.direita = deletarNo(no.direita, substituto.regra.getPrioridade());
        }

        return balancear(no);
    }

    public int getTotalRotacoes() { return totalRotacoes; }
    public int getAltura() { return altura(raiz); }
}
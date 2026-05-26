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
        if (x.pai == null)             raiz = y;
        else if (x == x.pai.esquerda)  x.pai.esquerda = y;
        else                           x.pai.direita  = y;
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
        if (y.pai == null)            raiz = x;
        else if (y == y.pai.direita)  y.pai.direita  = x;
        else                          y.pai.esquerda = x;
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
        if (pai == null)                                               raiz = novo;
        else if (novo.regra.getPrioridade() < pai.regra.getPrioridade()) pai.esquerda = novo;
        else                                                           pai.direita  = novo;
        corrigirInsercao(novo);
    }
    private void corrigirInsercao(No z) {
        while (z.pai != null && cor(z.pai) == VERMELHO) {
            if (z.pai == z.pai.pai.esquerda) {
                No tio = z.pai.pai.direita;
                if (cor(tio) == VERMELHO) {
                    z.pai.cor     = PRETO;
                    tio.cor       = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) { z = z.pai; rotacaoEsquerda(z); }
                    z.pai.cor     = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                No tio = z.pai.pai.esquerda;
                if (cor(tio) == VERMELHO) {
                    z.pai.cor     = PRETO;
                    tio.cor       = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) { z = z.pai; rotacaoDireita(z); }
                    z.pai.cor     = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = PRETO;
    }
    private void transplantar(No u, No v) {
        if (u.pai == null)            raiz = v;
        else if (u == u.pai.esquerda) u.pai.esquerda = v;
        else                          u.pai.direita  = v;
        if (v != null) v.pai = u.pai;
    }

    public void deletar(int prioridade) {
        No z = raiz;
        while (z != null) {
            if      (prioridade == z.regra.getPrioridade()) break;
            else if (prioridade  < z.regra.getPrioridade()) z = z.esquerda;
            else                                             z = z.direita;
        }
        if (z == null) return;

        No y = z;
        boolean corOriginal = y.cor;
        No x, xPai;

        if (z.esquerda == null) {
            x = z.direita; xPai = z.pai;
            transplantar(z, z.direita);
        } else if (z.direita == null) {
            x = z.esquerda; xPai = z.pai;
            transplantar(z, z.esquerda);
        } else {
            y = z.direita;
            while (y.esquerda != null) y = y.esquerda;
            corOriginal = y.cor;
            x = y.direita; xPai = y;
            if (y.pai == z) {
                if (x != null) x.pai = y;
            } else {
                transplantar(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplantar(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }
        if (corOriginal == PRETO) corrigirDelecao(x, xPai);
    }

    private void corrigirDelecao(No x, No xPai) {
        while (x != raiz && cor(x) == PRETO) {
            if (x == (xPai == null ? null : xPai.esquerda)) {
                No w = xPai.direita;
                if (cor(w) == VERMELHO) {
                    w.cor = PRETO; xPai.cor = VERMELHO;
                    rotacaoEsquerda(xPai); w = xPai.direita;
                }
                if (cor(w == null ? null : w.esquerda) == PRETO &&
                        cor(w == null ? null : w.direita)  == PRETO) {
                    if (w != null) w.cor = VERMELHO;
                    x = xPai; xPai = x.pai;
                } else {
                    if (cor(w == null ? null : w.direita) == PRETO) {
                        if (w != null && w.esquerda != null) w.esquerda.cor = PRETO;
                        if (w != null) w.cor = VERMELHO;
                        if (w != null) rotacaoDireita(w);
                        w = xPai.direita;
                    }
                    if (w != null) w.cor = xPai.cor;
                    xPai.cor = PRETO;
                    if (w != null && w.direita != null) w.direita.cor = PRETO;
                    rotacaoEsquerda(xPai); x = raiz;
                }
            } else {
                No w = xPai.esquerda;
                if (cor(w) == VERMELHO) {
                    w.cor = PRETO; xPai.cor = VERMELHO;
                    rotacaoDireita(xPai); w = xPai.esquerda;
                }
                if (cor(w == null ? null : w.direita)  == PRETO &&
                        cor(w == null ? null : w.esquerda) == PRETO) {
                    if (w != null) w.cor = VERMELHO;
                    x = xPai; xPai = x.pai;
                } else {
                    if (cor(w == null ? null : w.esquerda) == PRETO) {
                        if (w != null && w.direita != null) w.direita.cor = PRETO;
                        if (w != null) w.cor = VERMELHO;
                        if (w != null) rotacaoEsquerda(w);
                        w = xPai.esquerda;
                    }
                    if (w != null) w.cor = xPai.cor;
                    xPai.cor = PRETO;
                    if (w != null && w.esquerda != null) w.esquerda.cor = PRETO;
                    rotacaoDireita(xPai); x = raiz;
                }
            }
        }
        if (x != null) x.cor = PRETO;
    }

    public PacketRule buscar(int prioridade) {
        No atual = raiz;
        while (atual != null) {
            if      (prioridade == atual.regra.getPrioridade()) return atual.regra;
            else if (prioridade  < atual.regra.getPrioridade()) atual = atual.esquerda;
            else                                                 atual = atual.direita;
        }
        return null;
    }
    public int getTotalRotacoes() { return totalRotacoes; }

    public int getAltura() { return alturaNo(raiz); }

    private int alturaNo(No no) {
        if (no == null) return 0;
        return 1 + Math.max(alturaNo(no.esquerda), alturaNo(no.direita));
    }
}
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker

# ─────────────────────────────────────────────
# Dados coletados pelo StressTest.java
# ─────────────────────────────────────────────
volumes = [1_000, 5_000, 10_000, 50_000, 100_000]

avl_insert = [3060100, 695500,  1368200, 8047100, 8743300]
avl_search = [642900,  638800,  1625700, 4519400, 7846000]
avl_delete = [181200,  225600,  320700,  1114800, 3594700]
rbt_insert = [2597600, 942000,  1634300, 9371000, 14163400]
rbt_search = [540400,  696000,  1471200, 4371200, 8045900]
rbt_delete = [241200,  887200,  355300,  667100,  2913600]

# ─────────────────────────────────────────────
# Configuração visual
# ─────────────────────────────────────────────
COR_AVL = '#2196F3'   # azul
COR_RBT = '#F44336'   # vermelho
ESTILO  = 'o-'

fig, axes = plt.subplots(1, 3, figsize=(16, 5))
fig.suptitle('SDN-Scale: AVL vs Red-Black Tree — Comparativo de Desempenho\nSeed: 42 | Deleção: 20% | Tempos em nanossegundos (ns)',
             fontsize=13, fontweight='bold', y=1.02)

titulos   = ['Inserção (100k entradas ordenadas)', 'Busca', 'Deleção (20% dos nós)']
dados_avl = [avl_insert, avl_search, avl_delete]
dados_rbt = [rbt_insert, rbt_search, rbt_delete]

for i, ax in enumerate(axes):
    ax.plot(volumes, dados_avl[i], ESTILO, color=COR_AVL, linewidth=2, markersize=6, label='AVL')
    ax.plot(volumes, dados_rbt[i], ESTILO, color=COR_RBT, linewidth=2, markersize=6, label='Red-Black')
    ax.set_title(titulos[i], fontsize=11, fontweight='bold')
    ax.set_xlabel('Volume de dados', fontsize=10)
    ax.set_ylabel('Tempo (ns)', fontsize=10)
    ax.legend(fontsize=10)
    ax.grid(True, linestyle='--', alpha=0.5)
    ax.xaxis.set_major_formatter(ticker.FuncFormatter(lambda x, _: f'{int(x):,}'))
    ax.yaxis.set_major_formatter(ticker.FuncFormatter(lambda x, _: f'{int(x):,}'))
    ax.tick_params(axis='x', rotation=20)

plt.tight_layout()
plt.savefig('graficos_avl_vs_rbt.png', dpi=150, bbox_inches='tight')
plt.show()
print("Grafico salvo como: graficos_avl_vs_rbt.png")

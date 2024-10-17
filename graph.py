import matplotlib.pyplot as plt
import numpy as np

# Données
tailles = np.array([1, 2, 3, 4, 5])
temps_moyen_algo1 = np.array([1, 7, 14, 33, 95])
ecart_type_algo1 = np.array([0.1, 0.2, 0.4, 0.8, 1.6])
temps_moyen_algo2 = np.array([254, 1210, 4580, 9491, 14549])
ecart_type_algo2 = np.array([0.2, 0.6, 1.8, 3.6, 7.2])

# Largeur des bâtons
bar_width = 0.3

# Positions des bâtons
r1 = np.arange(len(tailles))
r2 = [x + bar_width for x in r1]

# Création des diagrammes en bâtons
plt.bar(r1, temps_moyen_algo1, color='blue', width=bar_width, edgecolor='grey', yerr=ecart_type_algo1, capsize=7, label='Welzl')
plt.bar(r2, temps_moyen_algo2, color='red', width=bar_width, edgecolor='grey', yerr=ecart_type_algo2, capsize=7, label='Algo naïf')

# Ajout des légendes, titre, etc.
plt.xlabel('Taille de l\'ensemble', fontweight='bold')
plt.xticks([r + bar_width/2 for r in range(len(tailles))], tailles)
plt.ylabel('Temps d\'exécution moyen (ms)', fontweight='bold')
plt.title('Comparaison des Performances : Welzl vs Algo naïf')
plt.legend()

plt.savefig('comparaison_performance.png', dpi=300)
# Affichage du diagramme
plt.show()

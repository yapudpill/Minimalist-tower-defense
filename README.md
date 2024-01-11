# Projet POOIG

*Anthony FERNANDES, Thomas LU, groupe 176*

## Lancer le jeu

Pour compiler ce projet, il suffit de se placer dans le dosser `src` et executer
les deux commandes suivantes :

```bash
javac controller/MainControl.java
java controller.MainControl
```

Il est également possible de lancer le jeu depuis le fichier `.jar` fourni avec
la commande suivante :

```bash
java -jar chemin/vers/pooig.jar
```

## Comment jouer

### Menu principal

Sur le menu d'accueil, les deux boutons du haut permettent de choisir entre deux
modes de jeu:

- **Level :** Une série de huit niveaux par difficulté (on l'espère) croissante,
  essayez d'obtenir 3 étoiles à chaque niveau
- **Marathon :** Mêmes cartes que les niveaux mais les ennemis apparaissent à
  l'infini et sont de plus en plus nombreux

Le bouton **Scores** sur le menu vous permet d'accéder aux statistiques des vos
parties précédentes en mode marathon, classées par difficulté.

Le bouton **Exit** permet, comme son nom l'indique, de quitter le jeu.

### En jeu

#### Interface

En haut de l'interface, vous trouverez, de gauche à droite :

- Le nom de la partie en cours ("Marathon" ou "Level x")
- La vie qu'il vous reste, si elle tombe à 0, c'est fini
- La quantité d'or à votre disposition pour acheter des tours
- Le numéro de la vague en cours et, en mode niveau, le nombre total de vague

Au centre de l'interface, ce trouve la grille.\
Les cases fléchée indiquent le chemin que suivent les ennemis. Ils partent des
cercles pleins et vous devez les empêcher d'atteindre les cercles vides.\
Les cases d'un gris plus foncé sont les emplacement pour les tours.\
Les cases noires sont vides.

En bas de l'interface, ce trouve le magasin des tours. Chaque bouton coloré
indique le type de tour qu'il permet d'acheter, ainsi que son prix entre
parenthèses. Pour placer une tour, il suffit de sélectionner l'un des bouton et
de cliquer sur un emplacement de tour dans la grille, même déjà occupé.

Le bouton **Back to menu** permet de revenir au menu de lancement de parties.

Quand la partie est terminée, un écran de fin s'affiche avec quelques
statistiques sur la partie, ainsi qu'un bouton pour venir au menu de lancement
de parties.

#### Ennemis

|                   | Basique | Rapide | Fort  |
| ----------------- | ------- | ------ | ----- |
| Couleur           | Vert    | Jaune  | Rouge |
| Vitesse (cases/s) | 1.1     | 1.5    | 0.8   |
| Vie               | 3       | 2      | 4     |
| Récompense        | 5       | 5      | 10    |

#### Tours

|                | Basique | Canon | Sniper |
| -------------- | ------- | ----- | ------ |
| Couleur        | Bleu    | Rouge | Vert   |
| Recharge (sec) | 1.5     | 2     | 1.7    |
| Dégâts         | 1       | 3     | 1      |
| Portée (cases) | 3       | 2     | 5      |
| Coût           | 40      | 60    | 50     |
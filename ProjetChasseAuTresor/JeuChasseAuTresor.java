
public class JeuChasseAuTresor {

	public static void main(String[] args) {

		// Positions de Lara et Indiana Jones
		int posIndi = 1;
		int posLara = 4;

		// Position de la bague
		int posBague = 7;
		boolean laraEstSurLaBague = false;
		boolean indiEstSurLaBague = false;

		// Position de la bombe, le joueur qui tombe sur cette case, voit sa partie
		// renitialisée
		int posBombe = 11;

		// Position du passage secret, si un joueur atteint cette case il est direct
		// propulsé à la case 14
		int posPassageSecret = 6;

		// Etat du jeu, vrai si personne n'a encore atteint le trésor
		boolean estEnCours = true;

		// Joueur Courant Indi --> 1 Lara --> 2
		int joueurCourant = 1;

		PlateauJeu jeu = new PlateauJeu();
		jeu.placerIndi(posIndi);
		jeu.placerLara(posLara);
		jeu.placerBague(posBague);
		jeu.placerPassage(posPassageSecret);
		jeu.placerBombe(posBombe);

		// Jeu
		while (estEnCours) {
			if (joueurCourant == 1) {
				jeu.afficherInformation("Tour d'Indi!");
				int de = jeu.lancerDe();
				posIndi += de;
				if (posIndi == posLara) {
					posIndi++;
				}
				// Si Indi est sur la case du passage, on supprime le passage et on empêche tout autre personne de marcher sur le passage
				if (posIndi == posPassageSecret) {
					jeu.afficherInformation(
							"Indi a pris le passage secret, il sera à la case 14, cliquez \"suivant\" pour continuer");
					posIndi = 14;
					jeu.supprimerPassage();
					posPassageSecret = -1;
					jeu.suivant();
				}
				// Si Indi est sur la case de la bombe, on supprime la bombe et on empêche tout autre personne de marcher sur une bombe
				if (posIndi == posBombe) {
					jeu.supprimerIndi();
					posIndi = 1;
					jeu.placerIndi(posIndi);
					jeu.supprimerBombe();
					posBombe = -1;
					jeu.afficherInformation(
							"Indi a pris la bombe, sa partie est renitialisée, cliquez \"suivant\" pour continuer");
					jeu.suivant();
				}
				// Si Indi est sur la case de la bague, on supprime la bague et on empêche tout autre personne de marcher sur une bague
				if (posIndi == posBague) {
					indiEstSurLaBague = true;
					jeu.supprimerBague();
					jeu.placerIndi(posIndi);
					posBague = -1;
					jeu.afficherInformation(
							"Indi a pris la bague, il perd un tour, cliquez \"suivant\" pour continuer");
					jeu.suivant();
				}
				
				posIndi = (posIndi > 16) ? 16 : posIndi;
				jeu.deplacerIndi(posIndi);
				
				// Si Lara n'a pas marché sur la bague, le tour prochain est le sien
				if (!laraEstSurLaBague) {
					joueurCourant = 2;
				}
				
				// Si Lara a marché sur la bague, elle perd un tour
				if (laraEstSurLaBague) {
					laraEstSurLaBague = false;
				}
			} else {
				jeu.afficherInformation("Tour de Lara!");
				int de = jeu.lancerDe();
				posLara += de;
				if (posLara == posIndi) {
					posLara++;
				}
				// Si Lara est sur la case du passage, on supprime le passage et on empêche tout autre personne de marcher sur le passage
				if (posLara == posPassageSecret) {
					jeu.afficherInformation(
							"Lara a pris le passage secret, elle sera à la case 14, cliquez \"suivant\" pour continuer");
					posLara = 14;
					jeu.supprimerPassage();
					posPassageSecret = -1;
					jeu.suivant();
				}
				// Si Lara est sur la case de la bombe, on supprime la bombe et on empêche tout autre personne de marcher sur une bombe
				if (posLara == posBombe) {
					jeu.supprimerLara();
					posLara = 4;
					jeu.placerLara(posLara);
					jeu.supprimerBombe();
					posBombe = -1;
					jeu.afficherInformation(
							"Lara a pris la bombe, sa partie est renitialisée, cliquez \"suivant\" pour continuer");
					jeu.suivant();
				}
				// Si Lara est sur la case de la bague, on supprime la bague et on empêche tout autre personne de marcher sur une bague
				if (posLara == posBague) {
					laraEstSurLaBague = true;
					jeu.supprimerBague();
					jeu.placerLara(posLara);
					posBague = -1;
					jeu.afficherInformation(
							"Lara a pris la bague, elle perd un tour, cliquez \"suivant\" pour continuer");
					jeu.suivant();
				}
				posLara = (posLara > 16) ? 16 : posLara;
				jeu.deplacerLara(posLara);
				
				// Si Indi n'a pas marché sur la bague, le tour prochain est le sien
				if (!indiEstSurLaBague) {
					joueurCourant = 1;
				}
				
				// Si Indi a marché sur la bague, il perd un tour
				if (indiEstSurLaBague) {
					indiEstSurLaBague = false;
				}
			}
			
			// Condition de continuation du jeu
			estEnCours = posLara < 16 && posIndi < 16;
		}

		if (posLara == 16) {
			jeu.afficherInformation("Lara a gagné! La partie est terminée");
		} else {
			jeu.afficherInformation("Indi a gagné! La partie est terminée");
		}

	}
}

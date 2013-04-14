package phase2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regle {
	private int minFreq;
	private int minConf;
	private List<Motif> arrayMotifFreq;

	public Regle(int minConf, int minFreq) {
		this.minConf = minConf;
		this.minFreq = minFreq;
	}

	public void Extraction(String ficOut) throws IOException {
		// lecture du fichier green.out
		String fichier = ficOut;
		String hyphen = "-";
		List<Motif> arrayMotifFreq = new ArrayList<Motif>();
		InputStream ips = new FileInputStream(fichier);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);

		String ligneMotif;
		String space = " ";
		
		// On stocke les objets motifs dans la liste arrayMotifFreq
		while ((ligneMotif = br.readLine()) != null) {
			Motif motif = new Motif();
			// On décompose la ligne du motif dans un tableau
			String[] ligneDecomp = ligneMotif.split(space);
			// On transforme le tableau en arrayList
			ArrayList<String> listDecomp = new ArrayList<String>(
					Arrays.asList(ligneDecomp));

			for (int i = 0; i < listDecomp.size(); ++i) {
				String supp = listDecomp.get(i);
				// On récupére le support
				Pattern p = Pattern.compile("\\((.*)\\)");
				Matcher m = p.matcher(supp);

				if (m.find()) {
					supp = m.group(1);
					motif.setSupport(Integer.parseInt(supp));
					// On enlève le dernier élément de l'arraylist (= le
					// support)
					listDecomp.remove(listDecomp.size() - 1);
					String mot = "";
					for (int j = 0; j < listDecomp.size(); ++j) {
						// On récupère le motif
						mot += listDecomp.get(j) + hyphen;
					}
					motif.setVal(mot);					
					// ajoute objet motif dans l'arrayList
					arrayMotifFreq.add(motif);
					// System.out.println(motif);
				}

			} // for()

		} // while ()
		
		// Calcul du nombre total de transactions présentes dans le fichier
		int nbrLignes = 0;
		for(int i = 1; i < arrayMotifFreq.size(); ++i){
			++nbrLignes;
		}
		System.out.println("Nbr lignes:" + nbrLignes);
		
		// Ajout des fréquences pour chaque motifs
		for(int i = 0; i < arrayMotifFreq.size(); ++i){
			float support = arrayMotifFreq.get(i).getSupport();
			//System.out.println(support);
			float freq = (support/nbrLignes);
			//System.out.println(freq);
			arrayMotifFreq.get(i).setFreq(freq);
		}
		
		br.close();
		//System.out.println(arrayMotifFreq);

		// Recherche de l'indice du premier motif dont le nombre d'attribut est > 1
		int posMotif = 0;
		for(int i = 0; i < arrayMotifFreq.size(); ++i) {
			String[] motSplit = arrayMotifFreq.get(i).getVal().split(hyphen);
			if (motSplit.length > 1) {
				posMotif = i;
				break;
			}
		}
		System.out.println("Postion:" + posMotif);
		
		for (int i = posMotif; i <arrayMotifFreq.size(); ++i){
			String motif = arrayMotifFreq.get(i).getVal();
			System.out.print("MOTIF:" + motif + "\n");
			
			for(int j = 0; i > j; ++j){
				String sousMotif = arrayMotifFreq.get(j).getVal();
				System.out.print("Sous-motif:" + sousMotif + "\n");
			}
		}
		
	} // Extraction ()

	public static void main(String[] args) throws IOException {
		Regle ER = new Regle(1, 1);
		ER.Extraction("green_test.out");

	} // main ()

} // class Regle

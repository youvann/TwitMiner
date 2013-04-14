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
		List<Motif> arrayMotifFreq = new ArrayList<Motif>();
		List<String> regleDF = new ArrayList<String>();
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
						mot += listDecomp.get(j) + space;
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
		
		// Ajout des fréquences pour chaque motifs
		for(int i = 0; i < arrayMotifFreq.size(); ++i){
			float support = arrayMotifFreq.get(i).getSupport();
			float freq = (support/nbrLignes);
			arrayMotifFreq.get(i).setFreq(freq);
		}
		br.close();
		

		// Recherche de l'indice du premier motif dont le nombre d'attribut est > 1
		int posMotif = 0;
		for(int i = 0; i < arrayMotifFreq.size(); ++i) {
			String[] motSplit = arrayMotifFreq.get(i).getVal().split(space);
			if (motSplit.length > 1) {
				posMotif = i;
				break;
			}
		}
		//System.out.println("Postion:" + posMotif);
		
		List<Integer> motifGlobal = new ArrayList<Integer>();
		List<Integer> substr_Motif = new ArrayList<Integer>();
		for (int i = posMotif; i <arrayMotifFreq.size(); ++i){
			float freqY =  arrayMotifFreq.get(i).getFreq();
			String motif = arrayMotifFreq.get(i).getVal();
			
			// On décompose le motif global pour l'insérer dans un arrayList (motifGlobal)
			// afin de comparer la liste motifGlobal et substr_Motif
			// pour savoir si le motif globale contient ou non le sous motif
			String [] motifDecompo = motif.split(space);
			for(int k = 0; k < motifDecompo.length; ++k){
				motifGlobal.add(Integer.parseInt(motifDecompo[k]));
			}
			
			System.out.print("MOTIF:" + motif + "array:" + motifGlobal + "\n");
						
			for(int j = 0; i > j; ++j){
				float freqX = arrayMotifFreq.get(j).getFreq();
				float confiance = freqY/freqX;
				String sousMotif = arrayMotifFreq.get(j).getVal();
				if(sousMotif.length() == 0) continue;
				
				// Sous motif décomposé dans une arraylist
				String [] sousMotifDecompo = sousMotif.split(space);	
				for(int l=0; l < sousMotifDecompo.length; ++l){								
					//System.out.println("substr :" + sousMotifDecompo[l] + "taille:" + sousMotifDecompo.length);
					substr_Motif.add(Integer.parseInt(sousMotifDecompo[l]));
				}
				System.out.print("Sous-motif:" + sousMotif + "substr" + substr_Motif);	
				
				if (motifGlobal.containsAll(substr_Motif)){
					System.out.print("ok\n");
					String opGauche = "";
					String opDroite = "";
					for (int n = 0; n < substr_Motif.size(); ++n){
						opGauche += substr_Motif.get(n) + ",";
					}
					for (int n = 0; n < motifGlobal.size(); ++n){
						opDroite += motifGlobal.get(n) + ",";
					}
					
					String DF = "";
					DF = opGauche + "=>" + opDroite;
					System.out.println(DF);
				}
				else
					System.out.println("pas ok\n");
				
				substr_Motif.clear();
			}
			motifGlobal.clear();
			
		} // for()
		
	} // Extraction ()

	public static void main(String[] args) throws IOException {
		Regle ER = new Regle(1, 1);
		ER.Extraction("green_test.out");

	} // main ()

} // class Regle

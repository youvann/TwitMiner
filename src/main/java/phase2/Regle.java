package phase2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
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
	private float minFreq;
	private float minConf;
	private List<Motif> arrayMotifFreq;

	public Regle(float minConf, float minFreq) {
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
			// On dŽcompose la ligne du motif dans un tableau
			String[] ligneDecomp = ligneMotif.split(space);
			// On transforme le tableau en arrayList
			ArrayList<String> listDecomp = new ArrayList<String>(
					Arrays.asList(ligneDecomp));

			for (int i = 0; i < listDecomp.size(); ++i) {
				String supp = listDecomp.get(i);
				// On rŽcupŽre le support
				Pattern p = Pattern.compile("\\((.*)\\)");
				Matcher m = p.matcher(supp);

				if (m.find()) {
					supp = m.group(1);
					motif.setSupport(Integer.parseInt(supp));
					// On enlve le dernier ŽlŽment de l'arraylist (= le
					// support)
					listDecomp.remove(listDecomp.size() - 1);
					String mot = "";
					for (int j = 0; j < listDecomp.size(); ++j) {
						// On rŽcupre le motif
						mot += listDecomp.get(j) + space;
					}
					motif.setVal(mot);					
					// ajoute objet motif dans l'arrayList
					arrayMotifFreq.add(motif);
					// System.out.println(motif);
				}

			} // for()

		} // while ()
		
		// Calcul du nombre total de transactions prŽsentes dans le fichier
		int nbrLignes = 0;
		for(int i = 1; i < arrayMotifFreq.size(); ++i){
			++nbrLignes;
		}
		
		// Ajout des frŽquences pour chaque motifs
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
			
			// On dŽcompose le motif global pour l'insŽrer dans un arrayList (motifGlobal)
			// afin de comparer la liste motifGlobal et substr_Motif
			// pour savoir si le motif globale contient ou non le sous motif
			String [] motifDecompo = motif.split(space);
			for(int k = 0; k < motifDecompo.length; ++k){
				motifGlobal.add(Integer.parseInt(motifDecompo[k]));
			}
			
			//System.out.print("\nMOTIF:" + motif + "array:" + motifGlobal);
						
			for(int j = 0; i > j; ++j){
				float freqX = arrayMotifFreq.get(j).getFreq();
				float confiance = freqY/freqX;
				//System.out.println(confiance);
				String sousMotif = arrayMotifFreq.get(j).getVal();
				if(sousMotif.length() == 0) continue;
				
				// Sous motif dŽcomposŽ dans une arraylist
				String [] sousMotifDecompo = sousMotif.split(space);	
				for(int l=0; l < sousMotifDecompo.length; ++l){								
					//System.out.println("substr :" + sousMotifDecompo[l] + "taille:" + sousMotifDecompo.length);
					substr_Motif.add(Integer.parseInt(sousMotifDecompo[l]));
				}
				//System.out.print("\nSous-motif:" + sousMotif + "substr" + substr_Motif);	
				
				if (motifGlobal.containsAll(substr_Motif) && (confiance > minConf)){
					//System.out.print("ok");
					String opGauche = "";
					String opDroite = "";
					
					opGauche += substr_Motif.get(0);
					for (int n = 1; n < substr_Motif.size(); ++n){
						opGauche += "-" + substr_Motif.get(n);
					}					
							
					opDroite += motifGlobal.get(0);
					for (int n = 1; n < motifGlobal.size(); ++n){
						opDroite += "-" + motifGlobal.get(n);
					}
					
					// X -> Y-X, on supprimer l'attribut X dans la cible
					String opGaucheTab [] = opGauche.split("-"); 					
					String opD = "";					
					String opDroiteTab [] = opDroite.split("-"); 
					// On transforme le tableau en arrayList
					ArrayList<String> ListDroit = new ArrayList<String>(
							Arrays.asList(opDroiteTab));
					
					for(int v = 0; v < ListDroit.size(); ++v){
						for(int w=0; w < opGaucheTab.length; ++w){
							if(opGaucheTab[w].equals(ListDroit.get(v)))
								ListDroit.remove(v);
						}
					}
					
					// re-gŽnŽration de l'opŽrande droite
					for (int n = 0; n < ListDroit.size(); ++n){
						opD += ListDroit.get(n) + "-";
					}					
					
					String DF = "";
					DF = opGauche + "=>" + opD + "conf:" + confiance;
					// On ajoute les DF ˆ l'array List
					regleDF.add(DF);
					
					//System.out.println(DF);
				}				
				substr_Motif.clear();
			}
			motifGlobal.clear();
			
		} // for()
		System.out.println("\n" + regleDF);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("DF_int", true));
		for(int i = 0; i < regleDF.size(); ++i){
			// �criture des DF dans DF_int			
			String resultat = regleDF.get(i);
			bw.write(resultat);
			bw.newLine();
		}
		bw.close();
		
		
	} // Extraction ()

	public static void main(String[] args) throws IOException {
		float minconf = (float) 0.75;
		Regle ER = new Regle(minconf, 1);
		ER.Extraction("green.out");

	} // main ()

} // class Regle
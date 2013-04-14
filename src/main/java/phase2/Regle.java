package phase2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regle {
	private int minFreq;
	private int minConf;
	public List<Motif> arrayMotif;
	private List<Motif> arrayMotifX;
	private List<Motif> arrayMotifY;

	public Regle(int minConf, int minFreq) {
		this.minConf = minConf;
		this.minFreq = minFreq;
	}

	public void Extraction() throws IOException {
		// lecture du fichier green.out
		String fichier = "green.out";
		try {
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			List<Motif> arrayMotif = new ArrayList<Motif>();
			String ligneMotif;
			String delim = " ";

			while ((ligneMotif = br.readLine()) != null) {
				Motif motif = new Motif();
				// On décompose la ligne du motif dans un tableau
				String[] ligneDecomp = ligneMotif.split(delim);
				// On transforme le tableau en arrayList
				ArrayList<String> listDecomp = new ArrayList<String>(
						Arrays.asList(ligneDecomp));

				for (int i = 0; i < listDecomp.size(); ++i) {
					String supp = listDecomp.get(i);
					// On cherche le nombre entre les parenthèses : le support
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
							mot += listDecomp.get(j) + " ";
						}
						motif.setVal(mot);
						arrayMotif.add(motif);
						// System.out.println(motif);
					}

				} // for()

			} // while ()
			System.out.println(arrayMotif);
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	} // Extraction ()

	public static void main(String[] args) throws IOException {
		Regle ER = new Regle (1,1);
		ER.Extraction();
	} // main ()

} // class Regle

package phase1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class converter {
	Vector<String> tabConv = new Vector<String>();

	public converter() {
	}

	public void convertToTxt(String csv, String txt) throws IOException {
		FileReader fr = new FileReader(csv);
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(new FileWriter(txt, true));
		try {
			while (!br.readLine().equals(null)) {
				String ligne = br.readLine();
				String delim = ";";
				String motifs = "";
				try {
					if (!ligne.contains(";")) {
						int pos = tabConv.indexOf(ligne);
						if (pos == -1) {
							pos = tabConv.size();
							tabConv.add(ligne);
						}
						motifs = pos + " ";
						bw.write(motifs);
						bw.newLine();
						continue;
					}

					String[] mot = ligne.split(delim);
					for (int i = 0; i < mot.length; i++) {

						int pos = tabConv.indexOf(mot[i]);

						// mot pas encor dans
						if (pos == -1) {
							pos = tabConv.size();
							tabConv.add(mot[i]);
						}
						motifs += pos + " ";

					}

				} catch (java.lang.NullPointerException e) {
					System.out.println("fin de conversion");
				}
				bw.write(motifs);
				bw.newLine();

			}
		} catch (java.lang.NullPointerException ex) {
			System.out.println("plus de ligne");
		} finally {
			br.close();
			bw.close();
		}

	}

	public void convertToCsv(String txt, String csv) throws IOException {
		FileReader fr = new FileReader(txt);
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		try {
			String prem = br.readLine();
			String Nbligne="";
			for (int k = 1; k < prem.length() - 1; k++) {
				Nbligne += prem.charAt(k);

			}
			System.out.println(Nbligne);
			while (true) {

				String ligne = br.readLine();
				String delim = " ";
				String motifs = "";
				try {
					String[] mot = ligne.split(delim);

					int taille = mot.length;
					String num = "";
					if (taille > 1) {
						num = mot[mot.length - 1];
						String numerateur = "";
						if (num.charAt(0) == '(') {
							taille = mot.length - 1;
							for (int j = 1; j < num.length() - 1; j++) {
								numerateur += num.charAt(j);

							}
							String freqmot = numerateur + "/" + Nbligne;
							motifs += "frequence : " + freqmot + ";";
							System.out.println(freqmot);
							
						}

					}
					for (int i = 0; i < taille; i++) {
						if (mot[i].equals("")
								|| Integer.parseInt(mot[i]) > 100000)
							continue;
						motifs += tabConv.get(Integer.parseInt(mot[i])) + ";";
					}

				} catch (java.lang.NullPointerException e) {
					System.out.println("fin de conversion");
				}
				bw.write(motifs);
				bw.newLine();
				if (br.readLine().isEmpty())
					break;

			}

		} catch (java.lang.NullPointerException ex) {
			// TODO: handle exception
		} finally {
			br.close();
			bw.close();
		}
	}

	public static void main(String[] args) throws IOException {
		converter conv = new converter();
		conv.convertToTxt("Tweet_green.csv", "green.trans");
		conv.convertToCsv("green.out", "out.csv");
	}

}

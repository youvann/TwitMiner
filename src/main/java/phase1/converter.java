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
					String[] mot = ligne.split(delim);
					for (int i = 0; i < mot.length; i++) {
						int pos = tabConv.indexOf(mot[i]);
						// mot pas encore dedans
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

	} // convertToTxt

	public void convertToCsv(String txt, String csv) throws IOException {
		FileReader fr = new FileReader(txt);
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		try {
			String prem = br.readLine();
			char nbligne = prem.charAt(1);
			System.out.println(nbligne);
			while (true) {

				String ligne = br.readLine();
				String delim = " ";
				String motifs = "";
				try {
					String[] mot = ligne.split(delim);
					String num = mot[mot.length - 1];
					String freqmot = num + "/" + nbligne;
					System.out.println(freqmot);
					for (int i = 0; i < mot.length - 1; i++) {

						motifs += tabConv.get(Integer.parseInt(mot[i])) + ";";
					}
					motifs += "frequence : " + freqmot;
				} catch (java.lang.NullPointerException e) {
					System.out.println("fin de conversion");
				}

				bw.write(motifs);
				bw.newLine();
				if (!br.readLine().equals(null))
					break;
			}

		} catch (java.lang.NullPointerException ex) {
			// TODO: handle exception
		} finally {
			br.close();
			bw.close();
		}

	} // convertToCsv ()

	public static void main(String[] args) throws IOException {
		converter conv = new converter();
		conv.convertToTxt("Tweet_green.csv", "green.trans");
		// conv.convertToCsv("motinutil.txt", "out.csv");
	}

}

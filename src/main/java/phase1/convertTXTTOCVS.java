package phase1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class convertTXTTOCVS {
	String csv;
	String txt;

	public convertTXTTOCVS(String txt, String csv) {

		this.csv = csv;
		this.txt = txt;
	}

	public void convert() throws IOException {
		FileReader fr = new FileReader(txt);
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

		while (!br.readLine().equals(null)) {
			String ligne = br.readLine();
			String delim = " ";
			String motifs = "";
			try {
				String[] mot = ligne.split(delim);
				for (int i = 0; i < mot.length; i++) {

				motifs += mot[i] + " ";
			}
			} catch (java.lang.NullPointerException e) {
				System.out.println("fin de conversion");
			}
			;

			bw.write(motifs);
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		
	}

}

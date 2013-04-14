package phase0;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TransactionTwitter {
	public void Lancer() throws IOException {
		Twitter twitter = new TwitterFactory().getSingleton();
		String quote = "\"";
		String coma = ";";
		BufferedWriter bw = null;
		
		try {
			Query query = new Query("#green");
			//query.setUntil("2013-04-06");
			QueryResult result; 
			do {
				// Flux pour Žcrire les tweet dans le fichier Tweet_green.csv
				bw = new BufferedWriter(new FileWriter("Tweet_green.csv", true));
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					String resultat = quote + tweet.getCreatedAt().toString() + quote + coma + quote
							+ "@" +tweet.getUser().getScreenName() + quote + coma + quote
							+ tweet.getUser().getLang() + quote + coma;
					String texte = tweet.getText();
					String delim = " ";
					String textcvs = "";
					String[] mot = texte.split(delim);
					for (int i = 0; i < mot.length; i++) {
						textcvs += quote + mot[i] + quote + coma;
					}
					resultat += textcvs;
					// On ne prend pas en compte les re-tweets
					if (resultat.contains("RT"))
						continue;
					System.out.println(resultat);
					bw.write(resultat);
					bw.newLine();
				}
				bw.flush();
				bw.close();

			} while ((query = result.nextQuery()) != null);

			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
	} // Lancer ()

	public static void main(String[] args) throws IOException {
		TransactionTwitter tr = new TransactionTwitter();
		tr.Lancer();

	} // main ()
}

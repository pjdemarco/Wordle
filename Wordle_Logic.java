import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Wordle_Logic {

    public static String[] loadWords() throws IOException {
        String[] words = new String[5757];
        int counter = 0;
        FileReader fr = new FileReader("sgb-words.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        while (line != null) {
            words[counter] = line;
            counter++;
            line = br.readLine();
        }
        return words;
    }

    public static String chooseWord(String[] words) {
        String word;
        Random rand = new Random();
        word = words[rand.nextInt(5757)];
        return word;
    }
}

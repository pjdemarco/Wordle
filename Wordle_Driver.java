import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Wordle_Driver {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        playGame();
    }

    public static void playGame() throws IOException {
        int tries = 0;
        String[] words = Wordle_Logic.loadWords();
        String target = Wordle_Logic.chooseWord(words);
        char[] match = new char[5];
        Set<Character> matchNoPos = new HashSet<Character>();
        Set<Character> incorrect = new HashSet<Character>();

        while (true) {
            if (tries >= 6) {
                System.out.println("You have exhausted all of your tries.  You lose.  Correct answer was " + target);
                playAgain();
            }

            System.out.println();
            System.out.println("Try number " + (tries+1) + " of 6");
            System.out.println("Guess a 5 letter word.");
            String guess = guess();

            if (guess.equals(target)) {
                System.out.println("That's correct!");
                playAgain();
            } else {
                for (int i = 0; i < guess.length(); i++) { // traverses guess
                    for (int j = 0; j < target.length(); j++) { // traverses target
                        if (guess.charAt(i) == target.charAt(j)) { // if current char in guess matches any char in target
                            if(guess.charAt(i) == target.charAt(i)) { // if matching char is in the correct position
                                match[i] = guess.charAt(i); // char is an exact match
                                if (matchNoPos.contains((Object)guess.charAt(i))) {
                                    matchNoPos.remove((Object)guess.charAt(i));
                                }
                            } else {
                                matchNoPos.add(guess.charAt(i)); // char is a match but wrong position
                            }
                        } else {
                            if (!contains(guess.charAt(i), target)) { // if current char is nowhere in target
                                incorrect.add(guess.charAt(i)); // char is incorrect
                            }
                        }
                    }
                }
                System.out.println("Matches:");
                display(match);
                System.out.println();

                System.out.println("Correct letter, wrong position:");
                hashDisplay(matchNoPos);
                System.out.println();

                System.out.println("Incorrect letters: ");
                hashDisplay(incorrect);
                System.out.println();

                tries++;

                System.out.println("Guess again.");
                System.out.println("**********************************************");
                System.out.println();
            }
        }
    }

    public static String guess() {
        String input = scanner.nextLine().toLowerCase();
        if (input.length() != 5) {
            System.out.println("Must be 5 letters");
            guess();
        } else {
            for (int i = 0; i < input.length(); i++) {
                if (notLetter(input.charAt(i))) {
                    System.out.println("Must contain only letters");
                    guess();
                } else {
                    return input;
                }
            }
        }
        return "-1";
    }

    public static boolean notLetter(char c) {
        return ((c >= '0' && c <= '9'));
    }

    public static void display(char[] letters) {
        for (int i = 0; i < letters.length; i++) {
            System.out.print(letters[i] + " ");
        }
    }

    public static void hashDisplay(Set<Character> incorrect) {
        for (char c : incorrect) {
            System.out.print(c + " ");
        }
    }

    public static boolean contains(char c, String target) {
        boolean contains = false;
        for (int i = 0; i < target.length(); i++) {
            if (c == target.charAt(i)) {
                contains = true;
            }
        }
        return contains;
    }

    public static void playAgain() throws IOException {
        System.out.println("Would you like to play again? (1 for yes, 2 for no)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            playGame();
        } else {
            System.exit(0);
        }
    }
}

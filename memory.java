import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Memory {
    int level;
    int chances;
    ArrayList<String> words = new ArrayList<String>();
    ArrayList<String> drawedWords = new ArrayList<String>();
    ArrayList<ArrayList> guessedPairs = new ArrayList<ArrayList>();
    
    public Memory(){
        this.words = readFile();
        this.level = askAboutLevel();
        this.drawedWords = randomWords(this.words, this.level);
        this.chances = setChances(this.level);
        this.guessedPairs = generateStartMatrix(this.level);
    }
    
    
    public static ArrayList<String> readFile() {
        BufferedReader reader;
        ArrayList<String> words = new ArrayList<String>();
        
        System.out.println("Reading the file...");
        try {
			reader = new BufferedReader(new FileReader("/uploads/Words.txt"));
			String line = reader.readLine();
			while (line != null) {
				//System.out.println(line);
				line = reader.readLine();
				words.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
    }
    public static int askAboutLevel() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter the level:");

        String level = myObj.nextLine();  // Read user input
        return Integer.parseInt(level);
    }
    
    public static int setChances(int level) {
        if (level == 1) {
            return 10;
        }else if (level == 2) {
            return 15;
        }
        return 0;
    }
    
    public ArrayList<String> randomWords(ArrayList<String> words, int level) {
        Collections.shuffle(words);
        List<String> wordsList = new ArrayList<String>();
        if (level == 1) {
            wordsList = words.subList(0, 4);
        }else if (level == 2) {
             wordsList = words.subList(0, 8);
        }
        ArrayList<String> drawedWords = new ArrayList<String>(wordsList);
        drawedWords.addAll(drawedWords);
        Collections.shuffle(drawedWords);
        return drawedWords;
    }
    public static void printResults(int level, int chances, ArrayList<ArrayList> guessedPairs){
        System.out.println("----------------------------");
        System.out.println("\t Level: " + level);
        System.out.println("\t Guess chances: " + chances + "\n");
        System.out.println("\t   1 2 3 4");
        for (ArrayList<String> row : guessedPairs){
            System.out.print("\t ");
            for (String field : row){
                System.out.print(field + " ");
            }
            System.out.println("\t ");
        }
        /***
        if (level == 1){
            System.out.println("\t   1 2 3 4");
            System.out.println("\t A X X X X");
            System.out.println("\t B X X X X");

        }else if (level == 2){
            System.out.println("\t   1 2 3 4");
            System.out.println("\t A X X X X");
            System.out.println("\t B X X X X");
            System.out.println("\t C X X X X");
            System.out.println("\t D X X X X");
        }
        **/
        System.out.println("----------------------------");

    }
    
    public static ArrayList<ArrayList> assignField(int level, ArrayList<String> drawedWords){
        ArrayList<ArrayList> matrix = new ArrayList<ArrayList>();
        ArrayList<String> rowA = new ArrayList<String>(drawedWords.subList(0,4));
        ArrayList<String> rowB = new ArrayList<String>(drawedWords.subList(4,8));
        matrix.add(rowA);
        matrix.add(rowB);
        if (level == 2){
            ArrayList<String> rowC = new ArrayList<String>(drawedWords.subList(8,12));
            ArrayList<String> rowD = new ArrayList<String>(drawedWords.subList(12,16));
            matrix.add(rowC);
            matrix.add(rowD);
            return matrix;
        }
        return matrix;
    }
    
    public static ArrayList<ArrayList> generateStartMatrix(int level){
        ArrayList<ArrayList> matrix = new ArrayList<ArrayList>();
        ArrayList<String> rowA = new ArrayList<String>();
        ArrayList<String> rowB = new ArrayList<String>();
        ArrayList<String> rowC = new ArrayList<String>();
        ArrayList<String> rowD = new ArrayList<String>();
        rowA.add("A");
        rowB.add("B");
        matrix.add(rowA);
        matrix.add(rowB);
        if (level == 2){
            rowC.add("C");
            rowD.add("D");
            matrix.add(rowC);
            matrix.add(rowD);
        }
        for (ArrayList row : matrix){
            row.add("X");
            row.add("X");
            row.add("X");
            row.add("X");
        }
        return matrix;
    }
    
    public static String askAboutChoice() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter your choice:");

        String choice = myObj.nextLine();  // Read user input
        return choice;
    }
    
    public static Boolean askAboutNewGame() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter your choice:");

        String newGame = myObj.nextLine();  // Read user input
        return Boolean.parseBoolean(newGame);
    }
    
    public static int convertLetterToIndex(String letter) {
        System.out.println("Letter: " + letter);
        if (letter.equals("A")){
            return 0;
        }
        else if (letter.equals("B")){
            return 1;
        }
        else if (letter.equals("C")){
            return 2;
        }
        else if (letter.equals("D")){
            return 3;
        }
        return 10000;
    }
    
    
    public static ArrayList<ArrayList> checkResult(int level, int chances, String choice, ArrayList<ArrayList> matrix, ArrayList<ArrayList> guessedPairs) {
        String previousChoice;
        ArrayList<ArrayList> temporalMatrix = new ArrayList<ArrayList>();
        int index1 = convertLetterToIndex(Character.toString(choice.charAt(0)));
        int index2 = Integer.parseInt(Character.toString(choice.charAt(1)));
        String guessedWorld1 = matrix.get(index1).get(index2).toString();
        temporalMatrix = guessedPairs;
        temporalMatrix.get(index1).set(index2, guessedWorld1);
        printResults(level, chances, temporalMatrix);
        previousChoice = askAboutChoice();
        int index3 = convertLetterToIndex(Character.toString(previousChoice.charAt(0)));
        int index4 = Integer.parseInt(Character.toString(previousChoice.charAt(1)));
        String guessedWorld2 = matrix.get(index3).get(index4).toString();
        System.out.println(guessedWorld2);
        
        temporalMatrix.get(index3).set(index4, guessedWorld2);
        printResults(level, chances, temporalMatrix);
        
        if (guessedWorld1.equals(guessedWorld2)){
            System.out.println("XXXX");
            guessedPairs = temporalMatrix;
        }
        else{
            temporalMatrix = null;
            temporalMatrix = guessedPairs;
        }
        
        return guessedPairs;
    }

    
    
    public static void main(String args[]) {
        ArrayList<ArrayList> matrix = new ArrayList<ArrayList>();
        String choice;
        String previousChoice = "A2";
        Memory game = new Memory();
        
		//myObj.askAboutLevel();
		for (int i = 0; i < game.drawedWords.size(); i++) {
            System.out.println(game.drawedWords.get(i));
        }
        //System.out.println("Level: " + game.level);
        //System.out.println("Chances: " + game.chances);
        //printResults(game.level, game.chances, game.guessedPairs, "", "");
        matrix = assignField(game.level, game.drawedWords);
        System.out.println("Matrix: " + matrix);
        //guessedPairs = generateStartMatrix(game.level);
        //choice = askAboutChoice();
        
        //System.out.println("Matrix: " + game.guessedPairs);
        //System.out.println("Choice: " + convertLetterToIndex(Character.toString(choice.charAt(0))));
        
        while (true){
            printResults(game.level, game.chances, game.guessedPairs);
            while (game.chances > 0){
                choice = askAboutChoice();
                System.out.println("Choice: " + choice);
                checkResult(game.level, game.chances, choice, matrix, game.guessedPairs);
                game.chances -= 1;
            }
            Boolean newGame = askAboutNewGame();
            if (newGame == false){
                break;
            }
        }
        
        


    }
    
}
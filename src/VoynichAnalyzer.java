import java.io.*;
import java.util.*;

public class VoynichAnalyzer {

    /**
     * Project: Voynich Manuscript Analyzer
     * Purpose Details: This program reads the text from the Voynich manuscript page 93v, performs frequency analysis on the symbols,
     * and attempts to map the symbols to Latin letters based on frequency analysis. The output includes the frequency analysis
     * and the symbol-to-letter mapping.
     * Course: IST 242
     * Author: Aayudh Nandiwdekar
     * Date Developed: November 26, 2024
     * Last Date Changed: November 26, 2024
     * Revision: 1.0
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for the file path from the user
        System.out.print("Enter the absolute file path for voynich_folio93v.txt: ");
        String filePath = scanner.nextLine();

        // Read the file and get the symbols
        String text = readFile(filePath);

        // If the file was read successfully
        if (text != null) {
            System.out.println("Voynich Manuscript Content:");
            System.out.println(text);

            // Perform frequency analysis
            Map<Character, Integer> frequencyMap = analyzeFrequency(text);

            // Sort symbols by frequency
            List<Map.Entry<Character, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
            sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Print out the frequency analysis
            System.out.println("\nFrequency Analysis:");
            for (Map.Entry<Character, Integer> entry : sortedEntries) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Try mapping the symbols to the Latin alphabet
            System.out.println("\nVoynich Symbol to Latin Letter Mapping Based on Frequency:");
            Map<Character, Character> symbolToLetterMap = mapSymbolsToLetters(sortedEntries);
            for (Map.Entry<Character, Character> entry : symbolToLetterMap.entrySet()) {
                System.out.println("Symbol '" + entry.getKey() + "' maps to Latin letter '" + entry.getValue() + "'");
            }
        }
    }

    // Method to read the file content
    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }

    // Method to perform frequency analysis on the symbols
    private static Map<Character, Integer> analyzeFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch) || Character.isWhitespace(ch)) {
                frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
            }
        }
        return frequencyMap;
    }

    // Method to map symbols to Latin letters based on frequency
    private static Map<Character, Character> mapSymbolsToLetters(List<Map.Entry<Character, Integer>> sortedEntries) {
        // Latin alphabet frequency (approximately ordered for simplicity)
        char[] latinAlphabet = {'e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h', 'd', 'l', 'u', 'c', 'm', 'f', 'y', 'p', 'b', 'v', 'k', 'g', 'w', 'x', 'z', 'j', 'q'};

        Map<Character, Character> symbolToLetterMap = new HashMap<>();
        for (int i = 0; i < sortedEntries.size() && i < latinAlphabet.length; i++) {
            symbolToLetterMap.put(sortedEntries.get(i).getKey(), latinAlphabet[i]);
        }

        return symbolToLetterMap;
    }
}

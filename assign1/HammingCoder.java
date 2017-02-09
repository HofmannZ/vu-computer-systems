import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Class for computing Hamming codes.
 */
public class HammingCoder {
    /**
     * Function that computes the Hamming code for a 16-bit word.
     * @param inWord Array that contains the bits of the input word, each one represented
     * as an integer number.
     * @return An array that contains the bits of the input word and the Hamming code
     * bits; each bit is represented as an integer number.
     */
    static int[] computeHammingCode(int[] inWord) {
        // ...
    }

    /**
     * This function counts the number of bits in the provided line.
     */
    static int getWordLength(String line) {
        // ...
    }

    /**
     * Estimates the total length of a coded word, given the length of its non-coded version.
     * For instance, for a non-coded word of 16 bits, which requires 5 Hamming code bits, the
     * total length returned by this function is 21.
     */
    static int getCodeLength(int wordLength) {
        // ...
    }

    public static void main(String args[]) {
        int i;

        if (args.length != 2) {
            System.out.println("Usage: java HammingCoder <input_file> <output_file>");
            System.exit(1);
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            PrintWriter writer = new PrintWriter(new FileWriter(args[1]));

            // Process the input file, line by line
            String line;
            while ((line = reader.readLine()) != null) {

                // Get the length of the input word
                int wordLength = getWordLength(line);

                // Estimate the length of the output word (Hamming code bits + data bits)
                int codeLength = getCodeLength(wordLength);

                int[] inWord = new int[wordLength];
                // Transform each character of the string into the corresponding int (0 or 1)
                for (i = 0; i < wordLength; i++) {
                    inWord[i] = (int)(line.charAt(i) - 48);
                }

                // Compute the Hamming code
                int[] outWord = computeHammingCode(inWord);

                // Print the result into the output file
                for (i = 0; i < codeLength; i++) {
                    writer.print(outWord[i]);
                }
                writer.println();
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

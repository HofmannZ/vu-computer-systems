import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
* Class for computing Hamming codes.
*/
public class HammingCoder {
  static int getParityBit(int[] hammingCodeWithBlanks, int parityPosition) {
    int sum = 0;
    int length = hammingCodeWithBlanks.length;

    // Iterate over n amount of bits, and skip n amount of bits.
    for (int i = parityPosition + 1; i < length; i += 2 * parityPosition) {
      for (int j = i; j < parityPosition && j < length; j++) {
        // Add the bits to the sum.
        sum += hammingCodeWithBlanks[j - 1];
      }
    }

    // If even return 0, if odd return 1.
    return sum % 2 == 0 ? 0 : 1;
  }

  /**
  * Function that computes the Hamming code for a 16-bit word.
  * @param inWord Array that contains the bits of the input word, each one represented
  * as an integer number.
  * @return An array that contains the bits of the input word and the Hamming code
  * bits; each bit is represented as an integer number.
  */
  static int[] computeHammingCode(int[] inWord, int codeLength) {
    int[] hammingCode = new int[codeLength];
    int exponentIterator = 0;

    // Insert black parity bits becouse their value does not matter right now.
    for (int i = 0; i < codeLength; i++) {

      // Check if the position of the bit is the next power of 2.
      if (i + 1 == Math.pow(2, exponentIterator)) {
        // Increment the exponent when it's a paroty bit.
        exponentIterator++;
      } else {
        // Set the bit to the next bit from the word.
        hammingCode[i] = inWord[i - exponentIterator];
      }
    }

    // Reset the exponent iterator.
    exponentIterator = 0;

    // Insert the right parity bits.
    for (int i = 0; i < codeLength; i++) {

      // Check if the position of the bit is the next power of 2.
      if (i + 1 == Math.pow(2, exponentIterator)) {
        // Set the parity bit to the right value.
        hammingCode[i] = getParityBit(hammingCode, i + 1);
        // hammingCode[i] = -3;
        exponentIterator++;
      }
    }

    return hammingCode;
  }

  /**
  * This function counts the number of bits in the provided line.
  */
  static int getWordLength(String line) {
    return line.length();
  }

  /**
  * Estimates the total length of a coded word, given the length of its non-coded version.
  * For instance, for a non-coded word of 16 bits, which requires 5 Hamming code bits, the
  * total length returned by this function is 21.
  */
  static int getCodeLength(int wordLength) {
    /**
    * If 16 = 2^x, we can use log base 2 to get x witch is 4.
    * And we add one becouse that is the first parity bit.
    */
    int numberOfParityBits = (int)(Math.ceil(Math.log(wordLength) / Math.log(2))) + 1;

    // Return the sum of the word length and the amount of needed parity bits.
    return wordLength + numberOfParityBits;
  }

  public static void main(String args[]) {
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
        for (int i = 0; i < wordLength; i++) {
          inWord[i] = line.charAt(i) - 48;
        }

        // Compute the Hamming code
        int[] outWord = computeHammingCode(inWord, codeLength);

        // Print the result into the output file
        for (int i = 0; i < codeLength; i++) {
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

package logic;

import java.io.*;
import java.util.BitSet;
import java.util.Map;

/**
 * The type File io.
 * This class serializes and de-serializes text, Character Dictionaries and compressed text.
 */
public class FileIO {
    /**
     * Serialize message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    public void serializeMessage(final String message) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/hugo/Desktop/Huffman/message.txt"))) {
            oos.writeChars(message);
        }
    }

    /**
     * Serialize Character Dictionary and the BitSet.
     *
     * @param charDictionary the char dictionary
     * @param compressedText the compressed text
     * @throws IOException the io exception
     */
    public void serializeCharDictionaryAndBitSet(final Map<Character, BitSet> charDictionary, final BitSet compressedText) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/hugo/Desktop/Huffman/encodedMessage.huff"))) {
            oos.writeObject(charDictionary);
            oos.writeObject(compressedText);
        }
    }

    /**
     * Deserialize Character Dictionary and BitSet.
     *
     * @return the Character Dictionary and BitSet in an object array
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Object[] deserializeCharDictionaryAndBitSet() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/Users/hugo/Desktop/Huffman/encodedMessage.huff"))) {
            final Map<Character, BitSet> charDictionary = (Map<Character, BitSet>) ois.readObject();
            final BitSet compressedText = (BitSet) ois.readObject();
            return new Object[] { charDictionary, compressedText };
        }
    }
}

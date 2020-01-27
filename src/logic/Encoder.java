package logic;

import java.util.BitSet;
import java.util.Map;
import java.util.Objects;

/**
 * The type Encoder.
 * This class encodes and decodes messages.
 */
class Encoder {
    /**
     * Encode message and generate BitSet.
     *
     * @param charDictionary the char dictionary
     * @param text           the text
     * @return the Character Dictionary BitSet
     */
    BitSet encodeMessage(final Map<Character, BitSet> charDictionary, final String text) {
        final BitSet bitSet = new BitSet();
        int index = 0;

        for (char c : text.toCharArray()) {
            for (int i = 0; i < charDictionary.get(c).length(); i++) {
                bitSet.set(index, charDictionary.get(c).get(i));
                index++;
            }
        }

        return bitSet;
    }

    /**
     * Decode message string.
     *
     * @param charDictionary the Character Dictionary
     * @param compressedText the compressed text
     * @return the decoded string
     */
    String decodeMessage(final Map<Character, BitSet> charDictionary, final BitSet compressedText) {
        final StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        int valueCounter = 0;
        BitSet tempSet = new BitSet();

        while (index <= compressedText.length()) {
            if (charDictionary.containsValue(tempSet)) {
                for (Map.Entry<Character, BitSet> entry : charDictionary.entrySet()) {
                    if (Objects.equals(tempSet, entry.getValue())) {
                        stringBuilder.append(entry.getKey());
                    }
                }
                tempSet.clear();
                valueCounter = 0;
            } else {
                if (compressedText.get(index)) {
                    tempSet.set(valueCounter);
                }
            }
            valueCounter++;
            index++;
        }

        return stringBuilder.toString();
    }
}

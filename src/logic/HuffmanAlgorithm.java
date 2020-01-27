package logic;

import java.util.*;

/**
 * The type Huffman algorithm.
 */
public class HuffmanAlgorithm {
    private final Encoder encoder = new Encoder();

    /**
     * Decompresses text string.
     *
     * @param charDictionary the Character Dictionary
     * @param compressedText the compressed text
     * @return the uncompressed text string
     */
    public String decompressText(final Map<Character, BitSet> charDictionary, final BitSet compressedText) {
        if (charDictionary == null || compressedText == null || compressedText.isEmpty()) {
            throw new IllegalArgumentException("Both the compressed text and the character dictionary can not be null or empty.");
        }

        return encoder.decodeMessage(charDictionary, compressedText);
    }

    /**
     * Compress text string.
     *
     * @param text the text
     * @return an object array with the Character Dictionary and the compressed text BitSet.
     */
    public Object[] compressText(final String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("The text can not be empty.");
        }

        final HashMap<Character, Integer> characterUsages = getCharacterUsages(text);

        final Node rootNode = createNodeTree(characterUsages);
        final Map<Character, BitSet> charDictionary = createCharDictionary(rootNode);
        final BitSet compressedText = encoder.encodeMessage(charDictionary, text);

        return new Object[] { charDictionary, compressedText };
    }

    /**
     * Gets character usages.
     *
     * @param text the text
     * @return a HashMap with the character and the number of usages in the given text
     */
    private HashMap<Character, Integer> getCharacterUsages(final String text) {
        HashMap<Character, Integer> characterUsages = new HashMap<>();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (characterUsages.containsKey(c)) {
                characterUsages.put(c, characterUsages.get(c) + 1);
            } else {
                characterUsages.put(c, 1);
            }
        }

        return characterUsages;
    }

    /**
     * Create Node Tree.
     *
     * @param characterUsages the Character usages hash map
     * @return the root node of the tree
     */
    private Node createNodeTree(HashMap<Character, Integer> characterUsages) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (char c: characterUsages.keySet()) {
            priorityQueue.add(new Node(c, characterUsages.get(c), null, null));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            priorityQueue.add(new Node('#', left.getFrequency() + right.getFrequency(), left, right));
        }

        return priorityQueue.poll();
    }

    /**
     * Create Character Dictionary HashMap.
     *
     * @param rootNode the root Node
     * @return the Character Dictionary HashMap
     */
    private Map<Character, BitSet> createCharDictionary(Node rootNode) {
        final Map<Character, BitSet> charDictionary = new HashMap<>();
        BitSet bitSet = new BitSet();

        doCreateCharDictionary(rootNode, charDictionary, bitSet, 0);
        return charDictionary;
    }

    /**
     * Create character dictionary.
     * Recursive method that creates the dictionary.
     *
     * @param node           the node
     * @param charDictionary the char dictionary
     * @param bitSet         the bit set
     * @param index          the index
     */
    private void doCreateCharDictionary(Node node, Map<Character, BitSet> charDictionary, BitSet bitSet, int index) {
        index++;

        if (node.getLeftNode() != null) {
            BitSet clonedBitSetLeft = (BitSet) bitSet.clone();
            BitSet clonedBitSetRight = (BitSet) bitSet.clone();

            clonedBitSetRight.set(index);

            doCreateCharDictionary(node.getLeftNode(), charDictionary, clonedBitSetLeft, index);
            doCreateCharDictionary(node.getRightNode(), charDictionary, clonedBitSetRight, index);
        } else {
            bitSet.set(index, true); //Dummy BitSet to know the length
            charDictionary.put(node.getCharacter(), bitSet);
        }
    }
}

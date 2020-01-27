package logic;

/**
 * The type Node.
 */
public class Node implements Comparable<Node> {
    private final char character;
    private final int frequency;
    private final Node left;
    private final Node right;

    /**
     * Instantiates a new Node.
     *
     * @param character the character
     * @param frequency the frequency
     * @param left      the left Node
     * @param right     the right Node
     */
    Node(char character, int frequency,  Node left,  Node right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the character.
     *
     * @return the character
     */
    char getCharacter() {
        return character;
    }

    /**
     * Gets the frequency.
     *
     * @return the frequency
     */
    int getFrequency() {
        return frequency;
    }

    /**
     * Gets the left Node.
     *
     * @return the left Node
     */
    Node getLeftNode() {
        return left;
    }

    /**
     * Gets the right Node.
     *
     * @return the right Node
     */
    Node getRightNode() {
        return right;
    }

    @Override
    public int compareTo(Node otherNode) {
        return Integer.compare(frequency, otherNode.getFrequency());
    }
}
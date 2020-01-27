package logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.BitSet;
import java.util.Map;

public class HuffmanAlgorithmTest {
    private static final String sampleText = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.";

    private HuffmanAlgorithm huffmanAlgorithm;
    private FileIO fileIO;

    @Before
    public void setUp() {
        huffmanAlgorithm = new HuffmanAlgorithm();
        fileIO = new FileIO();
    }

    @Test(expected = IllegalArgumentException.class)
    public void compressNullTest() {
        huffmanAlgorithm.compressText(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void compressEmptyTest() {
        huffmanAlgorithm.compressText("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void decompressNullTest() {
        huffmanAlgorithm.decompressText(null, null);
    }

    @Test
    public void compressText() {
        final Object[] nodeAndBitSet = huffmanAlgorithm.compressText(sampleText);
        final Map<Character, BitSet> compressedCharDictionary = (Map<Character, BitSet>) nodeAndBitSet[0];
        final BitSet compressedTextBitSet = (BitSet) nodeAndBitSet[1];

        Assert.assertNotNull(compressedCharDictionary);
        Assert.assertNotNull(compressedTextBitSet);
    }

    @Test
    public void serializeCompressedTextTest() throws IOException {
        final Object[] nodeAndBitSet = huffmanAlgorithm.compressText(sampleText);
        final Map<Character, BitSet> compressedCharDictionary = (Map<Character, BitSet>) nodeAndBitSet[0];
        final BitSet compressedTextBitSet = (BitSet) nodeAndBitSet[1];

        fileIO.serializeCharDictionaryAndBitSet(compressedCharDictionary, compressedTextBitSet);
    }

    @Test
    public void deserializeCompressedTextTest() throws IOException, ClassNotFoundException {
        final Object[] nodeAndBitSetInput = huffmanAlgorithm.compressText(sampleText);
        final Map<Character, BitSet> compressedCharDictionaryInput = (Map<Character, BitSet>) nodeAndBitSetInput[0];
        final BitSet compressedTextBitSetInput = (BitSet) nodeAndBitSetInput[1];

        fileIO.serializeCharDictionaryAndBitSet(compressedCharDictionaryInput, compressedTextBitSetInput);

        final Object[] nodeAndBitSetOutput = fileIO.deserializeCharDictionaryAndBitSet();
        final Map<Character, BitSet> compressedCharDictionaryOutput = (Map<Character, BitSet>) nodeAndBitSetOutput[0];
        final BitSet compressedTextBitSetOutput = (BitSet) nodeAndBitSetOutput[1];

        Assert.assertEquals(compressedCharDictionaryInput, compressedCharDictionaryOutput);
        Assert.assertEquals(compressedTextBitSetInput, compressedTextBitSetOutput);
    }

    @Test
    public void decompressText() throws IOException, ClassNotFoundException {
        final Object[] nodeAndBitSetInput = huffmanAlgorithm.compressText(sampleText);
        final Map<Character, BitSet> compressedCharDictionaryInput = (Map<Character, BitSet>) nodeAndBitSetInput[0];
        final BitSet compressedTextBitSetInput = (BitSet) nodeAndBitSetInput[1];

        fileIO.serializeCharDictionaryAndBitSet(compressedCharDictionaryInput, compressedTextBitSetInput);

        final Object[] nodeAndBitSetOutput = fileIO.deserializeCharDictionaryAndBitSet();
        final Map<Character, BitSet> compressedCharDictionaryOutput = (Map<Character, BitSet>) nodeAndBitSetOutput[0];
        final BitSet compressedTextBitSetOutput = (BitSet) nodeAndBitSetOutput[1];

        Assert.assertNotNull(huffmanAlgorithm.decompressText(compressedCharDictionaryOutput, compressedTextBitSetOutput));
        Assert.assertEquals(sampleText, huffmanAlgorithm.decompressText(compressedCharDictionaryOutput, compressedTextBitSetOutput));
    }
}

package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;
import java.util.Map;

public class HuffmanAlgorithmPerformanceTest {
    private static String tenThousandWords;
    private static String oneMillionWords;

    private HuffmanAlgorithm huffmanAlgorithm;

    @Before
    public void setUp() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            stringBuilder.append("Proin vehicula metus nec risus auctor, eu tempor felis lobortis. Aliquam ac blandit risus. Vestibulum eu nulla fermentum, laoreet tellus sit amet, tincidunt ligula. In vitae enim placerat, imperdiet turpis vitae, sagittis enim. Nulla a odio varius, ullamcorper urna sed, viverra sem. Curabitur ac sem in augue efficitur blandit ut at enim. Sed eu metus turpis. Ut commodo nisi diam, a consequat libero tempor sed. Phasellus neque tortor, fringilla id lacus nec, molestie faucibus felis. Sed vel tempor orci, vel ullamcorper nulla. Sed dignissim dignissim pharetra. Cras nec eros sed turpis mattis suscipit non id ipsum. Phasellus a nisi purus.");
        }
        tenThousandWords = stringBuilder.toString();

        stringBuilder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            stringBuilder.append("Proin vehicula metus nec risus auctor, eu tempor felis lobortis. Aliquam ac blandit risus. Vestibulum eu nulla fermentum, laoreet tellus sit amet, tincidunt ligula. In vitae enim placerat, imperdiet turpis vitae, sagittis enim. Nulla a odio varius, ullamcorper urna sed, viverra sem. Curabitur ac sem in augue efficitur blandit ut at enim. Sed eu metus turpis. Ut commodo nisi diam, a consequat libero tempor sed. Phasellus neque tortor, fringilla id lacus nec, molestie faucibus felis. Sed vel tempor orci, vel ullamcorper nulla. Sed dignissim dignissim pharetra. Cras nec eros sed turpis mattis suscipit non id ipsum. Phasellus a nisi purus.");
        }
        oneMillionWords = stringBuilder.toString();

        huffmanAlgorithm = new HuffmanAlgorithm();
    }

    @Test
    public void compressTenThousandWordsTest() {
        System.out.println("\nCompress 10.000 words:");
        long totalTime = 0;

        for (int i = 0; i < 10; i++) {
            final long startTime = System.nanoTime();

            huffmanAlgorithm.compressText(tenThousandWords);

            totalTime += (System.nanoTime() - startTime);
        }

        System.out.println(totalTime / 10L / 1000000L + " milliseconds");
    }

    @Test
    public void compressOneMillionWordsTest() {
        System.out.println("\nCompress 1.000.000 words:");
        long totalTime = 0;

        for (int i = 0; i < 10; i++) {
            final long startTime = System.nanoTime();

            huffmanAlgorithm.compressText(oneMillionWords);

            totalTime += (System.nanoTime() - startTime);
        }

        System.out.println(totalTime / 10L / 1000000L + " milliseconds");
    }

    @Test
    public void decompressTenThousandWordsTest() {
        System.out.println("\nDecompress 10.000 words:");
        long totalTime = 0;

        final Object[] nodeAndBitSetInput = huffmanAlgorithm.compressText(tenThousandWords);
        final Map<Character, BitSet> compressedCharDictionaryInput = (Map<Character, BitSet>) nodeAndBitSetInput[0];
        final BitSet compressedTextBitSetInput = (BitSet) nodeAndBitSetInput[1];

        for (int i = 0; i < 10; i++) {
            final long startTime = System.nanoTime();

            huffmanAlgorithm.decompressText(compressedCharDictionaryInput, compressedTextBitSetInput);

            totalTime += (System.nanoTime() - startTime);
        }

        System.out.println(totalTime / 10L / 1000000L + " milliseconds");
    }

    @Test
    public void decompressOneMillionWordsTest() {
        System.out.println("\nDecompress 1.000.000 words:");
        long totalTime = 0;

        final Object[] nodeAndBitSetInput = huffmanAlgorithm.compressText(oneMillionWords);
        final Map<Character, BitSet> compressedCharDictionaryInput = (Map<Character, BitSet>) nodeAndBitSetInput[0];
        final BitSet compressedTextBitSetInput = (BitSet) nodeAndBitSetInput[1];

        for (int i = 0; i < 10; i++) {
            final long startTime = System.nanoTime();

            huffmanAlgorithm.decompressText(compressedCharDictionaryInput, compressedTextBitSetInput);

            totalTime += (System.nanoTime() - startTime);
        }

        System.out.println(totalTime / 10L / 1000000L + " milliseconds");
    }
}

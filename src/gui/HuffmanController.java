package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import logic.FileIO;
import logic.HuffmanAlgorithm;

import java.io.IOException;
import java.net.URL;
import java.util.BitSet;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The type Huffman controller.
 * Controls the UI.
 */
public class HuffmanController implements Initializable {
    @FXML private TextArea txtUncompressed;
    @FXML private TextArea txtCompressed;

    private HuffmanAlgorithm huffmanAlgorithm;
    private FileIO fileIO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        huffmanAlgorithm = new HuffmanAlgorithm();
        fileIO = new FileIO();
    }

    /**
     * Compress the text in the txtUncompressed TextArea.
     * Put the result in the txtCompressed TextArea.
     * Also put the result in message.txt and encodedMessage.huff
     */
    public void Compress() {
        StringBuilder stringBuilder = new StringBuilder();

        final String inputText = txtUncompressed.getText();
        final Object[] nodeAndBitSet = huffmanAlgorithm.compressText(inputText);
        final Map<Character, BitSet> compressedCharDictionary = (Map<Character, BitSet>) nodeAndBitSet[0];
        final BitSet compressedTextBitSet = (BitSet) nodeAndBitSet[1];

        for (int i = 0; i < compressedTextBitSet.length(); i++) {
            stringBuilder.append(compressedTextBitSet.get(i) ? 1 : 0);
        }

        txtCompressed.setText(stringBuilder.toString());

        try {
            fileIO.serializeMessage(inputText);
            fileIO.serializeCharDictionaryAndBitSet(compressedCharDictionary, compressedTextBitSet);
        } catch (IOException e) {
            txtCompressed.setText(txtCompressed.getText() + "\n\nERROR SAVING FILE.");
        }
    }

    /**
     * Decompress the text in encodedMessage.huff.
     * Put the result in the txtUncompressed TextArea.
     */
    public void Decompress() {
        try {
            final Object[] nodeAndBitSet = fileIO.deserializeCharDictionaryAndBitSet();
            final Map<Character, BitSet> compressedCharDictionary = (Map<Character, BitSet>) nodeAndBitSet[0];
            final BitSet compressedText = (BitSet) nodeAndBitSet[1];
            txtUncompressed.setText(huffmanAlgorithm.decompressText(compressedCharDictionary, compressedText));
        } catch (IOException | ClassNotFoundException e) {
            txtCompressed.setText(txtCompressed.getText() + "\n\nERROR LOADING FILE.");
        }
    }
}

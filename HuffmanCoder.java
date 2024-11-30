import java.util.*;
import java.io.*;

public class HuffmanCoder {
    // Encoding and decoding maps
    HashMap<Character, String> encoder;
    HashMap<String, Character> decoder;

    // Node class representing each node in the Huffman tree
    private class Node implements Comparable<Node> {
        Character data;
        int cost; // frequency
        Node left;
        Node right;

        public Node(Character data, int cost) {
            this.data = data;
            this.cost = cost;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

    public static class FileHandler {
        public static String readFile(String fileName) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();  // Returns the file content as a string
        }

        public static void writeToFile(String fileName, String content) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
            writer.close();
        }


    }



    // Constructor to initialize encoder and decoder maps
    public HuffmanCoder(String feeder) throws Exception {
        HashMap<Character, Integer> fmap = new HashMap<>();
        
        // Build frequency map
        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            int ov = fmap.getOrDefault(cc, 0);
            fmap.put(cc, ov + 1);
        }

        // Min heap for Huffman Tree
        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        Set<Map.Entry<Character, Integer>> entrySet = fmap.entrySet();

        // Insert nodes into the priority queue
        for (Map.Entry<Character, Integer> entry : entrySet) {
            Node node = new Node(entry.getKey(), entry.getValue());
            minHeap.add(node);  // Use add instead of insert
        }

        // Build Huffman tree
        while (minHeap.size() > 1) {
            Node first = minHeap.poll();  // Use poll instead of remove
            Node second = minHeap.poll();  // Use poll instead of remove

            Node newNode = new Node('\0', first.cost + second.cost);
            newNode.left = first;
            newNode.right = second;

            minHeap.add(newNode);  // Use add instead of insert
        }

        // Root node of the Huffman Tree
        Node ft = minHeap.poll();  // Use poll instead of remove

        // Initialize encoder and decoder maps
        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        this.initEncoderDecoder(ft, "");
    }

    // Recursive method to generate encoder and decoder maps
    private void initEncoderDecoder(Node node, String osf) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            this.encoder.put(node.data, osf);
            this.decoder.put(osf, node.data);
        }
        initEncoderDecoder(node.left, osf + "0");
        initEncoderDecoder(node.right, osf + "1");
    }

    // Method to encode a source string using the Huffman codes
    public String encode(String source) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            ans.append(encoder.get(source.charAt(i)));
        }
        return ans.toString();
    }

    // Method to decode a coded string using the Huffman codes
    public String decode(String codedString) {
        StringBuilder key = new StringBuilder();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < codedString.length(); i++) {
            key.append(codedString.charAt(i));
            if (decoder.containsKey(key.toString())) {
                ans.append(decoder.get(key.toString()));
                key.setLength(0); // Reset the key after successful decoding
            }
        }
        return ans.toString();
    }

    // Method to print encoding and decoding maps for debugging
                // public void printMaps() {
                //     System.out.println("Encoder Map:");
                //     for (Map.Entry<Character, String> entry : encoder.entrySet()) {
                //         System.out.println(entry.getKey() + " -> " + entry.getValue());
                //     }
                //     System.out.println("Decoder Map:");
                //     for (Map.Entry<String, Character> entry : decoder.entrySet()) {
                //         System.out.println(entry.getKey() + " -> " + entry.getValue());
                //     }
                // }

    // Main method for testing
    public static void main(String[] args) throws Exception {
                    //     String feeder = "aaabbccddeee";
                    //     HuffmanCoder huffman = new HuffmanCoder(feeder);

                    //             // huffman.printMaps(); // Print maps for debugging

                    //     String source = "abcde";
                    //     String encoded = huffman.encode(source);
                    //     System.out.println("Encoded: " + encoded);

                    //     String decoded = huffman.decode(encoded);
                    //     System.out.println("Decoded: " + decoded);
                    // }

        // Read the file to be compressed
        String feeder = FileHandler.readFile("input.txt");

        // Initialize the HuffmanCoder with the content of the file
        HuffmanCoder huffman = new HuffmanCoder(feeder);

        // Encode the content
        String encoded = huffman.encode(feeder);
        System.out.println("Encoded: " + encoded);  // Just for demonstration

        // Write the encoded content to a new file
        FileHandler.writeToFile("encoded.txt", encoded);

        // Now, to decompress
        String encodedContent = FileHandler.readFile("encoded.txt");
        String decoded = huffman.decode(encodedContent);

        // Write the decoded content to a new file
        FileHandler.writeToFile("decoded.txt", decoded);

        System.out.println("Decoded content: " + decoded);  // Just for demonstration
    }


}

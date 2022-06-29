import java.util.*;

public class LZWComp {
    public static List<Integer> compress(String uncompressed) {
        
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)
            dictionary.put("" + (char)i, i);

        String found = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char next : uncompressed.toCharArray()) {
            String temp = found + next;
            if (dictionary.containsKey(temp))
                found = temp;
            else {
                result.add(dictionary.get(found));
                // Add temp to the dictionary.
                dictionary.put(temp, dictSize++);
                found = "" + next;
            }
        }

        // Output the code for found.
        if (!found.equals(""))
            result.add(dictionary.get(found));
        return result;
    }

    /** Decompress a list of output ks to a string. */
    public static String decompress(List<Integer> compressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char)i);

        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);

            result.append(entry);

            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));

            w = entry;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        List<Integer> compressed = compress("ABAABABBAABAABAAABABBBBBBB ");
        System.out.println(compressed);
        String decompressed = decompress(compressed);
        System.out.println(decompressed);
    }
}
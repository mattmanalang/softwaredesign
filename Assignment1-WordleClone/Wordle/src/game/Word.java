package game;

import java.util.HashMap;

public class Word {
    private String whatsTheWord;
    public HashMap<Character, Integer> composition = new HashMap<Character, Integer>();

    public Word(String aWord) {
        whatsTheWord = aWord;
        setHashMap(whatsTheWord, composition);
    }

    public void setWord(String newWord) {
        whatsTheWord = newWord;
        setHashMap(whatsTheWord, composition);
    }

    public String getWord() {
        return whatsTheWord;
    }

    public HashMap<Character, Integer> getComposition() {
        return composition;
    }

    static void setHashMap(String aWord, HashMap<Character, Integer> composition) {
        for (int i = 0; i < aWord.length(); i++) {
            char wordChar = aWord.charAt(i);
            if (composition.containsKey(wordChar)) {
                composition.put(wordChar, composition.get(wordChar) + 1);
            }
            else {
                composition.put(wordChar, 1);
            }
        }
    }
}

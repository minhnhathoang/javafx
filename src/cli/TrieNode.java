package cli;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    Map<Character, TrieNode> children;
    private boolean endOfWord;

    private String meaning;

    public TrieNode() {
        children = new HashMap<>();
        endOfWord = false;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setEndOfWord(boolean bool) {
        this.endOfWord = bool;
    }
}


package cli;

import java.util.ArrayList;

public class Dictionary {
    protected ArrayList<Word> listWord;

    public Trie trie = new Trie();

    public Dictionary() {
        listWord = new ArrayList<>();
    }

}

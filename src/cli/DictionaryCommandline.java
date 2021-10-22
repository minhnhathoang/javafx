package cli;

import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {

    public DictionaryCommandline() {
        super();
    }

    public static void main(String[] args) {
        DictionaryCommandline u = new DictionaryCommandline();
        u.insertFromFile();
        u.dictionarySearcher();
    }

    public void showAllWords() {
        System.out.printf("%-20s| %-20s| %-20s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < listWord.size(); ++i) {
            Word tmp = listWord.get(i);
            System.out.printf("%-20s| %-20s| %-20s\n", i + 1, tmp.getWord_target(), tmp.getWord_explain());
        }
    }

    public void dictionarySearcher() {
        System.out.println("Search: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        /*
        int nCharacters = word.length();
        for (Word value : listWord) {
            if (value.getWord_target().length() >= nCharacters) {
                boolean chk = true;
                for (int i = 0; i < nCharacters; ++i) {
                    if (value.getWord_target().charAt(i) != word.charAt(i)) {
                        chk = false;
                        break;
                    }
                }
                if (chk) {
                    System.out.println(value.getWord_target());
                }
            }
        }
        */
        trie.seacher(word);
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        insertFromFile();
        showAllWords();
        dictionaryLookup();
    }
}


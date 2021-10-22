package cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    public DictionaryManagement() {
        super();
    }

    public static void main(String[] args) {
        DictionaryManagement u = new DictionaryManagement();
        u.insertFromFile();
        u.dictionaryLookup();
        u.delete("zoom");
        u.dictionaryLookup();
    }

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; ++i) {
            Word cur = new Word(sc.nextLine(), sc.nextLine());
            listWord.add(cur);
        }
        trie.insertFromList(listWord);
    }

    /* src/dictionaries.txt is saved in UTF8 file without BOM, 65279 ascii */
    public void insertFromFile() {
        try {
            File myDict = new File("src/resources/data/dictionaries.txt");
            Scanner sc = new Scanner(myDict);
            while (sc.hasNext()) {
                String[] eng_vn = sc.nextLine().split("\t");
                listWord.add(new Word(eng_vn[0], eng_vn[1]));
            }
            trie.insertFromList(listWord);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup() {
        System.out.println("Lookup: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        /*
        for (Word value : listWord) {
            if (word.equals(value.getWord_target())) {
                System.out.println(value.getWord_explain());
                return value;
            }
        }
        */
        trie.lookup(word);
    }

    public void dictionaryEdit(String word, String value) {
        trie.edit(word, value);
    }

    public void dictionaryExportToFile() {
        System.out.print("Enter file's name +(.txt): ");
        Scanner sc = new Scanner(System.in);
        String nameFile = sc.nextLine();
        try {
            File newFile = new File("src/" + nameFile + ".txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + nameFile + ".txt");
            } else {
                System.out.println("File already exists");
            }
            FileWriter fileWriter = new FileWriter("src/" + nameFile + ".txt");
            for (Word word : listWord) {
                fileWriter.write(word.getWord_target() + "\t" + word.getWord_explain() + "\n");
            }
            fileWriter.close();
            System.out.println("Successful Export!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String word) {
        System.out.println(trie.delete(trie.getRoot(), word, 0));
    }
}

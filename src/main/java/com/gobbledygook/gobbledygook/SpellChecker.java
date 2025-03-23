package com.gobbledygook.gobbledygook;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import lombok.Data;
import java.io.File;

@Data
public class SpellChecker {
    private HashSet<String> words = new HashSet<>();
    
    public SpellChecker(String filePath){
        loadWords(filePath);
    }

    private void loadWords(String filePath){
        // Load words from file
        Scanner file = new Scanner(new File(filePath));
        while(file.hasNext()){
            words.add(file.nextLine().trim().toLowerCase());
        }
    }

    public String checkSpelling(String word){
        if (words.contains(word)) {
            return "yes";
        }
        else{
            return "no";
        }
    }
}

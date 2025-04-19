package com.gobbledygook.gobbledygook;
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
        try(Scanner file = new Scanner(new File(filePath))){
            // Load words from file
            while(file.hasNext()){
                words.add(file.nextLine().trim().toLowerCase());
            }
        }catch(Exception e){
            System.out.println("Oh No! " + e.getMessage());
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

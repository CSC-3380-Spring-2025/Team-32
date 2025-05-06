package com.gobbledygook.gobbledygook;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "words")

public class WordEntity {

    @Id
    private int id;
    @Column(name = "Word")
    private String word;
    @Column(name = "Definition")
    private String definition;

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }
    
    public String getDefinition() {
        return definition;
    }

}
package com.gobbledygook.gobbledygook;


import lombok.Data;

import java.util.UUID;

@Data
class Definition {
    private UUID id;
    private String definition;
    private Player writer;
    private boolean real;

    public Definition(UUID id, String definition, Player writer, boolean real) {
        this.id = id;
        this.definition = definition;
        this.writer = writer;
        this.real = real;
    }
}

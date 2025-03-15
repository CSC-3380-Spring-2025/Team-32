package com.gobbledygook.gobbledygook;

import lombok.Data;

import java.util.UUID;

@Data
public class Submission {
    private UUID playerId;
    /* type can be wordchain, definition, or story */
    private String type;
    private String content;
}

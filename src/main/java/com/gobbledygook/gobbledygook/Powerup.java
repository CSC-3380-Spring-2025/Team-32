package com.gobbledygook.gobbledygook;


import lombok.Data;

import java.util.UUID;

@Data
abstract class PowerUp {
    private UUID ownerOfPowerup;
    private String powerupName;
    private int numberOfUsesLeft;

    abstract void usePowerup();
}

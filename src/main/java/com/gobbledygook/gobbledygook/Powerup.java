package com.gobbledygook.gobbledygook;


import lombok.Data;

import java.util.UUID;

@Data
abstract class PowerUp {
    protected Player ownerOfPowerup;
    protected String powerUpName;
    protected int numberOfUsesLeft;

    public PowerUp(Player ownerOfPowerup, String powerUpName, int numberOfUsesLeft) {
        this.ownerOfPowerup = ownerOfPowerup;
        this.powerUpName = powerUpName;
        this.numberOfUsesLeft = numberOfUsesLeft;
    }

    abstract void usePowerup();
}

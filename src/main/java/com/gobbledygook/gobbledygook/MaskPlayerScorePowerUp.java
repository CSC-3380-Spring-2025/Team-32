package com.gobbledygook.gobbledygook;

import lombok.Data;

@Data
public class MaskPlayerScorePowerUp extends PowerUp {
    private boolean isActivated;

    public MaskPlayerScorePowerUp(Player ownerOfPowerup, String powerUpName, int numberOfUsesLeft) {
        super(ownerOfPowerup, powerUpName, numberOfUsesLeft);

    }

    @Override
    public boolean usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;
            isActivated = true;
            return true;

        } else {
            isActivated = false;
            return false;
        }

    }

}

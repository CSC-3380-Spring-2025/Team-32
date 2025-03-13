package com.gobbledygook.gobbledygook;

import lombok.Data;
import java.util.UUID;

@Data
class DoubleOrNothing extends PowerUp {
    public DoubleOrNothing(UUID ownerOfPowerup) {
        super(ownerOfPowerup, "Double or Nothing", 3); // Initialize base class fields
    }

    @Override
    void usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;
            System.out.println("Used Double or Nothing! Uses left: " + numberOfUsesLeft);
        } else {
            System.out.println("No uses left for Double or Nothing!");
        }
    }
}

package com.gobbledygook.gobbledygook;

import lombok.Data;

@Data
public class PeekDefinitionPowerUp extends PowerUp {

    public PeekDefinitionPowerUp(Player ownerOfPowerup, String powerUpName, int numberOfUsesLeft) {
        super(ownerOfPowerup, powerUpName, numberOfUsesLeft);
        
    }

    @Override
    public boolean usePowerup() {
       if (numberOfUsesLeft > 0){
        numberOfUsesLeft--;
        return true;
        
       }
       else{
        return false;
       }
    }

    
}

package supermario.logic;

import supermario.models.GameCharacter;
import supermario.models.State;
import supermario.models.User;

public class ShoppingHandler {
    public static boolean characterCanBeBought(GameCharacter c) {
        return !State.getCurrentUser().getOwnedCharacters().contains(c);
    }

    public static BuyCharacterResult buyCharacter(GameCharacter c) {
        User currentUser = State.getCurrentUser();
        if (c.getPrice() > currentUser.getCoins())
            return BuyCharacterResult.ERROR_NOT_ENOUGH_COINS;
        currentUser.addCoins(-c.getPrice());
        currentUser.addOwnedCharacter(c);
        return BuyCharacterResult.SUCCESS;
    }
}

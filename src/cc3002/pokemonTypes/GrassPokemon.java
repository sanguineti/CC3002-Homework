package cc3002.pokemonTypes;

import cc3002.AbstractPokemon;
import cc3002.Trainer;

public class GrassPokemon extends AbstractPokemon {
    public GrassPokemon(String cardName, int id, int hp) {
        super(cardName, id, hp);
    }

    @Override
    public String getCardName() {
        return null;
    }

    @Override
    public void setCardName(String cardName) {

    }

    @Override
    public void playCard(Trainer aTrainer) {

    }
}

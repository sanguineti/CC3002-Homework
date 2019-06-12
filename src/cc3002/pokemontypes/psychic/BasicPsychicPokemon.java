package cc3002.pokemontypes.psychic;

import cc3002.abilities.AttackContainer;
import cc3002.cardvisitors.ICardVisitor;
import cc3002.pokemontypes.IBasicPokemon;

public class BasicPsychicPokemon extends AbstractPsychicPokemon implements IBasicPokemon {
    /**
     * Creates a new psychic pokemon.
     *
     * @param cardName   The name of the card.
     * @param id         The card ID.
     * @param hp         The HP of the pokemon
     * @param attackList a list with the attacks of the Pokemon, that can be up to 4.
     */
    public BasicPsychicPokemon(String cardName, int id, int hp, AttackContainer attackList) {
        super(cardName, id, hp, attackList);
    }

    @Override
    public void accept(ICardVisitor v) {
        v.visitBasicPokemon(this);
    }
    /**
     * {@inheritDoc}
     *
     * @param o The target Pokemon object
     * @return True if are equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicPsychicPokemon)) return false;
        return super.equals(o);
    }

}

package cc3002.energies;

import cc3002.pokemon.IPokemon;

/**
 * Common interface for all the energies, that also are a card. Besides the card attributes, a energy card also can be
 * added to a target Pokemon.
 *
 * @author F. Giovanni Sanguineti
 */
public interface IEnergy {
    void addEnergyToPokemon(IPokemon other);
}

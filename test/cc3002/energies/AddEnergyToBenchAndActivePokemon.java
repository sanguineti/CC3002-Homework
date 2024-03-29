package cc3002.energies;

import cc3002.Trainer;
import cc3002.abilities.AbilityContainer;
import cc3002.abilities.Attack;
import cc3002.abilities.NullAbility;
import cc3002.abilities.effects.NullEffect;
import cc3002.pokemon.IBasicPokemon;
import cc3002.pokemon.fire.BasicFirePokemon;
import cc3002.pokemon.psychic.BasicPsychicPokemon;
import cc3002.pokemon.water.BasicWaterPokemon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddEnergyToBenchAndActivePokemon {
    private AbstractEnergy electric, fighting, fire, grass, psychic, water;
    private IBasicPokemon one, two, three;
    private EnergyContainer first = new EnergyContainer(0, 1, 2, 1, 2, 1);
    private Attack firstAttack = new Attack("Látigo Cepa", "Le pega con un látigo cepa al pókemon oponente",
            40, first, new NullEffect());
    private AbilityContainer firstContainer = new AbilityContainer(firstAttack, new NullAbility(), new NullAbility(), new NullAbility());

    private Trainer franco;

    @Before
    public void setUp() {
        electric = new ElectricEnergy();
        fighting = new FightingEnergy();
        fire = new FireEnergy();
        grass = new GrassEnergy();
        psychic = new PsychicEnergy();
        water = new WaterEnergy();
        one = new BasicFirePokemon("one", 15, 43, firstContainer);
        two = new BasicWaterPokemon("two", 54, 45, firstContainer);
        three = new BasicPsychicPokemon("three", 67, 56, firstContainer);
        franco = new Trainer("Franco");
    }

    @Test
    public void addEnergyTest() {

        franco.addCardToHand(electric);
        franco.addCardToHand(fighting);
        franco.addCardToHand(fire);
        franco.addCardToHand(grass);
        franco.addCardToHand(psychic);
        franco.addCardToHand(water);

        franco.addCardToHand(one);
        franco.addCardToHand(two);
        franco.addCardToHand(three);

        franco.play(one);
        franco.play(two);
        franco.play(three);

        franco.setSelectedPokemon(franco.getActivePokemon());
        franco.play(electric);
        franco.setSelectedPokemon(franco.getActivePokemon());
        franco.play(fighting);
        franco.unSelectPokemon();
        assertEquals(franco.getActivePokemon().getAllEnergyQuantity(), new EnergyContainer(1, 1, 0, 0, 0, 0));
        franco.setSelectedPokemon(franco.getBench().get(0));
        franco.play(fire);
        franco.setSelectedPokemon(franco.getBench().get(0));
        franco.play(grass);
        franco.unSelectPokemon();
        assertEquals(franco.getBench().get(0).getAllEnergyQuantity(), new EnergyContainer(0, 0, 1, 1, 0, 0));
        franco.setSelectedPokemon(franco.getBench().get(1));
        franco.play(psychic);
        franco.setSelectedPokemon(franco.getBench().get(1));
        franco.play(water);
        franco.unSelectPokemon();
        assertEquals(franco.getBench().get(1).getAllEnergyQuantity(), new EnergyContainer(0, 0, 0, 0, 1, 1));

    }
}

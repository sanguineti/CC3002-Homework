package cc3002.pokemon.pokemonevolvingtest;

import cc3002.Trainer;
import cc3002.abilities.AbilityContainer;
import cc3002.abilities.Attack;
import cc3002.abilities.NullAbility;
import cc3002.abilities.effects.NullEffect;
import cc3002.energies.EnergyContainer;
import cc3002.pokemon.psychic.BasicPsychicPokemon;
import cc3002.pokemon.psychic.PhaseOnePsychicPokemon;
import cc3002.pokemon.psychic.PhaseTwoPsychicPokemon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PsychicEvolvingTest {
    private BasicPsychicPokemon basic, basicTwo;
    private PhaseOnePsychicPokemon phaseOne, secondPhaseOne;
    private PhaseTwoPsychicPokemon phaseTwo, secondPhaseTwo;

    private EnergyContainer first, two;

    private Trainer trainer;

    @Before
    public void setUp() {
        first = new EnergyContainer(0, 1, 2, 1, 2, 1);
        two = new EnergyContainer(0, 1, 1, 0, 2, 1);

        Attack firstAttack = new Attack("Látigo Cepa", "Le pega con un látigo cepa al pókemon oponente",
                40, first, new NullEffect());
        AbilityContainer firstContainer = new AbilityContainer(firstAttack, new NullAbility(), new NullAbility(), new NullAbility());

        basic = new BasicPsychicPokemon("Pichu", 32, 40, firstContainer);
        phaseOne = new PhaseOnePsychicPokemon("Pikachu", 132, 60, firstContainer, 32);
        phaseTwo = new PhaseTwoPsychicPokemon("Raichu", 232, 90, firstContainer, 132);

        basicTwo = new BasicPsychicPokemon("Pichu2", 33, 40, firstContainer);
        secondPhaseOne = new PhaseOnePsychicPokemon("Pikachu2", 133, 60, firstContainer, 33);
        secondPhaseTwo = new PhaseTwoPsychicPokemon("Raichu2", 233, 90, firstContainer, 133);


        trainer = new Trainer("Franco");
    }

    @Test
    public void psychicEvolve() {

        basic.setEnergyContainer(first);
        phaseOne.setEnergyContainer(first);
        phaseTwo.setEnergyContainer(first);

        basicTwo.setEnergyContainer(two);
        secondPhaseOne.setEnergyContainer(two);
        secondPhaseTwo.setEnergyContainer(two);


        trainer.addCardToHand(basic);
        trainer.addCardToHand(basicTwo);
        trainer.play(trainer.getHand().get(0));
        trainer.play(trainer.getHand().get(0));

        assertEquals(trainer.getActivePokemon(), basic);
        assertEquals(trainer.getBench().get(0), basicTwo);

        trainer.addCardToHand(phaseOne);
        trainer.addCardToHand(phaseTwo);
        trainer.addCardToHand(secondPhaseOne);
        trainer.addCardToHand(secondPhaseTwo);

        trainer.setSelectedPokemon(basic);
        trainer.play(trainer.getHand().get(0));
        trainer.unSelectPokemon();
        assertEquals(trainer.getActivePokemon(), phaseOne);
        assertEquals(trainer.getActivePokemon().getAllEnergyQuantity(), first);
        assertEquals(trainer.getDiscardHeap().get(0), basic);

        trainer.setSelectedPokemon(basicTwo);
        trainer.play(secondPhaseOne);
        trainer.unSelectPokemon();
        assertEquals(trainer.getBench().get(0), secondPhaseOne);
        assertEquals(trainer.getBench().get(0).getAllEnergyQuantity(), first);
        assertEquals(trainer.getDiscardHeap().get(1), basicTwo);

        trainer.setSelectedPokemon(phaseOne);
        trainer.play(phaseTwo);
        trainer.unSelectPokemon();
        assertEquals(trainer.getActivePokemon(), phaseTwo);
        assertEquals(trainer.getActivePokemon().getAllEnergyQuantity(), first);
        assertEquals(trainer.getDiscardHeap().get(2), phaseOne);

        trainer.setSelectedPokemon(secondPhaseOne);
        trainer.play(secondPhaseTwo);
        trainer.unSelectPokemon();
        assertEquals(trainer.getBench().get(0), secondPhaseTwo);
        assertEquals(trainer.getBench().get(0).getAllEnergyQuantity(), first);
        assertEquals(trainer.getDiscardHeap().get(3), secondPhaseOne);
    }
}


package cc3002;

import cc3002.abilities.AbilityContainer;
import cc3002.abilities.ElectricShock;
import cc3002.abilities.EnergyBurn;
import cc3002.abilities.NullAbility;
import cc3002.abilities.effects.NullEffect;
import cc3002.energies.*;
import cc3002.pokemon.IPokemon;
import cc3002.pokemon.electric.BasicElectricPokemon;
import cc3002.pokemon.fire.BasicFirePokemon;
import cc3002.pokemon.fire.PhaseOneFirePokemon;
import cc3002.pokemon.fire.PhaseTwoFirePokemon;
import cc3002.pokemon.grass.BasicGrassPokemon;
import cc3002.pokemon.psychic.BasicPsychicPokemon;
import cc3002.pokemon.water.BasicWaterPokemon;
import cc3002.trainercards.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PokemonGameControllerTest {
    private Trainer t1, t2;
    private PokemonGameController game;
    private IPokemon firstPokemon, secondPokemon;

    @Before
    public void setUp() {
        t1 = new Trainer("Trainer 1");
        t2 = new Trainer("Trainer 2");

        firstPokemon = new BasicWaterPokemon("waterPokemon",
                54, 87, new AbilityContainer(new NullAbility(), new NullAbility(),
                new NullAbility(), new NullAbility()));
        secondPokemon = new BasicPsychicPokemon("psychicPokemon",
                76, 140, new AbilityContainer(new NullAbility(), new NullAbility(),
                new NullAbility(), new NullAbility()));

        ArrayList<ICard> firstEnergies = new ArrayList<>();
        ArrayList<ICard> secondEnergies = new ArrayList<>();
        ArrayList<ICard> firstPokemon = new ArrayList<>();
        ArrayList<ICard> secondPokemon = new ArrayList<>();
        ArrayList<ICard> firstTrainer = new ArrayList<>();
        ArrayList<ICard> secondTrainer = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            firstPokemon.add(new BasicElectricPokemon("", 34, 12,
                    new AbilityContainer(new NullAbility(), new NullAbility(), new NullAbility(), new NullAbility())));
        }
        for (int i = 0; i < 20; i++) {
            secondPokemon.add(new BasicWaterPokemon("", 34, 12,
                    new AbilityContainer(new NullAbility(), new NullAbility(), new NullAbility(), new NullAbility())));
        }
        for (int i = 0; i < 20; i++) {
            firstEnergies.add(new ElectricEnergy());
        }
        for (int i = 0; i < 20; i++) {
            secondEnergies.add(new WaterEnergy());
        }
        for (int i = 0; i < 20; i++) {
            firstTrainer.add(new ObjectCard("", "", new NullEffect(), 0));
        }
        for (int i = 0; i < 20; i++) {
            secondTrainer.add(new SupportCard("", "", new NullEffect(), 0));
        }

        ArrayList<ICard> firstDeck = new ArrayList<>(firstPokemon);
        firstDeck.addAll(firstEnergies);
        firstDeck.addAll(firstTrainer);

        ArrayList<ICard> secondDeck = new ArrayList<>(secondPokemon);
        secondDeck.addAll(secondEnergies);
        secondDeck.addAll(secondTrainer);

        game = new PokemonGameController(t1, firstDeck, t2, secondDeck);
    }

    @Test
    public void gameInitializationTest() {
        game.startGame();
        assertEquals(7, game.viewActivePlayerHand().size());
        assertEquals(0, game.viewActivePlayerBench().size());
        assertNull(game.viewActivePlayerActivePokemon());
        assertFalse(game.isAnEnergyPlayedInTurn());
        game.endTurn();
        assertEquals(7, game.viewActivePlayerHand().size());
        assertEquals(0, game.viewActivePlayerBench().size());
        assertNull(game.viewActivePlayerActivePokemon());
        assertFalse(game.isAnEnergyPlayedInTurn());
    }

    @Test
    public void drawAndHandQuantityIncreaseTest() {
        game.startGame();
        assertEquals(7, game.viewActivePlayerHand().size());
        game.endTurn();
        assertEquals(7, game.viewActivePlayerHand().size());
        game.endTurn();
        assertEquals(8, game.viewActivePlayerHand().size());
        game.endTurn();
        assertEquals(8, game.viewActivePlayerHand().size());
        game.endTurn();
        assertEquals(9, game.viewActivePlayerHand().size());
        game.endTurn();
        assertEquals(9, game.viewActivePlayerHand().size());
    }

    @Test
    public void onlyOneEnergyCanBePlayedInTurn() {
        game.startGame();
        ArrayList<ICard> hand1 = new ArrayList<>();
        ArrayList<ICard> hand2 = new ArrayList<>();

        hand1.add(firstPokemon);
        hand1.add(new ElectricEnergy());
        hand1.add(new FireEnergy());
        hand1.add(new WaterEnergy());

        hand2.add(secondPokemon);
        hand2.add(new PsychicEnergy());
        hand2.add(new GrassEnergy());
        hand2.add(new FightingEnergy());

        game.testSetTrainersHands(hand1, hand2);

        assertEquals(game.viewActivePlayerHand(), hand1);
        game.playActivePlayerCard(0);
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(firstPokemon, game.viewActivePlayerActivePokemon());
        assertEquals(1, game.viewActivePlayerActivePokemon().getElectricEnergyQuantity());
        assertTrue(game.isAnEnergyPlayedInTurn());
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertNotEquals(1, game.viewActivePlayerActivePokemon().getFireEnergyQuantity());
        game.endTurn();

        assertFalse(game.isAnEnergyPlayedInTurn());
        game.playActivePlayerCard(0);
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(secondPokemon, game.viewActivePlayerActivePokemon());
        assertEquals(1, game.viewActivePlayerActivePokemon().getPsychicEnergyQuantity());
        assertTrue(game.isAnEnergyPlayedInTurn());
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertNotEquals(1, game.viewActivePlayerActivePokemon().getGrassEnergyQuantity());
    }

    @Test
    public void attackAndAbilityAndObjectAndSupportTest() {
        ArrayList<ICard> hand1 = new ArrayList<>();
        ArrayList<ICard> hand2 = new ArrayList<>();
        ICard potionCard = new Potion(6);
        IPokemon firePokemon = new BasicFirePokemon("firePokemon", 102, 120,
                new AbilityContainer(new EnergyBurn(), new ElectricShock(50), new NullAbility(), new NullAbility()));
        IPokemon grassPokemon = new BasicGrassPokemon("grassPokemon", 23, 234,
                new AbilityContainer(new EnergyBurn(), new NullAbility(), new NullAbility(), new NullAbility()));


        hand1.add(firePokemon);
        hand1.add(new ElectricEnergy());
        hand1.add(new GrassEnergy());
        hand2.add(grassPokemon);
        hand2.add(potionCard);
        hand2.add(new N());
        game.startGame();
        game.testSetTrainersHands(hand1, hand2);
        game.playActivePlayerCard(0);
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        game.endTurn();
        game.playActivePlayerCard(0);
        game.endTurn();

        game.useActivePlayerActivePokemonAbility(2);
        assertEquals(34, game.viewActivePlayerActivePokemon().getHP());

        game.endTurn();

        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(1, game.viewActivePlayerActivePokemon().getGrassEnergyQuantity());
        assertEquals(1, game.viewActivePlayerActivePokemon().getElectricEnergyQuantity());
        game.useActivePlayerActivePokemonAbility(1);
        assertEquals(2, game.viewActivePlayerActivePokemon().getFireEnergyQuantity());
        game.endTurn();

        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(94, game.viewActivePlayerActivePokemon().getHP());
        game.playActivePlayerCard(0);
        assertEquals(6, game.viewActivePlayerHand().size());
        game.endTurn();

        assertEquals(7, game.viewActivePlayerHand().size());
    }

    @Test
    public void applyFrozenCityStadiumCardAndEvolveTest() {
        ArrayList<ICard> hand1 = new ArrayList<>();
        ArrayList<ICard> hand2 = new ArrayList<>();
        ICard potionCard = new Potion(6);
        IPokemon firePokemon = new BasicFirePokemon("basicFirePokemon", 102, 10,
                new AbilityContainer(new NullAbility(), new NullAbility(), new NullAbility(), new NullAbility()));
        IPokemon fireOnePokemon = new PhaseOneFirePokemon("phaseOneFirePokemon", 103, 40,
                new AbilityContainer(new NullAbility(), new NullAbility(), new NullAbility(), new NullAbility()), 102);
        IPokemon fireTwoPokemon = new PhaseTwoFirePokemon("phaseTwoFirePokemon", 104, 500,
                new AbilityContainer(new EnergyBurn(), new ElectricShock(50), new NullAbility(), new NullAbility()), 103);

        IPokemon grassPokemon = new BasicGrassPokemon("grassPokemon", 23, 234,
                new AbilityContainer(new EnergyBurn(), new NullAbility(), new NullAbility(), new NullAbility()));
        ICard firstFrozenCity = new FrozenCity(3);
        ICard secondFrozenCity = new FrozenCity(2);

        hand1.add(firstFrozenCity);
        hand1.add(firePokemon);
        hand1.add(fireOnePokemon);
        hand1.add(fireTwoPokemon);
        hand1.add(new ElectricEnergy());
        hand1.add(new GrassEnergy());
        hand1.add(new GrassEnergy());
        hand1.add(new GrassEnergy());
        hand1.add(new GrassEnergy());
        hand1.add(potionCard);

        hand2.add(secondFrozenCity);
        hand2.add(grassPokemon);
        hand2.add(new ElectricEnergy());
        hand2.add(new GrassEnergy());
        hand2.add(new GrassEnergy());
        hand2.add(new GrassEnergy());
        hand2.add(new GrassEnergy());

        for (ICard c : hand1) {
            c.setTrainer(t1);
        }
        for (ICard c : hand2) {
            c.setTrainer(t2);
        }

        game.startGame();
        game.testSetTrainersHands(hand1, hand2);

        game.playActivePlayerCard(0);
        assertEquals(firstFrozenCity, game.viewActiveStadiumCard());
        game.playActivePlayerCard(0);
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(fireOnePokemon, game.viewActivePlayerActivePokemon());
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(fireTwoPokemon, game.viewActivePlayerActivePokemon());
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(470, game.viewActivePlayerActivePokemon().getHP());

        game.endTurn();
        game.endTurn();

        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(440, game.viewActivePlayerActivePokemon().getHP());

        game.endTurn();
        game.endTurn();

        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(410, game.viewActivePlayerActivePokemon().getHP());

        game.endTurn();
        game.endTurn();

        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(2);
        assertEquals(470, game.viewActivePlayerActivePokemon().getHP());

        game.endTurn();
        game.playActivePlayerCard(0);
        assertEquals(secondFrozenCity, game.viewActiveStadiumCard());
        game.playActivePlayerCard(0);
        game.selectPokemon(game.viewActivePlayerActivePokemon());
        game.playActivePlayerCard(0);
        assertEquals(214, game.viewActivePlayerActivePokemon().getHP());
        game.playActivePlayerCard(0);
        assertEquals(214, game.viewActivePlayerActivePokemon().getHP());


    }

}
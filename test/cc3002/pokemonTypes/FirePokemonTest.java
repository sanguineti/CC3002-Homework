package cc3002.pokemonTypes;

import cc3002.Abilities.Attack;
import cc3002.Abilities.AttackContainer;
import cc3002.energyTypes.EnergyContainer;
import cc3002.energyTypes.*;
import cc3002.pokemonTypes.fire.FirePokemon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FirePokemonTest {

    private FirePokemon tepig, flareon;
    private Attack firstAttack, secondAttack, thirdAttack, fourthAttack;
    private ElectricEnergy electric1, electric2, electric3;
    private FightingEnergy fighting1, fighting2, fighting3;
    private FireEnergy fire1, fire2, fire3;
    private GrassEnergy grass1, grass2, grass3;
    private PsychicEnergy psychic1, psychic2, psychic3;
    private WaterEnergy water1, water2, water3;

    private AttackContainer firstContainer, secondContainer, thirdContainer, fourthContainer;

    @Before
    public void setUp() {

        EnergyContainer first = new EnergyContainer(0, 1, 5, 0, 0, 0);
        EnergyContainer second = new EnergyContainer(4, 0, 2, 0, 0, 0);
        EnergyContainer third = new EnergyContainer(0, 1, 2, 1, 2, 1);
        EnergyContainer fourth = new EnergyContainer(0, 1, 1, 0, 2, 1);

        firstAttack = new Attack("Látigo Cepa", "Le pega con un látigo cepa al pókemon oponente",
                40, first);
        secondAttack = new Attack("Placaje", "Le pega un buen colpe al oponente con su cuerpo",
                30, second);
        thirdAttack = new Attack("Vuelo", "Vuela y le pega al oponente", 50, third);
        fourthAttack = new Attack("Golpe Karate", "Le pega un golpe de karate", 20, fourth);

        firstContainer = new AttackContainer(firstAttack, secondAttack, null, null);
        secondContainer = new AttackContainer(thirdAttack, null, null, null);
        thirdContainer = new AttackContainer(firstAttack, secondAttack, thirdAttack, null);
        fourthContainer = new AttackContainer(fourthAttack, thirdAttack, secondAttack, firstAttack);
        tepig = new FirePokemon("Tepig", 3, 70, thirdContainer);
        flareon = new FirePokemon("Flareon", 88, 90, fourthContainer);

        electric1 = new ElectricEnergy();
        electric2 = new ElectricEnergy();
        electric3 = new ElectricEnergy();

        fighting1 = new FightingEnergy();
        fighting2 = new FightingEnergy();
        fighting3 = new FightingEnergy();

        fire1 = new FireEnergy();
        fire2 = new FireEnergy();
        fire3 = new FireEnergy();

        grass1 = new GrassEnergy();
        grass2 = new GrassEnergy();
        grass3 = new GrassEnergy();

        psychic1 = new PsychicEnergy();
        psychic2 = new PsychicEnergy();
        psychic3 = new PsychicEnergy();

        water1 = new WaterEnergy();
        water2 = new WaterEnergy();
        water3 = new WaterEnergy();

    }

    @Test
    public void getCardName() {
        assertEquals(tepig.getCardName(), "Tepig");
        assertEquals(flareon.getCardName(), "Flareon");
    }

    @Test
    public void getID() {
        assertEquals(tepig.getID(), 3);
        assertEquals(flareon.getID(), 88);
    }

    @Test
    public void getHP() {
        assertEquals(tepig.getHP(), 70);
        assertEquals(flareon.getHP(), 90);
    }
    @Test
    public void receiveElectricEnergy() {
        tepig.receiveElectricEnergy(electric1);
        tepig.receiveElectricEnergy(electric2);
        flareon.receiveElectricEnergy(electric3);
        assertEquals(tepig.getElectricEnergyQuantity(), 2);
        assertEquals(flareon.getElectricEnergyQuantity(), 1);
    }

    @Test
    public void receiveFightingEnergy() {
        flareon.receiveFightingEnergy(fighting1);
        flareon.receiveFightingEnergy(fighting2);
        tepig.receiveFightingEnergy(fighting3);
        assertEquals(tepig.getFightingEnergyQuantity(),1);
        assertEquals(flareon.getFightingEnergyQuantity(),2);
    }

    @Test
    public void receiveFireEnergy() {
        flareon.receiveFireEnergy(fire1);
        flareon.receiveFireEnergy(fire2);
        flareon.receiveFireEnergy(fire3);
        assertEquals(flareon.getFireEnergyQuantity(), 3);
        assertEquals(tepig.getFireEnergyQuantity(), 0);


    }

    @Test
    public void receiveGrassEnergy() {
        tepig.receiveGrassEnergy(grass1);
        tepig.receiveGrassEnergy(grass2);
        tepig.receiveGrassEnergy(grass3);
        assertEquals(tepig.getGrassEnergyQuantity(), 3);
        assertEquals(flareon.getGrassEnergyQuantity(), 0);

    }

    @Test
    public void receivePsychicEnergy() {
        tepig.receivePsychicEnergy(psychic1);
        tepig.receivePsychicEnergy(psychic2);
        flareon.receivePsychicEnergy(psychic3);
        assertEquals(tepig.getPsychicEnergyQuantity(), 2);
        assertEquals(flareon.getPsychicEnergyQuantity(), 1);
    }

    @Test
    public void receiveWaterEnergy() {
        flareon.receiveWaterEnergy(water1);
        flareon.receiveWaterEnergy(water2);
        tepig.receiveWaterEnergy(water3);
        assertEquals(tepig.getWaterEnergyQuantity(),1);
        assertEquals(flareon.getWaterEnergyQuantity(),2);
    }

    @Test
    public void receiveWaterPokemonAttack() {
        flareon.receiveWaterPokemonAttack(firstAttack);
        assertEquals(flareon.getHP(), 10);
    }

    @Test
    public void attack() {
        tepig.setActiveAttack(1);
        tepig.attack(flareon);
        assertEquals(flareon.getHP(), 50);
    }

    @Test
    public void equals() {
        assertEquals(tepig, new FirePokemon("Tepig", 3, 70, thirdContainer));
    }
}
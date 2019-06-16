package cc3002.visitor.ability;

import cc3002.Trainer;
import cc3002.abilities.Ability;
import cc3002.abilities.Attack;
import cc3002.abilities.NullAbility;

public class PlayAbilityVisitor implements IAbilityVisitor {
    @Override
    public void visitAttack(Attack anAttack) {
        Trainer opponent = anAttack.getAssociatedPokemon().getTrainer().getOpponent();
        anAttack.getAssociatedPokemon().getTrainer().getActivePokemon().setActiveAttack(anAttack);
        opponent.receiveAnAttack(anAttack.getAssociatedPokemon().getTrainer());
        anAttack.getAssociatedPokemon().getTrainer().getActivePokemon().setActiveAttack(new NullAbility());
    }

    @Override
    public void visitAbility(Ability anAbility) {
        anAbility.getEffect().doEffect();
    }
}

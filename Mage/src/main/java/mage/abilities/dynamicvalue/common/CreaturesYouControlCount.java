package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum CreaturesYouControlCount implements DynamicValue {

    PLURAL(true),
    SINGULAR(false);
    private final boolean plural;

    CreaturesYouControlCount(boolean plural) {
        this.plural = plural;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURES,
                sourceAbility.getControllerId(), sourceAbility, game
        );
    }

    @Override
    public CreaturesYouControlCount copy() {
        return PLURAL;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "creature" + (plural ? "s" : "") + " you control";
    }
}

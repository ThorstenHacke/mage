
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WellOfLostDreams extends CardImpl {

    public WellOfLostDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever you gain life, you may pay {X}, where X is less than or equal to the amount of life you gained. If you do, draw X cards.
        this.addAbility(new GainLifeControllerTriggeredAbility(new WellOfLostDreamsEffect(), true, true));
    }

    private WellOfLostDreams(final WellOfLostDreams card) {
        super(card);
    }

    @Override
    public WellOfLostDreams copy() {
        return new WellOfLostDreams(this);
    }
}

class WellOfLostDreamsEffect extends OneShotEffect {

    WellOfLostDreamsEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}, where X is less than or equal to the amount of life you gained. If you do, draw X cards";
    }

    private WellOfLostDreamsEffect(final WellOfLostDreamsEffect effect) {
        super(effect);
    }

    @Override
    public WellOfLostDreamsEffect copy() {
        return new WellOfLostDreamsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = SavedGainedLifeValue.MANY.calculate(game, source, this);
            if (amount > 0) {
                int xValue = controller.announceX(0, amount, "Announce the value for {X} (pay for draw cards)", game, source, true);
                if (xValue > 0) {
                    if (new GenericManaCost(xValue).pay(source, game, source, controller.getId(), false)) {
                        game.informPlayers(controller.getLogName() + " payed {" + xValue + '}');
                        controller.drawCards(xValue, source, game);
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}


package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * As Meandering Towershell returns to the battlefield because of the delayed
 * triggered ability, you choose which opponent or opposing planeswalker it's
 * attacking. It doesn't have to attack the same opponent or opposing
 * planeswalker that it was when it was exiled.
 * --
 * If Meandering Towershell enters the battlefield attacking, it wasn't declared
 * as an attacking creature that turn. Abilities that trigger when a creature
 * attacks, including its own triggered ability, won't trigger.
 * --
 * On the turn Meandering Towershell attacks and is exiled, raid abilities will
 * see it as a creature that attacked. Conversely, on the turn Meandering
 * Towershell enters the battlefield attacking, raid abilities will not.
 * --
 * If you attack with a Meandering Towershell that you don't own, you'll control
 * it when it returns to the battlefield.
 *
 * @author LevelX2
 */
public final class MeanderingTowershell extends CardImpl {

    public MeanderingTowershell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.TURTLE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(9);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Meandering Towershell attacks, exile it.
        // Return it to the battlefield under your control tapped and attacking
        // at the beginning of the next declare attackers step on your next turn.
        this.addAbility(new AttacksTriggeredAbility(new MeanderingTowershellEffect(), false));

    }

    private MeanderingTowershell(final MeanderingTowershell card) {
        super(card);
    }

    @Override
    public MeanderingTowershell copy() {
        return new MeanderingTowershell(this);
    }
}

class MeanderingTowershellEffect extends OneShotEffect {

    MeanderingTowershellEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile it. Return it to the battlefield under your control tapped and attacking at the beginning of the declare attackers step on your next turn";
    }

    private MeanderingTowershellEffect(final MeanderingTowershellEffect effect) {
        super(effect);
    }

    @Override
    public MeanderingTowershellEffect copy() {
        return new MeanderingTowershellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            controller.moveCardToExileWithInfo(sourcePermanent, null, "", source, game, Zone.BATTLEFIELD, true);
            game.addDelayedTriggeredAbility(new AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility(new MeanderingTowershellReturnEffect(), GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE), source);
            return true;
        }
        return false;
    }
}

class MeanderingTowershellReturnEffect extends OneShotEffect {

    MeanderingTowershellReturnEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private MeanderingTowershellReturnEffect(final MeanderingTowershellReturnEffect effect) {
        super(effect);
    }

    @Override
    public MeanderingTowershellReturnEffect copy() {
        return new MeanderingTowershellReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.EXILED) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                game.getCombat().addAttackingCreature(card.getId(), game);
                return true;
            }
        }
        return false;
    }
}

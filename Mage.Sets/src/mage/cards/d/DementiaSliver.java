package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DementiaSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("All Slivers");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public DementiaSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Slivers have "{T}: Name a card. Target opponent reveals a card at random from their hand. If it's the named card, that player discards it. Activate this ability only during your turn."
        Ability gainedAbility = new ActivateIfConditionActivatedAbility(
                new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL),
                new TapSourceCost(), MyTurnCondition.instance
        );
        gainedAbility.addEffect(new DementiaSliverEffect());
        gainedAbility.addTarget(new TargetOpponent());
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAllEffect(
                        gainedAbility, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS,
                        "All Slivers have \"{T}: Choose a card name. "
                                + "Target opponent reveals a card at random from their hand."
                                + " If that card has the chosen name, that player discards it."
                                + " Activate only during your turn.\""
                )
        ));
    }

    private DementiaSliver(final DementiaSliver card) {
        super(card);
    }

    @Override
    public DementiaSliver copy() {
        return new DementiaSliver(this);
    }
}

class DementiaSliverEffect extends OneShotEffect {

    DementiaSliverEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from their hand. If that card has the chosen name, that player discards it";
    }

    private DementiaSliverEffect(final DementiaSliverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (opponent != null && sourceObject != null && cardName != null && !cardName.isEmpty()) {
            if (!opponent.getHand().isEmpty()) {
                Cards revealed = new CardsImpl();
                Card card = opponent.getHand().getRandom(game);
                if (card != null) {
                    revealed.add(card);
                    opponent.revealCards(sourceObject.getName(), revealed, game);
                    if (CardUtil.haveSameNames(card, cardName, game)) {
                        opponent.discard(card, false, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DementiaSliverEffect copy() {
        return new DementiaSliverEffect(this);
    }

}

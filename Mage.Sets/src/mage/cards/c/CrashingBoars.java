package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class CrashingBoars extends CardImpl {

    public CrashingBoars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Crashing Boars attacks, defending player chooses an untapped creature they control. That creature block Crashing Boars this turn if able.
        this.addAbility(new AttacksTriggeredAbility(new CrashingBoarsEffect(), false, "", SetTargetPointer.PLAYER));
    }

    private CrashingBoars(final CrashingBoars card) {
        super(card);
    }

    @Override
    public CrashingBoars copy() {
        return new CrashingBoars(this);
    }
}

class CrashingBoarsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    CrashingBoarsEffect() {
        super(Outcome.Benefit);
        this.staticText = "defending player chooses an untapped creature they control. That creature blocks {this} this turn if able";
    }

    private CrashingBoarsEffect(final CrashingBoarsEffect effect) {
        super(effect);
    }

    @Override
    public CrashingBoarsEffect copy() {
        return new CrashingBoarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defendingPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (defendingPlayer == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE,
                defendingPlayer.getId(), source, game, 1
        )) {
            return false;
        }
        Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE);
        target.withNotTarget(true);
        defendingPlayer.choose(Outcome.Neutral, target, source, game);
        RequirementEffect effect = new MustBeBlockedByTargetSourceEffect();
        effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
        game.addEffect(effect, source);
        return true;
    }
}

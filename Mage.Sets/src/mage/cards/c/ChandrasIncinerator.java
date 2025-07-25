package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandrasIncinerator extends CardImpl {

    public ChandrasIncinerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell costs {X} less to cast, where X is the total amount of noncombat damage dealt to your opponents this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new ChandrasIncineratorCostReductionEffect()
        ), new ChandrasIncineratorWatcher());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a source you control deals noncombat damage to an opponent, Chandra's Incinerator deals that much damage to target creature or planeswalker that player controls.
        Ability ability = new SourceDealsNoncombatDamageToOpponentTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH), SetTargetPointer.PLAYER);
        ability.addTarget(new TargetPermanent(new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker that player controls")));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private ChandrasIncinerator(final ChandrasIncinerator card) {
        super(card);
    }

    @Override
    public ChandrasIncinerator copy() {
        return new ChandrasIncinerator(this);
    }
}

class ChandrasIncineratorCostReductionEffect extends CostModificationEffectImpl {

    ChandrasIncineratorCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is the total amount of noncombat damage dealt to your opponents this turn";
    }

    private ChandrasIncineratorCostReductionEffect(final ChandrasIncineratorCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        ChandrasIncineratorWatcher watcher = game.getState().getWatcher(ChandrasIncineratorWatcher.class);
        if (watcher == null) {
            return true;
        }
        int reductionAmount = watcher.getDamage(source.getControllerId());
        CardUtil.reduceCost(abilityToModify, Math.max(0, reductionAmount));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public ChandrasIncineratorCostReductionEffect copy() {
        return new ChandrasIncineratorCostReductionEffect(this);
    }
}

class ChandrasIncineratorWatcher extends Watcher {

    private final Map<UUID, Integer> damageMap = new HashMap<>();

    ChandrasIncineratorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || ((DamagedPlayerEvent) event).isCombatDamage()) {
            return;
        }
        for (UUID playerId : game.getOpponents(event.getTargetId())) {
            damageMap.compute(playerId, ((u, i) -> i == null ? event.getAmount() : Integer.sum(i, event.getAmount())));
        }
    }

    @Override
    public void reset() {
        damageMap.clear();
        super.reset();
    }

    int getDamage(UUID playerId) {
        return damageMap.getOrDefault(playerId, 0);
    }
}

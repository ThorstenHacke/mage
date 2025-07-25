package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 *
 * @author LevelX2
 */
public final class WeaverOfLightning extends CardImpl {

    public WeaverOfLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Whenever you cast an instant or sorcery spell, Weaver of Lightning deals 1 damage to target creature an opponent controls.
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(1), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private WeaverOfLightning(final WeaverOfLightning card) {
        super(card);
    }

    @Override
    public WeaverOfLightning copy() {
        return new WeaverOfLightning(this);
    }
}

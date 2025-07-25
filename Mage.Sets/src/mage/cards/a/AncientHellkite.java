package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AncientHellkite extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature defending player controls");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public AncientHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Ancient Hellkite deals 1 damage to target creature defending player controls. Activate this ability only if Ancient Hellkite is attacking.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DamageTargetEffect(1), new ManaCostsImpl<>("{R}"), SourceAttackingCondition.instance
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AncientHellkite(final AncientHellkite card) {
        super(card);
    }

    @Override
    public AncientHellkite copy() {
        return new AncientHellkite(this);
    }

}


package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SolemnRecruit extends CardImpl {

    public SolemnRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, put a +1/+1 counter on Solemn Recruit.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withInterveningIf(RevoltCondition.instance)
                .setAbilityWord(AbilityWord.REVOLT)
                .addHint(RevoltCondition.getHint()), new RevoltWatcher());
    }

    private SolemnRecruit(final SolemnRecruit card) {
        super(card);
    }

    @Override
    public SolemnRecruit copy() {
        return new SolemnRecruit(this);
    }
}

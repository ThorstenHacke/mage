package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KefkaRulerOfRuin extends CardImpl {

    public KefkaRulerOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);
        this.nightCard = true;
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent loses life during your turn, you draw that many cards.
        this.addAbility(new ConditionalTriggeredAbility(new LoseLifeTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedLifeLossValue.MANY),
                TargetController.OPPONENT, false, false
        ), MyTurnCondition.instance, "Whenever an opponent loses life during your turn, you draw that many cards."));
    }

    private KefkaRulerOfRuin(final KefkaRulerOfRuin card) {
        super(card);
    }

    @Override
    public KefkaRulerOfRuin copy() {
        return new KefkaRulerOfRuin(this);
    }
}

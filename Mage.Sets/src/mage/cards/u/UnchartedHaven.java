package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnchartedHaven extends CardImpl {

    public UnchartedHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Uncharted Haven enters the battlefield tapped.
        // As Uncharted Haven enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldTappedAsItEntersChooseColorAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private UnchartedHaven(final UnchartedHaven card) {
        super(card);
    }

    @Override
    public UnchartedHaven copy() {
        return new UnchartedHaven(this);
    }
}

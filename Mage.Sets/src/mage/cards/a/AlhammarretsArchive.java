package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AlhammarretsArchive extends CardImpl {

    public AlhammarretsArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));

        // If you draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new AlhammarretsArchiveReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    private AlhammarretsArchive(final AlhammarretsArchive card) {
        super(card);
    }

    @Override
    public AlhammarretsArchive copy() {
        return new AlhammarretsArchive(this);
    }
}

class AlhammarretsArchiveReplacementEffect extends ReplacementEffectImpl {

    AlhammarretsArchiveReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead";
    }

    private AlhammarretsArchiveReplacementEffect(final AlhammarretsArchiveReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretsArchiveReplacementEffect copy() {
        return new AlhammarretsArchiveReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        if (!game.isActivePlayer(event.getPlayerId())
                || game.getPhase().getStep().getType() != PhaseStep.DRAW) {
            return true;
        }
        CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0;
    }
}

package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/phtr
 * @author TheElk801
 */
public final class The2016HeroesOfTheRealm extends ExpansionSet {

    private static final The2016HeroesOfTheRealm instance = new The2016HeroesOfTheRealm();

    public static The2016HeroesOfTheRealm getInstance() {
        return instance;
    }

    private The2016HeroesOfTheRealm() {
        super("2016 Heroes of the Realm", "PHTR", ExpansionSet.buildDate(2017, 9, 20), SetType.JOKE_SET);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Gremlin Wrangler", 1, Rarity.MYTHIC, mage.cards.c.ChandraGremlinWrangler.class));
        // Cards not implemented
        //cards.add(new SetCardInfo("Dungeon Master", 2, Rarity.MYTHIC, mage.cards.d.DungeonMaster.class));
        //cards.add(new SetCardInfo("Nira, Hellkite Duelist", 3, Rarity.MYTHIC, mage.cards.n.NiraHellkiteDuelist.class));
    }
}

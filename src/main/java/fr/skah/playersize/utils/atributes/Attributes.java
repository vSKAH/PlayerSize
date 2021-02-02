package fr.skah.playersize.utils.atributes;

import fr.skah.playersize.PlayerSizeMod;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class Attributes {
    public static final IAttribute ENTITY_HEIGHT = new RangedAttribute(null, PlayerSizeMod.MODID + ".entityHeight",
            0.55F, Float.MIN_VALUE, Float.MAX_VALUE).setDescription("Entity Height").setShouldWatch(true);

    public static final IAttribute ENTITY_WIDTH = new RangedAttribute(null, PlayerSizeMod.MODID + ".entityWidth",
            0.55F, Float.MIN_VALUE, Float.MAX_VALUE).setDescription("Entity Width").setShouldWatch(true);
}

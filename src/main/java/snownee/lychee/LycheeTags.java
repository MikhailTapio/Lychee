package snownee.lychee;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;

public final class LycheeTags {

	public static void init() {
	}

	public static final Named<Item> FIRE_IMMUNE = ItemTags.createOptional(new ResourceLocation(Lychee.ID, "fire_immune"));

}

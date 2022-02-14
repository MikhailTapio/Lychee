package snownee.lychee.item_exploding;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import snownee.lychee.RecipeSerializers;
import snownee.lychee.RecipeTypes;
import snownee.lychee.core.recipe.ItemShapelessRecipe;
import snownee.lychee.core.recipe.LycheeRecipe;
import snownee.lychee.core.recipe.type.LycheeRecipeType;

public class ItemExplodingRecipe extends ItemShapelessRecipe {

	public ItemExplodingRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public LycheeRecipe.Serializer<?> getSerializer() {
		return RecipeSerializers.ITEM_EXPLODING;
	}

	@Override
	public LycheeRecipeType<?, ?> getType() {
		return RecipeTypes.ITEM_EXPLODING;
	}

	public static void on(Level level, Vec3 position, List<Entity> list1) {
		if (level.isClientSide) {
			return;
		}
		Stream<ItemEntity> itemEntities = list1.stream().filter($ -> {
			return $ instanceof ItemEntity;
		}).map(ItemEntity.class::cast);
		RecipeTypes.ITEM_EXPLODING.process(level, itemEntities, $ -> {
			$.withParameter(LootContextParams.ORIGIN, position);
		});
	}

}

package snownee.lychee.item_burning;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import snownee.lychee.LycheeLootContextParamSets;
import snownee.lychee.RecipeSerializers;
import snownee.lychee.RecipeTypes;
import snownee.lychee.core.LycheeContext;
import snownee.lychee.core.LycheeRecipe;

public class ItemBurningRecipe extends LycheeRecipe<LycheeContext> {

	protected Ingredient input;

	public ItemBurningRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(LycheeContext ctx, Level pLevel) {
		ItemStack stack = ((ItemEntity) ctx.getParam(LootContextParams.THIS_ENTITY)).getItem();
		return checkConditions(this, ctx, 1) > 0 && input.test(stack);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializers.ITEM_BURNING;
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypes.ITEM_BURNING;
	}

	public Ingredient getInput() {
		return input;
	}

	public static class Serializer extends LycheeRecipe.Serializer<ItemBurningRecipe> {

		public Serializer() {
			super(ItemBurningRecipe::new);
		}

		@Override
		public void fromJson(ItemBurningRecipe pRecipe, JsonObject pSerializedRecipe) {
			pRecipe.input = Ingredient.fromJson(pSerializedRecipe.get("item_in"));
		}

		@Override
		public void fromNetwork(ItemBurningRecipe pRecipe, FriendlyByteBuf pBuffer) {
			pRecipe.input = Ingredient.fromNetwork(pBuffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, ItemBurningRecipe pRecipe) {
			super.toNetwork(pBuffer, pRecipe);
			pRecipe.input.toNetwork(pBuffer);
		}

	}

	public static void on(ItemEntity entity) {
		LootContext.Builder builder = new LootContext.Builder((ServerLevel) entity.level);
		builder.withParameter(LootContextParams.ORIGIN, entity.position());
		builder.withParameter(LootContextParams.THIS_ENTITY, entity);
		LycheeContext ctx = new LycheeContext(builder.create(LycheeLootContextParamSets.ITEM_BURNING));
		entity.level.getRecipeManager().getRecipeFor(RecipeTypes.ITEM_BURNING, ctx, entity.level).ifPresent($ -> {
			int times = $.willBatchRun ? entity.getItem().getCount() : 1;
			entity.getItem().shrink(times);
			$.applyPostActions(ctx, times);
		});
	}

}

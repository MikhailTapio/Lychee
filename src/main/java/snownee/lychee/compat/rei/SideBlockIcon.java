package snownee.lychee.compat.rei;

import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.world.level.block.state.BlockState;
import snownee.lychee.client.gui.GuiGameElement;
import snownee.lychee.client.gui.ILightingSettings;
import snownee.lychee.client.gui.RenderElement;
import snownee.lychee.client.gui.ScreenElement;

public class SideBlockIcon extends RenderElement {

	private final ScreenElement mainIcon;
	private final Supplier<BlockState> blockProvider;

	public SideBlockIcon(ScreenElement mainIcon, Supplier<BlockState> blockProvider) {
		this.mainIcon = mainIcon;
		this.blockProvider = blockProvider;
	}

	@Override
	public void render(PoseStack ms) {
		ms.pushPose();
		ms.translate(x, y, z);
		//		ms.scale(.75F, .75F, .75F);
		ms.scale(.625F, .625F, .625F);
		mainIcon.render(ms, 0, 0);
		ms.popPose();
		/* off */
		GuiGameElement.of(blockProvider.get())
				.lighting(ILightingSettings.DEFAULT_JEI)
				.scale(7)
				.rotateBlock(12.5f, 22.5f, 0)
				.at(x + 6, y + 16)
				.render(ms);
		/* on */
	}

}

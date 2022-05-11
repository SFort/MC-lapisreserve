package sf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantScreen extends AbstractContainerMenu {

	protected EnchantScreen(@Nullable MenuType<?> p_38851_, int p_38852_) {
		super(p_38851_, p_38852_);
	}

	@Inject(method= "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at=@At("RETURN"))
	public void open(int p_39457_, Inventory playerInventory, ContainerLevelAccess p_39459_, CallbackInfo ci) {
		slots.get(1).set(((PlayerInterface)playerInventory).getLapisreserve());
	}
	@Inject(method="removed", at=@At("HEAD"))
	public void close(Player player, CallbackInfo ci) {

		((PlayerInterface)player.getInventory()).setLapisreserve(slots.get(1).getItem());
		slots.get(1).set(ItemStack.EMPTY);
	}
}

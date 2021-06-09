package sf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(net.minecraft.inventory.container.EnchantmentContainer.class)
public abstract class EnchantScreen extends Container {
	@Shadow @Final @Mutable
	private final IWorldPosCallable worldPosCallable;
	protected EnchantScreen(ContainerType<?> type, int syncId, Inventory inventory, IWorldPosCallable context) {
		super(type, syncId);
		this.worldPosCallable = context;
	}
	@Inject(method= "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/util/IWorldPosCallable;)V", at=@At("RETURN"))
	public void open(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable, CallbackInfo info) {
		inventorySlots.get(1).putStack(((PlayerInterface)playerInventory).getLapisreserve());
	}
	@Inject(method="onContainerClosed", at=@At("HEAD"), cancellable = true)
	public void close(PlayerEntity player, CallbackInfo info) {
		((PlayerInterface)player.inventory).setLapisreserve(inventorySlots.get(1).getStack());
		inventorySlots.get(1).putStack(ItemStack.EMPTY);
	}
}

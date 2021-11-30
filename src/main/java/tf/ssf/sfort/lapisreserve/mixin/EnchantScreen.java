package tf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantScreen extends ScreenHandler{
	@Shadow @Final @Mutable
	private final ScreenHandlerContext context;
	protected EnchantScreen(ScreenHandlerType<?> type, int syncId, Inventory inventory, ScreenHandlerContext context) {
		super(type, syncId);
		this.context = context;
	}
	@Inject(method="<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at=@At("RETURN"))
	public void open(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo info) {
		slots.get(1).setStack(((PlayerInterface)playerInventory).getLapisreserve());
	}
	@Inject(method="close(Lnet/minecraft/entity/player/PlayerEntity;)V", at=@At("HEAD"), cancellable = true)
	public void close(PlayerEntity player, CallbackInfo info) {
		((PlayerInterface)player.getInventory()).setLapisreserve(slots.get(1).getStack());
		slots.get(1).setStack(ItemStack.EMPTY);
	}
}

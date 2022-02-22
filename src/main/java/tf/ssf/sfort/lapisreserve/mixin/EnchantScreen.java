package tf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(ContainerEnchantment.class)
public abstract class EnchantScreen extends Container {
	@Shadow public IInventory tableInventory;

	@Inject(method="<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at=@At("RETURN"))
	public void open(InventoryPlayer p_i45798_1_, World p_i45798_2_, BlockPos p_i45798_3_, CallbackInfo ci) {
		tableInventory.setInventorySlotContents(1, ((PlayerInterface)p_i45798_1_).getLapisreserve());
	}
	@Inject(method= "onContainerClosed(Lnet/minecraft/entity/player/EntityPlayer;)V", at=@At("HEAD"))
	public void close(EntityPlayer player, CallbackInfo info) {
		((PlayerInterface)player.inventory).setLapisreserve(tableInventory.getStackInSlot(1));
		tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
	}
}

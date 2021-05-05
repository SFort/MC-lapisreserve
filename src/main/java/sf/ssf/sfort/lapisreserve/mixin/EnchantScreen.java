package sf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IWorldPosCallable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.inventory.container.EnchantmentContainer.class)
public abstract class EnchantScreen extends Container {
	@Shadow
	private final IWorldPosCallable worldPosCallable;
	protected EnchantScreen(ContainerType<?> type, int syncId, Inventory inventory, IWorldPosCallable context) {
		super(type, syncId);
		this.worldPosCallable = context;
	}
	@Inject(method="<init>*", at=@At("RETURN"))
	public void open(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable, CallbackInfo info) {
		ListNBT tags = playerInventory.write(new ListNBT());
		for(int i = 0; i < tags.size(); ++i) {
			CompoundNBT compoundTag = tags.getCompound(i);
			if (compoundTag.contains("LapisReserve")){
				ItemStack lapis =ItemStack.read(compoundTag);
				this.inventorySlots.get(1).putStack(lapis);
				break;
			}
		}
	}
	@Inject(method="onContainerClosed", at=@At("HEAD"), cancellable = true)
	public void close(PlayerEntity player, CallbackInfo info) {
		ListNBT tag= player.inventory.write(new ListNBT());
		CompoundNBT compoundTag = new CompoundNBT();
		compoundTag.putByte("LapisReserve", (byte)0);
		this.inventorySlots.get(1).getStack().write(compoundTag);
		for(int i = 0; i < tag.size(); ++i) {
			if (tag.getCompound(i).contains("LapisReserve")) {
				tag.set(i,compoundTag);
				break;
			}
		}
		player.inventory.read(tag);
		super.onContainerClosed(player);
		this.worldPosCallable.consume((world, blockPos) -> {
			player.inventory.placeItemBackInInventory(world, this.inventorySlots.get(0).getStack());;
		});
		info.cancel();
	}
}

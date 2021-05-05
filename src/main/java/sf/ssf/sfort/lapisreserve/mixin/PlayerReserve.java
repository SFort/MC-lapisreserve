package sf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerReserve {
	@Dynamic
	public ItemStack lapisreserve = ItemStack.EMPTY;

	@Inject(method = "write",at=@At("HEAD"))
	public void serialize(ListNBT tag, CallbackInfoReturnable info) {
		CompoundNBT compoundTag = new CompoundNBT();
		compoundTag.putByte("LapisReserve", (byte)0);
		lapisreserve.write(compoundTag);
		tag.add(compoundTag);
	}
	@Inject(method = "read",at=@At("HEAD"),cancellable = true)
	public void deserialize(ListNBT tag, CallbackInfo info) {
		for(int i = 0; i < tag.size(); ++i) {
			CompoundNBT compoundTag = tag.getCompound(i);
			if (compoundTag.contains("LapisReserve")){
				lapisreserve = ItemStack.read(compoundTag);
				tag.remove(i);
				break;
			}
		}
	}
}

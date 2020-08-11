package sf.ssf.sfort.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

	@Inject(method = "serialize",at=@At("HEAD"))
	public void serialize(ListTag tag, CallbackInfoReturnable info) {
		CompoundTag compoundTag = new CompoundTag();
		compoundTag.putByte("LapisReserve", (byte)0);
		lapisreserve.toTag(compoundTag);
		tag.add(compoundTag);
	}
	@Inject(method = "deserialize",at=@At("HEAD"),cancellable = true)
	public void deserialize(ListTag tag, CallbackInfo info) {
		for(int i = 0; i < tag.size(); ++i) {
			CompoundTag compoundTag = tag.getCompound(i);
			if (compoundTag.contains("LapisReserve")){
				lapisreserve = ItemStack.fromTag(compoundTag);
				tag.remove(i);
				break;
			}
		}
	}
}

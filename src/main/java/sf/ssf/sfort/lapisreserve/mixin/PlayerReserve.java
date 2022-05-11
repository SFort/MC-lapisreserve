package sf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(Inventory.class)
public class PlayerReserve implements PlayerInterface {
	@Dynamic
	public ItemStack lapisreserve = ItemStack.EMPTY;

	@Inject(method = "save",at=@At("HEAD"))
	public void serialize(ListTag tag, CallbackInfoReturnable<ListTag> cir) {
		CompoundTag compoundTag = new CompoundTag();
		compoundTag.putByte("LapisReserve", (byte)0);
		lapisreserve.save(compoundTag);
		tag.add(compoundTag);
	}
	@Inject(method = "load",at=@At("HEAD"))
	public void deserialize(ListTag tag, CallbackInfo ci) {
		for(int i = 0; i < tag.size(); ++i) {
			CompoundTag compoundTag = tag.getCompound(i);
			if (compoundTag.contains("LapisReserve")){
				lapisreserve = ItemStack.of(compoundTag);
				tag.remove(i);
				break;
			}
		}
	}
	@Override public ItemStack getLapisreserve(){ return lapisreserve; }
	@Override public void setLapisreserve(ItemStack stack) { lapisreserve = stack; }
}

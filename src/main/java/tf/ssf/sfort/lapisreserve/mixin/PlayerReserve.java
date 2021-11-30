package tf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(PlayerInventory.class)
public class PlayerReserve implements PlayerInterface {
	@Dynamic
	public ItemStack lapisreserve = ItemStack.EMPTY;

	@Inject(method = "writeNbt(Lnet/minecraft/nbt/NbtList;)Lnet/minecraft/nbt/NbtList;",at=@At("HEAD"))
	public void serialize(NbtList tag, CallbackInfoReturnable<NbtList> info) {
		NbtCompound compoundTag = new NbtCompound();
		compoundTag.putByte("LapisReserve", (byte)0);
		lapisreserve.writeNbt(compoundTag);
		tag.add(compoundTag);
	}
	@Inject(method = "readNbt(Lnet/minecraft/nbt/NbtList;)V",at=@At("HEAD"),cancellable = true)
	public void deserialize(NbtList tag, CallbackInfo info) {
		for(int i = 0; i < tag.size(); ++i) {
			NbtCompound compoundTag = tag.getCompound(i);
			if (compoundTag.contains("LapisReserve")){
				lapisreserve = ItemStack.fromNbt(compoundTag);
				tag.remove(i);
				break;
			}
		}
	}
	@Override public ItemStack getLapisreserve(){ return lapisreserve; }
	@Override public void setLapisreserve(ItemStack stack) { lapisreserve = stack; }
}

package tf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(InventoryPlayer.class)
public class PlayerReserve implements PlayerInterface {
	@Dynamic
	public ItemStack lapisreserve = ItemStack.EMPTY;

	@Inject(method = "writeToNBT",at=@At("HEAD"))
	public void serialize(NBTTagList tag, CallbackInfoReturnable<NBTTagList> cir) {
		NBTTagCompound compoundTag = new NBTTagCompound();
		compoundTag.setByte("LapisReserve", (byte)0);
		lapisreserve.writeToNBT(compoundTag);
		tag.appendTag(compoundTag);
	}
	@Inject(method = "readFromNBT",at=@At("HEAD"),cancellable = true)
	public void deserialize(NBTTagList tag, CallbackInfo ci) {
		for(int i = 0; i < tag.tagCount(); ++i) {
			NBTTagCompound compoundTag = tag.getCompoundTagAt(i);
			if (compoundTag.hasKey("LapisReserve")){
				lapisreserve = new ItemStack(compoundTag);
				tag.removeTag(i);
				break;
			}
		}
	}
	@Override public ItemStack getLapisreserve(){ return lapisreserve; }
	@Override public void setLapisreserve(ItemStack stack) { lapisreserve = stack; }
}

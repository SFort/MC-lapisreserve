package tf.ssf.sfort.lapisreserve.mixin;

import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(EnchantingTableBlockEntity.class)
public class EnchantTable implements PlayerInterface {
	ItemStack stack = ItemStack.EMPTY;

	@Inject(method = "writeNbt", at=@At("HEAD"))
	public void writeNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.put("lapisreserve$lapis", stack.writeNbt(new NbtCompound()));

	}

	@Inject(method = "readNbt", at=@At("HEAD"))
	public void readNbt(NbtCompound nbt, CallbackInfo ci) {
		stack = ItemStack.fromNbt(nbt.getCompound("lapisreserve$lapis"));
	}
	@Override
	public ItemStack getLapisreserve() {
		return stack;
	}

	@Override
	public void setLapisreserve(ItemStack stack) {
		this.stack = stack;
	}
}

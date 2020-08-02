package sf.ssf.sfort.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//TODO This is some of the hackiest shit i've ever coded, access to lapisreserve in playerInv needs to be redone
@Mixin(EnchantmentScreenHandler.class)
public class EnchantScreen extends ScreenHandler{
	@Shadow
	private final ScreenHandlerContext context;
	protected EnchantScreen(ScreenHandlerType<?> type, int syncId, Inventory inventory, ScreenHandlerContext context) {
		super(type, syncId);
		this.context = context;
	}
	@Inject(method = "<init>*",at=@At("RETURN"))
	public void open(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo info) {
		ListTag tags = playerInventory.serialize(new ListTag());
		for(int i = 0; i < tags.size(); ++i) {
			CompoundTag compoundTag = tags.getCompound(i);
			if (compoundTag.contains("LapisReserve")){
				ItemStack lapis =ItemStack.fromTag(compoundTag);
				//this.inventory.setStack(1,lapis);
				this.slots.get(1).setStack(lapis);
			}
		}
	}
	@Inject(method="close", at=@At("HEAD"), cancellable = true)
	public void close(PlayerEntity player, CallbackInfo info) {
		ListTag tag= new ListTag();
		CompoundTag compoundTag = new CompoundTag();
		compoundTag.putByte("LapisReserveWRITE", (byte)0);
		this.slots.get(1).getStack().toTag(compoundTag);
		tag.add(compoundTag);
		player.inventory.deserialize(tag);
		super.close(player);
		info.cancel();
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(this.context, player, Blocks.ENCHANTING_TABLE);
	}
}

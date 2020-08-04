package sf.ssf.sfort.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerReserve extends PlayerEntity {
	public ServerPlayerReserve(World world, BlockPos blockPos, GameProfile gameProfile) {
		super(world, blockPos, gameProfile);
	}
	//TODO i really need a better way todo this
	@Inject(method = "copyFrom",at=@At("TAIL"))
	public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
		if (!alive){
			ListTag tags = oldPlayer.inventory.serialize(new ListTag());
			for(int i = 0; i < tags.size(); ++i) {
				CompoundTag compoundTag = tags.getCompound(i);
				if (compoundTag.contains("LapisReserve")){
					ListTag tag= new ListTag();
					tag.add(compoundTag);
					this.inventory.deserialize(tag);
					break;
				}
			}
		}
	}

	@Surrogate
	public boolean isSpectator() {
		return false;
	}

	@Surrogate
	public boolean isCreative() {
		return false;
	}
}

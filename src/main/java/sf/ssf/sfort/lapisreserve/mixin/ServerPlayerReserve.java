package sf.ssf.sfort.lapisreserve.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerReserve extends PlayerEntity {
	public ServerPlayerReserve(World world, BlockPos blockPos, float i, GameProfile gameProfile) {
		super(world, blockPos, i, gameProfile);
	}
	@Inject(method = "copyFrom",at=@At("TAIL"))
	public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
		((PlayerInterface)inventory).setLapisreserve(((PlayerInterface)oldPlayer.inventory).getLapisreserve());
	}
}

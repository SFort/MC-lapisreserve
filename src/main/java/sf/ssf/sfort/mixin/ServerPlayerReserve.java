package sf.ssf.sfort.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sf.ssf.sfort.PlayerInterface;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerReserve extends PlayerEntity {
	public ServerPlayerReserve(MinecraftServer server, ServerWorld world, GameProfile profile) {
		super(world, world.getSpawnPos(), world.getSpawnAngle(), profile);
	}
	@Inject(method = "copyFrom",at=@At("TAIL"))
	public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
		((PlayerInterface)getInventory()).setLapisreserve(((PlayerInterface)oldPlayer.getInventory()).getLapisreserve());
	}
}

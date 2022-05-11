package sf.ssf.sfort.lapisreserve.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerReserve extends Player {
	public ServerPlayerReserve(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
		super(p_36114_, p_36115_, p_36116_, p_36117_);
	}

	@Inject(method = "restoreFrom",at=@At("TAIL"))
	public void copyFrom(ServerPlayer oldPlayer, boolean p_9017_, CallbackInfo ci) {
		((PlayerInterface)getInventory()).setLapisreserve(((PlayerInterface)oldPlayer.getInventory()).getLapisreserve());
	}
}

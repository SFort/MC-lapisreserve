package tf.ssf.sfort.lapisreserve.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tf.ssf.sfort.lapisreserve.PlayerInterface;

@Mixin(EntityPlayerMP.class)
public abstract class ServerPlayerReserve extends EntityPlayer {
	public ServerPlayerReserve(MinecraftServer p_i45285_1_, WorldServer p_i45285_2_, GameProfile p_i45285_3_, PlayerInteractionManager p_i45285_4_) {
		super(p_i45285_2_, p_i45285_3_);
	}
	@Inject(method = "copyFrom",at=@At("TAIL"))
	public void copyFrom(EntityPlayerMP oldPlayer, boolean p_copyFrom_2_, CallbackInfo ci) {
		((PlayerInterface)this.inventory).setLapisreserve(((PlayerInterface)oldPlayer.inventory).getLapisreserve());
	}
}

package sf.ssf.sfort.lapisreserve;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(LapisReserve.MODID)
public class LapisReserve {
    public static final String MODID = "lapisreserve";


    public LapisReserve() {
        MinecraftForge.EVENT_BUS.register(this);
    }

}

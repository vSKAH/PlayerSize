package fr.skah.playersize.proxy;

import fr.skah.playersize.PlayerSizeMod;
import fr.skah.playersize.capabilities.Capabilities;
import fr.skah.playersize.capabilities.CapabilitiesHandler;
import fr.skah.playersize.handler.PlayerHandler;
import fr.skah.playersize.network.PacketCSAttributes;
import fr.skah.playersize.utils.AttachAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class CommonProxy {

    public static SimpleNetworkWrapper network;

    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }

    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(PlayerSizeMod.MODID);
        network.registerMessage(PacketCSAttributes.ServerHandler.class, PacketCSAttributes.class, 0, Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        Capabilities.init();
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CapabilitiesHandler());
        MinecraftForge.EVENT_BUS.register(new AttachAttributes());
    }

    public IThreadListener getThreadListener(final MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player.getServer();
        } else {
            throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
        }
    }

    @Nullable
    public EntityLivingBase getEntityLivingBase(MessageContext context, int entityID) {
        if (context.side.isServer()) {
            final Entity entity = context.getServerHandler().player.world.getEntityByID(entityID);
            return entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
        }
        throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
    }

    static class WrongSideException extends RuntimeException {
        public WrongSideException(final String message) {
            super(message);
        }

        public WrongSideException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}

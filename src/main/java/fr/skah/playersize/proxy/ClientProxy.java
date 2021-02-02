package fr.skah.playersize.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {

    private static final KeyBinding SIZE_KEYBINDING = new KeyBinding("Réglage de la taille", Keyboard.KEY_P, "key.categories.gameplay");

    public static KeyBinding getSizeKeybinding() {
        return SIZE_KEYBINDING;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ClientRegistry.registerKeyBinding(SIZE_KEYBINDING);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    @Nullable
    public EntityLivingBase getEntityLivingBase(MessageContext context, int entityID)
    {
        final EntityPlayer player = context.side.isClient() ? Minecraft.getMinecraft().player : context.getServerHandler().player;
        final Entity entity = player.world.getEntityByID(entityID);
        return entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
    }

    @Override
    public IThreadListener getThreadListener(final MessageContext context)
    {
        if (context.side.isClient())
        {
            return Minecraft.getMinecraft();
        }
        else
        {
            return context.getServerHandler().player.getServer();
        }
    }

}

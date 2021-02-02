package fr.skah.playersize.handler;

import fr.skah.playersize.capabilities.sizecapa.ISizeCap;
import fr.skah.playersize.capabilities.sizecapa.SizeCapPro;
import fr.skah.playersize.gui.GuiPlayerSize;
import fr.skah.playersize.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class PlayerHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onFOVChange(FOVUpdateEvent event) {
        if (event.getEntity() != null) {
            EntityPlayer player = event.getEntity();
            GameSettings settings = Minecraft.getMinecraft().gameSettings;
            PotionEffect speed = player.getActivePotionEffect(MobEffects.SPEED);
            float fov = settings.fovSetting / settings.fovSetting;

            if (player.isSprinting()) event.setNewfov(speed != null ? fov + ((0.1F * (speed.getAmplifier() + 1)) + 0.15F) : fov + 0.1F);
            else event.setNewfov(speed != null ? fov + (0.1F * (speed.getAmplifier() + 1)) : fov);

        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onCameraSetup(CameraSetup event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        float scale = player.height / 1.8F;

        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 1)
            if (player.height > 1.8F) GL11.glTranslatef(0, 0, -scale * 2);
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2)
            if (player.height > 1.8F) GL11.glTranslatef(0, 0, scale * 2);

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onEntityRenderPre(RenderLivingEvent.Pre event) {
        final EntityLivingBase entity = event.getEntity();

        if (entity.hasCapability(SizeCapPro.sizeCapability, null)) {
            final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);

            if (cap.getTrans() == true) {
                float scale = entity.height / cap.getDefaultHeight();

                if (scale<  0.4F) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(scale * 2.5F, 1, scale * 2.5F);
                    GlStateManager.translate(event.getX() / scale * 2.5F - event.getX(), event.getY() / scale * 2.5F - event.getY(), event.getZ() / scale * 2.5F - event.getZ());
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onLivingRenderPost(RenderLivingEvent.Post event) {
        final EntityLivingBase entity = event.getEntity();

        if (entity.hasCapability(SizeCapPro.sizeCapability, null)) {
            final ISizeCap cap = entity.getCapability(SizeCapPro.sizeCapability, null);

            if (cap.getTrans() == true) {
                float scale = entity.height / cap.getDefaultHeight();
                if (scale < 0.4F) GlStateManager.popMatrix();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (ClientProxy.getSizeKeybinding().isPressed()) Minecraft.getMinecraft().displayGuiScreen(new GuiPlayerSize());
    }
}

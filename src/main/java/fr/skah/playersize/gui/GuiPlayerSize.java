package fr.skah.playersize.gui;

import fr.skah.playersize.network.PacketCSAttributes;
import fr.skah.playersize.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPlayerSize extends GuiScreen {


    private int visibleTime;
    private GuiSlider slider;
    private double size;

    public void initGui() {
        this.size = 1.2;
        this.buttonList.clear();
        this.buttonList.add(slider = new GuiSlider(58, this.width / 2 - 100, this.height / 2 + 40, 200, 20, "", "", 1.50F, 1.9F, 1.80F, true, true));
        this.buttonList.add(new GuiButton(59, this.width / 2 - 100, this.height / 2 + 70, "Valider"));
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 59) {
            CommonProxy.getNetwork().sendToServer(new PacketCSAttributes(mc.player.getEntityId(), (float)slider.getValue()));
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }



    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(selectedButton != null && selectedButton.id == 58) {
            size = slider.getValue();
        }
    }

    public void updateScreen() {
        super.updateScreen();
        ++this.visibleTime;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int i = this.width / 2;
        int j = this.height / 2;
        this.drawCenteredString(this.fontRenderer, I18n.format("Réglage de la taille du joueur"), this.width / 2, 15, 16777215);
        this.drawEntity(i, j + 20, 30 * size, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, this.mc.player);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawEntity(int posX, int posY, double scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan((mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float) Math.atan((mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float) Math.atan((mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}

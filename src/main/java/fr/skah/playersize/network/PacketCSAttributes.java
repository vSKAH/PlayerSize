package fr.skah.playersize.network;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import fr.skah.playersize.utils.atributes.Attributes;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketCSAttributes implements IMessage {

    private static final UUID UUID_HEIGHT = UUID.fromString("5440b01a-974f-4495-bb9a-c7c87424bca4");
    private static final UUID UUID_WIDTH = UUID.fromString("3949d2ed-b6cc-4330-9c13-98777f48ea51");

    private int entityId;
    private float size;

    public PacketCSAttributes() {
    }

    public PacketCSAttributes(int entityId, float size) {
        this.entityId = entityId;
        this.size = size;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.size = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeFloat(size);
    }

    public static class ServerHandler implements IMessageHandler<PacketCSAttributes, IMessage> {
        @Override
        public IMessage onMessage(PacketCSAttributes message, MessageContext ctx) {
            EntityPlayer p = ctx.getServerHandler().player;
            message.size = MathHelper.clamp(message.size, 1.5F, 1.9F);

            Multimap<String, AttributeModifier> attributes = HashMultimap.create();
            attributes.put(Attributes.ENTITY_HEIGHT.getName(), new AttributeModifier(UUID_HEIGHT, "Player Height", message.size - 1, 2));
            attributes.put(Attributes.ENTITY_WIDTH.getName(), new AttributeModifier(UUID_WIDTH, "Player Width", MathHelper.clamp(message.size - 1, 0.4 - 1, 1.9F), 2));
            p.getAttributeMap().applyAttributeModifiers(attributes);
            return null;
        }
    }

}

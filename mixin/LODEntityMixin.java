package me.horizonlite.mixin;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.horizonlite.lod.LodRenderer;

@Mixin(EntityRenderDispatcher.class)
public class LODEntityMixin {

    @Inject(method="render(Lnet/minecraft/entity/Entity;FFFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at=@At("HEAD"), cancellable=true)
    private void applyExtremeEntityLOD(Entity entity, float yaw, float tickDelta, MatrixStack matrices,
                                       VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        double distanceSq = entity.squaredDistanceTo(mc.player);
        if (!LodRenderer.shouldRenderEntity(distanceSq)) {
            ci.cancel();
        }
    }
}

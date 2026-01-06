package me.horizonlite.mixin;

import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.horizonlite.lod.LodRenderer;

@Mixin(RenderSectionManager.class)
public class LODChunksMixin {

    @Inject(method="renderSections", at=@At("HEAD"))
    private void applyMeshInstancing(MatrixStack matrices, double x, double y, double z, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        for (RenderSection section : ((RenderSectionManager)(Object)this).getSections()) {
            double dx = section.getOrigin().getX() - mc.player.getX();
            double dz = section.getOrigin().getZ() - mc.player.getZ();
            double distanceSq = dx*dx + dz*dz;

            if (!LodRenderer.shouldRenderChunk(distanceSq)) {
                // Substitui chunk por mesh instanciada real
                section.setBuiltSection(LodRenderer.getMeshInstance(section));
            }
        }
    }
}

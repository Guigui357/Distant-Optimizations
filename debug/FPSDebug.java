package me.horizonlite.debug;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import me.horizonlite.lod.LodRenderer;

public class FPSDebug {

    public static void init() {
        HudRenderCallback.EVENT.register(FPSDebug::render);
    }

    private static void render(DrawContext context, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        int fps = mc.getCurrentFps();
        LodRenderer.updateFPS(fps);
        context.drawText(mc.textRenderer, "FPS: " + fps + " | DHLE Ultra+ ativo", 10, 10, 0xFFFFFF, false);
    }
}

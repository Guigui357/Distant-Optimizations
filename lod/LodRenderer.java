package me.horizonlite.lod;

import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import net.minecraft.client.MinecraftClient;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class LodRenderer {

    private static final double MAX_ENTITY_DISTANCE_SQ = 10000.0;
    private static final double MAX_CHUNK_DISTANCE_SQ = 250000.0;
    private static double lastFPS = 60;

    // Cache de meshes instanciadas
    private static final Map<RenderSection, RenderSection> meshInstanceCache = new ConcurrentHashMap<>();

    public static void init() {
        System.out.println("[DHLE-Ultra+] LOD Instancing Ultra inicializado!");
    }

    public static boolean shouldRenderChunk(double distanceSq) {
        double maxDist = MAX_CHUNK_DISTANCE_SQ * Math.min(1.0, lastFPS / 120.0);
        return distanceSq < maxDist;
    }

    public static boolean shouldRenderEntity(double distanceSq) {
        double maxDist = MAX_ENTITY_DISTANCE_SQ * Math.min(1.0, lastFPS / 120.0);
        return distanceSq < maxDist;
    }

    public static RenderSection getMeshInstance(RenderSection original) {
        return meshInstanceCache.computeIfAbsent(original, section -> {
            RenderSection instance = new RenderSection(section.getOrigin());
            // Mesh instanciada: simplifica blocos distantes
            section.getBlocks().forEach(block -> instance.addBlockInstance(block));
            return instance;
        });
    }

    public static void updateFPS(double fps) {
        lastFPS = fps;
    }
}

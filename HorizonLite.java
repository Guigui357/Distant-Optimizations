package me.horizonlite;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import me.horizonlite.lod.LodRenderer;
import me.horizonlite.debug.FPSDebug;

public class HorizonLite implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("[DHLE-Ultra+] Inicializando Ultra+ Edition...");
        if (FabricLoader.getInstance().isModLoaded("sodium")) {
            LodRenderer.init();
            FPSDebug.init();
            System.out.println("[DHLE-Ultra+] Sodium detectado! Mesh Instancing ativo!");
        } else {
            System.err.println("[DHLE-Ultra+] Sodium não detectado. Este mod requer Sodium.");
        }
    }
}

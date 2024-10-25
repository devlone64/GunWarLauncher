/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.data;

import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.openlauncherlib.NoFramework;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author lone64dev
 */
@Getter
public class MinecraftClient {
    private final String name;
    private final NoFramework.ModLoader loader;
    private final Map<String, String> version;
    private final boolean autoConnect;
    private final String server;
    private final List<Mod> modules;
    private final List<ShaderPack> shaderPacks;
    private final List<ResourcePack> resourcePacks;
    
    public MinecraftClient(String name, String loader, Map<String, String> version, boolean autoConnect, String server, List<Mod> modules, List<ShaderPack> shaderPacks, List<ResourcePack> resourcePacks) {
        this.name = name;
        this.loader = NoFramework.ModLoader.valueOf(loader.toUpperCase());
        this.version = version;
        this.autoConnect = autoConnect;
        this.server = server;
        this.modules = modules;
        this.shaderPacks = shaderPacks;
        this.resourcePacks = resourcePacks;
    }
    
    public boolean isMod(String modName) {
        for (Mod mod : this.modules) {
            if (mod.getName().equalsIgnoreCase(modName)) {
                return true;
            }
        }
        return false;
    }
}
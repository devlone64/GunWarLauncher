/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.json;

import com.google.gson.JsonElement;
import dev.lone64.launcher.occupationalwar.Main;
import dev.lone64.launcher.occupationalwar.data.MinecraftClient;
import dev.lone64.launcher.occupationalwar.data.ResourcePack;
import dev.lone64.launcher.occupationalwar.data.ShaderPack;
import dev.lone64.launcher.occupationalwar.util.FileUtil;
import dev.lone64.launcher.occupationalwar.util.HttpUtil;
import fr.flowarg.flowupdater.download.json.Mod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lone64dev
 */
public class JsonLauncher {
    public MinecraftClient getSelectedClient() {
        var element = HttpUtil.getJsonData(Main.LAUNCHER_FILE);
        if (element == null) {
            System.out.print("[%s] An error occurred while downloading the launcher file.\n".formatted(Main.LAUNCHER_NAME));
            return null;
        }

        var object = element.getAsJsonObject();
        var name = object.get("name").getAsString();
        var loader = object.get("loader").getAsString();
        var autoConnect = object.get("autoconnect").getAsBoolean();
        var server = object.get("server").getAsString();

        var object1 = object.get("version").getAsJsonObject();
        Map<String, String> version = new HashMap<>();
        version.put("client", object1.get("client").getAsString());
        version.put("loader", object1.get("loader").getAsString());

        var modules = new ArrayList<Mod>();
        for (JsonElement e : object.get("modules").getAsJsonArray().asList()) {
            var obj = e.getAsJsonObject();
            modules.add(new Mod(
                    obj.get("name").getAsString(),
                    obj.get("download").getAsString(),
                    obj.get("sha1").getAsString(),
                    obj.get("size").getAsInt()
            ));
        }

        for (var fileTag : FileUtil.getFileTags("Minecraft/mods")) {
            if (!isMod(fileTag, modules)) {
                var file = FileUtil.getDataFolder("Minecraft/mods/%s".formatted(fileTag));
                if (file.exists()) FileUtil.deleteFile(file);
            }
        }
        
        var shaderPacks = new ArrayList<ShaderPack>();
        for (JsonElement e : object.get("shaderPacks").getAsJsonArray().asList()) {
            var obj = e.getAsJsonObject();
            shaderPacks.add(new ShaderPack(obj.get("name").getAsString(), obj.get("download").getAsString()));
        }
        
        for (var fileTag : FileUtil.getFileTags("Minecraft/shaderpacks")) {
            if (!isShaderPack(fileTag, shaderPacks)) {
                var file = FileUtil.getDataFolder("Minecraft/shaderpacks/%s".formatted(fileTag));
                if (file.exists()) FileUtil.deleteFile(file);
            }
        }
        
        var resourcePacks = new ArrayList<ResourcePack>();
        for (JsonElement e : object.get("resourcePacks").getAsJsonArray().asList()) {
            var obj = e.getAsJsonObject();
            resourcePacks.add(new ResourcePack(obj.get("name").getAsString(), obj.get("download").getAsString()));
        }
        
        for (var fileTag : FileUtil.getFileTags("Minecraft/resourcepacks")) {
            if (!isResourcePack(fileTag, resourcePacks)) {
                var file = FileUtil.getDataFolder("Minecraft/resourcepacks/%s".formatted(fileTag));
                if (file.exists()) FileUtil.deleteFile(file);
            }
        }
        return new MinecraftClient(name, loader, version, autoConnect, server, modules, shaderPacks, resourcePacks);
    }
    
    public List<String> getMods(List<Mod> modules) {
        return modules.stream().map(Mod::getName).map(name -> name.replace(".jar", "")).toList();
    }
    
    public List<String> getShaderPacks(List<ShaderPack> shaderPacks) {
        return shaderPacks.stream().map(ShaderPack::getName).map(name -> name.replace(".zip", "")).toList();
    }
    
        public List<String> getResourcepacks(List<ResourcePack> resourcePacks) {
        return resourcePacks.stream().map(ResourcePack::getName).map(name -> name.replace(".zip", "")).toList();
    }
    
    public boolean isMod(String modName, List<Mod> modules) {
        modName = modName.replace(".jar", "");
        for (var mod : getMods(modules)) {
            if (mod.equalsIgnoreCase(modName)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isShaderPack(String shaderName, List<ShaderPack> shaderPacks) {
        shaderName = shaderName.replace(".zip", "");
        for (var shader : getShaderPacks(shaderPacks)) {
            if (shader.equalsIgnoreCase(shaderName)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isResourcePack(String resourceName, List<ResourcePack> resourcePacks) {
        resourceName = resourceName.replace(".zip", "");
        for (var resource : getResourcepacks(resourcePacks)) {
            if (resource.equalsIgnoreCase(resourceName)) {
                return true;
            }
        }
        return false;
    }
    
    public static JsonLauncher getInstance() {
        return new JsonLauncher();
    }
}
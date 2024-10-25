/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.client;

import dev.lone64.launcher.occupationalwar.Main;
import dev.lone64.launcher.occupationalwar.data.MinecraftAccount;
import dev.lone64.launcher.occupationalwar.data.MinecraftClient;
import dev.lone64.launcher.occupationalwar.frame.MainFrame;
import dev.lone64.launcher.occupationalwar.util.FileUtil;
import dev.lone64.launcher.occupationalwar.util.MCUtil;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.flowupdater.versions.fabric.FabricVersionBuilder;
import fr.flowarg.flowupdater.versions.forge.ForgeVersionBuilder;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import java.util.List;
import javax.swing.JOptionPane;
import lombok.Getter;

/**
 *
 * @author lone64dev
 */
@Getter
public class ClientLoader {
    private final MinecraftClient client;
    private final MinecraftAccount account;
    
    public ClientLoader(MinecraftClient client, MinecraftAccount account) {
        this.client = client;
        this.account = account;
    }
    
    public void playClient() {
        if (this.client == null) {
            System.out.println("[%s] An error occurred while running the Minecraft client.\n".formatted(Main.LAUNCHER_NAME));
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "마인크래프트 클라이언트을 실행하던 도중 오류가 발생했습니다. (FF7812)", Main.LAUNCHER_NAME, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        new Thread(this::update).start();
        MainFrame.INSTANCE.setProgressPanelVisible(true);
    }
    
    private void update() {
        try {
            var runnable = new ClientRunnable();
            var vanilla = new VanillaVersion.VanillaVersionBuilder().withName(this.client.getVersion().get("client")).build();
            if (this.client.getLoader() != null) {
                switch (this.client.getLoader()) {
                    case FABRIC ->  {
                        var fabric = new FabricVersionBuilder().withFabricVersion(this.client.getVersion().get("loader")).withMods(this.client.getModules()).build();
                        var updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanilla).withModLoaderVersion(fabric).withProgressCallback(runnable).build();
                        updater.update(FileUtil.getDataFolder("Minecraft").toPath());
                    }
                    case FORGE ->  {
                        var forge = new ForgeVersionBuilder().withForgeVersion(this.client.getVersion().get("loader")).withMods(this.client.getModules()).build();
                        var updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanilla).withModLoaderVersion(forge).withProgressCallback(runnable).build();
                        updater.update(FileUtil.getDataFolder("Minecraft").toPath());
                    }
                }
                
                if (this.client.isMod("iris-shader.jar")) {
                    loadShaderPacks();
                }
            }
            
            loadResourcePacks();
            if (this.client.getLoader() == null) {
                var updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanilla).withProgressCallback(runnable).build();
                updater.update(FileUtil.getDataFolder("Minecraft").toPath());
            }
            
            startGame();
        } catch (Exception e) {
            e.printStackTrace();
            MainFrame.getInstance().setProgressPanelVisible(false);
            MainFrame.getInstance().getPlayButton().setEnabled(true);
            System.out.println("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "마인크래프트 클라이언트을 실행하던 도중 오류가 발생했습니다. (FF3317)", Main.LAUNCHER_NAME, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadShaderPacks() {
        var packFolder = FileUtil.getDataFolder("Minecraft/shaderpacks");
        if (!packFolder.exists() && FileUtil.createDirectory(packFolder)) {
            System.out.print("[%s] Successfully created '%s'!\n".formatted(Main.LAUNCHER_NAME, packFolder.getPath()));
        }
        
        for (var pack : this.client.getShaderPacks()) {
            var packFile = FileUtil.getDataFolder("Minecraft/shaderpacks/%s".formatted(pack.getName().contains(".zip") ? pack.getName() : pack.getName() + ".zip"));
            if (!packFile.exists() && FileUtil.downloadFile(pack.getDownload(), packFile, true)) {
                System.out.print("[%s] Successfully downloaded '%s'!\n".formatted(Main.LAUNCHER_NAME, packFile.getPath()));
            }
        }
    }
    
    private void loadResourcePacks() {
        var packFolder = FileUtil.getDataFolder("Minecraft/resourcepacks");
        if (!packFolder.exists() && FileUtil.createDirectory(packFolder)) {
            System.out.print("[%s] Successfully created '%s'!\n".formatted(Main.LAUNCHER_NAME, packFolder.getPath()));
        }
        
        for (var pack : this.client.getResourcePacks()) {
            var packFile = FileUtil.getDataFolder("Minecraft/resourcepacks/%s".formatted(pack.getName().contains(".zip") ? pack.getName() : pack.getName() + ".zip"));
            if (!packFile.exists() && FileUtil.downloadFile(pack.getDownload(), packFile, true)) {
                System.out.print("[%s] Successfully downloaded '%s'!\n".formatted(Main.LAUNCHER_NAME, packFile.getPath()));
            }
        }
    }
    
    private void startGame() {
        try {
            var server = this.client.getServer();
            if (!server.contains(":")) {
                server = "%s:%s".formatted(server, "25565");
            }
            var mcFolder = FileUtil.getDataFolder("Minecraft");
            var clientVersion = this.client.getVersion().get("client");
            var loaderVersion = this.client.getVersion().get("loader");
            var account = new AuthInfos(this.account.getUserName(), this.account.getAccessToken(), this.account.getUniqueId().toString());
            var framework = new NoFramework(mcFolder.toPath(), account, GameFolder.FLOW_UPDATER);
            if (this.client.isAutoConnect()) {
                if (MCUtil.mcVersionAtLeast("1.20", clientVersion)) {
                    framework.setAdditionalArgs(List.of("--quickPlayMultiplayer", server));
                } else {
                    var servers = server.split(":");
                    var hostname = servers[0];
                    var port = "25565";
                    if (server.contains(":")) {
                        port = servers[1];
                    }
                    framework.setAdditionalArgs(List.of("--server", hostname, "--port", port));
                }
            }
            framework.launch(clientVersion, loaderVersion, this.client.getLoader());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
        }
    }
}
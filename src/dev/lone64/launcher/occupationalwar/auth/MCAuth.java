/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.auth;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.lone64.launcher.occupationalwar.Main;
import dev.lone64.launcher.occupationalwar.data.MinecraftAccount;
import dev.lone64.launcher.occupationalwar.util.FileUtil;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author lone64dev
 */
public class MCAuth {
    public MCAuth() {
        var dataFolder = FileUtil.getDataFolder();
        if (!dataFolder.exists() && FileUtil.createDirectory(dataFolder)) {
            System.out.print("[%s] Successfully created '%s'!\n".formatted(Main.LAUNCHER_NAME, dataFolder.getPath()));
        }
    }
    
    public void createMinecraftDir() {
        var mcFolder = FileUtil.getDataFolder("Minecraft");
        if (!mcFolder.exists() && FileUtil.createDirectory(mcFolder)) {
            System.out.print("[%s] Successfully created '%s'!\n".formatted(Main.LAUNCHER_NAME, mcFolder.getPath()));
        }
    }
    
    public MinecraftAccount loginAccount() {
        try {
            var gson = new Gson();
            var newAccount = MSAuth.loginAccount();
            if (newAccount != null) {
                try (Writer writer = Files.newBufferedWriter(FileUtil.getDataFolder("playerdata.json").toPath())) {
                    gson.toJson(newAccount.getJsonData(), writer);
                }
                return newAccount;
            }
            System.out.println("[%s] Your Microsoft account login failed. (#FF0102)\n".formatted(Main.LAUNCHER_NAME));
            return null;
        } catch (JsonIOException | IOException e) {
            e.printStackTrace();
            System.out.println("[%s] Your Microsoft account login failed. (#FF0101)\n".formatted(Main.LAUNCHER_NAME));
            return null;
        }
    }
    
    public MinecraftAccount account() {
        var account = loadedSession();
        if (account != null) {
            return account;
        }
        return null;
    }
    
    public MinecraftAccount loadedSession() {
        try {
            var file = FileUtil.getDataFolder("playerdata.json");
            if (file.exists()) {
                var gson = new Gson();
                JsonObject serializedSession;
                try (Reader reader = Files.newBufferedReader(Paths.get(FileUtil.getDataFolder("playerdata.json").getAbsolutePath()))) {
                    serializedSession = gson.fromJson(reader, JsonObject.class);
                }
                return MSAuth.updateAccount(serializedSession);
            }
            return null;
        } catch (final JsonIOException | JsonSyntaxException | IOException e) {
            return null;
        }
    }
    
    public static MCAuth getInstance() {
        return new MCAuth();
    }
}
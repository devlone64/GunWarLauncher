/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.data;

import com.google.gson.JsonObject;
import java.util.UUID;
import lombok.Getter;
import net.raphimc.minecraftauth.step.java.StepMCToken;

/**
 *
 * @author lone64dev
 */
@Getter
public class MinecraftAccount {
    private final UUID uniqueId;
    private final String userName;
    private final String accessToken;
    private final String headUrl;
    private final StepMCToken.MCToken mcToken;
    private final JsonObject jsonData;
    
    public MinecraftAccount(UUID uniqueId, String userName, String accessToken, StepMCToken.MCToken mcToken, JsonObject jsonData) {
        this.uniqueId = uniqueId;
        this.userName = userName;
        this.accessToken = accessToken;
        this.headUrl = "https://mc-heads.net/avatar/%s/%s".formatted(userName, 64);
        this.mcToken = mcToken;
        this.jsonData = jsonData;
    }
}
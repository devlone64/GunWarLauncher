/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.auth;

import com.google.gson.JsonObject;
import dev.lone64.launcher.occupationalwar.Main;
import dev.lone64.launcher.occupationalwar.data.MinecraftAccount;
import dev.lone64.launcher.occupationalwar.util.AuthUtil;
import dev.lone64.launcher.occupationalwar.util.HttpUtil;
import dev.lone64.launcher.occupationalwar.util.WebUtil;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

/**
 *
 * @author lone64dev
 */
public class MSAuth {
    public static MinecraftAccount loginAccount() {
        try {
            var client = HttpUtil.createHttpClient();
            var session = AuthUtil.getFromInput(client, new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
                WebUtil.openWebview(msaDeviceCode.getDirectVerificationUri()).show();
                System.out.println("[%s] Logging in with a Microsoft account...\n".formatted(Main.LAUNCHER_NAME));
            }));
            if (session.isExpired()) {
                System.out.println("[%s] Your Microsoft account login failed. (#FF0103)\n".formatted(Main.LAUNCHER_NAME));
                return null;
            }
            
            var data = AuthUtil.toJson(session);
            var uniqueId = session.getMcProfile().getId();
            var userName = session.getMcProfile().getName();
            var accessToken = session.getMcProfile().getMcToken().getAccessToken();
            var mcToken = session.getMcProfile().getMcToken();
            System.out.println("[%s] Successfully logged in to Microsoft account (%s)!\n".formatted(Main.LAUNCHER_NAME, userName));
            return new MinecraftAccount(uniqueId, userName, accessToken, mcToken, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MinecraftAccount updateAccount(JsonObject data) {
        try {
            var session = AuthUtil.fromJson(data);
            if (session.isExpired()) {
                var client = HttpUtil.createHttpClient();
                session = AuthUtil.refresh(client, session);
                System.out.println("[%s] Logging in with a Microsoft account...\n".formatted(Main.LAUNCHER_NAME));
            }
            
            var object = AuthUtil.toJson(session);
            var uniqueId = session.getMcProfile().getId();
            var userName = session.getMcProfile().getName();
            var accessToken = session.getMcProfile().getMcToken().getAccessToken();
            var mcToken = session.getMcProfile().getMcToken();
            System.out.println("[%s] Successfully logged in to Microsoft account (%s)!\n".formatted(Main.LAUNCHER_NAME, userName));
            return new MinecraftAccount(uniqueId, userName, accessToken, mcToken, object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.util;

import com.google.gson.JsonObject;
import net.lenni0451.commons.httpclient.HttpClient;
import net.raphimc.minecraftauth.MinecraftAuth;
import net.raphimc.minecraftauth.step.AbstractStep;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;

/**
 *
 * @author lone64dev
 */
public class AuthUtil {
    public static JsonObject toJson(StepFullJavaSession.FullJavaSession session) {
        return MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.toJson(session);
    }
    
    public static StepFullJavaSession.FullJavaSession fromJson(JsonObject data) {
        return MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.fromJson(data);
    }
    
    public static StepFullJavaSession.FullJavaSession refresh(HttpClient client, StepFullJavaSession.FullJavaSession session) throws Exception {
        return MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.refresh(client, session);
    }
    
    public static StepFullJavaSession.FullJavaSession getFromInput(HttpClient client, AbstractStep.InitialInput input) throws Exception {
        return MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(client, input);
    }
}
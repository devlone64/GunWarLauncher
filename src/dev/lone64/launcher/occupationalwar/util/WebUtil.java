/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.util;

import ca.weblite.webview.WebView;

/**
 *
 * @author lone64dev
 */
public class WebUtil {
    public static WebView openWebview() {
        return new WebView()
                .title("GunWar Launcher | Webview")
                .resizable(true);
    }
    
    public static WebView openWebview(String webUrl) {
        return new WebView()
                .title("GunWar Launcher | Webview")
                .resizable(true)
                .url(webUrl);
    }
}
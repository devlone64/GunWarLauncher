/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.util;

/**
 *
 * @author lone64dev
 */
public class MCUtil {
    public static boolean mcVersionAtLeast(String version, String currentVersion) {
        var ver = version.split("\\.");
        var curVer = currentVersion.split("\\.");
        if (ver.length < curVer.length) {
            for (var i = curVer.length; i < ver.length; i++) {
                curVer[i] = "0";
            }
        }
        
        for (var i = 0; i < ver.length; i++) {
            var parsedVer = Integer.parseInt(ver[i]);
            var parsedCurVer = Integer.parseInt(curVer[i]);
            if (parsedCurVer > parsedVer) {
                return true;
            } else if (parsedCurVer < parsedVer) {
                return false;
            }
        }
        return true;
    }
}
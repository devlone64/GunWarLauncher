/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar;

import dev.lone64.launcher.occupationalwar.auth.MCAuth;
import dev.lone64.launcher.occupationalwar.frame.MSAuthFrame;
import dev.lone64.launcher.occupationalwar.frame.MainFrame;
import java.awt.EventQueue;

/**
 *
 * @author lone64dev
 */
public class Main {
    public static final String LAUNCHER_NAME = "OccupationalWar";
    public static final String LAUNCHER_PATH = "OccupationalWar";
    public static final String LAUNCHER_FILE = "https://launcher.lone64.dev/repo/occupationalwar/launcher.json";
    
    public static void main(String[] args) {
        var account = MCAuth.getInstance().account();
        if (account != null) {
            EventQueue.invokeLater(() -> MainFrame.visible(account));
            return;
        }
        
        EventQueue.invokeLater(() -> MSAuthFrame.visible());
    }
}
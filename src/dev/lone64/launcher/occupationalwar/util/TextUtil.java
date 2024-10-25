/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.util;

import dev.lone64.launcher.occupationalwar.Main;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author lone64dev
 */
public class TextUtil {
    public static Font createFont(URL fontUrl) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        } catch (FontFormatException | IOException ex) {
            System.out.print("[%s] Unable to load the font.".formatted(Main.LAUNCHER_NAME));
            return null;
        }
    }
}
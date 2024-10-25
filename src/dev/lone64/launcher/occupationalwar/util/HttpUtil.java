/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.lone64.launcher.occupationalwar.Main;
import dev.lone64.launcher.occupationalwar.frame.MainFrame;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.lenni0451.commons.httpclient.HttpClient;
import net.raphimc.minecraftauth.MinecraftAuth;

/**
 *
 * @author lone64dev
 */
public class HttpUtil {
    public static void openBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public static HttpClient createHttpClient() {
        return MinecraftAuth.createHttpClient();
    }
    
    public static ImageIcon createImage(String url) {
        try {
            var img = ImageIO.read(new URL(url));
            return new ImageIcon(img);
        } catch(IOException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }
    
    public static JsonElement getJsonData(String urlString) {
        try {
            var url = new URL(urlString);
            var conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                var response = new StringBuffer();
                try (java.io.BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = input.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                var element = JsonParser.parseString(response.toString());
                //System.out.println(gson.toJson(element));
                return element;
            } else {
                System.exit(0);
                System.out.print("[%s] An error occurred while retrieving the launcher file.\n");
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "런처 파일를 불러오는 도중 오류가 발생했습니다.", Main.LAUNCHER_NAME, JOptionPane.ERROR_MESSAGE);
                //System.out.println("[%s] GET request failed: %s".formatted(Main.LAUNCHER_NAME, responseCode));
                return null;
            }
        } catch (JsonSyntaxException | IOException e) {
            System.exit(0);
            e.printStackTrace();
            System.out.print("[%s] An error occurred while retrieving the launcher file.\n");
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "런처 파일를 불러오는 도중 오류가 발생했습니다.", Main.LAUNCHER_NAME, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
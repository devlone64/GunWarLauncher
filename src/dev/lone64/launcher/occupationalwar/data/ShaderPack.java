/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.data;

import lombok.Getter;

/**
 *
 * @author lone64dev
 */
@Getter
public class ShaderPack {
    private final String name;
    private final String download;
    
    public ShaderPack(String name, String download) {
        this.name = name;
        this.download = download;
    }
}
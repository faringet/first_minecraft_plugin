package me.fari.firstplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class Firstplugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try (
                Socket socket = new Socket("localhost", 8100);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Connected to server");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("response:" + line);
            }

        } catch (IOException e) {

        }

    }

    @Override
    public void onDisable() {
        System.out.println("Пока мир");
    }
}

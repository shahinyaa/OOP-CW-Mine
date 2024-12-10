package com.shahinya.ticketbookingsystem.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookingConfigManager {
    private static final String CONFIG_FILE_PATH = "booking_config.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Save configuration to file
    public static void saveConfig(BookingConfig config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Load configuration from file
    public static BookingConfig loadConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            return gson.fromJson(reader, BookingConfig.class);
        } catch (IOException e) {
            System.out.println("No configuration file found, starting fresh.");
            return null;
        }
    }
}

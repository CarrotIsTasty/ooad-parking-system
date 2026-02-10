/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

// FileConfigurationService.java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FileConfigurationService implements ConfigurationService {

    private final Path configFilePath;
    private final Map<String, String> configMap;
    private final ReentrantLock lock;
    private boolean autoSave;
    private static FileConfigurationService instance;

    /**
     * Create a configuration service with specified file path
     *
     * @param filePath Path to the configuration file
     * @param autoSave Whether to automatically save on every change
     */
    public FileConfigurationService(String filePath, boolean autoSave) {
        this.configFilePath = Paths.get(filePath);
        this.configMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
        this.autoSave = autoSave;
    }

    public FileConfigurationService(String filePath) {
        this(filePath, false);
    }
    
    public static FileConfigurationService getInstance(String filePath, boolean autoSave) {
        if (FileConfigurationService.instance == null) 
            FileConfigurationService.instance = new FileConfigurationService(filePath, autoSave);
        else
            FileConfigurationService.instance.setAutoSave(autoSave);
        return FileConfigurationService.instance;
    }

    public static FileConfigurationService getInstance(String filePath) {
        return FileConfigurationService.getInstance(filePath, false);
    }

    @Override
    public String getString(String key, String defaultValue) {
        lock.lock();
        try {
            return configMap.getOrDefault(key, defaultValue);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getInt(String key, int defaultValue) {
        lock.lock();
        try {
            String value = configMap.get(key);
            if (value == null) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        lock.lock();
        try {
            String value = configMap.get(key);
            if (value == null) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        lock.lock();
        try {
            String value = configMap.get(key);
            if (value == null) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void set(String key, String value) {
        lock.lock();
        try {
            configMap.put(key, value);
            if (autoSave) {
                save();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to auto-save configuration", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void set(String key, int value) {
        set(key, String.valueOf(value));
    }

    @Override
    public void set(String key, boolean value) {
        set(key, String.valueOf(value));
    }

    @Override
    public void set(String key, double value) {
        set(key, String.valueOf(value));
    }

    @Override
    public boolean contains(String key) {
        lock.lock();
        try {
            return configMap.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(String key) {
        lock.lock();
        try {
            configMap.remove(key);
            if (autoSave) {
                save();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to auto-save configuration", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void save() throws IOException {
        lock.lock();
        try {
            // Create parent directories if they don't exist
            Path parentDir = configFilePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            // Write configuration to file
            try (BufferedWriter writer = Files.newBufferedWriter(configFilePath,
                    StandardCharsets.UTF_8)) {
                for (Map.Entry<String, String> entry : configMap.entrySet()) {
                    // Escape special characters in key and value
                    String escapedKey = escape(entry.getKey());
                    String escapedValue = escape(entry.getValue());
                    writer.write(escapedKey + "=" + escapedValue);
                    writer.newLine();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean load() throws IOException {
        lock.lock();
        try {
            configMap.clear();

            if (!Files.exists(configFilePath)) {
                // Create empty file if it doesn't exist
                save();
                return false;
            }

            // Read configuration from file
            try (BufferedReader reader = Files.newBufferedReader(configFilePath,
                    StandardCharsets.UTF_8)) {
                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();

                    // Skip empty lines and comments
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    // Parse key-value pair
                    int equalsIndex = line.indexOf('=');
                    if (equalsIndex == -1) {
                        System.err.printf("Warning: Invalid format at line %d: %s%n",
                                lineNumber, line);
                        continue;
                    }

                    String key = line.substring(0, equalsIndex).trim();
                    String value = line.substring(equalsIndex + 1).trim();

                    // Unescape key and value
                    String unescapedKey = unescape(key);
                    String unescapedValue = unescape(value);

                    configMap.put(unescapedKey, unescapedValue);
                }
            }
            
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<String, String> getAll() {
        lock.lock();
        try {
            return new HashMap<>(configMap);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            configMap.clear();
            if (autoSave) {
                save();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to auto-save configuration", e);
        } finally {
            lock.unlock();
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        StringBuilder escaped = new StringBuilder();
        for (char c : value.toCharArray()) {
            switch (c) {
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '=':
                    escaped.append("\\=");
                    break;
                default:
                    escaped.append(c);
            }
        }
        return escaped.toString();
    }

    private String unescape(String value) {
        if (value == null) {
            return "";
        }

        StringBuilder unescaped = new StringBuilder();
        boolean escaped = false;

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (escaped) {
                switch (c) {
                    case 'n':
                        unescaped.append('\n');
                        break;
                    case 'r':
                        unescaped.append('\r');
                        break;
                    case 't':
                        unescaped.append('\t');
                        break;
                    case '\\':
                        unescaped.append('\\');
                        break;
                    case '=':
                        unescaped.append('=');
                        break;
                    default:
                        unescaped.append('\\').append(c);
                }
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else {
                unescaped.append(c);
            }
        }

        // Handle trailing backslash
        if (escaped) {
            unescaped.append('\\');
        }

        return unescaped.toString();
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public Path getConfigFilePath() {
        return configFilePath;
    }

    public boolean isAutoSave() {
        return autoSave;
    }
}

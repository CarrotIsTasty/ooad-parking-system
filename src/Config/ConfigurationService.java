/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

// ConfigurationService.java

import java.io.IOException;
import java.util.Map;

public interface ConfigurationService {

    String getString(String key, String defaultValue);

    int getInt(String key, int defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    double getDouble(String key, double defaultValue);

    void set(String key, String value);

    void set(String key, int value);

    void set(String key, boolean value);

    void set(String key, double value);

    boolean contains(String key);

    void remove(String key);

    void save() throws IOException;

    boolean load() throws IOException;

    Map<String, String> getAll();

    void clear();
}
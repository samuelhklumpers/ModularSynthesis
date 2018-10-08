package synthesis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;

public abstract class ModularNode {
    Map<String, Connection> in = new HashMap<>();
    Map<String, Connection> out = new HashMap<>();

    public abstract void process(long t, Connection connection) throws OperationNotSupportedException;
    
    public static Properties configure(Class<? extends ModularNode> name) {
        ClassLoader loader = name.getClassLoader();
        
        InputStream input = loader.getResourceAsStream(String.format("resources/config/%s.config", name.getSimpleName()));
        
        Properties properties = new Properties();
        
        //config files should set default to false unless they are to be ignored
        properties.setProperty("default", "true");
        
        if (input == null)
        {
            return properties;
        }
        
        try
        {
            properties.load(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return properties;
    }
}

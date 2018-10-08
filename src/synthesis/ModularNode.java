package synthesis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class ModularNode extends JPanel {
    private static final long serialVersionUID = -1010939408985658101L;
    
    Map<String, Connection> in = new HashMap<>();
    Map<String, Connection> out = new HashMap<>();
    
    public static Properties getConfiguration(Class<? extends ModularNode> name) {
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
    
    public abstract Properties getProperties();
    
    public abstract void process(long t, Connection connection) throws OperationNotSupportedException;
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        paintChildren(g);
    }
    
    public void configure() {
        //TODO is this the right pattern for this?
        //TODO configurability
        setBorder(BorderFactory.createLineBorder(new Color(0), 2));
        this.setPreferredSize(new Dimension(200, 120));
        
        //Properties properties = this.getProperties();
        
        for (Entry<String, Connection> input : in.entrySet())
        {
            //Integer x = Integer.valueOf(properties.getProperty(String.format("%s x", input.getKey())));
            input.getValue().setLocation(20, 20);
        }
    }
}

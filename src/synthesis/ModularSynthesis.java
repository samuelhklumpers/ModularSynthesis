package synthesis;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ModularSynthesis {
    public static void main(String[] args) {
        //JFrame frame = setupFrame();
        
        /*Properties prop = new Properties();
        
        prop.setProperty("Input x", "20");
        prop.setProperty("Input y", "50");
        
        try (OutputStream os = new FileOutputStream(new File("config/IdentityNode.config"));)
        {
            prop.store(os, "test");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
        
        System.out.println(IdentityNode.class.getSimpleName());
    }

    private static JFrame setupFrame() {
        JFrame frame = new JFrame("Modular Synthesis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        fillFrame(frame);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        
        return frame;
    }

    private static void fillFrame(JFrame frame) {
        JPanel nodePanel = new JPanel();

        frame.add(nodePanel, BorderLayout.CENTER);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());

        JPanel nodeBar = new JPanel();
        nodeBar.setLayout(new GridLayout(0, 1));

        JButton identityButton = new JButton("identity");

        nodeBar.add(identityButton);

        sideBar.add(nodeBar, BorderLayout.CENTER);

        frame.add(sideBar, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save as...");

        fileMenu.add(saveItem);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }
}
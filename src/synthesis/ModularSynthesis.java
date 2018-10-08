package synthesis;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ModularSynthesis {
    public static void main(String[] args) {
        setupFrame();
        
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
        
        //System.out.println(IdentityNode.prop);
    }

    private static JFrame setupFrame() {
        JFrame frame = new JFrame("Modular Synthesis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        fillFrame(frame);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        //this is stupid
        /*Dimension size = frame.getSize();
        
        frame.setExtendedState(frame.getExtendedState() & ~JFrame.MAXIMIZED_BOTH);
        
        frame.setPreferredSize(new Dimension(size.width / 2, size.width / 2));
        
        frame.pack();*/
        
        frame.setVisible(true);
        
        return frame;
    }

    private static void fillFrame(JFrame frame) {
        JPanel nodePanel = new JPanel();

        frame.add(nodePanel, BorderLayout.CENTER);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());

        NodeBar nodeBar = new NodeBar(nodePanel);

        sideBar.add(nodeBar, BorderLayout.CENTER);

        frame.add(sideBar, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save as...");

        fileMenu.add(saveItem);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }
    
    static class NodeBar extends JPanel {
        private static final long serialVersionUID = 5916641943671165454L;
        
        private JPanel nodePanel;
        
        class NodeButton extends JButton {
            private static final long serialVersionUID = 8426554697988695356L;
            
            private Constructor<? extends ModularNode> factory;
            
            public NodeButton(Class<? extends ModularNode> node) {
                try
                {
                    this.factory = node.getConstructor();
                }
                catch (NoSuchMethodException | SecurityException e1)
                {
                    e1.printStackTrace();
                }
                
                add(new JLabel(node.getSimpleName().replace("Node", "")));
                
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                            nodePanel.add(factory.newInstance());
                        }
                        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
        
        public NodeBar(JPanel nodePanel) {
            this.nodePanel = nodePanel;
            
            setLayout(new GridLayout(0, 1));
            
            add(new NodeButton(IdentityNode.class));
        }
    }
    
    class NodePanel extends JPanel {
        private static final long serialVersionUID = 1799239113595611114L;
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            this.paintChildren(g);
        }
    }
}
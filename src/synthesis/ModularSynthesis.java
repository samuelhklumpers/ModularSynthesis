package synthesis;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        NodePanel nodePanel = new NodePanel();

        frame.add(nodePanel, BorderLayout.CENTER);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());

        NodeBar nodeBar = new NodeBar(nodePanel);

        sideBar.add(nodeBar, BorderLayout.CENTER);

        frame.add(sideBar, BorderLayout.WEST);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save as...");

        fileMenu.add(saveItem);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }
    
    static class NodeBar extends JPanel {
        private static final long serialVersionUID = 5916641943671165454L;

        private NodePanel nodePanel;
        
        class NodeButton extends JButton {
            private static final long serialVersionUID = 8426554697988695356L;

            private Constructor<? extends ModularNode> constructor;

            NodeButton(Class<? extends ModularNode> node) {
                try
                {
                    this.constructor = node.getConstructor();
                }
                catch (NoSuchMethodException | SecurityException e1)
                {
                    e1.printStackTrace();
                }
                
                add(new JLabel(node.getSimpleName().replace("Node", "")));

                addActionListener(e -> {
                    try
                    {
                        nodePanel.add(constructor.newInstance());
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e1)
                    {
                        e1.printStackTrace();
                    }
                });
            }
        }

        NodeBar(NodePanel nodePanel) {
            this.nodePanel = nodePanel;
            
            setLayout(new GridLayout(0, 1));
            
            add(new NodeButton(IdentityNode.class));
        }
    }

    static class NodePanel extends JPanel {
        private static final long serialVersionUID = 1799239113595611114L;

        private JComponent drawingLayer;

        class NodeLayout implements LayoutManager {
            private static final String DRAWING_LAYER = "drawing";

            //TODO is there a good reason to make this a layoutmgr2?
            @Override
            public void addLayoutComponent(String name, Component comp) {
                if (name.equals(DRAWING_LAYER))
                {

                }
            }

            @Override
            public void removeLayoutComponent(Component comp) {

            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                return null;
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return null;
            }

            @Override
            public void layoutContainer(Container parent) {

            }
        }

        NodePanel() {
            //TODO mouse listeners on the drawing layer?
            this.drawingLayer = new JComponent() {

            };

            this.setLayout(new NodeLayout());

            add(drawingLayer);
        }

        public void add(ModularNode node) {
            //TODO do layout mgr things so the drawinglayer and this doesn't turn into such a mess
            super.add(node);
            node.setLocation((int)(Math.random() * getWidth()), (int)(Math.random() * getHeight()));
            node.setSize(node.getPreferredSize());
            node.revalidate();
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //TODO could be moved to only the updating methods to save performance
//            if (getComponentZOrder(drawingLayer) > 0)
//            {
//                setComponentZOrder(drawingLayer, 0);
//            }

            this.paintChildren(g);
        }

        @Override
        public void setSize(int width, int height) {
            //TODO eliminate this with a layoutmgr
            drawingLayer.setSize(width, height);

            super.setSize(width, height);
        }
    }
}
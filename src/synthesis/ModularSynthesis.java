package synthesis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
        
        frame.setPreferredSize(new Dimension(size.width / 2, size.width / 2));*/

        frame.pack();
        
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
        private static final String DRAWING_LAYER = "drawing";
        private static final long serialVersionUID = 1799239113595611114L;

        private JComponent drawingLayer;

        class NodeLayout implements LayoutManager {

            //TODO is there a good reason to make this a layoutmgr2?
            @Override
            public void addLayoutComponent(String name, Component comp) {
                if (name.equals(DRAWING_LAYER))
                {
                    //uhh
                    NodePanel.this.setComponentZOrder(comp, 0);
                }
                else
                {
                    NodePanel.this.setComponentZOrder(comp, 1);
                }
            }

            @Override
            public void removeLayoutComponent(Component comp) {
            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                //if this gets called something has severely gone to shit
                //TODO configurability get the preferredsize default?
                return new Dimension(200, 120);
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                //dito
                return null;
            }

            @Override
            public void layoutContainer(Container parent) {
                drawingLayer.setSize(parent.getSize());
            }
        }

        NodePanel() {
            //TODO mouse listeners on the drawing layer?
            this.drawingLayer = new JComponent() {
                @Override
                public void paintComponent(Graphics g) {
                    for (Component comp : NodePanel.this.getComponents())
                    {
                        if (comp instanceof ModularNode)
                        {
                            ModularNode node = (ModularNode)comp;

                            for (Connection conn : node.in.values())
                            {
                                Connection to = conn.target;

                                if (to != null)
                                {
                                    g.drawLine(conn.getX(), conn.getY(), to.getX(), to.getY());
                                }
                            }
                        }
                    }
                }
            };

            //TODO where is the right place to add listeners to things

            drawingLayer.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //TODO popup if rmb
                    //TODO separate the nodepanel and the drawinglayer into two panels of a superpanel so getComponentAt doesn't return drawinglayer
                    System.out.println(e.getPoint());
                    //System.out.println(NodePanel.this.getComponentAt(e.getPoint()));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //ignore rmb
                    //TODO if not shifting set selection else add to selection
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

            drawingLayer.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    //TODO move the selection or the background
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });

            this.setLayout(new NodeLayout());

            add(drawingLayer);

            drawingLayer.setFocusable(true);
            drawingLayer.grabFocus();
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

            this.paintChildren(g);
        }
    }
}
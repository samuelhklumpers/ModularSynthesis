package synthesis;

import java.awt.*;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;

public abstract class Connection extends JPanel {
    private static final long serialVersionUID = 4488456006650613649L;
    
    ModularNode parent;
    Connection target; //input nodes have no target?

    Connection() {
        //TODO configurability, maybe push around
        setBorder(BorderFactory.createLineBorder(new Color(0), 2, true));
        setBackground(new Color(0xFF0000));
        setPreferredSize(new Dimension(10, 10));
    }

    private boolean isOutput() {
        return parent.out.containsValue(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
         super.paintComponent(g);
    }
    
    public void connect(Connection to) {
        if (this.isOutput() == to.isOutput())
        {
            return; //can't connect two outputs
            //yet
        }
        
        if (this.getClass() != to.getClass())
        {
            return; //can't connect two different types of outputs
        }
        
        this.target = to;
        to.target = this;
    }

    public double getSignal(long t) throws OperationNotSupportedException {
        if (this.isOutput())
        {
            throw new OperationNotSupportedException();
        }
        else
        {
            return target.getSignal(t);
        }
    }

    public double[] getFourier(long t) throws OperationNotSupportedException {
        if (this.isOutput())
        {
            throw new OperationNotSupportedException();
        }
        else
        {
            return target.getFourier(t);
        }
    }

    public void setSignal(long t, double value) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public void setFourier(long t, double[] value) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
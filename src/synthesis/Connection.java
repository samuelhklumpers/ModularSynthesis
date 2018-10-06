package synthesis;

import javax.naming.OperationNotSupportedException;

public abstract class Connection {
    ModularNode parent;
    ModularNode target; //input nodes have no target?

    private boolean isOutput() {
        return parent.out.containsValue(this);
    }

    public double getSignal(long t) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public double[] getFourier(long t) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public void setSignal(long t, double value) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public void setFourier(long t, double[] value) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
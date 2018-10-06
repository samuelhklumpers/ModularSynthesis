package synthesis;

import javax.naming.OperationNotSupportedException;

public class IdentityNode extends ModularNode {
    public IdentityNode() {
        this.in.put("In", new DoubleConnection());
        this.out.put("Out", new DoubleConnection());
    }

    @Override
    public void process(long t, Connection connection) throws OperationNotSupportedException {
        out.get("Out").setSignal(t, in.get("In").getSignal(t));
    }
}

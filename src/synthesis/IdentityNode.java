package synthesis;

import java.util.Properties;

import javax.naming.OperationNotSupportedException;

public class IdentityNode extends ModularNode {
    static Properties prop;
    
    static {
        prop = configure(IdentityNode.class);
    }
    
    public IdentityNode() {
        this.in.put("In", new DoubleConnection());
        this.out.put("Out", new DoubleConnection());
    }

    @Override
    public void process(long t, Connection connection) throws OperationNotSupportedException {
        out.get("Out").setSignal(t, in.get("In").getSignal(t));
    }
}

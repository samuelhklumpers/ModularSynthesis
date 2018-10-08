package synthesis;

import java.util.Properties;

import javax.naming.OperationNotSupportedException;

public class IdentityNode extends ModularNode {
    private static final long serialVersionUID = 7349129981616194111L;
    static Properties prop; //TODO configurability, push up and make non-static?
    
    static {
        prop = getConfiguration(IdentityNode.class);
    }
    
    public IdentityNode() {
        this.in.put("in", new DoubleConnection());
        this.out.put("out", new DoubleConnection());
        
        configure();
    }
    
    @Override
    public Properties getProperties() {
        return prop;
    }

    @Override
    public void process(long t, Connection connection) throws OperationNotSupportedException {
        out.get("Out").setSignal(t, in.get("In").getSignal(t));
    }
}

package synthesis;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;
import java.util.Map;

public abstract class ModularNode {
    Map<String, Connection> in = new HashMap<>();
    Map<String, Connection> out = new HashMap<>();

    public abstract void process(long t, Connection connection) throws OperationNotSupportedException;
}

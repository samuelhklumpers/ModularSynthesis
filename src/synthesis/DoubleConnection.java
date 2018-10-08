package synthesis;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

public class DoubleConnection extends Connection {
    private static final long serialVersionUID = 1799235052737434520L;
    
    private double[] buff;

    DoubleConnection() {
        this.buff = new double[1024];
    }

    private void resize() {
        double[] newBuff = new double[2 * buff.length];

        System.arraycopy(buff, 0, newBuff, 0, buff.length);
        Arrays.fill(newBuff, buff.length, 2 * buff.length, Double.NaN);

        buff = newBuff;
    }

    @Override
    public double getSignal(long t) throws OperationNotSupportedException {
        if (!isAvailable(t))
        {
            parent.process(t, this);
        }

        return buff[(int)t];
    }

    private boolean isAvailable(long t) {
        return isInBuffer(t) && !Double.isNaN(buff[(int)t]);
    }

    private boolean isInBuffer(long t) {
        return 0 <= t && t < buff.length;
    }

    @Override
    public void setSignal(long t, double value) {
        if (!isInBuffer(t))
        {
            resize();
        }

        buff[(int)t] = value;
    }
}
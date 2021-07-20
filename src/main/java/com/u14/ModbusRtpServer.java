package com.u14;

import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.procimg.ProcessImage;
import com.ghgande.j2mod.modbus.slave.ModbusSlave;
import com.ghgande.j2mod.modbus.slave.ModbusSlaveFactory;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class ModbusRtpServer extends AbstractModbusServer {

    protected static String slavePortName = "CNCA0";
    protected static String encoding = Modbus.SERIAL_ENCODING_RTU;
    protected static Process socat = null;

    public static void main(String[] args) {
        try {
            if (isUnix()) {
                socat = Runtime.getRuntime().exec("socat -d -d pty,raw,echo=0 pty,raw,echo=0");
                slavePortName = "/dev/pts/2";
            }

            createSerialSlave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ModbusSlave createSerialSlave() throws Exception {
        ModbusSlave slave = null;
        try {
            // Create the test data
            ProcessImage spi = getSimpleProcessImage();

            // Create a serial slave
            SerialParameters parameters = new SerialParameters();
            parameters.setPortName(slavePortName);
            parameters.setOpenDelay(1000);
            parameters.setEncoding(encoding);
            slave = ModbusSlaveFactory.createSerialSlave(parameters);
            slave.addProcessImage(UNIT_ID, spi);

            // Open the slave
            slave.open();
        } catch (Exception x) {
            if (slave != null) {
                slave.close();
            }
            throw new Exception(x.getMessage());
        }
        return slave;
    }

    private static boolean isUnix() {
        final String osName = System.getProperty("os.name").toLowerCase();
        return (osName.indexOf("nix") >= 0
                || osName.indexOf("nux") >= 0
                || osName.indexOf("aix") > 0);
    }

}

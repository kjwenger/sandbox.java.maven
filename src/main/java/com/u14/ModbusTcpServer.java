package com.u14;

import com.ghgande.j2mod.modbus.slave.ModbusSlave;
import com.ghgande.j2mod.modbus.slave.ModbusSlaveFactory;

public class ModbusTcpServer extends AbstractModbusServer {

    private static final int PORT = 4502;

    public static void main(String[] args) {
        try {
            createTcpSlave();
            System.out.println(String.format("Modbus TCP server running on port %d", PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ModbusSlave createTcpSlave() throws Exception {
        ModbusSlave slave;
        try {
            // Create a TCP slave on the 'all interfaces' address 0.0.0.0
            slave = ModbusSlaveFactory.createTCPSlave(PORT, 20);
            slave.addProcessImage(UNIT_ID, getSimpleProcessImage());
            slave.open();
        }
        catch (Exception x) {
            throw new Exception(x.getMessage());
        }
        return slave;
    }

}

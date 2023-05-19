package com.u14;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.util.BitVector;

public class ModbusTcpScanner extends AbstractModbusServer {

    public static void main(String[] args) {
        try {
            String[] ipAddresses = new String[]{"127.0.0.1", "192.168.129.203"};
            int[] ports = new int[]{502, 2502, 4520};
            for (String ipAddress : ipAddresses) {
                for (int port : ports) {
                    ModbusTCPMaster master = new ModbusTCPMaster(ipAddress, port);
                    try {
                        master.connect();
                        for (int unitId = 0; unitId < 0x000F; unitId++) {
                            try {
                                master.readCoils(unitId, 0, 1);
                                System.out.println(String.format("Unit %d found at %s:%d",
                                        unitId, ipAddress, port));
                                for (int coilAddress = 0; coilAddress < 256; coilAddress++) {
                                    try {
                                        BitVector coils = master.readCoils(unitId, coilAddress, 1);
                                        System.out.println(String.format("Coil %d found at %d",
                                                coils.toString(), coilAddress));
                                    } catch (ModbusException e) {
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        master.disconnect();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}

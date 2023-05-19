package com.u14;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.util.BitVector;

public class ModbusTcpWriter extends AbstractModbusServer {

    public static void main(String[] args) {
        try {
            boolean toggle = false;
            String[] ipAddresses = new String[]{"192.168.129.203"};
            int[] ports = new int[]{502};
            while (true) {
                for (String ipAddress : ipAddresses) {
                    for (int port : ports) {
                        ModbusTCPMaster master = new ModbusTCPMaster(ipAddress, port);
                        try {
                            master.connect();
                            for (int unitId = 0; unitId < 1; unitId++) {
                                try {
                                    for (int coilAddress = 0x8000; coilAddress < 0x8000 + 8; coilAddress++) {
                                        try {
                                            boolean coil = master.writeCoil(unitId, coilAddress, toggle);
                                            System.out.println(String.format("Coils at %d set to %d",
                                                    coilAddress, coil));
                                        } catch (ModbusException e) {
                                        }
                                    }
//                                    for (int coilAddress = 0x8000; coilAddress <= 0x8000; coilAddress++) {
//                                        try {
//                                            BitVector coils = new BitVector(8);
//                                            for (int pitPos = 0; pitPos <= 8; pitPos++) {
//                                                coils.setBit(pitPos, toggle);
//                                            }
//                                            master.writeMultipleCoils(unitId, coilAddress, coils);
//                                            System.out.println(String.format("Coils at %d set to %d",
//                                                    coilAddress, toggle));
//                                        } catch (ModbusException e) {
//                                        }
//                                    }
                                } catch (Exception e) {
                                }
                            }
                            master.disconnect();
                        } catch (Exception e) {
                        }
                    }
                }
                Thread.sleep(1000);
                toggle = !toggle;
            }
        } catch (Exception e) {
        }
    }

}

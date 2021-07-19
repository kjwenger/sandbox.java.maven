package com.u14;

import com.ghgande.j2mod.modbus.procimg.*;
import com.ghgande.j2mod.modbus.slave.ModbusSlave;
import com.ghgande.j2mod.modbus.slave.ModbusSlaveFactory;
import com.ghgande.j2mod.modbus.util.Observable;
import com.ghgande.j2mod.modbus.util.Observer;

public class ModbusServer {

    private static final int UNIT_ID = 15;
    private static final int PORT = 2502;

    private static Observer observer = new ObserverMonitor();
    private static Observable updatedRegister;
    private static String updatedArgument;

    public static void main(String[] args) {
        try {
            createTcpSlave();
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

    private static SimpleProcessImage getSimpleProcessImage() {
        // Create a Slave that we can use to exercise each and every register type

        SimpleProcessImage spi = new SimpleProcessImage(UNIT_ID);

        // Create some coils
        spi.addDigitalOut(new SimpleDigitalOut(true));
        spi.addDigitalOut(new SimpleDigitalOut(false));

        spi.addDigitalOut(50000, new SimpleDigitalOut(false));
        spi.addDigitalOut(50001, new SimpleDigitalOut(true));
        spi.addDigitalOut(50002, new SimpleDigitalOut(false));

        // Now some discretes
        spi.addDigitalIn(new SimpleDigitalIn(false));
        spi.addDigitalIn(new SimpleDigitalIn(true));
        spi.addDigitalIn(new SimpleDigitalIn(false));
        spi.addDigitalIn(new SimpleDigitalIn(true));
        spi.addDigitalIn(new SimpleDigitalIn(true));
        spi.addDigitalIn(new SimpleDigitalIn(true));
        spi.addDigitalIn(new SimpleDigitalIn(true));
        spi.addDigitalIn(new SimpleDigitalIn(true));

        spi.addDigitalIn(65535, new SimpleDigitalIn(true));

        // A couple of files
        spi.addFile(new com.ghgande.j2mod.modbus.procimg.File(0, 10)
                .setRecord(0, new Record(0, 10))
                .setRecord(1, new Record(1, 10))
                .setRecord(2, new Record(2, 10))
                .setRecord(3, new Record(3, 10))
                .setRecord(4, new Record(4, 10))
                .setRecord(5, new Record(5, 10))
                .setRecord(6, new Record(6, 10))
                .setRecord(7, new Record(7, 10))
                .setRecord(8, new Record(8, 10))
                .setRecord(9, new Record(9, 10)));
        spi.addFile(new com.ghgande.j2mod.modbus.procimg.File(1, 20)
                .setRecord(0, new Record(0, 10))
                .setRecord(1, new Record(1, 20))
                .setRecord(2, new Record(2, 20))
                .setRecord(3, new Record(3, 20))
                .setRecord(4, new Record(4, 20))
                .setRecord(5, new Record(5, 20))
                .setRecord(6, new Record(6, 20))
                .setRecord(7, new Record(7, 20))
                .setRecord(8, new Record(8, 20))
                .setRecord(9, new Record(9, 20))
                .setRecord(10, new Record(10, 10))
                .setRecord(11, new Record(11, 20))
                .setRecord(12, new Record(12, 20))
                .setRecord(13, new Record(13, 20))
                .setRecord(14, new Record(14, 20))
                .setRecord(15, new Record(15, 20))
                .setRecord(16, new Record(16, 20))
                .setRecord(17, new Record(17, 20))
                .setRecord(18, new Record(18, 20))
                .setRecord(19, new Record(19, 20)));

        // Some input registers
        ObservableRegister or = new ObservableRegister();
        or.setValue(251);
        or.addObserver(observer);
        spi.addRegister(or);

        or = new ObservableRegister();
        or.setValue(1111);
        or.addObserver(observer);
        spi.addRegister(or);

        or = new ObservableRegister();
        or.setValue(2222);
        or.addObserver(observer);
        spi.addRegister(or);

        or = new ObservableRegister();
        or.setValue(3333);
        or.addObserver(observer);
        spi.addRegister(or);

        or = new ObservableRegister();
        or.setValue(4444);
        or.addObserver(observer);
        spi.addRegister(or);

        or = new ObservableRegister();
        or.setValue(1234);
        or.addObserver(observer);
        spi.addRegister(40000,or);

        or = new ObservableRegister();
        or.setValue(2345);
        or.addObserver(observer);
        spi.addRegister(40001,or);

        or = new ObservableRegister();
        or.setValue(3456);
        or.addObserver(observer);
        spi.addRegister(40002,or);

        // Some holding registers
        spi.addInputRegister(new SimpleInputRegister(45));
        spi.addInputRegister(new SimpleInputRegister(9999));
        spi.addInputRegister(new SimpleInputRegister(8888));
        spi.addInputRegister(new SimpleInputRegister(7777));
        spi.addInputRegister(new SimpleInputRegister(6666));

        return spi;
    }

    private static class ObserverMonitor implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            updatedRegister = o;
            updatedArgument = (String)arg;
        }
    }

}

package org.spaceapps.aircheck.client.sensor;

public class BluetoothProtocol {
    private boolean initialized = false;
    private StringBuilder buffer = new StringBuilder();

    public String consume(int data) {
        char c = (char)data;
        String result = null;
        if (!initialized) {
            if (c == '[') {
                initialized = true;
                buffer.append(c);
            }
        } else {
            buffer.append(c);
            if (c == ']') {
                result = buffer.toString();
                buffer.setLength(0);
            } else {
                if ((c < '0' || c > '9') && c != '.' && c != ',') {
                    buffer.setLength(0);
                    initialized = false;
                }
            }
        }
        return result;
    }
}

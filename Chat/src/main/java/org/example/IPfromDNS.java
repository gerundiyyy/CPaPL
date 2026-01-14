package org.example;

import java.net.*;
public class IPfromDNS {
    public static void main(String[] args) {
        InetAddress omgtu = null;
        try {
            omgtu = InetAddress.getByName("bsac.by");
        } catch (UnknownHostException e) {
            System.out.println( " ошибка доступа ->" + e);
        }
        System.out.println( "IP-address ->" + omgtu );
    }
}


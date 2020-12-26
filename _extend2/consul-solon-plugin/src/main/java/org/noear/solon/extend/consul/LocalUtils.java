package org.noear.solon.extend.consul;

import org.noear.solon.core.util.PrintUtil;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 地址工具
 *
 * @author 夜の孤城
 * @since 1.2
 */
public class LocalUtils {
    private static String localAddress;

    public static String getLocalAddress() {
        if (localAddress == null) {
            InetAddress address = findFirstNonLoopbackAddress();
            localAddress = address.getHostAddress();
        }

        return localAddress;
    }

    public static InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics
                    .hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    PrintUtil.blueln("Testing interface: " + ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }
                    for (Enumeration<InetAddress> addrs = ifc.getInetAddresses(); addrs.hasMoreElements(); ) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()
                        ) {
                            result = address;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            PrintUtil.redln("Cannot get first non-loopback address" + ex.getStackTrace());
        }

        if (result != null) {
            return result;
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            PrintUtil.redln("Unable to retrieve localhost");
        }

        return null;
    }
}

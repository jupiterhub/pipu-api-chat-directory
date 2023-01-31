package org.jupiterhub.pipu.chatdir.util;

public class StringUtil {

    public static String lowerCase(String data) {
        if (data == null) {
            return "";
        }
        return data.trim().toLowerCase();
    }
}

package org.jupiterhub.pipu.chatdir.record;

import org.jupiterhub.pipu.chatdir.util.StringUtil;

public record Directory(String userId, String host) {
    public Directory(String userId, String host) {
        if (userId == null || host == null) {
            throw new IllegalArgumentException("A userId and host are required.");
        }
        this.userId = StringUtil.lowerCase(userId);
        this.host = host;
    }
}

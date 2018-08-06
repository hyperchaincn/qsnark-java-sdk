package cn.qsnark.sdk.crypto.jce;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;

public final class SpongyCastleProvider {

    private static class Holder {
        private static final Provider INSTANCE = new BouncyCastleProvider();
    }

    public static Provider getInstance() {
        return Holder.INSTANCE;
    }
}

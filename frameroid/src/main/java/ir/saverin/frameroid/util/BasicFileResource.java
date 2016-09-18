package ir.saverin.frameroid.util;

import ir.saverin.frameroid.api.io.FileStorage;
import ir.saverin.frameroid.api.util.FileResource;

/**
 * Created by fahim on 2/14/15.
 */
public class BasicFileResource implements FileResource {

    protected final String address;
    protected final FileStorage.StorageType storageType;
    protected final boolean isInsideApp;

    public BasicFileResource(String address, FileStorage.StorageType storageType, boolean isInsideApp) {
        this.address = address;
        this.storageType = storageType;
        this.isInsideApp = isInsideApp;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public FileStorage.StorageType getStorageType() {
        return storageType;
    }

    @Override
    public boolean isInsideApp() {
        return isInsideApp;
    }
}

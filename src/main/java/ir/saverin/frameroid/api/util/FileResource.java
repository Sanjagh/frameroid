package ir.saverin.frameroid.api.util;

import ir.saverin.frameroid.api.io.FileStorage;

/**
 * Created by fahim on 2/8/15.
 */
public interface FileResource {

    /**
     * if storage type is EXT_FILE the last letter of extension is repeated
     */
    String getAddress();

    FileStorage.StorageType getStorageType();


    /**
     * <p>
     * If default app path must be added to the beginning of the address, this method returns true
     * </p>
     * Note that this flag is ignored if type is either internal storage or asset
     *
     * @return true if address is in app folder, false if address is from root
     */
    boolean isInsideApp();
}

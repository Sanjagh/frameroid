package ir.saverin.frameroid.api.io;

import ir.saverin.frameroid.api.util.FileResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileStorage {

    /**
     * Creates a readable stream from a file on external storage or application resources
     *
     * @param resource a wrapper on address and type
     */
    InputStream createInputStream(FileResource resource) throws IOException;

    /**
     * creates a writable stream from a file on external storage
     *
     * @param resource address of desired file (type must be internal or external, not asset)
     */
    OutputStream createOutputStream(FileResource resource) throws IOException;

    /**
     * Creates full path of a file by adding the external storage address at the beginning of the address.
     */
    String createAbsoluteAddress(FileResource resource);

    String[] getChildren(FileResource resource) throws IOException;

    String[] getChildrenAbsolutePath(FileResource resource) throws IOException;

    boolean deleteFile(FileResource resource);

    boolean move(FileResource source, FileResource target);

    public enum StorageType {
        EXT_FILE(0),
        INT_FILE(1),
        ASSET(2);

        private final int code;

        private StorageType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static StorageType getByCode(int code) {
            for (StorageType t : StorageType.values()) {
                if (t.code == code)
                    return t;
            }
            return null;
        }
    }


}

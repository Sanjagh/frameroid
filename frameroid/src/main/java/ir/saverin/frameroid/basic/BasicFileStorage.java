package ir.saverin.frameroid.basic;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import ir.saverin.frameroid.api.io.FileStorage;
import ir.saverin.frameroid.api.util.FileResource;
import ir.saverin.frameroid.api.util.FrameroidConfig;

import java.io.*;

public class BasicFileStorage implements FileStorage {

    private static final String TAG = BasicFileStorage.class.getName();

    private final AssetManager assetManager;
    private final String externalAddress;
    private final String internalAddress;
    private final String appStorage;
    private final Context context;

    public BasicFileStorage(AssetManager assetManager, Context context, FrameroidConfig config) {
        this.assetManager = assetManager;
        this.context = context;

        String external = Environment.getExternalStorageDirectory().getAbsolutePath();

        externalAddress = normalizePath(external);

        String internal = context.getFilesDir().getAbsolutePath();
        internalAddress = normalizePath(internal);

        appStorage = normalizePath(externalAddress + config.getAppAddress());

    }

    private final static String normalizePath(String directoryPath) {
        return (directoryPath + "/").replace("//", "/");
    }


    @Override
    public InputStream createInputStream(FileResource resource) throws IOException {
        InputStream stream = null;

        switch (resource.getStorageType()) {
            case ASSET:
                String assetAddress = resource.getAddress();
                while (assetAddress.startsWith("/")) {
                    assetAddress = assetAddress.replaceFirst("/", "");
                }
                stream = assetManager.open(assetAddress.replace("//", "/"));
                break;
            case INT_FILE:
            case EXT_FILE:
                String address = createAbsoluteAddress(resource).replace("//", "/");
                stream = new FileInputStream(address);
                break;
        }

        return stream;
    }

    @Override
    public OutputStream createOutputStream(FileResource resource) throws IOException {
        String address = createAbsoluteAddress(resource);
        File file = new File(address);
        file.getParentFile().mkdirs();
        file.createNewFile();
        return new FileOutputStream(address);
    }

    @Override
    public String createAbsoluteAddress(FileResource resource) {
        String address;
        switch (resource.getStorageType()) {
            case INT_FILE:
                address = createInternalAddress(resource.getAddress());
                break;
            case EXT_FILE:
                address = createExternalAddress(resource);
                break;
            default:
                address = resource.getAddress();
                break;
        }
        return address;
    }

    public String createExternalAddress(FileResource resource) {
        String storage = resource.getAddress();
        if (!storage.startsWith(externalAddress)) {
            if (resource.isInsideApp()) {
                storage = appStorage + storage;
            } else {
                storage = (externalAddress + storage);
            }
        }
        return storage.replace("//", "/");
    }

    public String createInternalAddress(String simpleAddress) {
        String storage = simpleAddress;
        if (!simpleAddress.startsWith(internalAddress)) {
            storage = (internalAddress + simpleAddress);
        }
        return storage.replace("//", "/");
    }

    /*
     * (non-Javadoc)
     *
     * @see ir.saverin.frameroid.api.io.FileStorage#getChildren(java.lang.String,
     * ir.saverin.frameroid.api.io.FileStorage.StorageType)
     */
    @Override
    public String[] getChildren(FileResource resource) throws IOException {
        if (resource.getStorageType() == StorageType.ASSET) {
            return assetManager.list(resource.getAddress());
        } else {
            String path = createAbsoluteAddress(resource);
            File file = new File(path);
            return file.list();
        }
    }

    @Override
    public String[] getChildrenAbsolutePath(FileResource resource) throws IOException {
        if (resource.getStorageType() == StorageType.ASSET) {
            return assetManager.list(resource.getAddress());
        } else {
            String path = createAbsoluteAddress(resource);
            if (!path.endsWith("/"))
                path = path + "/";

            File file = new File(path);
            String[] baseList = file.list();
            if (baseList != null && baseList.length > 0) {
                String[] list = new String[baseList.length];
                for (int counter = 0; counter < baseList.length; counter++) {
                    list[counter] = path + baseList[counter];
                }
                return list;
            }
            return new String[]{};
        }
    }


    @Override
    public boolean deleteFile(FileResource resource) {
        File file = new File(createAbsoluteAddress(resource));
        boolean deleted = file.delete();
        if (deleted) {
            Log.d(TAG, "File delete Successful:" + resource);
        } else {
            Log.d(TAG, "File delete Failed:" + resource);
        }
        return deleted;
    }

    @Override
    public boolean move(FileResource source, FileResource target) {
        String sourceAddress = createAbsoluteAddress(source);
        String targetAddress = createAbsoluteAddress(target);

        Log.i(TAG, "Moving " + sourceAddress + " to " + targetAddress);

        File sourceFile = new File(sourceAddress);
        File targetFile = new File(targetAddress);

        return sourceFile.renameTo(targetFile);
    }

}

package de.fhws.fiw.fds.sutton.server.database.binaryData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The BinaryDataResourceHandler class provides methods to handle binary data resources.
 * It provides methods to save, read, update, and delete binary data on the FileSystem.
 */
public class BinaryDataResourceHandler {

    /**
     * The directory where the binary data resources are stored.
     */
    private static final String RESOURCE_DIRECTORY = "src/main/resources/binaryData/";

    /**
     * Saves the given binary data with the given id. The id is also the fileName.
     *
     * @param id the id of the binary data
     * @param data the binary data to be saved
     * @return the file where the binary data is saved
     * @throws IOException if an I/O error occurs
     */
    public File saveBinaryData(long id, byte[] data) throws IOException {
        Path resourceDirectoryPath = Paths.get(RESOURCE_DIRECTORY);
        if (!Files.exists(resourceDirectoryPath)) {
            Files.createDirectories(resourceDirectoryPath);
        }

        File file = new File(RESOURCE_DIRECTORY + id);
        if(!file.exists()){
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
            }
        }else{
            throw new IOException("File already exists");
        }

        return file;
    }

    /**
     * Reads the binary data with the given id. The id is also the fileName.
     *
     * @param id the id of the binary data to be retrieved
     * @return the file where the binary data is stored
     */
    public File getBinaryData(long id) {
        return new File(RESOURCE_DIRECTORY + id);
    }

    /**
     * Reads all binary data in the FileSystem.
     *
     * @return a list of files where the binary data are stored
     */
    public List<File> getAllBinaryData() {
        File folder = new File(RESOURCE_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles != null ? Arrays.asList(listOfFiles) : new ArrayList<>();
    }

    /**
     * Updates the binary data with the given id. The id is also the fileName.
     *
     * @param id the id of the binary data to be updated
     * @param data the new binary data
     * @return the file where the updated binary data is saved
     * @throws IOException if an I/O error occurs
     */
    public File updateBinaryData(long id, byte[] data) throws IOException {
        File existingFile = new File(RESOURCE_DIRECTORY + id);

        if (existingFile.exists()) {
            boolean delete = existingFile.delete();
            if (!delete) {
                throw new IOException("File not found or could not be updated");
            }
        } else {
            throw new IOException("File not found");
        }

        return saveBinaryData(id, data);
    }

    /**
     * Deletes the binary data with the given id. The id is also the fileName.
     *
     * @param id the id of the binary data to be deleted
     * @return true if the binary data is successfully deleted, false otherwise
     * @throws IOException if an I/O error occurs
     */
    public boolean deleteBinaryData(long id) throws IOException {
        File file = new File(RESOURCE_DIRECTORY + id);
        boolean deleted = file.delete();
        if(!deleted){
            throw new IOException("Failed to delete file: " + file.getName());
        }
        return true;
    }

    /**
     * Deletes all binary data in the FileSystem.
     *
     * @throws IOException if an I/O error occurs
     */
    public void deleteAllBinaryData() throws IOException {
        List<File> allFiles = getAllBinaryData();
        for (File file : allFiles) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new IOException("Failed to delete file: " + file.getName());
            }
        }
    }
}

package com.quantitymeasurement;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    public static final String FILE_NAME = "quantity_measurement_repo.ser";

    private static QuantityMeasurementCacheRepository instance;
    private final List<QuantityMeasurementEntity> cache;

    private QuantityMeasurementCacheRepository() {
        cache = new ArrayList<>();
        loadFromDisk();
    }

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
        saveToDisk(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return Collections.unmodifiableList(cache);
    }

    private void saveToDisk(QuantityMeasurementEntity entity) {
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists() && file.length() > 0;
        try (ObjectOutputStream oos = fileExists
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(entity);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Warning: Could not persist entity: " + e.getMessage());
        }
    }

    private void loadFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    cache.add((QuantityMeasurementEntity) ois.readObject());
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Warning: Could not load persisted data: " + e.getMessage());
        }
    }

    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}
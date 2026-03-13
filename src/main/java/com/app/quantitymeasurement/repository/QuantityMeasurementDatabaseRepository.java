package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

    private static QuantityMeasurementDatabaseRepository instance;

    private ConnectionPool connectionPool;

    private static final String INSERT_QUERY =
            "INSERT INTO quantity_measurement_entity " +
            "(this_value,this_unit,this_measurement_type," +
            "that_value,that_unit,that_measurement_type," +
            "operation,result_value,result_unit,result_measurement_type," +
            "result_string,is_error,error_message,created_at,updated_at)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";

    private static final String SELECT_BY_OPERATION =
            "SELECT * FROM quantity_measurement_entity WHERE operation=?";

    private static final String SELECT_BY_TYPE =
            "SELECT * FROM quantity_measurement_entity WHERE this_measurement_type=?";

    private static final String DELETE_ALL =
            "DELETE FROM quantity_measurement_entity";

    private static final String COUNT_QUERY =
            "SELECT COUNT(*) FROM quantity_measurement_entity";

    private QuantityMeasurementDatabaseRepository() {

        try {
            connectionPool = ConnectionPool.getInstance();
            initializeDatabase();
        }
        catch (Exception e) {
            throw new DatabaseException("Repository initialization failed", e);
        }
    }

    public static synchronized QuantityMeasurementDatabaseRepository getInstance() {

        if(instance == null){
            instance = new QuantityMeasurementDatabaseRepository();
        }

        return instance;
    }

    private void initializeDatabase(){

        try(Connection conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement()){

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS quantity_measurement_entity(" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "this_value DOUBLE," +
                            "this_unit VARCHAR(50)," +
                            "this_measurement_type VARCHAR(50)," +
                            "that_value DOUBLE," +
                            "that_unit VARCHAR(50)," +
                            "that_measurement_type VARCHAR(50)," +
                            "operation VARCHAR(50)," +
                            "result_value DOUBLE," +
                            "result_unit VARCHAR(50)," +
                            "result_measurement_type VARCHAR(50)," +
                            "result_string VARCHAR(100)," +
                            "is_error BOOLEAN," +
                            "error_message VARCHAR(255)," +
                            "created_at TIMESTAMP," +
                            "updated_at TIMESTAMP)"
            );

        }catch(Exception e){

            throw new DatabaseException("Database initialization failed",e);
        }
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        try(Connection conn = connectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY)){

            stmt.setDouble(1,entity.getThisValue());
            stmt.setString(2,entity.getThisUnit());
            stmt.setString(3,entity.getThisMeasurementType());

            stmt.setDouble(4,entity.getThatValue());
            stmt.setString(5,entity.getThatUnit());
            stmt.setString(6,entity.getThatMeasurementType());

            stmt.setString(7,entity.getOperation());

            stmt.setDouble(8,entity.getResultValue());
            stmt.setString(9,entity.getResultUnit());
            stmt.setString(10,entity.getResultMeasurementType());

            stmt.setString(11,entity.getResultString());

            stmt.setBoolean(12,entity.isError());
            stmt.setString(13,entity.getErrorMessage());

            stmt.executeUpdate();

        }catch(Exception e){

            throw new DatabaseException("Insert failed",e);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> results = new ArrayList<>();

        try(Connection conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY)){

            while(rs.next()){

                results.add(mapResultSetToEntity(rs));
            }

        }catch(Exception e){

            throw new DatabaseException("Fetch failed",e);
        }

        return results;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {

        List<QuantityMeasurementEntity> results = new ArrayList<>();

        try(Connection conn = connectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_OPERATION)){

            stmt.setString(1,operation);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                results.add(mapResultSetToEntity(rs));
            }

        }catch(Exception e){

            throw new DatabaseException("Query by operation failed",e);
        }

        return results;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {

        List<QuantityMeasurementEntity> results = new ArrayList<>();

        try(Connection conn = connectionPool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_TYPE)){

            stmt.setString(1,type);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                results.add(mapResultSetToEntity(rs));
            }

        }catch(Exception e){

            throw new DatabaseException("Query by type failed",e);
        }

        return results;
    }

    @Override
    public int getTotalCount() {

        try(Connection conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(COUNT_QUERY)){

            if(rs.next()){
                return rs.getInt(1);
            }

        }catch(Exception e){

            throw new DatabaseException("Count query failed",e);
        }

        return 0;
    }

    @Override
    public void deleteAll() {

        try(Connection conn = connectionPool.getConnection();
            Statement stmt = conn.createStatement()){

            stmt.executeUpdate(DELETE_ALL);

        }catch(Exception e){

            throw new DatabaseException("Delete failed",e);
        }
    }

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setId(rs.getInt("id"));

        entity.setThisValue(rs.getDouble("this_value"));
        entity.setThisUnit(rs.getString("this_unit"));
        entity.setThisMeasurementType(rs.getString("this_measurement_type"));

        entity.setThatValue(rs.getDouble("that_value"));
        entity.setThatUnit(rs.getString("that_unit"));
        entity.setThatMeasurementType(rs.getString("that_measurement_type"));

        entity.setOperation(rs.getString("operation"));

        entity.setResultValue(rs.getDouble("result_value"));
        entity.setResultUnit(rs.getString("result_unit"));
        entity.setResultMeasurementType(rs.getString("result_measurement_type"));

        entity.setResultString(rs.getString("result_string"));

        entity.setError(rs.getBoolean("is_error"));
        entity.setErrorMessage(rs.getString("error_message"));

        return entity;
    }
}
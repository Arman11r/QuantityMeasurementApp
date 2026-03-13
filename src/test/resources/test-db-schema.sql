DROP TABLE IF EXISTS quantity_measurement_entity;

CREATE TABLE quantity_measurement_entity (
    id INT AUTO_INCREMENT PRIMARY KEY,

    this_value DOUBLE,
    this_unit VARCHAR(50),
    this_measurement_type VARCHAR(50),

    that_value DOUBLE,
    that_unit VARCHAR(50),
    that_measurement_type VARCHAR(50),

    operation VARCHAR(50),

    result_value DOUBLE,
    result_unit VARCHAR(50),
    result_measurement_type VARCHAR(50),

    result_string VARCHAR(100),

    is_error BOOLEAN,
    error_message VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
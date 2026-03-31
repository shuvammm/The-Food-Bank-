create database SHUVAM_JDBC;
use SHUVAM_JDBC;

CREATE TABLE donations (
    don_id INT PRIMARY KEY AUTO_INCREMENT, donor VARCHAR(100), item VARCHAR(100), qty_kg DECIMAL(8,2),
    donated_on DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE families (
    fam_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), address VARCHAR(200), size INT
);

CREATE TABLE distributions (
    dist_id INT PRIMARY KEY AUTO_INCREMENT, fam_id INT, item VARCHAR(100), qty_kg DECIMAL(8,2),
    dist_date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (fam_id) REFERENCES families(fam_id)
);

select * from donations;
select * from families;
select * from distributions;
-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
CREATE DATABASE IF NOT EXISTS zhuatech_peopledetect DEFAULT CHARACTER SET utf8mb4;
USE zhuatech_peopledetect;
CREATE TABLE monitored_zone (id BIGINT PRIMARY KEY AUTO_INCREMENT, zone_code VARCHAR(40) UNIQUE NOT NULL, zone_name VARCHAR(100) NOT NULL, capacity INT NOT NULL, warning_threshold DECIMAL(5,2), critical_threshold DECIMAL(5,2), enabled BOOLEAN DEFAULT TRUE);
CREATE TABLE zone_observation (id BIGINT PRIMARY KEY AUTO_INCREMENT, zone_id BIGINT NOT NULL, people_count INT NOT NULL, occupancy_percent DECIMAL(5,2), alert_level VARCHAR(20), observed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, INDEX idx_zone_observed(zone_id,observed_at));
CREATE TABLE camera_source (id BIGINT PRIMARY KEY AUTO_INCREMENT, source_code VARCHAR(40) UNIQUE NOT NULL, source_name VARCHAR(100), authorization_status VARCHAR(30), privacy_mode BOOLEAN NOT NULL DEFAULT TRUE, original_frame_retention BOOLEAN NOT NULL DEFAULT FALSE);

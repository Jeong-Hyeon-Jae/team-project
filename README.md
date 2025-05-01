![image](https://github.com/user-attachments/assets/eba48ee9-f06f-4d66-bff0-f42961406497)

CREATE SCHEMA `leave`;

CREATE TABLE `leave`.`users`
(
    `id`         INT UNSIGNED                          NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(50)                           NOT NULL UNIQUE,
    `password`   VARCHAR(128)                          NOT NULL,
    `name`       VARCHAR(50)                           NOT NULL,
    `role`       ENUM ('USER', 'ADMIN') DEFAULT 'USER' NOT NULL,
    `created_at` DATETIME               DEFAULT NOW(),
    CONSTRAINT PRIMARY KEY (`id`)
);

CREATE TABLE `leave`.`annual_leaves`
(
    `id`             INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`        INT UNSIGNED NOT NULL,
    `total_days`     INT UNSIGNED NOT NULL, /*총 연차 일 수*/
    `used_days`      INT UNSIGNED NOT NULL DEFAULT 0, /*사용 연차 일 수*/
    `remaining_days` INT UNSIGNED NOT NULL DEFAULT 0, /*남은 연차 일 수*/
    CONSTRAINT PRIMARY KEY (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `leave`.`users` (`id`)
);

CREATE TABLE `leave`.`leave_requests`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`     INT UNSIGNED NOT NULL, /*사용자인 경우*/
    `start_date`  DATE         NOT NULL, /*연차 언제부터*/
    `end_date`    DATE         NOT NULL, /*언제까지*/
    `days`        INT UNSIGNED NOT NULL, /*연차 신청 몇일했는지*/
    `content`     VARCHAR(255) NOT NULL, /*사유*/
    `status`      ENUM ('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' NOT NULL, /*보류, 승인처리, 거절*/
    `reviewed_by` INT UNSIGNED NULL, /*users 테이블과 연결하는 외래키 관리자인 경우*/
    `reviewed_at` DATETIME     NULL, /*연차 검토 일시*/
    `created_at`  DATETIME     NOT NULL, /*연차 신청 일시*/
    CONSTRAINT PRIMARY KEY (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `leave`.`users` (`id`),
    CONSTRAINT FOREIGN KEY (`reviewed_by`) REFERENCES `leave`.`users` (`id`)
);

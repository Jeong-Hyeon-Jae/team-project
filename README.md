## 📊 DB 다이어그램

![DB 다이어그램](https://github.com/user-attachments/assets/eba48ee9-f06f-4d66-bff0-f42961406497)

---

## 🗄️ Users 테이블 생성 SQL

```sql
create table `leave`.`users`
(
    `id`          int unsigned not null auto_increment,
    `email`       varchar(50)  not null,
    `password`    varchar(100) not null,
    `name`        varchar(50)  not null,
    `created_at`  date default now() comment '입사일',
    `modified_at` date         null comment '수정일',
    `is_delete`    varchar(1)   not null comment '탈퇴여부 (y,n)',
    `is_admin`     boolean      not null comment '관리자여부 (true,false)',
    `contact_mvno_code` varchar(3)     not null comment '연락처 통신사 코드 FK',
    `contact_first`     varchar(4)     not null comment '연락처 앞',
    `contact_second`    varchar(4)     not null comment '연락처 중간',
    `contact_third`     varchar(4)     not null comment '연락처 끝',
    `address_postal`    varchar(10)     not null comment '주소 우편번호',
    `address_primary`   varchar(100)   not null comment '주소 기본',
    `address_secondary` varchar(100)   not null comment '주소 상세',
    constraint primary key (`id`),
    constraint unique (`email`)
);
```
## 🗄️ Annual Leaves 테이블 생성 SQL
``` sql
CREATE TABLE `leave`.`annual_leaves` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL,
    `total_days` INT UNSIGNED NOT NULL,           -- 총 연차 일 수
    `used_days` INT UNSIGNED NOT NULL DEFAULT 0,  -- 사용 연차 일 수
    -- `remaining_days` INT UNSIGNED NOT NULL DEFAULT 0, 이새퀴 방생해야함

    CONSTRAINT PRIMARY KEY (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `leave`.`users`(`id`)
);
```
## 🗄️ Leave Requests 테이블 생성 SQL
```sql
CREATE TABLE `leave`.`leave_requests` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL,
    `start_date` DATE NOT NULL,                   -- 연차 시작일
    `end_date` DATE NOT NULL,                     -- 연차 종료일
    `days` INT UNSIGNED NOT NULL,                 -- 신청한 일 수
    `content` VARCHAR(255) NOT NULL,              -- 연차 사유
    `status` ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING', -- 상태
    `reviewed_by` INT UNSIGNED NULL,              -- 검토자 ID (users FK)
    `reviewed_at` DATETIME NULL DEFAULT NULL,     -- 검토 일시
    `created_at` DATETIME NOT NULL,               -- 신청 일시

    CONSTRAINT PRIMARY KEY (`id`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `leave`.`users`(`id`),
    CONSTRAINT FOREIGN KEY (`reviewed_by`) REFERENCES `leave`.`users`(`id`)
);
```
## INDEX.html
![index](https://github.com/user-attachments/assets/cde311ae-94f0-4c3b-b431-f2db203888ea)

## ADMIN.html
![admin html](https://github.com/user-attachments/assets/63af1014-271b-434c-a8f8-f7c99692a2fe)






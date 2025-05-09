## 📊 DB 다이어그램

![DB 다이어그램](https://github.com/user-attachments/assets/eba48ee9-f06f-4d66-bff0-f42961406497)

---

## 🗄️ Users 테이블 생성 SQL

```sql
CREATE TABLE `leave`.`users` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,       -- 고유 ID (자동 증가)
    `email` VARCHAR(50) NOT NULL,                    -- 사용자 이메일 (중복 불가)
    `password` VARCHAR(100) NOT NULL,                 -- 비밀번호
    `name` VARCHAR(50) NOT NULL,                     -- 사용자 이름
    `role` ENUM('USER','ADMIN') DEFAULT 'user',      -- 역할: 일반 사용자 / 관리자
    `created_at` DATE DEFAULT NOW(),                 -- 생성 일시 (기본값 현재시간)
    `modified_at` DATE NULL,                         -- 수정 일시

    CONSTRAINT PRIMARY KEY (`id`),                   -- 기본 키 지정
    CONSTRAINT UNIQUE (`email`)                      -- 이메일 중복 방지
);
```
## 🗄️ Annual Leaves 테이블 생성 SQL
``` sql
CREATE TABLE `leave`.`annual_leaves` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL,
    `total_days` INT UNSIGNED NOT NULL,           -- 총 연차 일 수
    `used_days` INT UNSIGNED NOT NULL DEFAULT 0,  -- 사용 연차 일 수
    `remaining_days` INT UNSIGNED NOT NULL DEFAULT 0, -- 남은 연차 일 수

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






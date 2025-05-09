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



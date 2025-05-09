## DB 다이어그램

![image](https://github.com/user-attachments/assets/eba48ee9-f06f-4d66-bff0-f42961406497)


<pre> ```sql CREATE TABLE `leave`.`users` ( `id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `email` VARCHAR(50) NOT NULL, `password` VARCHAR(50) NOT NULL, `name` VARCHAR(50) NOT NULL, `role` ENUM('USER','ADMIN') DEFAULT 'user', `created_at` DATETIME DEFAULT NOW(), CONSTRAINT PRIMARY KEY (`id`), CONSTRAINT UNIQUE (`email`) ); ``` </pre>

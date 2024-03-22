DROP SCHEMA IF EXISTS `ezimenu_db` ;
-- Tạo schema
CREATE SCHEMA IF NOT EXISTS `ezimenu_db`;

-- Sử dụng schema
USE `ezimenu_db`;

-- Tạo bảng User
CREATE TABLE `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `fullname` VARCHAR(255),
    `phonenumber` VARCHAR(20)
);

-- Tạo bảng Eatery
CREATE TABLE `eatery` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `address` VARCHAR(255),
    `description` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `ezimenu_db`.`user`(`id`)
);

-- Tạo bảng Category
CREATE TABLE `category` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `eatery_id` INT,
    `name` VARCHAR(255),
    FOREIGN KEY (`eatery_id`) REFERENCES `ezimenu_db`.`eatery`(`id`)
);

-- Tạo bảng Dish
CREATE TABLE `dish` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_id` INT,
    `name` VARCHAR(255),
    `price` INT,
    `status` BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (`category_id`) REFERENCES `ezimenu_db`.`category`(`id`)
);

-- Tạo bảng TableDinner
CREATE TABLE `table_dinner` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `eatery_id` INT,
    `status` BOOLEAN DEFAULT FALSE,
    `description` VARCHAR(255),
    FOREIGN KEY (`eatery_id`) REFERENCES `ezimenu_db`.`eatery`(`id`)
);

-- Tạo bảng Order
CREATE TABLE `order_table` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `table_dinner_id` INT,
    `description` VARCHAR(255),
    `status` INT DEFAULT -1,
    `total_price` INT DEFAULT 0,
    FOREIGN KEY (`table_dinner_id`) REFERENCES `ezimenu_db`.`table_dinner`(`id`)
);

-- Tạo bảng OrderItem
CREATE TABLE `order_item` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT,
    `dish_id` INT,
    `quantity` INT DEFAULT 0,
    `status` BOOLEAN DEFAULT false,
    FOREIGN KEY (`order_id`) REFERENCES `ezimenu_db`.`order_table`(`id`),
    FOREIGN KEY (`dish_id`) REFERENCES `ezimenu_db`.`dish`(`id`)
);

-- Tạo bảng Notify
CREATE TABLE `notify`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
     `table_dinner_id` INT,
     `type` INT,
     `description` VARCHAR(255),
     `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (`table_dinner_id`) REFERENCES `ezimenu_db`.`table_dinner`(`id`)
);

-- Chèn dữ liệu mẫu
-- Chèn dữ liệu vào bảng User
INSERT INTO `ezimenu_db`.`user` (`username`, `password`, `fullname`, `phonenumber`) VALUES
('admin', 'admin', 'T1 Lucifer', '0868524122'),
('user2', 'password2', 'User Two', '987654321');

-- Chèn dữ liệu vào bảng Eatery
INSERT INTO `ezimenu_db`.`eatery` (`user_id`, `address`, `description`) VALUES
(1, '00 Đường 11 Thạch Hòa Thạch Thất Hà Nội', 'Galaxy Dream'),
(2, '456 Đường XYZ, Quận ABC, Thành phố HCM', 'Quán NTN cơ sở 2');

-- Chèn dữ liệu vào bảng Category
INSERT INTO `ezimenu_db`.`category` (`eatery_id`, `name`) VALUES
(1, 'Bún'),
(1, 'Phở'),
(2, 'Đồ ăn vặt'),
(2, 'Đồ uống');

-- Chèn dữ liệu vào bảng Dish
INSERT INTO `ezimenu_db`.`dish` (`category_id`, `name`, `price`, `status`) VALUES
(1, 'Bún bò Huế đặc biệt', 45000, true),
(1, 'Bún chả Hà Nội', 30000, true),
(2, 'Phở Bò Nam Định', 35000, true),
(2, 'Phở Gà Gia Truyền', 50000, true),
(3, 'Xúc xích', 10000, true),
(3, 'Cánh Gà rán', 40000, true),
(4, 'Coca', 15000, true),
(4, 'Nước lọc', 5000, true);

-- Chèn dữ liệu mẫu vào bảng TableDinner
INSERT INTO `ezimenu_db`.`table_dinner` (`eatery_id`, `status`, `description`) VALUES
(1, true, 'Bàn số 1 - Phòng VIP'),
(1, true, 'Bàn số 2 - Phòng VIP'),
(1, true, 'Bàn số 3 - Phòng thường'),
(2, true, 'Bàn số 1 - Góc nhà'),
(2, true, 'Bàn số 2 - Góc nhà');

-- Chèn dữ liệu mẫu vào bảng Order
INSERT INTO `ezimenu_db`.`order_table` (`table_dinner_id`, `description`, `status`, `total_price`) VALUES
(1, 'Đơn hàng số 1', -1, 120000),
(2, 'Đơn hàng số 2', -1, 320000),
(3, 'Đơn hàng số 3', -1, 0);

-- Chèn dữ liệu mẫu vào bảng OrderItem
INSERT INTO `ezimenu_db`.`order_item` (`order_id`, `dish_id`, `quantity`, `status`) VALUES
(1, 1, 2, false),
(1, 2, 1, false),
(2, 3, 2, false),
(2, 4, 5, false);

-- Chèn dữ liệu mẫu vào bảng Notify
INSERT INTO `ezimenu_db`.`notify`(`table_dinner_id`, `type` , `description`) VALUES
(1,-1,''),
(1,0,''),
(1,1,'Làm nhanh giúp em với ạ');
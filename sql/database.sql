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
    `price` DOUBLE,
    `status` BOOLEAN,
    FOREIGN KEY (`category_id`) REFERENCES `ezimenu_db`.`category`(`id`)
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
(2, 'Phở Bò Nam Định', 34000, true),
(2, 'Phở Gà Gia Truyền', 50000, true),
(3, 'Xúc xích', 10000, true),
(3, 'Cánh Gà rán', 40000, true),
(4, 'Coca', 15000, true),
(4, 'Nước lọc', 5000, true);


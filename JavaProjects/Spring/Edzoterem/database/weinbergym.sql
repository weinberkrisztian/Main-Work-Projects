
DROP SCHEMA IF EXISTS `weinbergym`;

CREATE SCHEMA `weinbergym`;
USE `weinbergym` ;


DROP TABLE IF EXISTS `weinbergym`.`ticket`;

CREATE TABLE `weinbergym`.`ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `checking_limit` int(11) NOT NULL,
  `student_price` int(11) NOT NULL,
  `normal_price` int(11) NOT NULL,
	

  

    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO `ticket` (type,checking_limit,student_price,normal_price) VALUES ('Nincs bérlet',0,1000,1500);
INSERT INTO `ticket` (type,checking_limit,student_price,normal_price) VALUES ('Havi korlátlan',7,11000,15000);
INSERT INTO `ticket` (type,checking_limit,student_price,normal_price) VALUES ('Heti 4 alkalom',4,9000,12000);



CREATE TABLE IF NOT EXISTS `weinbergym`.`trainer` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `image_url` VARCHAR(255) DEFAULT NULL,
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  
  PRIMARY KEY (`id`))
ENGINE=InnoDB
AUTO_INCREMENT = 1;



INSERT INTO trainer (image_url, first_name,last_name,email,description) VALUES ('boxing-trainer.jpg','Box','Béla','boxer@gmail.com','Fontos szöveg');
INSERT INTO trainer (image_url,first_name,last_name,email,description) VALUES ('jiu-jitsu-trainer.jpg','Jiu','János','jiu@gmail.com','Fontos szöveg');
INSERT INTO trainer (image_url,first_name,last_name,email,description) VALUES ('wrestling-trainer.jpg','Birkó','Benő','birko@gmail.com','Fontos szöveg');



CREATE TABLE IF NOT EXISTS `weinbergym`.`training_class` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL,
   `image_url` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `trainer_id` BIGINT(20),
   
  CONSTRAINT `FK_TRAINER_05` FOREIGN KEY (`trainer_id`) 
  REFERENCES `trainer` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  PRIMARY KEY (`id`))
ENGINE=InnoDB
AUTO_INCREMENT = 1;



INSERT INTO training_class (name, description,image_url,trainer_id) VALUES ('Box','Boxolás','boxing.jpg',1);
INSERT INTO training_class (name, description,image_url,trainer_id) VALUES ('Jiu-jitsu','Fojtások','jiu-jitsu.jpg',2);
INSERT INTO training_class (name, description,image_url,trainer_id) VALUES ('Birkó','Birkó leírás','wrestling.jpg',3);


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL unique,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password_code` varchar(50),
  `ticket_start` DATE,
  `ticket_id` int(11) NOT NULL,
  `checked_in` int(11) NOT NULL,
  `check_in` TIME ,
  `check_out` TIME,
  `weekly_check_in` int(11) NOT NULL,
  `gender` int(11) NOT NULL,
  `box_number` varchar(50),
  
  
  
  CONSTRAINT `FK_TICKET` FOREIGN KEY (`ticket_id`) 
  REFERENCES `ticket` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  PRIMARY KEY (`id`)
  
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



INSERT INTO `user` (username,password,first_name,last_name,email,checked_in,weekly_check_in,gender,ticket_id)
VALUES 
('edina','$2a$10$5peTtQCxYw2pf4HikC/3y.wWYOE2/ICq8OVs2EBhsLmUS.d5BIqOe','Edina','Urspringer','elsoadmin@gmail.com',1,0,0,1),
('tibor','$2a$10$5peTtQCxYw2pf4HikC/3y.wWYOE2/ICq8OVs2EBhsLmUS.d5BIqOe','Tibor','Somogyi','masodikadmin@gmail.com',1,0,1,1),
('robert','$2a$10$5peTtQCxYw2pf4HikC/3y.wWYOE2/ICq8OVs2EBhsLmUS.d5BIqOe','Róbert','Weinber','harmadikadmin@gmail.com',1,0,1,1);
-- ('krissz97','$2a$10$q4gZPXK08pa6KeFlkGAtvOb2Db8Oa9pDIfa.IuHDwSUpAkqjkoMua','Krisztián', 'Weinber', 'wkrissz97@gmail.com', 1,0,1,1);




DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



INSERT INTO `role` (name)
VALUES 
('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN'),('ROLE_USER');



DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);
-- (4,4);




DROP TABLE IF EXISTS `weinbergym`.`info`;

CREATE TABLE `weinbergym`.`info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `check_in` TIME NOT NULL ,
  `check_out` TIME NOT NULL ,
  `app_date` DATE NOT NULL ,
  `user_name` varchar(50) NOT NULL,
  `training_type` varchar(50) NOT NULL,
  `trainer_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ticket_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ticket_start` DATE,
  `daily_ticket` varchar(50) NOT NULL,
	

    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `weinbergym`.`appointment`;

CREATE TABLE `weinbergym`.`appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` DATE NOT NULL ,
  `time_from_id` int(11) NOT NULL,
  `time_from` TIME NOT NULL,
  `time_to` TIME NOT NULL,
  `day_txt` varchar(50)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
  `order_number` int(11) NOT NULL,
  `current_number` int(11) NOT NULL,
  `max_number` int(11) NOT NULL,
  `training_class_id` BIGINT(20),

  
      KEY(`training_class_id`),

  CONSTRAINT `FK_APP_TRAINCLASS` FOREIGN KEY (`training_class_id`) 
  REFERENCES `training_class` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,


    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
-- optimalizálva lett - weekday(now()) -- hetfo -- weekday(now())-1 kedd és így iterál

-- Hétfő
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (11,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),1,'08:00:00','09:00:00','Hétfő',1,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (12,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),2,'09:00:00','10:00:00','Hétfő',1,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (13,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),3,'10:00:00','11:00:00','Hétfő',1,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (14,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),4,'11:00:00','12:00:00','Hétfő',1,0,20,1);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (15,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),5,'17:00:00','18:00:00','Hétfő',1,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (16,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),6,'18:00:00','19:00:00','Hétfő',1,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (17,DATE_SUB(date(now()), INTERVAL weekday(now())-0 - if(weekday(now())>=0,7,0) DAY),7,'19:00:00','20:00:00','Hétfő',1,0,20,null);

-- Kedd
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (21,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),1,'08:00:00','09:00:00','Kedd',2,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (22,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),2,'09:00:00','10:00:00','Kedd',2,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (23,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),3,'10:00:00','11:00:00','Kedd',2,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (24,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),4,'11:00:00','12:00:00','Kedd',2,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (25,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),5,'17:00:00','18:00:00','Kedd',2,0,20,2);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (26,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),6,'18:00:00','19:00:00','Kedd',2,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (27,DATE_SUB(date(now()), INTERVAL weekday(now())-1 - if(weekday(now())>=1,7,0) DAY),7,'19:00:00','20:00:00','Kedd',2,0,20,null);

-- Szerda
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (31,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),1,'08:00:00','09:00:00','Szerda',3,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (32,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),2,'09:00:00','10:00:00','Szerda',3,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (33,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),3,'10:00:00','11:00:00','Szerda',3,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (34,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),4,'11:00:00','12:00:00','Szerda',3,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (35,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),5,'17:00:00','18:00:00','Szerda',3,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (36,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),6,'18:00:00','19:00:00','Szerda',3,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (37,DATE_SUB(date(now()), INTERVAL weekday(now())-2 - if(weekday(now())>=2,7,0) DAY),7,'19:00:00','20:00:00','Szerda',3,0,20,null);

-- Csütörtök
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (41,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),1,'08:00:00','09:00:00','Csütörtök',4,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (42,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),2,'09:00:00','10:00:00','Csütörtök',4,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (43,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),3,'10:00:00','11:00:00','Csütörtök',4,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (44,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),4,'11:00:00','12:00:00','Csütörtök',4,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (45,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),5,'17:00:00','18:00:00','Csütörtök',4,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (46,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),6,'18:00:00','19:00:00','Csütörtök',4,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (47,DATE_SUB(date(now()), INTERVAL weekday(now())-3 - if(weekday(now())>=3,7,0) DAY),7,'19:00:00','20:00:00','Csütörtök',4,0,20,null);

-- Péntek
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (51,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),1,'08:00:00','09:00:00','Péntek',5,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (52,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),2,'09:00:00','10:00:00','Péntek',5,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (53,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),3,'10:00:00','11:00:00','Péntek',5,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (54,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),4,'11:00:00','12:00:00','Péntek',5,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (55,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),5,'17:00:00','18:00:00','Péntek',5,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (56,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),6,'18:00:00','19:00:00','Péntek',5,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (57,DATE_SUB(date(now()), INTERVAL weekday(now())-4 - if(weekday(now())>=4,7,0) DAY),7,'19:00:00','20:00:00','Péntek',5,0,20,null);

-- Szombat
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (61,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),1,'08:00:00','09:00:00','Szombat',6,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (62,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),2,'09:00:00','10:00:00','Szombat',6,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (63,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),3,'10:00:00','11:00:00','Szombat',6,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (64,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),4,'11:00:00','12:00:00','Szombat',6,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (65,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),5,'17:00:00','18:00:00','Szombat',6,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (66,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),6,'18:00:00','19:00:00','Szombat',6,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (67,DATE_SUB(date(now()), INTERVAL weekday(now())-5 - if(weekday(now())>=5,7,0) DAY),7,'19:00:00','20:00:00','Szombat',6,0,20,null);

-- Vasárnap
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (71,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),1,'08:00:00','09:00:00','Vasárnap',7,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (72,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),2,'09:00:00','10:00:00','Vasárnap',7,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (73,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),3,'10:00:00','11:00:00','Vasárnap',7,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (74,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),4,'11:00:00','12:00:00','Vasárnap',7,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (75,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),5,'17:00:00','18:00:00','Vasárnap',7,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (76,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),6,'18:00:00','19:00:00','Vasárnap',7,0,20,null);
INSERT INTO `appointment` (id,day,time_from_id,time_from,time_to,day_txt,order_number,current_number,max_number,training_class_id) VALUES (77,DATE_SUB(date(now()), INTERVAL weekday(now())-6 - if(weekday(now())>=6,7,0) DAY),7,'19:00:00','20:00:00','Vasárnap',7,0,20,null);







DROP TABLE IF EXISTS `weinbergym`.`user_appointment`;

CREATE TABLE `weinbergym`.`user_appointment` (
  `user_id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`appointment_id`),
  

  
  CONSTRAINT `FK_USER_10` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_APPOINTMENT_TIME` FOREIGN KEY (`appointment_id`) 
  REFERENCES `appointment` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `users_roles`
--





DELIMITER //
create procedure refresh_course_pocedure() 
begin 
delete from weinbergym.user_appointment where appointment_id=(
	SELECT weinbergym.appointment.id from weinbergym.appointment
where WEEKDAY(now()) =WEEKDAY(weinbergym.appointment.day) and hour(now())=hour(weinbergym.appointment.time_to)+2
);
update weinbergym.appointment set weinbergym.appointment.day=DATE_ADD(weinbergym.appointment.day, INTERVAL 7 DAY)
where WEEKDAY(now()) =WEEKDAY(weinbergym.appointment.day) and hour(now())=hour(weinbergym.appointment.time_to)+2;

update weinbergym.appointment set weinbergym.appointment.current_number=0
where WEEKDAY(now()) =WEEKDAY(weinbergym.appointment.day) and hour(now())=hour(weinbergym.appointment.time_to)+2;

end // 
DELIMITER ;

create event IF NOT EXISTS refresh_course 
on schedule every 1 hour 
starts timestamp("2000-01-01","01:00:01") 
ON COMPLETION PRESERVE
DO call refresh_course_pocedure();


DELIMITER //
create procedure refresh_tickets_procedure() 
begin 
update weinbergym.user set weinbergym.user.ticket_id = 1 and weinbergym.user.ticket_start=NULL
where (DAYOFMONTH(NOW())=DAYOFMONTH(weinbergym.user.ticket_start) or (DAYOFMONTH(NOW())=30 and DAYOFMONTH(weinbergym.user.ticket_start)=31)) and  MONTH(NOW())=MONTH(weinbergym.user.ticket_start)+1 ;
end // 
DELIMITER ;

create event IF NOT EXISTS refresh_tickets 
on schedule every 1 DAY 
starts timestamp("2000-01-01","01:00:01") 
ON COMPLETION PRESERVE
DO call refresh_tickets_procedure();

DELIMITER //
create procedure refresh_weekly_check_in_procedure() 
begin 
update weinbergym.user set weinbergym.user.weekly_check_in = 0
where DAYOFWEEK(NOW())=DAYOFWEEK(user.ticket_start);
end // 
DELIMITER ;

create event IF NOT EXISTS refresh_weekly_check_in 
on schedule every 1 DAY
starts timestamp("2000-01-01","01:00:01") 
ON COMPLETION PRESERVE
DO call refresh_weekly_check_in_procedure();






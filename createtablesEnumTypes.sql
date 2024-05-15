CREATE schema iotbay;

USE iotbay;

CREATE TABLE User(
    UserID BIGINT primary key,
    UserFirstName varchar(50),
    UserMiddleName varchar(50),
    UserLastName varchar(50),
    UserType ENUM('Admin', 'Customer', 'Employee', 'Guest'), -- could use a enum value
    UserEmail varchar(255),
    UserPhone varchar(255),
    UserGender varchar(11), -- could use a enum type to restrict inputs
    PasswordHash varchar(64),
    UserCreationDate datetime,
    ActivationFlag boolean,
    VerificationCode varchar(11)
);

CREATE TABLE ProductData(
    ProductID BIGINT primary key,
    ProductName varchar(50),
    ProductStatus ENUM('Backorder', 'InStock', 'OutOfStock'),
    ProductReleaseDate date, -- update to be a datetime
    ProductStockLevel BIGINT,
    ProductDescription varchar(150),
    ProductType varchar(50),
    ProductCost float
);

CREATE TABLE AccessData(
    LogID BIGINT,
    UserID BIGINT,
    LoginTime datetime,
    LogoutTime datetime,
    primary key(LogID),
    foreign key(UserID) references User(UserID)
);

CREATE TABLE Card(
    CardID BIGINT,
    CardNumber BIGINT,
    CardHolderName varchar(128),
    CardExpiry date,
    CardCVV int(3),
    UserID BIGINT,
    primary key(CardID),
    foreign key(UserID) references User(UserID)
);

CREATE TABLE Payments(
    PaymentID BIGINT,
    PaymentAmount decimal(13,2),
    PaymentMethod ENUM('Card'),
    PaymentDate datetime,
    PaymentStatus ENUM('Approved', 'Failed', 'Pending'), -- ahhhh there are errors in the report in regards to type
    CardID BIGINT,
    primary key(PaymentID),
    foreign key(CardID) references Card(CardID)
);

CREATE TABLE ShipmentData(
    ShipmentID BIGINT,
    ShipmentAddress varchar(110),
    ShipmentType ENUM('FedEx', 'Aramex', 'DHL'),
    ShipmentExpectedDate date,
    primary key(ShipmentID)
);

CREATE TABLE Orders (
    OrderID BIGINT AUTO_INCREMENT PRIMARY KEY,
    UserID BIGINT,
    OrderDate DATETIME,
    OrderStatus ENUM('Pending', 'Complete', 'Not submitted'),
    OrderDeliveryStatus ENUM('Being Prepared', 'Delivered', 'Not Submitted', "On It's Way"),
    PaymentID BIGINT,
    ShippingID BIGINT,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (PaymentID) REFERENCES Payments(PaymentID),
    FOREIGN KEY (ShippingID) REFERENCES ShipmentData(ShipmentID)
);

CREATE TABLE OrderLineItem (
    OrderID BIGINT,
    ProductID BIGINT,
    OrderAmount INT,
    PRIMARY KEY (OrderID, ProductID),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES ProductData(ProductID)
);

CREATE TABLE Cart (
    UserID BIGINT,
    ProductID BIGINT,
    Quantity INT,
    PRIMARY KEY (UserID, ProductID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (ProductID) REFERENCES ProductData(ProductID)
);

ALTER TABLE `iotbay`.`Card` 
DROP FOREIGN KEY `Card_ibfk_1`;
ALTER TABLE `iotbay`.`Card` 
ADD CONSTRAINT `Card_ibfk_1`
  FOREIGN KEY (`UserID`)
  REFERENCES `iotbay`.`user` (`UserID`)
  ON DELETE CASCADE;

ALTER TABLE `iotbay`.`orders` 
DROP FOREIGN KEY `orders_ibfk_2`,
DROP FOREIGN KEY `orders_ibfk_3`;
ALTER TABLE `iotbay`.`orders` 
ADD CONSTRAINT `orders_ibfk_2`
  FOREIGN KEY (`PaymentID`)
  REFERENCES `iotbay`.`payments` (`PaymentID`)
  ON DELETE CASCADE,
ADD CONSTRAINT `orders_ibfk_3`
  FOREIGN KEY (`ShippingID`)
  REFERENCES `iotbay`.`shipmentdata` (`ShipmentID`)
  ON DELETE CASCADE;

ALTER TABLE `iotbay`.`accessdata` 
CHANGE COLUMN `LogID` `LogID` BIGINT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `iotbay`.`accessdata` 
DROP FOREIGN KEY `accessdata_ibfk_1`;
ALTER TABLE `iotbay`.`accessdata` 
DROP INDEX `UserID` ;
;
    

ALTER TABLE `iotbay`.`orderlineitem` 
DROP FOREIGN KEY `orderlineitem_ibfk_2`;
ALTER TABLE `iotbay`.`orderlineitem` 
ADD CONSTRAINT `orderlineitem_ibfk_2`
  FOREIGN KEY (`ProductID`)
  REFERENCES `iotbay`.`productdata` (`ProductID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;



insert into User values(
    1111111111,
    'Jim',
    'Jimmy',
    'Jamerson',
    'Admin',
    'jjj@gmail.com',
    '5551111111',
    'Male',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 11:11:11',
    true,
    '2FA111' -- 2fa code
);

insert into User values(
    2222222222,
    'Marvin',
    'Mellachlan',
    'Murdoc',
    'Customer',
    'bbb@gmail.com',
    '5552222222',
    'Male',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 12:35:12',
    true,
    '2FA222' -- 2fa code
);

insert into User values(
    3333333333,
    'Sammy',
    'Something',
    'Simpson',
    'Employee',
    'sss@gmail.com',
    '5553333333',
    'Female',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 11:11:11',
    true,
    '2FA333' -- 2fa code
);

insert into User values(
    4444444444,
    'Aron',
    'Abacus',
    'Actually',
    'Guest',
    'aaa@gmail.com',
    '5554444444',
    'Male',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 11:11:11',
    true,
    '2FA444' -- 2fa code
);

insert into User values(
    5555555555,
    'Zylus',
    'Zebra',
    'Zylophone', -- Spelt with a Z
    'Customer',
    'zzz@gmail.com',
    '5555555555',
    'Male',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 11:11:11',
    False,  -- requested his data be removed due to gdpr regulations
    '2FA444' -- 2fa code
);

INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('6666666666', 'Spirax', 'Comms', 'Allergy', 'Customer', 'sca@gmail.com', '5556666666', 'Female', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('7777777777', 'Blistex', 'Kleenex', 'Virpil', 'Customer', 'bkv@gmail.com', '5557777777', 'Female', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('8888888888', 'Anderson', 'A', 'Alice', 'Customer', 'alice@gmail.com', '5558888888', 'Male', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('9999999999', 'Tommy', 'R', 'Richman', 'Customer', 'trr@gmail.com', '5559999999', 'Female', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('9999999998', 'Niall', 'W', 'Horan', 'Customer', 'NWH@gmail.com', '5559999998', 'Male', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111112', 'Beau', 'C', 'Saillard', 'Customer', 'BCS@gmail.com', '5551111112', 'Male', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111113', 'Travis', '', 'Lenton', 'Customer', 'TL@gmail.com', '5551111113', 'Male', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111114', 'James', 'C', 'Eye', 'Customer', 'JE@gmail.com', '5551111114', 'Female', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111115', 'Jackie', 'Jonhson', 'Employee', 'JJ@gmail.com', '5551213132', 'Male', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111116', 'Wallet', 'Drywall', 'Insanity', 'Customer', 'WDI@gmail.com', '5554321414', 'Male', 'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111117', 'Not', 'Actual', 'Data', 'Admin', 'admin@gmail.com', '5555555555', 'Male', 'admin', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111118', 'Canon', 'Nikon', 'Sony', 'Customer', 'Cameras@commerical.com', '1111111111', 'Male', 'realpass', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111119', 'Pedro', 'P', 'Pascal', 'Customer', 'celeb@gmail.com', '2331112322', 'Female', 'Fall', '2024-05-05 12:53:24', '0');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111120', 'Lounge', 'Sofa', 'Chair', 'Customer', 'Furniture@gmail.com', '3434343434', 'Female', 'goods', '2024-05-05 12:53:24', '1');
INSERT INTO `iotbay`.`user` (`UserID`, `UserFirstName`, `UserMiddleName`, `UserLastName`, `UserType`, `UserEmail`, `UserPhone`, `UserGender`, `PasswordHash`, `UserCreationDate`, `ActivationFlag`) VALUES ('1111111121', 'Sofa', 'Chair', 'Lounge', 'Customer', 'TotallyDifferent@gmail.com', '2222222222', 'Male', 'password', '2024-05-05 12:53:24', '1');


insert into ProductData values(
    2111111111,
    'ADXL345',
    'InStock',
    '2024-01-01',
    500,
    'The ADXL345 is a 3 axis accelerometer with a 10bit resolution and capable of up to 16 g of acceleration',
    'Sensor',
    5.99
);

insert into ProductData values(
    2222222222,
    'Arduino Uno R3',
    'OutOfStock',
    '2024-01-01',
    0,
    'The Arduino Uno R3 is a 8bit microcontroller development board with 14 digital in and output pins',
    'Microcontroller',
    25.99
);

insert into ProductData values(
    2333333333,
    'Raspberry Pi 1b+',
    'InStock',
    '2024-01-01',
    4,
    'The Raspberry Pi 1b+ is a unix single board computer featuring GPIO pins for interacting with the physical world',
    'Computer',
    500.99
);

insert into ProductData values(
    2444444444,
    'Smart Bulb',
    'InStock',
    '2024-01-01',
    270,
    'Smart bulb has wifi and makes your home colourful',
    'Home Iot',
    22.99
);

insert into ProductData values(
    2555555555,
    'Smart Watch',
    'InStock',
    '2024-01-01',
    3500,
    'This Smart Watch has the latest and greatest features such as heart rate monitoring',
    'Gear',
    160
);

insert into ProductData values(
    2666666666,
    'Thermostat',
    'InStock',
    '2024-01-01',
    500,
    'This Thermostat enables maintaining of precise and stable home temperatures',
    'Home Iot',
    200
);

insert into Card values (
    8111111111,
    3566000020000410,
    "Jim J Jamerson",
    '2024-07-01',
    123,
    1111111111
);

insert into Card values (
    8222222222,
    4263982640269299,
    "Jim J Jamerson",
    '2024-03-01',
    837,
    1111111111
);


insert into Card values (
    8333333333,
    4263982640269299,
    "Marvin M Murdoc",
    '2024-07-01',
    738,
    2222222222
);

insert into Card values (
    8444444444,
    4263982640269299,
    "Marvin M Murdoc",
    '2026-04-01',
    887,
    2222222222
);

INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('8555555555', '378282246310005', 'Marvin M Murdoc', '2026-04-01', '111', '1111111114');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('8666666666', '371449635398431', 'Marvin M Murdoc', '2026-04-01', '111', '1111111116');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('8777777777', '378734493671000', 'Marvin M Murdoc', '2026-04-01', '111', '1111111119');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('8888888888', '5610591081018250', 'Jim J Jamerson', '2026-04-01', '111', '7777777777');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('8999999999', '30569309025904', 'Jim J Jamerson', '2026-04-01', '223', '8888888888');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('9999999999', '38520000023237', 'Nuclear Nadal', '2026-04-01', '332', '9999999998');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000001', '6011111111111117', 'Jim J Jamerson', '2026-04-01', '221', '9999999999');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000022', '6011000990139424', 'Jim J Jamerson', '2026-04-01', '223', '9999999999');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000201', '3530111333300000', 'Jim J Jamerson', '2026-04-01', '223', '1111111117');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('1111111211', '3566002020360505', 'Jim J Jamerson', '2026-04-01', '443', '1111111112');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('2323151215', '5555555555554444', 'Jim J Jamerson', '2026-04-01', '554', '1111111112');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000016', '5105105105105100', 'Alex Abagale', '2026-04-01', '334', '1111111115');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('1111111117', '4111111111111111', 'Alex Abagale', '2026-04-01', '812', '1111111116');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000018', '4012888888881881', 'Alex Abagale', '2026-04-01', '123', '1111111118');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000019', '4222222222222', 'Alex Abagale', '2026-04-01', '321', '1111111120');
INSERT INTO `iotbay`.`Card` (`CardID`, `CardNumber`, `CardHolderName`, `CardExpiry`, `CardCVV`, `UserID`) VALUES ('0000000020', '4242424242424242', 'Alex Abagale', '2026-04-01', '555', '1111111120');


INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111111', '400', 'Card', '2024-05-05 00:00:00', 'Approved', '1');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111112', '123', 'Card', '2024-05-05 00:00:00', 'Approved', '16');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111113', '654', 'Card', '2024-05-05 00:00:00', 'Approved', '18');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111114', '6543', 'Card', '2024-05-05 00:00:00', 'Approved', '19');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111115', '34235', 'Card', '2024-05-05 00:00:00', 'Approved', '20');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111116', '999999', 'Card', '2024-05-05 00:00:00', 'Approved', '22');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111117', '0.15', 'Card', '2024-05-05 00:00:00', 'Pending', '201');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111118', '1', 'Card', '2024-05-05 00:00:00', 'Pending', '1111111117');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111119', '2', 'Card', '2024-05-05 00:00:00', 'Pending', '1111111211');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111120', '3', 'Card', '2024-05-05 00:00:00', 'Pending', '2323151215');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111121', '365', 'Card', '2024-05-05 00:00:00', 'Failed', '8111111111');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111122', '578', 'Card', '2024-05-05 00:00:00', 'Failed', '8222222222');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111123', '635', 'Card', '2024-05-05 00:00:00', 'Failed', '1');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111124', '324.23', 'Card', '2024-05-05 00:00:00', 'Pending', '1');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111125', '11.23', 'Card', '2024-05-05 00:00:00', 'Pending', '1');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111126', '123.53', 'Card', '2024-05-05 00:00:00', 'Approved', '1');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111127', '3234.12', 'Card', '2024-05-05 00:00:00', 'Approved', '16');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111128', '1231.62', 'Card', '2024-05-05 00:00:00', 'Approved', '16');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111129', '134.67', 'Card', '2024-05-05 00:00:00', 'Approved', '16');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111130', '13123.13', 'Card', '2024-05-05 00:00:00', 'Approved', '16');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111131', '123.12', 'Card', '2024-05-05 00:00:00', 'Approved', '16');
INSERT INTO `iotbay`.`payments` (`PaymentID`, `PaymentAmount`, `PaymentMethod`, `PaymentDate`, `PaymentStatus`, `CardID`) VALUES ('111111132', '1.12', 'Card', '2024-05-05 00:00:00', 'Approved', '1');
INSERT INTO `iotbay`.`payments` (`PaymentID`) VALUES ('21');



INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111111', '1111111111', '2024-05-09 18:46:52', '2024-05-09 18:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111112', '1111111111', '2024-05-09 13:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111113', '1111111112', '2024-05-09 18:46:52', '2024-05-09 18:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111114', '1111111112', '2024-05-09 13:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111115', '1111111113', '2024-05-06 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111116', '1111111112', '2024-05-06 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111117', '1111111114', '2024-05-06 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111118', '1111111115', '2024-05-06 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111119', '1111111112', '2024-05-08 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111120', '1111111114', '2024-05-06 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111121', '1111111115', '2024-05-06 18:46:52', '2024-05-09 15:46:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111122', '1111111112', '2024-05-10 09:46:52', '2024-05-10 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111123', '1111111111', '2024-05-10 09:46:52', '2024-05-10 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111124', '1111111115', '2024-05-10 09:46:52', '2024-05-10 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111125', '1111111111', '2024-05-02 09:46:52', '2024-05-03 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111126', '1111111111', '2024-05-02 09:46:52', '2024-05-03 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111127', '1111111111', '2024-05-02 09:46:52', '2024-05-03 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111128', '1111111111', '2024-05-02 09:46:52', '2024-05-03 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111129', '1111111112', '2024-05-02 09:46:52', '2024-05-03 09:59:52');
INSERT INTO `iotbay`.`accessdata` (`LogID`, `UserID`, `LoginTime`, `LogoutTime`) VALUES ('11111111130', '5555555555', '2024-05-02 09:46:52', '2024-05-03 09:59:52');


INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111117', 'PiicoDev Starter Kit', 'InStock', '2024-01-01', '20', 'Get started coding high-tech sensors and modules with the PiicoDev', 'Kit', '89.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111118', 'SSD1306 OLED Display', 'InStock', '2024-01-15', '2', 'Display text, draw shapes, animations, and even create plots', 'Display', '8.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111119', 'Jumper Wire', 'OutOfStock', '2024-01-15', '0', 'Jumpstart your electronics project', 'Cable', '3.95');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111120', 'Outlaw Chassis Kit', 'InStock', '2024-01-15', '5', 'A literal Tank Chassis', 'Kit', '2000.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111121', 'RC System', 'InStock', '2024-01-15', '500', 'Perfect for your Remote Control Projects', 'RC', '300.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111122', 'Particle Argon', 'InStock', '2024-01-15', '500', 'The Argon is a powerful Wi-Fi enabled development kit', 'Home Iot', '53.25');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111123', 'Soldering Iron', 'InStock', '1954-01-15', '1000', 'A Hot Stick', 'Tools', '20.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111124', 'Lithium Ion Battery', 'InStock', '1999-05-05', '255', 'Spicy Pillow', 'Power', '29.99');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111125', '12.5W Micro usb power supply', 'OutOfStock', '2005-12-01', '0', 'A power supply for your projects or phone', 'Power', '20.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111126', 'PiKVM', 'InStock', '2021-02-15', '10', 'For all your server management needs', 'Network', '600.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111127', '12Gauge Wire', 'InStock', '1802-02-25', '90000', 'For your Medium Power Needs', 'Cable', '5.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111128', '8Gauge Wire', 'InStock', '1802-02-25', '50000', 'For your Heavy Power Needs', 'Cable', '10.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111129', '2Gauge Wire', 'InStock', '1802-02-25', '2000', 'UNLIMITED POWER', 'Cable', '20.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111130', 'The Holy Grail', 'InStock', '1975-05-05', '1', 'The perfect cup for that special someone', 'Relics', '10.00');
INSERT INTO `iotbay`.`productdata` (`ProductID`, `ProductName`, `ProductStatus`, `ProductReleaseDate`, `ProductStockLevel`, `ProductDescription`, `ProductType`, `ProductCost`) VALUES ('1111111131', 'The Holy Hand Grenade Of Antioch', 'InStock', '1975-05-05', '3', 'good for removing rabbits', 'Relics', '10.00');

INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('1', 'University of Technology Sydney Ultimo NSW', 'FedEx', '2024/09/09');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('2', 'University of Technology Sydney Ultimo NSW', 'Aramex', '2024/09/09');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('3', 'University of Technology Sydney Ultimo NSW', 'DHL', '2024/09/09');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('4', 'University of New South Wales Kensington ', 'Aramex', '2024/09/09');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('5', 'Literally Whoop whoop', 'Aramex', '2025/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('6', 'Barangaroo', 'Aramex', '2025/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('7', 'Woy Woy', 'DHL', '2025/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('8', 'Wagga Wagga', 'FedEx', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('9', 'Up Shit Creek', 'Aramex', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('10', 'Parliment House', 'DHL', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('11', 'Local Water Hole', 'FedEx', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('12', 'Old Mate Barry\'s', 'FedEx', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('13', 'A place that seems like home but isnt', 'FedEx', '2024/09/09');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('14', '25 Carnation Ave Bankstown NSW 2200', 'DHL', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('15', '65 Dutton St Bankstown NSW 2200', 'DHL', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('16', '57 Dutton St Bankstown NSW 2200', 'DHL', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('17', '185 Hill St Orange NSW 2800', 'DHL', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('18', '88 Margaret St Orange NSW 2800', 'FedEx', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('19', '19 Oak St Orange NSW 2800', 'FedEx', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`, `ShipmentAddress`, `ShipmentType`, `ShipmentExpectedDate`) VALUES ('20', '19 Oak St Orange NSW 2800', 'FedEx', '2021/01/01');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`) VALUES ('21');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`) VALUES ('22');
INSERT INTO `iotbay`.`shipmentdata` (`ShipmentID`) VALUES ('23');


INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `UserID`) VALUES ('1', '2024-05-13', 'Pending', 'On It\'s Way', '111111111', '1111111111');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('2', '2024-05-13', 'Complete', 'Delivered', '111111113', '1', '1111111112');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `UserID`) VALUES ('3', '2024-05-13', 'Not submitted', 'Not Submitted', '111111111', '1111111111');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `UserID`) VALUES ('4', '2024-05-13', 'Not submitted', 'Not Submitted', '111111113', '1111111112');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('5', '2024-05-13', 'Complete', 'Delivered', '111111111', '2', '1111111111');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('6', '2024-05-13', 'Not submitted', 'Not Submitted', '111111113', '21', '1111111112');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('7', '2024-05-13', 'Complete', 'Delivered', '111111113', '3', '1111111112');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('8', '2024-05-13', 'Complete', 'Delivered', '111111111', '4', '1111111111');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('9', '2024-05-13', 'Complete', 'Delivered', '111111114', '5', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('10', '2024-05-13', 'Complete', 'Delivered', '111111111', '6', '1111111111');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('11', '2024-05-13', 'Complete', 'Delivered', '111111114', '7', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('12', '2024-05-13', 'Complete', 'Delivered', '111111111', '8', '1111111111');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('13', '2024-05-13', 'Not submitted', 'Not Submitted', '111111114', '22', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('14', '2024-05-13', 'Complete', 'Delivered', '111111114', '9', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('15', '2024-05-13', 'Not submitted', 'Not Submitted', '111111114', '23', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('16', '2024-05-13', 'Complete', 'Delivered', '111111114', '10', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('17', '2024-05-13', 'Complete', 'Delivered', '111111114', '11', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('18', '2024-05-13', 'Complete', 'Delivered', '111111114', '12', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('19', '2024-05-13', 'Complete', 'Delivered', '111111114', '13', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('20', '2024-05-13', 'Complete', 'Delivered', '111111114', '14', '1111111113');
INSERT INTO `iotbay`.`orders` (`OrderID`, `OrderDate`, `OrderStatus`, `OrderDeliveryStatus`, `PaymentID`, `ShippingID`, `UserID`) VALUES ('21', '2024-05-13', 'Not submitted', 'Not submitted', '21', '21', '4444444444');

INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('2', '1111111117', '5');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('2', '1111111121', '10');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('2', '1111111119', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('1', '1111111117', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('1', '1111111121', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('1', '1111111119', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('7', '2111111111', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('8', '2222222222', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('9', '2333333333', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('10', '2444444444', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('11', '2555555555', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('12', '2666666666', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('13', '1111111118', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('14', '1111111118', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('15', '1111111118', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('16', '1111111118', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('17', '1111111122', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('18', '1111111122', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('19', '1111111122', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('20', '1111111122', '3');
INSERT INTO `iotbay`.`orderlineitem` (`OrderID`, `ProductID`, `OrderAmount`) VALUES ('21', '1111111122', '3');


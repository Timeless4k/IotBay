CREATE schema IotBay;

USE IotBay;

CREATE TABLE User(
    UserID BIGINT primary key,
    UserFirstName varchar(50),
    UserMiddleName varchar(50),
    UserLastName varchar(50),
    UserType ENUM('Admin', 'Customer', 'Employee', 'Guest'), -- could use a enum value
    UserEmail varchar(255),
    UserPhone BIGINT,
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
    ProductReleaseDate varchar(11), -- update to be a datetime
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

CREATE TABLE CardInformation(
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
    PaymentMethod ENUM('Credit Card', 'Debit Card', 'Payment on Delivery'),
    PaymentDate datetime,
    PaymentStatus ENUM('Approved', 'Failed', 'Pending'), -- ahhhh there are errors in the report in regards to type
    CardID BIGINT,
    primary key(PaymentID),
    foreign key(CardID) references CardInformation(CardID)
);

CREATE TABLE ShipmentData(
    ShipmentID BIGINT,
    ShipmentAddress varchar(110),
    ShipmentType ENUM('Express', 'Standard'),
    ShipmentTrackingNumber varchar(11),
    ShipmentExpectedDate datetime,
    primary key(ShipmentID)
);

CREATE TABLE Orders(
    OrderID BIGINT,
    OrderDate datetime,
    OrderStatus varchar(20),
    OrderDeliveryStatus ENUM('Being Prepared', "Hasn't Been Delivered", 'Not Submitted', "On It's Way"),
    PaymentID BIGINT,
    ShippingID BIGINT,
    UserID BIGINT,
    primary key(OrderID),
    foreign key(UserID) references User(UserID),
    foreign key(PaymentID) references Payments(PaymentID),
    foreign key(ShippingID) references ShipmentData(ShipmentID)
);

CREATE TABLE OrderLineItem(
    OrderID BIGINT,
    ProductID BIGINT,
    OrderAmount int(99),
    primary key(OrderID, ProductID),
    foreign key(OrderID) references Orders(OrderID),
    foreign key(ProductID) references ProductData(ProductID)
);





insert into user values(
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

insert into user values(
    2222222222,
    'Marvin',
    'Mellachlan',
    'Murdoc',
    'Customer',
    'mmm@gmail.com',
    '5552222222',
    'Male',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 12:35:12',
    true,
    '2FA222' -- 2fa code
);

insert into user values(
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

insert into user values(
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

insert into user values(
    5555555555,
    'Zylus',
    'Zebra',
    'Zylophone', -- Spelt with a Z
    'Customer',
    'aaa@gmail.com',
    '5554444444',
    'Male',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 11:11:11',
    False,  -- requested his data be removed due to gdpr regulations
    '2FA444' -- 2fa code
);

insert into ProductData values(
    2111111111,
    'ADXL345',
    'InStock',
    'Aug20th',
    500,
    'The ADXL345 is a 3 axis accelerometer with a 10bit resolution and capable of up to 16 g of acceleration',
    'Sensor',
    5.99
);

insert into ProductData values(
    2222222222,
    'Arduino Uno R3',
    'NotInStock',
    'Aug32nd',
    0,
    'The Arduino Uno R3 is a 8bit microcontroller development board with 14 digital in and output pins',
    'Microcontroller',
    25.99
);

insert into ProductData values(
    2333333333,
    'Raspberry Pi 1b+',
    'InStock',
    'Feb25th',
    4,
    'The Raspberry Pi 1b+ is a unix single board computer featuring GPIO pins for interacting with the physical world',
    'Computer',
    500.99
);

insert into ProductData values(
    2444444444,
    'Smart Bulb',
    'InStock',
    'Aug20th',
    270,
    'Smart bulb has wifi and makes your home colourful',
    'Home Iot',
    22.99
);

insert into ProductData values(
    2555555555,
    'Smart Watch',
    'InStock',
    'Aug20th',
    3500,
    'This Smart Watch has the latest and greatest features such as heart rate monitoring',
    'Gear',
    160
);

insert into ProductData values(
    2666666666,
    'Thermostat',
    'InStock',
    'Aug20th',
    500,
    'This Thermostat enables maintaining of precise home temperatures',
    'Home Iot',
    200
);
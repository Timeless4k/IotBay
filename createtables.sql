CREATE schema IotBay;

USE IotBay;

CREATE TABLE User(
    UserID int(10) primary key,
    UserFirstName varchar(50),
    UserMiddleName varchar(50),
    UserLastName varchar(50),
    UserType varchar(10), --could use a enum value
    UserEmail varchar(255),
    UserPhone int(10),
    UserGender varchar(10), -- could use a enum type to restrict inputs
    PasswordHash varchar(64),
    UserCreationDate datetime,
    ActivationFlag boolean,
    LogID int(10), --needs fk
    CardID int(10), --needs fk
    OrderID int(10), --needs fk
    VerificationCode varchar(10),
);

CREATE TABLE ProductData(
    ProductID int(10) primary key,
    ProductName varchar(50),
    ProductStatus varchar(20),
    ProductReleaseDate varchar(10),
    ProductStockLevel int(10),
    ProductDescription varchar(50),
    ProductType varchar(50)
);

CREATE TABLE Order(
    OrderID int(10),
    OrderDate datetime,
    OrderStatus varchar(20),
    OrderDeliveryStatus varchar(20),
    PaymentID int(10),
    ShippingID int(10),
    UserID int(10)
);

CREATE TABLE Payments(
    PaymentID int(10) primary key,
    PaymentAmount decimal(13,2),
    PaymentMethod varchar(50),
    PaymentDate datetime,
    PaymentStatus varchar(20), --ahhhh there are errors in the report
    OrderID int(10),
    CardID int(10)
);

CREATE TABLE OrderLineItem(
    OrderID int(10),
    ProductID int(10),
    OrderAmount int(99)
);

CREATE TABLE AccessData(
    LogID int(10),
    UserID int(10),
    LoginTime datetime,
    LogoutTime datetime
);

CREATE TABLE ShipmentData(
    ShipmentID int(10),
    ShipmentAddress varchar(100),
    ShipmentType varchar(30),
    ShipmentTrackingNumber varchar(10),
    ShipmentExpectedDate datetime,
    OrderID int(10)
);

CREATE TABLE CardInformation(
    CardID int(10),
    CardNumber int(10),
    CardHolderName varchar(128),
    CardExpiry date,
    CardCVV int(3)
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
    6111111111, -- logid
    8111111111, -- cardid
    3111111111, -- orderid
    '2FA111', -- 2fa code
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
    6222222222, -- logid
    8222222222, -- cardid
    3222222222, -- orderid
    '2FA222', -- 2fa code
);

insert into user values(
    3333333333,
    'Sammy',
    'Something',
    'Simpson',
    'Employee',
    'sss@gmail.com',
    '5553333333',
    'Employee',
    'A4DD5658EC0219465B705EA7C7435D9786A3C66D4F448CABD7488DABCEAFB699',
    '2024-01-01 11:11:11',
    true,
    6333333333, -- logid
    8333333333, -- cardid
    3333333333, -- orderid
    '2FA333', -- 2fa code
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
    6444444444, -- logid
    8444444444, -- cardid
    3444444444, -- orderid
    '2FA444', -- 2fa code
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
    False,  --requested his data be removed due to gdpr regulations
    6555555555, -- logid
    8555555555, -- cardid
    3555555555, -- orderid
    '2FA444', -- 2fa code
);


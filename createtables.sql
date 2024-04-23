CREATE schema IotBay;

USE IotBay;

CREATE TABLE User(
    UserID BIGINT primary key,
    UserFirstName varchar(50),
    UserMiddleName varchar(50),
    UserLastName varchar(50),
    UserType varchar(11), -- could use a enum value
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
    ProductStatus varchar(20),
    ProductReleaseDate varchar(11),
    ProductStockLevel BIGINT,
    ProductDescription varchar(50),
    ProductType varchar(50)
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
    PaymentMethod varchar(50),
    PaymentDate datetime,
    PaymentStatus varchar(20), -- ahhhh there are errors in the report in regards to type
    CardID BIGINT,
    primary key(PaymentID),
    foreign key(CardID) references CardInformation(CardID)
);

CREATE TABLE ShipmentData(
    ShipmentID BIGINT,
    ShipmentAddress varchar(110),
    ShipmentType varchar(30),
    ShipmentTrackingNumber varchar(11),
    ShipmentExpectedDate datetime,
    primary key(ShipmentID)
);

CREATE TABLE Orders(
    OrderID BIGINT,
    OrderDate datetime,
    OrderStatus varchar(20),
    OrderDeliveryStatus varchar(20),
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


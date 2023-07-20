DROP DATABASE GSN;
CREATE DATABASE GSN;
use GSN;

CREATE TABLE employee_salary
(
    salary_id VARCHAR(20) primary key,
    time      TIME,
    date      DATE,
    salary    double

);
CREATE TABLE employee
(
    fist_name           VARCHAR(45),
    second_name         VARCHAR(45),
    employee_id         VARCHAR(45) primary key,
    contact_number      VARCHAR(20),
    NIC                 VARCHAR(20),
    Street              VARCHAR(30),
    lane                VARCHAR(30),
    City                VARCHAR(30)
);

CREATE TABLE employee_Attendance
(
    employee_id            VARCHAR(20),
    employee_attendance_Id VARCHAR(20) PRIMARY KEY,
    date                   DATE,
    time                   TIME,
    CONSTRAINT FOREIGN KEY employee_Attendance (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE ON UPDATE CASCADE


);
CREATE TABLE customer
(
    customer_id    VARCHAR(20) primary key,
    fist_name      VARCHAR(45),
    second_name    VARCHAR(45),
    contact_number VARCHAR(20),
    NIC            VARCHAR(20),
    address        VARCHAR(30)


);
CREATE TABLE appointment
(
    appointment_id varchar(20) PRIMARY KEY,
    time           time,
    date           date,
    customer_id    varchar(20),
    address        varchar(30),
    pnnumber       varchar(30),
    service        varchar(30),
    endtime         time,
    CONSTRAINT FOREIGN KEY Appointment (customer_id) REFERENCES customer (customer_id) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Payment
(
    payment_id     VARCHAR(20) PRIMARY KEY,
    appointment_id VARCHAR(20),
    price          decimal,
    time           TIME,
    Date           DATE,
    CONSTRAINT Foreign Key Appointment (appointment_id) REFERENCES Payment (payment_id) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE Appointment_Service_Details
(
    date           DATE,
    appointment_id VARCHAR(20),
    Service_ID     VARCHAR(20),
    CONSTRAINT foreign key Appointment_Service_Details (appointment_id) REFERENCES Appointment (appointment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Product_Item_Stock
(
    Product_Item_Stock_Id VARCHAR(20) PRIMARY KEY ,
    qty                   INT,
    Description           VARCHAR(30),
    brand                 VARCHAR(30)
);

CREATE TABLE Rent_item_order
(
    Rent_item_order_id VARCHAR(20) PRIMARY KEY,
    date               DATE,
    time               TIME,
    price              decimal,
    customer_id        VARCHAR(20),
    CONSTRAINT Foreign Key Rent_item_order (Rent_item_order_id) REFERENCES customer (customer_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Rent_item_details
(
    price                 decimal,
    Rent_item_order_id    VARCHAR(20),
    Product_Item_Stock_Id VARCHAR(20),
    qty                   INT,
    CONSTRAINT FOREIGN KEY  (Rent_item_order_id) REFERENCES Rent_item_order (Rent_item_order_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Foreign Key  (Product_Item_Stock_Id) REFERENCES Product_Item_Stock (Product_Item_Stock_Id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE supplier
(
    Supplier_Id    VARCHAR(20) PRIMARY KEY,
    location       TEXT,
    email          VARCHAR(30),
    name           VARCHAR(45),
    contact_number VARCHAR(15)
);

CREATE TABLE supplier_order
(
    Supplier_Id VARCHAR(20),
    Order_Id VARCHAR(20)primary key ,
    date        DATE,
    time        TIME,
    qty INT,
    constraint foreign key(Supplier_Id) REFERENCES Supplier(Supplier_Id)ON DELETE CASCADE ON UPDATE  CASCADE
);
CREATE TABLE order_details(
                              price decimal(10),
                              qty INT,
                              Product_Item_Stock_Id VARCHAR(20),
                              Supplier_Id VARCHAR(20),
                              CONSTRAINT Foreign Key (Supplier_Id) REFERENCES Supplier(Supplier_Id) ON UPDATE CASCADE ON DELETE CASCADE ,
                              CONSTRAINT FOREIGN KEY (Product_Item_Stock_Id)REFERENCES Product_Item_Stock(Product_Item_Stock_Id)ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE password
(
    username            VARCHAR(30),
    password VARCHAR(30) PRIMARY KEY

);

CREATE TABLE service
(
    service_id    VARCHAR(20) primary key,
    Service_type VARCHAR(30),
    price    VARCHAR(45)


);



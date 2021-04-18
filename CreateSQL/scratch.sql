create schema wolfwr collate utf8mb4_general_ci;

create table Bills
(
    BillID int unsigned auto_increment
        primary key,
    Amount double not null,
    PaymentStatus tinyint(1) null
);

create table ClubMembers
(
    CustomerID int unsigned auto_increment
        primary key,
    Email varchar(128) not null,
    FirstName varchar(128) not null,
    LastName varchar(128) not null,
    Phone varchar(16) not null,
    Address varchar(128) not null,
    Level varchar(16) not null,
    ActiveStatus tinyint(1) not null
);

create table RegistrationRecords
(
    CustomerID int unsigned not null
        primary key,
    SignUpDate date not null,
    StaffID int not null,
    StoreID int not null,
    constraint registrationrecords_ibfk_1
        foreign key (CustomerID) references ClubMembers (CustomerID)
            on update cascade
);

create table ReturnReward
(
    BillID int unsigned not null,
    CustomerID int unsigned not null,
    primary key (BillID, CustomerID),
    constraint returnreward_ibfk_1
        foreign key (BillID) references Bills (BillID)
            on update cascade,
    constraint returnreward_ibfk_2
        foreign key (CustomerID) references ClubMembers (CustomerID)
            on update cascade
);

create index CustomerID
    on ReturnReward (CustomerID);

create table SignUp
(
    CustomerID int unsigned not null
        primary key,
    constraint signup_ibfk_1
        foreign key (CustomerID) references ClubMembers (CustomerID)
            on update cascade
);

create table Suppliers
(
    SupplierID int unsigned auto_increment
        primary key,
    SupplierName varchar(128) not null,
    Location varchar(128) not null,
    Phone varchar(16) not null,
    Email varchar(128) not null
);

create table OwedTo
(
    BillID int unsigned not null,
    SupplierID int unsigned not null,
    primary key (BillID, SupplierID),
    constraint owedto_ibfk_1
        foreign key (BillID) references Bills (BillID)
            on update cascade,
    constraint owedto_ibfk_2
        foreign key (SupplierID) references Suppliers (SupplierID)
            on update cascade
);

create index SupplierID
    on OwedTo (SupplierID);

create table merchandise
(
    ProductID int unsigned auto_increment
        primary key,
    ProductName varchar(128) not null,
    Quantity int unsigned not null,
    MarketPrice double not null,
    BuyPrice double not null,
    ProductDate date not null,
    ExpirationDate date not null,
    SupplierID int unsigned not null,
    StoreID int not null,
    constraint merchandise_ibfk_1
        foreign key (SupplierID) references Suppliers (SupplierID)
            on update cascade
);

create table SuppliedBy
(
    ProductID int unsigned not null,
    SupplierID int unsigned not null,
    primary key (ProductID, SupplierID),
    constraint suppliedby_ibfk_1
        foreign key (ProductID) references merchandise (ProductID)
            on update cascade,
    constraint suppliedby_ibfk_2
        foreign key (SupplierID) references Suppliers (SupplierID)
            on update cascade
);

create index SupplierID
    on SuppliedBy (SupplierID);

create index SupplierID
    on merchandise (SupplierID);

create index quantityIndex
    on merchandise (Quantity);

create table staff
(
    StaffID int unsigned auto_increment
        primary key,
    Name varchar(128) not null,
    Age int unsigned not null,
    Address varchar(128) not null,
    Phone varchar(16) not null,
    EmploymentTime datetime not null
);

create table Admin
(
    StaffID int unsigned not null
        primary key,
    constraint admin_ibfk_1
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create table AssistantManager
(
    StaffID int unsigned not null
        primary key,
    constraint assistantmanager_ibfk_1
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create table BillingStaff
(
    StaffID int unsigned not null
        primary key,
    constraint billingstaff_ibfk_1
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create table Cashier
(
    StaffID int unsigned not null
        primary key,
    constraint cashier_ibfk_1
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create table Generate
(
    BillID int unsigned not null,
    StaffID int unsigned not null,
    primary key (BillID, StaffID),
    constraint generate_ibfk_1
        foreign key (BillID) references Bills (BillID)
            on update cascade,
    constraint generate_ibfk_2
        foreign key (StaffID) references BillingStaff (StaffID)
            on update cascade
);

create index StaffID
    on Generate (StaffID);

create table PayFor
(
    BillID int unsigned not null,
    StaffID int unsigned not null,
    primary key (BillID, StaffID),
    constraint payfor_ibfk_1
        foreign key (BillID) references Bills (BillID)
            on update cascade,
    constraint payfor_ibfk_2
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create index StaffID
    on PayFor (StaffID);

create table SetUp
(
    CustomerID int unsigned not null,
    StaffID int unsigned not null,
    primary key (CustomerID, StaffID),
    constraint setup_ibfk_1
        foreign key (CustomerID) references ClubMembers (CustomerID)
            on update cascade,
    constraint setup_ibfk_2
        foreign key (StaffID) references AssistantManager (StaffID)
            on update cascade
);

create index StaffID
    on SetUp (StaffID);

create table Store
(
    StoreID int unsigned auto_increment,
    ManagerID int unsigned not null,
    Address varchar(128) not null,
    Phone varchar(16) not null,
    primary key (StoreID, ManagerID),
    constraint store_ibfk_1
        foreign key (ManagerID) references staff (StaffID)
            on update cascade
);

create table Employ
(
    StoreID int unsigned not null,
    StaffID int unsigned not null,
    primary key (StoreID, StaffID),
    constraint employ_ibfk_1
        foreign key (StoreID) references Store (StoreID)
            on update cascade,
    constraint employ_ibfk_2
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create index StaffID
    on Employ (StaffID);

create table OnSaleProductions
(
    ProductID int unsigned not null,
    StoreID int unsigned not null,
    Discount double not null,
    ValidDate date not null,
    primary key (ProductID, StoreID),
    constraint onsaleproductions_ibfk_1
        foreign key (ProductID) references merchandise (ProductID)
            on update cascade,
    constraint onsaleproductions_ibfk_2
        foreign key (StoreID) references Store (StoreID)
            on update cascade
);

create index StoreID
    on OnSaleProductions (StoreID);

create index ManagerID
    on Store (ManagerID);

create table WarehouseChecker
(
    StaffID int unsigned not null
        primary key,
    constraint warehousechecker_ibfk_1
        foreign key (StaffID) references staff (StaffID)
            on update cascade
);

create table Maintain
(
    ProductID int unsigned not null,
    StaffID int unsigned not null,
    primary key (ProductID, StaffID),
    constraint maintain_ibfk_1
        foreign key (ProductID) references merchandise (ProductID)
            on update cascade,
    constraint maintain_ibfk_2
        foreign key (StaffID) references WarehouseChecker (StaffID)
            on update cascade
);

create index StaffID
    on Maintain (StaffID);

create table transactionrecords
(
    TransactionID int unsigned auto_increment
        primary key,
    CashierID int unsigned not null,
    StoreID int unsigned not null,
    TotalPrice double not null,
    Date datetime not null,
    CustomerID int unsigned null,
    constraint transactionrecords_ibfk_1
        foreign key (CashierID) references Cashier (StaffID)
            on update cascade,
    constraint transactionrecords_ibfk_2
        foreign key (CustomerID) references ClubMembers (CustomerID)
            on update cascade
);

create table CheckOut
(
    TransactionID int unsigned not null,
    StaffID int unsigned not null,
    primary key (TransactionID, StaffID),
    constraint checkout_ibfk_1
        foreign key (TransactionID) references transactionrecords (TransactionID)
            on update cascade,
    constraint checkout_ibfk_2
        foreign key (StaffID) references Cashier (StaffID)
            on update cascade
);

create index StaffID
    on CheckOut (StaffID);

create table TransactionContains
(
    TransactionID int unsigned not null,
    ProductID int unsigned not null,
    Count int unsigned not null,
    Discount double not null,
    primary key (TransactionID, ProductID),
    constraint transactioncontains_ibfk_1
        foreign key (TransactionID) references transactionrecords (TransactionID)
            on update cascade,
    constraint transactioncontains_ibfk_2
        foreign key (ProductID) references merchandise (ProductID)
            on update cascade
);

create index ProductID
    on TransactionContains (ProductID);

create index CashierID
    on transactionrecords (CashierID);

create index CustomerID
    on transactionrecords (CustomerID);

create index dateindex
    on transactionrecords (Date);


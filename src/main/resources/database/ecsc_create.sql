drop database if exists ECSC_SQL;
create database if not exists ECSC_SQL;
use ECSC_SQL;

create table boat
(
    BOAT_ID          int NOT NULL auto_increment primary key,
    MANUFACTURER     varchar(20),
    MANUFACTURE_YEAR varchar(5),
    REGISTRATION_NUM varchar(30),
    MODEL            varchar(40),
    BOAT_NAME        varchar(30),
    SAIL_NUMBER      varchar(20),
    HAS_TRAILER      boolean,
    LENGTH           varchar(4),
    WEIGHT           varchar(6),
    KEEL             varchar(4),
    PHRF             integer
);

ALTER TABLE ECSC_SQL.boat
    ADD DRAFT varchar(15) NULL;
ALTER TABLE ECSC_SQL.boat
    ADD BEAM varchar(20) NULL;
ALTER TABLE ECSC_SQL.boat
    MODIFY COLUMN `LENGTH` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL;
ALTER TABLE ECSC_SQL.boat
    MODIFY COLUMN WEIGHT varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL;
ALTER TABLE ECSC_SQL.boat
    ADD LWL varchar(20) NULL;
ALTER TABLE ECSC_SQL.boat
    MODIFY COLUMN MANUFACTURER varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL;
ALTER TABLE ECSC_SQL.boat ADD AUX TINYINT(1) DEFAULT 0 NOT NULL;


create table boat_memo
(
    BOAT_MEMO_ID int  NOT NULL auto_increment primary key,
    BOAT_ID      int  NOT NULL,
    MEMO_DATE    date NOT NULL,
    MEMO         varchar(2000),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

CREATE TABLE boat_picture
(
    BOAT_PICTURE_ID   int  NOT NULL auto_increment primary key,
    BOAT_ID           int  NOT NULL,
    BOAT_PICTURE_DATE date NOT NULL,
    PICTURE_PATH      varchar(2000),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

create table winter_storage
(
    WS_ID       int           NOT NULL auto_increment primary key,
    BOAT_ID     int           NOT NULL,
    FISCAL_YEAR numeric(4, 0) NOT NULL,
    unique (FISCAL_YEAR, BOAT_ID),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);
ALTER TABLE ECSC_SQL.winter_storage
    MODIFY COLUMN FISCAL_YEAR INTEGER NOT NULL;

create table membership
(
    MS_ID     int        NOT NULL auto_increment primary key,
    P_ID      int UNIQUE NOT NULL,
    JOIN_DATE date,
    MEM_TYPE  varchar(4),
    # ACTIVE_MEMBERSHIP boolean, # this may be redundant because of money
        ADDRESS   varchar(40), # each membership has the same address
        CITY      varchar(20),
    STATE     varchar(4),
    ZIP       varchar(15)
);

ALTER TABLE membership
    ADD UNIQUE (P_ID);

create table membership_id
(
    MID           int           NOT NULL auto_increment primary key,
    FISCAL_YEAR   numeric(4, 0) NOT NULL,
    MS_ID         int           NOT NULL,
    MEMBERSHIP_ID int,
    RENEW         boolean,
    MEM_TYPE      varchar(4),
    SELECTED      boolean,
    foreign key (MS_ID) references membership (MS_ID),
    unique (FISCAL_YEAR, MS_ID),
    unique (FISCAL_YEAR, MEMBERSHIP_ID)
);
ALTER TABLE ECSC_SQL.membership_id
    ADD RETURN_MEMBER BOOL NULL;
ALTER TABLE ECSC_SQL.membership_id
    CHANGE RETURN_MEMBER LATE_RENEW tinyint(1) NULL;
ALTER TABLE ECSC_SQL.membership_id
    MODIFY COLUMN FISCAL_YEAR INT NOT NULL;

create table slip
(
    SLIP_ID      int               NOT NULL auto_increment primary key,
    MS_ID        int unique NULL,
    SLIP_NUM     varchar(4) unique NOT NULL,
    SUBLEASED_TO int unique,
    ALT_TEXT     varchar(20),
    foreign key (MS_ID) references membership (MS_ID)
);

CREATE TABLE fee
(
    FEE_ID int NOT NULL auto_increment primary key,
    FIELD_NAME varchar(40),
    FIELD_VALUE DECIMAL(10,2),
    FIELD_QTY int,
    FEE_YEAR int NOT NULL
);
ALTER TABLE ECSC_SQL.fee ADD Description varchar(40) NULL;

create table memo
(
    MEMO_ID   int         NOT NULL auto_increment primary key,
    MS_ID     int         NOT NULL,
    MEMO_DATE date        NOT NULL,
    MEMO      varchar(2000),
    MONEY_ID  int,
    CATEGORY  varchar(20) NOT NULL,
    foreign key (MS_ID) references membership (MS_ID)
);

create table person
(
    P_ID        int NOT NULL auto_increment primary key,
    MS_ID       int, # attaches person to membership
        MEMBER_TYPE int, # 1 for primary 2 for secondary 3 for children
        F_NAME      varchar(20),
    L_NAME      varchar(20),
    BIRTHDAY    date,
    OCCUPATION  varchar(50),
    BUISNESS    varchar(50),
    IS_ACTIVE   boolean,
    PICTURE     blob,
    foreign key (MS_ID) references membership (MS_ID)
);
ALTER TABLE ECSC_SQL.person
    ADD NICK_NAME varchar(30) NULL;
ALTER TABLE ECSC_SQL.person ADD OLDMSID INT NULL;

create table email
(
    EMAIL_ID     int NOT NULL auto_increment primary key,
    P_ID         int NOT NULL,
    PRIMARY_USE  boolean,
    EMAIL        varchar(60),
    EMAIL_LISTED boolean,
    foreign key (P_ID) references person (P_ID)
);

create table phone
(
    PHONE_ID     int NOT NULL auto_increment primary key,
    P_ID         int NOT NULL,
    PHONE        varchar(30),
    PHONE_TYPE   varchar(30),
    PHONE_LISTED boolean,
    foreign key (P_ID) references person (P_ID)
);

create table boat_owner
(
    MS_ID   int NOT NULL,
    BOAT_ID int NOT NULL,
    foreign key (MS_ID) references membership (MS_ID),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

ALTER TABLE ECSC_SQL.boat_owner
DROP FOREIGN KEY boat_owner_ibfk_2;
-- #We want to change this to be a key but not prevent deletions, so make sure boat exists only for creation

create table deposit
(
    DEPOSIT_ID   int           NOT NULL auto_increment primary key unique,
    DEPOSIT_DATE date          NOT NULL,
    FISCAL_YEAR  numeric(4, 0) NOT NULL,
    BATCH        numeric(2, 0) NOT NULL,
    unique (FISCAL_YEAR, BATCH)
);

ALTER TABLE ECSC_SQL.deposit
    MODIFY COLUMN FISCAL_YEAR INTEGER NOT NULL;
ALTER TABLE ECSC_SQL.deposit
    MODIFY COLUMN BATCH INTEGER NOT NULL;


create table money
(
    MONEY_ID               int NOT NULL auto_increment primary key unique,
    MS_ID                  int NOT NULL,
    FISCAL_YEAR            numeric(4, 0),
    BATCH                  numeric(2, 0),
    OFFICER_CREDIT         numeric(4, 0), #this is the dues and gets derived.
        EXTRA_KEY              numeric(2, 0),
    KAYAK_SHED_KEY         numeric(4, 0),
    SAIL_LOFT_KEY          numeric(4, 0),
    SAIL_SCHOOL_LOFT_KEY   numeric(4, 0),
    BEACH                  numeric(4, 0),
    WET_SLIP               numeric(4, 0),
    KAYAK_RACK             numeric(4, 0),
    KAYAK_SHED             numeric(4, 0),
    SAIL_LOFT              numeric(4, 0),
    SAIL_SCHOOL_LASER_LOFT numeric(4, 0),
    WINTER_STORAGE         numeric(2, 0),
    YSC_DONATION           numeric(4, 0),
    PAID                   numeric(4, 0),
    TOTAL                  numeric(4, 0),
    CREDIT                 numeric(4, 0),
    BALANCE                numeric(4, 0),
    DUES                   numeric(4, 0),
    COMMITED               boolean,
    CLOSED                 boolean,
    OTHER                  numeric(4, 0),
    INITIATION             numeric(4, 0),
    SUPPLEMENTAL           boolean,
    foreign key (MS_ID) references membership (MS_ID)
);

ALTER TABLE ECSC_SQL.money MODIFY COLUMN FISCAL_YEAR INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN BATCH INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN EXTRA_KEY INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN KAYAK_SHED_KEY INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN SAIL_LOFT_KEY INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN SAIL_SCHOOL_LOFT_KEY INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN BEACH INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN KAYAK_RACK INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN KAYAK_SHED INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN SAIL_LOFT INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN SAIL_SCHOOL_LASER_LOFT INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN WINTER_STORAGE INTEGER NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN OFFICER_CREDIT DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN WET_SLIP DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN YSC_DONATION DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN PAID DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN TOTAL DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN CREDIT DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN BALANCE DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN DUES DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN OTHER DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money MODIFY COLUMN INITIATION DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money ADD WORK_CREDIT INT NULL;
ALTER TABLE ECSC_SQL.money ADD OTHER_CREDIT DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.money ADD KAYAK_BEACH_RACK DECIMAL(4,0) DEFAULT 0 NULL;


create table payment
(
    PAY_ID       int           NOT NULL auto_increment primary key,
    MONEY_ID     int           NOT NULL,
    CHECKNUMBER  int,
    PAYMENT_TYPE varchar(4)    NOT NULL,
    PAYMENT_DATE date          NOT NULL,
    AMOUNT       numeric(4, 0) NOT NULL,
    DEPOSIT_ID   int           NOT NULL,
    foreign key (DEPOSIT_ID) references deposit (DEPOSIT_ID),
    foreign key (MONEY_ID) references money (MONEY_ID)
);
ALTER TABLE ECSC_SQL.payment
    MODIFY COLUMN CHECKNUMBER VARCHAR(20) NULL;
ALTER TABLE ECSC_SQL.payment MODIFY COLUMN AMOUNT DECIMAL(10,2) NOT NULL;

# should attach to money_id, if put in early, just create money_id along with it
create table officer
(
    O_ID       int NOT NULL auto_increment primary key,
    P_ID       int NOT NULL,
    BOARD_YEAR numeric(4, 0),
    OFF_TYPE   varchar(20),
    OFF_YEAR   numeric(4, 0), # This maintains the record forever
        foreign key (P_ID) references person (P_ID),
    unique (P_ID, OFF_YEAR, OFF_TYPE)
);

ALTER TABLE ECSC_SQL.officer
    MODIFY COLUMN BOARD_YEAR INTEGER NULL;
ALTER TABLE ECSC_SQL.officer
    MODIFY COLUMN OFF_YEAR INTEGER NULL;


create table defined_fee
(
    FISCAL_YEAR            numeric(4, 0) unique primary key,
    DUES_REGULAR           numeric(4, 0),
    DUES_FAMILY            numeric(4, 0),
    DUES_LAKE_ASSOCIATE    numeric(4, 0),
    DUES_SOCIAL            numeric(4, 0),
    INITIATION             numeric(5, 0),
    WET_SLIP               numeric(4, 0),
    BEACH                  numeric(4, 0),
    WINTER_STORAGE         numeric(4, 0),
    MAIN_GATE_KEY          numeric(4, 0),
    SAIL_LOFT              numeric(4, 0),
    SAIL_LOFT_KEY          numeric(4, 0),
    SAIL_SCHOOL_LASER_LOFT numeric(4, 0),
    SAIL_SCHOOL_LOFT_KEY   numeric(4, 0),
    KAYAK_RACK             numeric(4, 0),
    KAYAK_SHED             numeric(4, 0),
    KAYAK_SHED_KEY         numeric(4, 0),
    WORK_CREDIT            numeric(4, 0)
);

ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN FISCAL_YEAR INTEGER NOT NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN DUES_REGULAR DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN DUES_FAMILY DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN DUES_LAKE_ASSOCIATE DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN DUES_SOCIAL DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN INITIATION DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN WET_SLIP DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN BEACH DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN WINTER_STORAGE DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN MAIN_GATE_KEY DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN SAIL_LOFT DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN SAIL_LOFT_KEY DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN SAIL_SCHOOL_LASER_LOFT DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN SAIL_SCHOOL_LOFT_KEY DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN KAYAK_RACK DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN KAYAK_SHED DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN KAYAK_SHED_KEY DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee MODIFY COLUMN WORK_CREDIT DECIMAL(10,2) NULL;
ALTER TABLE ECSC_SQL.defined_fee ADD KAYAK_BEACH_RACK DECIMAL(10,2) NULL;

CREATE TABLE stats
(
    STAT_ID            int NOT NULL PRIMARY KEY UNIQUE,
    FISCAL_YEAR        int NOT NULL,
    ACTIVE_MEMBERSHIPS int,
    NON_RENEW          int,
    RETURN_MEMBERS     int,
    NEW_MEMBERS        int,
    SECONDARY_MEMBERS  int,
    DEPENDANTS         int,
    NUMBER_OF_BOATS    int,
    FAMILY             int,
    REGULAR            int,
    SOCIAL             int,
    LAKEASSOCIATES     int,
    LIFEMEMBERS        int,
    RACEFELLOWS        int,
    STUDENT            int,
    DEPOSITS           DECIMAL(13, 2),
    INIATION           DECIMAL(13, 2)
);

CREATE TABLE awards
(
    AWARD_ID   int         NOT NULL auto_increment primary key,
    P_ID       int         NOT NULL,
    AWARD_YEAR varchar(10) NOT NULL,
    AWARD_TYPE varchar(10) NOT NULL,
    foreign key (P_ID) references person (P_ID)
);

-- # one-to-one relation with money
create table work_credit
(
    MONEY_ID int NOT NULL primary key unique,
    MS_ID    int NOT NULL,
    RACING   numeric(4, 0),
    HARBOR   numeric(4, 0),
    SOCIAL   numeric(4, 0),
    OTHER    numeric(4, 0),
    foreign key (MONEY_ID) references money (MONEY_ID) on DELETE no action on UPDATE no action
);
ALTER TABLE ECSC_SQL.work_credit
    MODIFY COLUMN RACING INTEGER NULL;
ALTER TABLE ECSC_SQL.work_credit
    MODIFY COLUMN HARBOR INTEGER NULL;
ALTER TABLE ECSC_SQL.work_credit
    MODIFY COLUMN SOCIAL INTEGER NULL;
ALTER TABLE ECSC_SQL.work_credit
    MODIFY COLUMN OTHER INTEGER NULL;


-- #one-to-one relation with membership
create table waitlist
(
    MS_ID          int NOT NULL primary key unique,
    SLIPWAIT       boolean,
    KAYAKRACKWAIT  boolean,
    SHEDWAIT       boolean,
    WANTSUBLEASE   boolean,
    WANTRELEASE    boolean,
    WANTSLIPCHANGE boolean,
    foreign key (MS_ID) references membership (MS_ID) on DELETE no action on UPDATE no action
);

ALTER TABLE ECSC_SQL.msid_hash MODIFY COLUMN HASH BIGINT NOT NULL;

CREATE TABLE id_change
(
    CHANGE_ID   int         NOT NULL auto_increment primary key,
    ID_YEAR     int         NOT NULL unique,
    CHANGED      boolean
);

-- not sure what this is for, there are no rows locally
CREATE TABLE api_key
(
    API_ID   int         	NOT NULL auto_increment primary key,
    NAME     varchar(50)    NOT NULL unique,
    APIKEY   varchar(50)	NOT NULL unique,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table form_msid_hash
(
    HASH_ID    int        NOT NULL auto_increment primary key,
    HASH       int unique NOT NULL,
    MS_ID      int        NOT NULL,
    foreign key (MS_ID) references membership (MS_ID) on DELETE no action on UPDATE no action
);

-- This one row table holds email credentials to send email
CREATE TABLE ECSC_SQL.form_email_auth
(
    HOST     varchar(100),
    PORT     int,
    USER     varchar(100) primary key unique,
    PASS     varchar(100),
    PROTOCOL varchar(20),
    SMTP_AUTH boolean,
    TTLS     boolean,
    DEBUG    boolean
);

-- Table to record everytime a has request for a hash is made
CREATE TABLE ECSC_SQL.form_hash_request
(
    FORM_HASH_ID    int NOT NULL auto_increment primary key unique,
    REQ_DATE        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRI_MEM         varchar(20),
    LINK			varchar(120),
    MSID            int NOT NULL,
    MAILED_TO       varchar(120)
);

-- Table to record everytime a form request is made
CREATE TABLE ECSC_SQL.form_request
(
    FORM_ID    int NOT NULL auto_increment primary key unique,
    REQ_DATE        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRI_MEM         varchar(20),
    SUCCESS			boolean
);

-- This one row table holds settings for program
CREATE TABLE ECSC_SQL.form_settings
(
    PORT int,
    LINK varchar(200)
);
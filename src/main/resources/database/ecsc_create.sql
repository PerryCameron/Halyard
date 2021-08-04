drop database  if exists ECSC_SQL;
create database if not exists ECSC_SQL;
use ECSC_SQL;

 create table boat(
	BOAT_ID int NOT NULL auto_increment primary key,
    MANUFACTURER varchar(20),
    MANUFACTURE_YEAR varchar(5),
    REGISTRATION_NUM varchar(30),
    MODEL varchar(40),
    BOAT_NAME varchar(30),
    SAIL_NUMBER varchar(20),
    HAS_TRAILER boolean,
    LENGTH varchar(4),
    WEIGHT varchar(6),
    KEEL varchar(4),
    PHRF integer
    );
   
	ALTER TABLE ECSC_SQL.boat ADD DRAFT varchar(15) NULL;
	ALTER TABLE ECSC_SQL.boat ADD BEAM varchar(20) NULL;
	ALTER TABLE ECSC_SQL.boat MODIFY COLUMN `LENGTH` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL;
	ALTER TABLE ECSC_SQL.boat MODIFY COLUMN WEIGHT varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL;
	ALTER TABLE ECSC_SQL.boat ADD LWL varchar(20) NULL;
	ALTER TABLE ECSC_SQL.boat MODIFY COLUMN MANUFACTURER varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL;

	create table boat_memo(
    BOAT_MEMO_ID int NOT NULL auto_increment primary key,
    BOAT_ID int NOT NULL,
    MEMO_DATE date NOT NULL,
    MEMO varchar(2000),
    foreign key(BOAT_ID) references boat(BOAT_ID)
    );
   
   	CREATE TABLE boat_picture(
   	BOAT_PICTURE_ID int NOT NULL auto_increment primary key,
   	BOAT_ID int NOT NULL,
   	BOAT_PICTURE_DATE date NOT NULL,
    PICTURE_PATH varchar(2000),
    foreign key(BOAT_ID) references boat(BOAT_ID)
   	);
    
    create table winter_storage(
    WS_ID int NOT NULL auto_increment primary key,
    BOAT_ID int NOT NULL,
    FISCAL_YEAR numeric(4,0) NOT NULL,
    unique (FISCAL_YEAR,BOAT_ID),
    foreign key(BOAT_ID) references boat(BOAT_ID)
    );
    
	create table membership(
    MS_ID  int NOT NULL auto_increment primary key,
    P_ID int UNIQUE NOT NULL, 
    JOIN_DATE date,
    MEM_TYPE varchar(4),
    # ACTIVE_MEMBERSHIP boolean, # this may be redundant because of money
	ADDRESS varchar(40),  # each membership has the same address
    CITY varchar(20),
    STATE varchar(4),
    ZIP varchar(15)
    ); 
   
   	ALTER TABLE membership ADD UNIQUE (P_ID);
    
    create table membership_id (
    MID int NOT NULL auto_increment primary key,
    FISCAL_YEAR numeric(4,0) NOT NULL,
    MS_ID  int NOT NULL,
    MEMBERSHIP_ID int,
    RENEW boolean,
    MEM_TYPE varchar(4),
    SELECTED boolean,
    foreign key(MS_ID) references membership(MS_ID),
	unique (FISCAL_YEAR,MS_ID),
    unique (FISCAL_YEAR,MEMBERSHIP_ID)
    );
    ALTER TABLE ECSC_SQL.membership_id ADD RETURN_MEMBER BOOL NULL;
    ALTER TABLE ECSC_SQL.membership_id CHANGE RETURN_MEMBER LATE_RENEW tinyint(1) NULL;

    
    create table slip(
    SLIP_ID  int NOT NULL auto_increment primary key,
    MS_ID int unique NOT NULL,
	SLIP_NUM varchar(4) unique NOT NULL,
    SUBLEASED_TO int unique,
    foreign key(MS_ID) references membership(MS_ID)
    );
    
    create table memo(
    MEMO_ID int NOT NULL auto_increment primary key,
    MS_ID int NOT NULL,
    MEMO_DATE date NOT NULL,
    MEMO varchar(2000),
    MONEY_ID int,
    CATEGORY varchar(20) NOT NULL,
    foreign key(MS_ID) references membership(MS_ID)
    );
    
	create table person (
	P_ID int NOT NULL auto_increment primary key,
    MS_ID int,  # attaches person to membership
    MEMBER_TYPE int,  # 1 for primary 2 for secondary 3 for children
	F_NAME varchar(20),
	L_NAME varchar(20),
	BIRTHDAY date,
    OCCUPATION varchar(50),
    BUISNESS varchar(50),
    IS_ACTIVE boolean,
    PICTURE blob,
    foreign key(MS_ID) references membership(MS_ID)
	);
    ALTER TABLE ECSC_SQL.person ADD NICK_NAME varchar(30) NULL;
    
	create table email(
    EMAIL_ID int NOT NULL auto_increment primary key,
	P_ID int NOT NULL,
    PRIMARY_USE boolean,
    EMAIL varchar(60),
    EMAIL_LISTED boolean,
    foreign key(P_ID) references person(P_ID)
    );
     
	create table phone(
    PHONE_ID int NOT NULL auto_increment primary key,
    P_ID int NOT NULL, # determines who phone number belongs to
    PHONE varchar(30),
    PHONE_TYPE varchar(30),
    PHONE_LISTED boolean,
    foreign key(P_ID) references person(P_ID)
    );
    
	create table boat_owner(
	MS_ID int NOT NULL,
	BOAT_ID int NOT NULL,
	foreign key(MS_ID) references membership(MS_ID),
	foreign key(BOAT_ID) references boat(BOAT_ID)
	); 

	ALTER TABLE ECSC_SQL.boat_owner DROP FOREIGN KEY boat_owner_ibfk_2;
    #We want to change this to be a key but not prevent deletions, so make sure boat exists only for creation

    create table deposit(
    DEPOSIT_ID int NOT NULL auto_increment primary key unique,
    DEPOSIT_DATE date NOT NULL,
    FISCAL_YEAR numeric(4,0) NOT NULL,
    BATCH numeric(2,0) NOT NULL,
    unique (FISCAL_YEAR,BATCH)
    );
    
    create table money(
	MONEY_ID int NOT NULL auto_increment primary key unique,
	MS_ID int NOT NULL,
	FISCAL_YEAR numeric(4,0),
    BATCH numeric(2,0),
	OFFICER_CREDIT numeric(4,0), #this is the dues and gets derived.
	EXTRA_KEY numeric(2,0),
    KAYAK_SHED_KEY numeric(4,0),
    SAIL_LOFT_KEY numeric (4,0),
    SAIL_SCHOOL_LOFT_KEY numeric(4,0),
    BEACH numeric(4,0),
    WET_SLIP numeric(4,0),
	KAYAK_RACK numeric (4,0),
	KAYAK_SHED numeric(4,0),
	SAIL_LOFT numeric (4,0),
	SAIL_SCHOOL_LASER_LOFT numeric (4,0),
	WINTER_STORAGE numeric (2,0),
	YSC_DONATION numeric(4,0),
	PAID numeric(4,0),
    TOTAL numeric(4,0),
    CREDIT numeric(4,0),
    BALANCE numeric(4,0),
    DUES numeric(4,0),
    COMMITED boolean,
    CLOSED boolean,
    OTHER numeric(4,0),
    INITIATION numeric(4,0),
    SUPPLEMENTAL boolean,
	foreign key(MS_ID) references membership(MS_ID)
	);
	
    
	create table payment(
    PAY_ID int NOT NULL auto_increment primary key,
    MONEY_ID int NOT NULL,
    CHECKNUMBER int,
    PAYMENT_TYPE varchar(4) NOT NULL,
    PAYMENT_DATE date NOT NULL,
    AMOUNT numeric(4,0) NOT NULL,
    DEPOSIT_ID int NOT NULL,
    foreign key(DEPOSIT_ID) references deposit(DEPOSIT_ID),
    foreign key(MONEY_ID) references money(MONEY_ID)
    );
	ALTER TABLE ECSC_SQL.payment MODIFY COLUMN CHECKNUMBER VARCHAR(20) NULL;

    # should attach to money_id, if put in early, just create money_id along with it
	create table officer(
    O_ID int NOT NULL auto_increment primary key,
    P_ID int NOT NULL,
    BOARD_YEAR numeric(4,0),
    OFF_TYPE varchar(20),
    OFF_YEAR numeric(4,0), # This maintains the record forever
    foreign key(P_ID) references person(P_ID),
    unique (P_ID,OFF_YEAR,OFF_TYPE)
    );
    
	create table defined_fee (
	FISCAL_YEAR numeric(4,0) unique primary key,
	DUES_REGULAR numeric(4,0),
	DUES_FAMILY numeric(4,0),
	DUES_LAKE_ASSOCIATE numeric(4,0),
	DUES_SOCIAL numeric(4,0),
	INITIATION numeric(5,0),
	WET_SLIP numeric(4,0),
	BEACH numeric(4,0),
	WINTER_STORAGE numeric(4,0),
	MAIN_GATE_KEY numeric (4,0),
	SAIL_LOFT numeric (4,0),
	SAIL_LOFT_KEY numeric (4,0),
	SAIL_SCHOOL_LASER_LOFT numeric (4,0),
	SAIL_SCHOOL_LOFT_KEY numeric(4,0),
	KAYAK_RACK numeric (4,0),
	KAYAK_SHED numeric(4,0),
	KAYAK_SHED_KEY numeric(4,0),
    WORK_CREDIT numeric(4,0)
	);

	CREATE TABLE stats (
 	STAT_ID int NOT NULL PRIMARY KEY UNIQUE,
 	FISCAL_YEAR int NOT NULL,
 	ACTIVE_MEMBERSHIPS int,
 	NON_RENEW int,
 	RETURN_MEMBERS int,
 	NEW_MEMBERS int,
 	SECONDARY_MEMBERS int,
 	DEPENDANTS int,
 	NUMBER_OF_BOATS int,
 	FAMILY int,
 	REGULAR int,
 	SOCIAL int,
 	LAKEASSOCIATES int,
 	LIFEMEMBERS int,
 	RACEFELLOWS int,
 	STUDENT int,
 	DEPOSITS DECIMAL(13,2),
 	INIATION DECIMAL(13,2)
	);

	CREATE TABLE awards ( 
	AWARD_ID int NOT NULL auto_increment primary key,
	P_ID int NOT NULL,
    AWARD_YEAR varchar(10) NOT NULL,
    AWARD_TYPE varchar(10) NOT NULL,
    foreign key(P_ID) references person(P_ID)
	);

	# one-to-one relation with money
	create table work_credit (
	MONEY_ID int NOT NULL primary key unique,
    MS_ID int NOT NULL,
	RACING numeric(4,0),
	HARBOR numeric(4,0),
	SOCIAL numeric(4,0),
	OTHER numeric(4,0),
	foreign key(MONEY_ID) references money(MONEY_ID) on DELETE no action on UPDATE no action
	);
    
    #one-to-one relation with membership
    create table waitlist (
    MS_ID int NOT NULL primary key unique ,
    SLIPWAIT boolean,
    KAYAKRACKWAIT boolean,
    SHEDWAIT boolean,
    WANTSUBLEASE boolean,
    WANTRELEASE boolean,
    WANTSLIPCHANGE boolean,
    foreign key(MS_ID) references membership(MS_ID) on DELETE no action on UPDATE no action
    );

    

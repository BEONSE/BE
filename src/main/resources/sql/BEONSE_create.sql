CREATE SEQUENCE branch_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE branchimage_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE coupon_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE mateBoard_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE mateComment_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE member_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE reviewBoard_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE point_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;
CREATE SEQUENCE reservation_seq START WITH 1 INCREMENT BY 1 MAXVALUE 999999 CYCLE NOCACHE;

--DROP TABLE branch CASCADE CONSTRAINTS;
--DROP TABLE branchimage CASCADE CONSTRAINTS;
--DROP TABLE coupon CASCADE CONSTRAINTS;
--DROP TABLE mateboard CASCADE CONSTRAINTS;
--DROP TABLE matecomment CASCADE CONSTRAINTS;
--DROP TABLE member CASCADE CONSTRAINTS;
--DROP TABLE point CASCADE CONSTRAINTS;
--DROP TABLE reservation CASCADE CONSTRAINTS;
--DROP TABLE reviewboard CASCADE CONSTRAINTS;
--
--DROP SEQUENCE member_seq;
--DROP SEQUENCE branch_seq;
--DROP SEQUENCE reviewBoard_seq;
--DROP SEQUENCE mateBoard_seq;
--DROP SEQUENCE mateComment_seq;
--DROP SEQUENCE point_seq;
--DROP SEQUENCE coupon_seq;
--DROP SEQUENCE reservation_seq;
--DROP SEQUENCE branchimage_seq;

CREATE TABLE member (
    mid                 INTEGER NOT NULL,
    email               NVARCHAR2(30) NOT NULL,
    nickname            NVARCHAR2(20) NOT NULL,
    password            NVARCHAR2(100) NOT NULL,
    name                NVARCHAR2(10) NOT NULL,
    address             NVARCHAR2(100) NOT NULL,
    role                NVARCHAR2(10) NOT NULL,
    payment_amount      NUMBER DEFAULT 0 NOT NULL,
    point_amount        NUMBER DEFAULT 0 NOT NULL,
    status              CHAR(1)DEFAULT '0' NOT NULL,
    image               BLOB,
    original_file_name  NVARCHAR2(100),
    image_type          NVARCHAR2(100),
    is_approval         NVARCHAR2(10),
    created_at          TIMESTAMP NOT NULL,
    modified_at         TIMESTAMP NOT NULL
);

ALTER TABLE member ADD CONSTRAINT member_pk PRIMARY KEY ( mid );

CREATE TABLE branch (
    bid                 INTEGER NOT NULL,
    member_mid          INTEGER NOT NULL,
    name                NVARCHAR2(50) NOT NULL,
    address             NVARCHAR2(50) NOT NULL,
    lat                 FLOAT NOT NULL,
    lng                 FLOAT NOT NULL,
    image               BLOB,
    original_file_name  NVARCHAR2(100),
    image_type          NVARCHAR2(100),
    introduction        NVARCHAR2(1000),
    created_at          TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    modified_at         TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

ALTER TABLE branch ADD CONSTRAINT branch_pk PRIMARY KEY ( bid );

CREATE TABLE branchimage (
    bmid                INTEGER NOT NULL,
    branch_bid          INTEGER NOT NULL,
    image               BLOB,
    original_file_name  NVARCHAR2(100),
    image_type          NVARCHAR2(100)
);

ALTER TABLE branchimage ADD CONSTRAINT branchimage_pk PRIMARY KEY ( bmid );

CREATE TABLE coupon (
    cid            INTEGER NOT NULL,
    member_mid     INTEGER NOT NULL,
    type           NVARCHAR2(20) NOT NULL,
    price          NUMBER NOT NULL,
    branch_name    NVARCHAR2(50),
    is_used        CHAR(1) DEFAULT '0' NOT NULL,
    payment_date   TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    used_date      TIMESTAMP DEFAULT SYSTIMESTAMP
);

ALTER TABLE coupon ADD CONSTRAINT coupon_pk PRIMARY KEY ( cid );

CREATE TABLE mateboard (
    mbid        INTEGER NOT NULL,
    member_mid  INTEGER NOT NULL,
    branch_name NVARCHAR2(50) NOT NULL,
    title       NVARCHAR2(50) NOT NULL,
    content     NVARCHAR2(1000) NOT NULL,
    status      CHAR(1),
    created_at  TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    modified_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

ALTER TABLE mateboard ADD CONSTRAINT mateboard_pk PRIMARY KEY ( mbid );

CREATE TABLE matecomment (
    mcid           INTEGER NOT NULL,
    member_mid     INTEGER NOT NULL,
    mateboard_mbid INTEGER NOT NULL,
    content        NVARCHAR2(1000) NOT NULL,
    status         CHAR(1) DEFAULT '0' NOT NULL,
    created_at     TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

ALTER TABLE matecomment ADD CONSTRAINT matecomment_pk PRIMARY KEY ( mcid );


CREATE TABLE point (
    pid           INTEGER NOT NULL,
    member_mid    INTEGER NOT NULL,
    points        NUMBER DEFAULT 0 NOT NULL,
    payment_price NUMBER DEFAULT 0 NOT NULL,
    card_name     NVARCHAR2(20) NOT NULL,
    card_number   NVARCHAR2(19) NOT NULL,
    payment_date  TIMESTAMP NOT NULL
);

ALTER TABLE point ADD CONSTRAINT point_pk PRIMARY KEY ( pid );

CREATE TABLE reviewboard (
    rbid                INTEGER NOT NULL,
    member_mid          INTEGER NOT NULL,
    branch_bid          INTEGER NOT NULL,
    coupon_cid          INTEGER NOT NULL,
    title               NVARCHAR2(30) NOT NULL,
    content             NVARCHAR2(1000) NOT NULL,
    status              CHAR(1) DEFAULT '0' NOT NULL,
    image               BLOB,
    original_file_name  NVARCHAR2(100),
    image_type          NVARCHAR2(100),
    created_at          TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    modified_at         TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

ALTER TABLE reviewboard ADD CONSTRAINT board_pk PRIMARY KEY ( rbid );

CREATE TABLE reservation (
    rvid             INTEGER NOT NULL,
    member_mid       INTEGER NOT NULL,
    branch_bid       INTEGER NOT NULL,
    reservation_time TIMESTAMP,
    status           CHAR(1) DEFAULT '0' NOT NULL
);

ALTER TABLE reservation ADD CONSTRAINT reservation_pk PRIMARY KEY ( rvid );

ALTER TABLE reviewboard
    ADD CONSTRAINT board_branch_fk FOREIGN KEY ( branch_bid )
        REFERENCES branch ( bid );

ALTER TABLE reviewboard
    ADD CONSTRAINT board_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );

ALTER TABLE reviewboard
    ADD CONSTRAINT board_coupon_fk FOREIGN KEY ( coupon_cid )
        REFERENCES coupon ( cid );
        
ALTER TABLE branch
    ADD CONSTRAINT branch_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );


ALTER TABLE mateboard
    ADD CONSTRAINT mateboard_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );

ALTER TABLE matecomment
    ADD CONSTRAINT matecomment_mateboard_fk FOREIGN KEY ( mateboard_mbid )
        REFERENCES mateboard ( mbid );

ALTER TABLE matecomment
    ADD CONSTRAINT matecomment_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );

ALTER TABLE point
    ADD CONSTRAINT point_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );

ALTER TABLE reservation
    ADD CONSTRAINT reservation_branch_fk FOREIGN KEY ( branch_bid )
        REFERENCES branch ( bid );

ALTER TABLE reservation
    ADD CONSTRAINT reservation_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );

ALTER TABLE coupon
    ADD CONSTRAINT coupon_member_fk FOREIGN KEY ( member_mid )
        REFERENCES member ( mid );

ALTER TABLE branchimage
    ADD CONSTRAINT branchimage_branch_fk FOREIGN KEY ( branch_bid )
        REFERENCES branch ( bid );
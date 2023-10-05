-- liquibase formatted sql

-- changeset dominikbicenc:1.0
CREATE TABLE address
(
    id           UUID        NOT NULL,
    changed      TIMESTAMP WITHOUT TIME ZONE,
    changed_by   VARCHAR(40),
    created      TIMESTAMP WITHOUT TIME ZONE,
    created_by   VARCHAR(40),
    deleted      BOOLEAN     NOT NULL,
    deleted_time TIMESTAMP WITHOUT TIME ZONE,
    deleted_by   VARCHAR(40),
    version      INTEGER,
    name         VARCHAR(60) NOT NULL,
    code         VARCHAR(10),
    street       VARCHAR(60) NOT NULL,
    zip_code     VARCHAR(10) NOT NULL,
    city         VARCHAR(60) NOT NULL,
    gps_id       UUID,
    country_id   VARCHAR(3)  NOT NULL,
    CONSTRAINT pk_adress PRIMARY KEY (id)
);

CREATE TABLE asset
(
    id            UUID         NOT NULL,
    external_code VARCHAR(20),
    changed       TIMESTAMP WITHOUT TIME ZONE,
    changed_by    VARCHAR(40),
    created       TIMESTAMP WITHOUT TIME ZONE,
    created_by    VARCHAR(40),
    deleted       BOOLEAN      NOT NULL,
    deleted_time  TIMESTAMP WITHOUT TIME ZONE,
    deleted_by    VARCHAR(40),
    version       INTEGER,
    name          VARCHAR(60)  NOT NULL,
    code          VARCHAR(10),
    customer_id   UUID         NOT NULL,
    state         VARCHAR(255) NOT NULL,
    location_id   UUID,
    CONSTRAINT pk_asset PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id           UUID    NOT NULL,
    changed      TIMESTAMP WITHOUT TIME ZONE,
    changed_by   VARCHAR(40),
    created      TIMESTAMP WITHOUT TIME ZONE,
    created_by   VARCHAR(40),
    deleted      BOOLEAN NOT NULL,
    deleted_time TIMESTAMP WITHOUT TIME ZONE,
    deleted_by   VARCHAR(40),
    version      INTEGER,
    asset_id     UUID,
    text         TEXT    NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);

CREATE TABLE contact
(
    id             UUID         NOT NULL,
    changed        TIMESTAMP WITHOUT TIME ZONE,
    changed_by     VARCHAR(40),
    created        TIMESTAMP WITHOUT TIME ZONE,
    created_by     VARCHAR(40),
    deleted        BOOLEAN      NOT NULL,
    deleted_time   TIMESTAMP WITHOUT TIME ZONE,
    deleted_by     VARCHAR(40),
    version        INTEGER,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    type           VARCHAR(255) NOT NULL,
    phone_number   VARCHAR(9)   NOT NULL,
    email          VARCHAR(255) NOT NULL,
    priority_order INTEGER      NOT NULL,
    description    VARCHAR(255),
    asset_id       UUID,
    CONSTRAINT pk_contact PRIMARY KEY (id)
);

CREATE TABLE country
(
    id     VARCHAR(3) NOT NULL,
    enable BOOLEAN    NOT NULL,
    CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE gps
(
    id           UUID             NOT NULL,
    changed      TIMESTAMP WITHOUT TIME ZONE,
    changed_by   VARCHAR(40),
    created      TIMESTAMP WITHOUT TIME ZONE,
    created_by   VARCHAR(40),
    deleted      BOOLEAN          NOT NULL,
    deleted_time TIMESTAMP WITHOUT TIME ZONE,
    deleted_by   VARCHAR(40),
    version      INTEGER,
    longitude    DOUBLE PRECISION NOT NULL,
    latitude     DOUBLE PRECISION NOT NULL,
    type         VARCHAR(255)     NOT NULL,
    CONSTRAINT pk_gps PRIMARY KEY (id)
);

CREATE TABLE place
(
    id        UUID NOT NULL,
    adress_id UUID,
    CONSTRAINT pk_place PRIMARY KEY (id)
);

CREATE TABLE rule
(
    id           UUID         NOT NULL,
    changed      TIMESTAMP WITHOUT TIME ZONE,
    changed_by   VARCHAR(40),
    created      TIMESTAMP WITHOUT TIME ZONE,
    created_by   VARCHAR(40),
    deleted      BOOLEAN      NOT NULL,
    deleted_time TIMESTAMP WITHOUT TIME ZONE,
    deleted_by   VARCHAR(40),
    version      INTEGER,
    text         VARCHAR(255),
    type         VARCHAR(255) NOT NULL,
    asset_id     UUID,
    CONSTRAINT pk_rule PRIMARY KEY (id)
);

CREATE TABLE asset_rule_mapping
(
    asset_id  UUID         NOT NULL,
    rule_id   UUID         NOT NULL,
    rules_key VARCHAR(255) NOT NULL,
    CONSTRAINT pk_asset_rule_mapping PRIMARY KEY (asset_id, rules_key)
);

ALTER TABLE asset
    ADD CONSTRAINT uc_asset_external_code UNIQUE (external_code);

ALTER TABLE address
    ADD CONSTRAINT FK_ADRESS_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE address
    ADD CONSTRAINT FK_ADRESS_ON_GPS FOREIGN KEY (gps_id) REFERENCES gps (id);

ALTER TABLE asset
    ADD CONSTRAINT FK_ASSET_ON_LOCATION FOREIGN KEY (location_id) REFERENCES gps (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_ASSET FOREIGN KEY (asset_id) REFERENCES asset (id);

ALTER TABLE contact
    ADD CONSTRAINT FK_CONTACT_ON_ASSET FOREIGN KEY (asset_id) REFERENCES asset (id);

ALTER TABLE place
    ADD CONSTRAINT FK_PLACE_ON_ADRESS FOREIGN KEY (adress_id) REFERENCES address (id);

ALTER TABLE place
    ADD CONSTRAINT FK_PLACE_ON_ID FOREIGN KEY (id) REFERENCES asset (id);

ALTER TABLE asset_rule_mapping
    ADD CONSTRAINT uc_asset_rule_mapping_rule UNIQUE (rule_id);

ALTER TABLE asset_rule_mapping
    ADD CONSTRAINT fk_assrulmap_on_asset FOREIGN KEY (asset_id) REFERENCES asset (id);

ALTER TABLE asset_rule_mapping
    ADD CONSTRAINT fk_assrulmap_on_rule FOREIGN KEY (rule_id) REFERENCES rule (id);

ALTER TABLE rule
    ADD CONSTRAINT FK_RULE_ON_ASSET FOREIGN KEY (asset_id) REFERENCES asset (id);

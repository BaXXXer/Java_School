DROP TABLE IF EXISTS lg_trucks;

CREATE TABLE lg_trucks(
                          tr_id serial,
                          tr_regNum varchar(7) not null,
                          tr_workingHours integer not null,
                          tr_capacityKg integer not null,
                          tr_condition varchar(10) not null,
                          tr_cityId integer not null,
                          PRIMARY KEY (tr_id)
);
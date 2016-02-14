create schema if not exists ROAMY;

drop table ROAMY.city if exists;
drop table ROAMY.category if exists;
drop table ROAMY.user if exists;
drop table ROAMY.trip if exists;
drop table ROAMY.trip_category if exists;
drop table ROAMY.trip_city if exists;
drop table ROAMY.trip_image if exists;
drop table ROAMY.trip_option if exists;
drop table ROAMY.trip_review if exists;
drop table ROAMY.favorite_trip if exists;
drop table ROAMY.trip_instance if exists;
drop table ROAMY.trip_instance_city if exists;
drop table ROAMY.vendor if exists;
drop table ROAMY.vendor_account if exists;
drop table ROAMY.reservation if exists;
drop table ROAMY.reservation_trip_instance if exists;
drop table ROAMY.reservation_trip_option if exists;
drop table ROAMY.payment if exists;
drop table ROAMY.sms_notification if exists;
drop table ROAMY.alert_notification if exists;
drop table ROAMY.email_template if exists;
drop table ROAMY.email_notification if exists;

create table ROAMY.city (
    id bigint generated by default as identity,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.city add constraint UNQ_city_code unique (code);

create table ROAMY.category (
    id bigint generated by default as identity,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    image_caption varchar(256),
    image_url varchar(1024) not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.category add constraint UNQ_category_code unique (code);

create table ROAMY.user (
    id bigint generated by default as identity,
    type varchar(64) not null,
    phone_number varchar(64) not null,
    email varchar(64) not null,
    fname varchar(64),
    lname varchar(64),
    wallet_balance double,
    profile_image_id varchar(256),
    profile_image_url varchar(1024),
    birth_date timestamp,
    address varchar(64),
    city varchar(64),
    country varchar(64),
    pin varchar(64),
    status varchar(64) not null,
    verification_code varchar(64),
    verification_expiry timestamp,
    verified boolean,
    referral_code varchar(64),
    device_id varchar(64),
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.user add constraint UNQ_user_phone_number unique (phone_number);

create table ROAMY.trip (
    id bigint generated by default as identity,
    type varchar(64) not null,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    additional_description varchar(1024),
    thrill_meter integer,
    number_of_days integer,
    price_per_adult double,
    tac double,
    itinerary varchar(1024),
    inclusions varchar(1024),
    exclusions varchar(1024),
    meeting_points varchar(1024),
    things_to_carry varchar(1024),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.trip add constraint UNQ_trip_code unique (code);

create table ROAMY.trip_category (
    trip_id bigint not null,
    category_id bigint not null
);

alter table ROAMY.trip_category add constraint FK_trip_category_category_id foreign key (category_id) references ROAMY.category;
alter table ROAMY.trip_category add constraint FK_trip_category_trip_id foreign key (trip_id) references ROAMY.trip;

create table ROAMY.trip_city (
    trip_id bigint not null,
    city_id bigint not null
);

alter table ROAMY.trip_city add constraint FK_trip_city_city_id foreign key (city_id) references ROAMY.city;
alter table ROAMY.trip_city add constraint FK_trip_city_trip_id foreign key (trip_id) references ROAMY.trip;

create table ROAMY.trip_image (
    trip_id bigint not null,
    caption varchar(256),
    url varchar(1024) not null
);

alter table ROAMY.trip_image add constraint FK_trip_trip_id foreign key (trip_id) references ROAMY.trip;

create table ROAMY.trip_option (
    id bigint generated by default as identity,
    trip_id bigint,
    name varchar(64) not null,
    description varchar(256),
    price double,
    capacity double,
    age_based_pricing boolean not null,
    adult_price double,
    senior_price double,
    child_price double,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.trip_option add constraint FK_trip_option_trip_id foreign key (trip_id) references ROAMY.trip;

create table ROAMY.favorite_trip (
    id bigint generated by default as identity,
    user_id bigint not null,
    trip_id bigint not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.favorite_trip add constraint FK_favorite_trip_trip_id foreign key (trip_id) references ROAMY.trip;
alter table ROAMY.favorite_trip add constraint FK_favorite_trip_user_id foreign key (user_id) references ROAMY.user;

create table ROAMY.trip_instance (
    id bigint generated by default as identity,
    type varchar(64) not null,
    name varchar(64) not null,
    trip_id bigint,
    description varchar(256),
    additional_description varchar(1024),
    thrill_meter integer,
    number_of_days integer,
    price_per_adult double,
    tac double,
    itinerary varchar(1024),
    inclusions varchar(1024),
    exclusions varchar(1024),
    meeting_points varchar(1024),
    things_to_carry varchar(1024),
    traveller_capacity integer,
    date timestamp not null,
    display_start_date timestamp,
    display_end_date timestamp,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.trip_instance add constraint FK_trip_instance_trip_id foreign key (trip_id) references ROAMY.trip;

create table ROAMY.trip_instance_city (
    trip_instance_id bigint not null,
    city_id bigint not null
);

alter table ROAMY.trip_instance_city add constraint FK_trip_instance_city_city_id foreign key (city_id) references ROAMY.city;
alter table ROAMY.trip_instance_city add constraint FK_trip_instance_city_trip_id foreign key (trip_instance_id) references ROAMY.trip_instance;

create table ROAMY.trip_instance_option (
    id bigint generated by default as identity,
    trip_instance_id bigint,
    name varchar(64) not null,
    description varchar(256),
    price double,
    capacity double,
    age_based_pricing boolean not null,
    adult_price double,
    senior_price double,
    child_price double,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.trip_instance_option add constraint FK_trip_option_trip_instance_id foreign key (trip_instance_id) references ROAMY.trip_instance;

create table ROAMY.vendor (
    id bigint generated by default as identity,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    phone_number varchar(64),
    email varchar(256),
    additional_email varchar(256),
    notes varchar(2048),
    commission double,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.vendor add constraint UNQ_vendor_code unique (code);

create table ROAMY.vendor_account (
    id bigint generated by default as identity,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    vendor_id bigint,
    bank_name varchar(256) not null,
    account_number varchar(64) not null,
    ifsc_code varchar(64) not null,
    pan varchar(64),
    tan varchar(64),
    service_tax_number varchar(64),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.vendor_account add constraint UNQ_vendor_account_code unique (code);
alter table ROAMY.vendor_account add constraint FK_vendor_account_vendor_id foreign key (vendor_id) references ROAMY.vendor;

create table ROAMY.reservation (
    id bigint generated by default as identity,
    type varchar(64) not null,
    user_id bigint,
    email varchar(256) not null,
    number_of_roamies integer not null,
    amount double not null,
    start_date timestamp not null,
    end_date timestamp,
    phone_number varchar(64) not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.reservation add constraint FK_reservation_user_id foreign key (user_id) references ROAMY.user;

create table ROAMY.reservation_trip_instance (
    reservation_id bigint not null,
    trip_instance_id bigint not null
);

alter table ROAMY.reservation_trip_instance add constraint FK_res_trip_instance_trip_instance_id foreign key (trip_instance_id) references ROAMY.trip_instance;
alter table ROAMY.reservation_trip_instance add constraint FK_res_trip_instance_reservation_id foreign key (reservation_id) references ROAMY.reservation;

create table ROAMY.reservation_trip_option (
    id bigint generated by default as identity,
    reservation_id bigint,
    count integer,
    age_based_pricing boolean not null,
    adult_count integer,
    senior_count integer,
    child_count integer,
    trip_instance_option_id bigint not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.reservation_trip_option add constraint FK_res_trip_option_trip_instance_option_id foreign key (trip_instance_option_id) references ROAMY.trip_instance_option;
alter table ROAMY.reservation_trip_option add constraint FK_res_trip_option_reservation_id foreign key (reservation_id) references ROAMY.reservation;

create table ROAMY.reservation_payment (
    id bigint generated by default as identity,
    reservation_id bigint,
    type varchar(64) not null,
    amount double not null,
    transaction_id varchar(64),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.reservation_payment add constraint FK_reservation_payment_reservation_id foreign key (reservation_id) references ROAMY.reservation;

create table ROAMY.payment (
    id bigint generated by default as identity,
    amount double not null,
    date timestamp not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

create table ROAMY.trip_review (
    id bigint generated by default as identity,
    title varchar(64) not null,
    description varchar(2048) not null,
    rating integer not null,
    trip_id bigint,
    reservation_id bigint,
    user_id bigint,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.trip_review add constraint FK_trip_review_reservation_id foreign key (reservation_id) references ROAMY.reservation;
alter table ROAMY.trip_review add constraint FK_trip_review_trip_id foreign key (trip_id) references ROAMY.trip;
alter table ROAMY.trip_review add constraint FK_trip_review_user_id foreign key (user_id) references ROAMY.user;

create table ROAMY.sms_notification (
    id bigint generated by default as identity,
    phone_number varchar(64) not null,
    message varchar(1024) not null,
    error_code varchar(64),
    error_description varchar(1024),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

create table ROAMY.alert (
    id bigint generated by default as identity,
    type varchar(64) not null,
    user_id bigint,
    title varchar(64) not null,
    message varchar(256) not null,
    promo_code varchar(64),
    read_status boolean,
    expiry_date timestamp not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.alert add constraint FK_alert_user_id foreign key (user_id) references ROAMY.user;

create table ROAMY.email_template (
    id bigint generated by default as identity,
    code varchar(64) not null,
    description varchar(256),
    name varchar(64) not null,
    subject_template varchar(1024) not null,
    template varchar(4096) not null,
    email_type varchar(64) not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.email_template add constraint UNQ_email_template_code unique (code);


create table ROAMY.email_notification (
    id bigint generated by default as identity,
    email_template_id bigint not null,
    email varchar(256) not null,
    subject_params varchar(1024),
    params varchar(4096),
    user_id bigint,
    reservation_id bigint,
    sent_on timestamp,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null,
    primary key (id)
);

alter table ROAMY.email_notification add constraint FK_email_notification_email_template_id foreign key (email_template_id) references ROAMY.email_template;


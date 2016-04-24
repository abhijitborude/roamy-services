create schema if not exists ROAMY;

use ROAMY;

drop table if exists city;
drop table if exists category;
drop table if exists user;
drop table if exists trip;
drop table if exists trip_category;
drop table if exists trip_city;
drop table if exists trip_image;
drop table if exists trip_option;
drop table if exists trip_review;
drop table if exists favorite_trip;
drop table if exists trip_instance;
drop table id exists trip_instance_option;
drop table if exists vendor;
drop table if exists vendor_account;
drop table if exists reservation;
drop table if exists reservation_trip_instance;
drop table if exists reservation_trip_option;
drop table if exists reservation_payment;
drop table if exists payment;
drop table if exists sms_notification;
drop table if exists alert;
drop table if exists email_template;
drop table if exists email_notification;

create table city (
    id bigint not null auto_increment primary key,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table city add constraint UNQ_city_code unique (code);

create table category (
    id bigint not null auto_increment primary key,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    image_caption varchar(256),
    image_url varchar(1024) not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table category add constraint UNQ_category_code unique (code);

create table user (
    id bigint not null auto_increment primary key,
    type varchar(64) not null,
    account_type varchar(64) not null,
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
    token varchar(64),
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table user add constraint UNQ_user_phone_number unique (phone_number);

create table trip (
    id bigint not null auto_increment primary key,
    type varchar(64) not null,
    code varchar(64) not null,
    name varchar(64) not null,
    description varchar(256),
    additional_description varchar(1024),
    thrill_meter integer,
    number_of_days integer,
    price_per_adult double,
    tac double,
    cover_picture varchar(1024),
    itinerary varchar(1024),
    inclusions varchar(1024),
    exclusions varchar(1024),
    meeting_points varchar(1024),
    things_to_carry varchar(1024),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table trip add constraint UNQ_trip_code unique (code);

create table trip_category (
    trip_id bigint not null,
    category_id bigint not null
);

alter table trip_category add constraint FK_trip_category_category_id foreign key (category_id) references category (id);
alter table trip_category add constraint FK_trip_category_trip_id foreign key (trip_id) references trip (id);

create table trip_city (
    trip_id bigint not null,
    city_id bigint not null
);

alter table trip_city add constraint FK_trip_city_city_id foreign key (city_id) references city (id);
alter table trip_city add constraint FK_trip_city_trip_id foreign key (trip_id) references trip (id);

create table trip_image (
    trip_id bigint not null,
    caption varchar(256),
    url varchar(1024) not null
);

alter table trip_image add constraint FK_trip_trip_id foreign key (trip_id) references trip (id);

create table trip_option (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table trip_option add constraint FK_trip_option_trip_id foreign key (trip_id) references trip (id);

create table favorite_trip (
    id bigint not null auto_increment primary key,
    user_id bigint not null,
    trip_id bigint not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table favorite_trip add constraint FK_favorite_trip_trip_id foreign key (trip_id) references trip (id);
alter table favorite_trip add constraint FK_favorite_trip_user_id foreign key (user_id) references user (id);

create table trip_instance (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table trip_instance add constraint FK_trip_instance_trip_id foreign key (trip_id) references trip (id);

create table trip_instance_option (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table trip_instance_option add constraint FK_trip_option_trip_instance_id foreign key (trip_instance_id) references trip_instance (id);

create table vendor (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table vendor add constraint UNQ_vendor_code unique (code);

create table vendor_account (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table vendor_account add constraint UNQ_vendor_account_code unique (code);
alter table vendor_account add constraint FK_vendor_account_vendor_id foreign key (vendor_id) references vendor (id);

create table reservation (
    id bigint not null auto_increment primary key,
    type varchar(64) not null,
    user_id bigint,
    email varchar(256) not null,
    amount double not null,
    start_date timestamp not null,
    end_date timestamp,
    phone_number varchar(64) not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table reservation add constraint FK_reservation_user_id foreign key (user_id) references user (id);

create table reservation_trip_instance (
    reservation_id bigint not null,
    trip_instance_id bigint not null
);

alter table reservation_trip_instance add constraint FK_res_trip_instance_trip_instance_id foreign key (trip_instance_id) references trip_instance (id);
alter table reservation_trip_instance add constraint FK_res_trip_instance_reservation_id foreign key (reservation_id) references reservation (id);

create table reservation_trip_option (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table reservation_trip_option add constraint FK_res_trip_option_trip_instance_option_id foreign key (trip_instance_option_id) references trip_instance_option (id);
alter table reservation_trip_option add constraint FK_res_trip_option_reservation_id foreign key (reservation_id) references reservation (id);

create table reservation_payment (
    id bigint not null auto_increment primary key,
    reservation_id bigint,
    type varchar(64) not null,
    amount double not null,
    transaction_id varchar(64),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table reservation_payment add constraint FK_reservation_payment_reservation_id foreign key (reservation_id) references reservation (id);

create table payment (
    id bigint not null auto_increment primary key,
    amount double not null,
    date timestamp not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

create table trip_review (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table trip_review add constraint FK_trip_review_reservation_id foreign key (reservation_id) references reservation (id);
alter table trip_review add constraint FK_trip_review_trip_id foreign key (trip_id) references trip (id);
alter table trip_review add constraint FK_trip_review_user_id foreign key (user_id) references user (id);

create table sms_notification (
    id bigint not null auto_increment primary key,
    phone_number varchar(64) not null,
    message varchar(1024) not null,
    error_code varchar(64),
    error_description varchar(1024),
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

create table alert (
    id bigint not null auto_increment primary key,
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
    last_modified_on timestamp not null
);

alter table alert add constraint FK_alert_user_id foreign key (user_id) references user (id);

create table email_template (
    id bigint not null auto_increment primary key,
    code varchar(64) not null,
    description varchar(256),
    name varchar(64) not null,
    subject_template varchar(1024) not null,
    template varchar(16384) not null,
    email_type varchar(64) not null,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table email_template add constraint UNQ_email_template_code unique (code);


create table email_notification (
    id bigint not null auto_increment primary key,
    email_template_id bigint not null,
    email varchar(256) not null,
    subject_params varchar(1024),
    params varchar(2048),
    user_id bigint,
    reservation_id bigint,
    sent_on timestamp,
    status varchar(64) not null,
    created_by varchar(256) not null,
    created_on timestamp not null,
    last_modified_by varchar(256) not null,
    last_modified_on timestamp not null
);

alter table email_notification add constraint FK_email_notification_email_template_id foreign key (email_template_id) references email_template (id);


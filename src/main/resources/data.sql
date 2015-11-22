-- City Data
insert into ROAMY.city(id,  code,   name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1, 'MUMBAI',   'Mumbai',   'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(2, 'PUNE',     'Pune',     'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(3, 'PATNA',    'Patna',    'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);


-- Category Data
insert into ROAMY.category
(id,  code,                     name,                       description,                        status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1,   'GET_OUT_OF_THE_CITY',    'Get out of the city',      'Get out of the city Description',  'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(2,   'TREK_IT_EASY',           'Trek it easy',             'Trek it easy Description',         'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(3,   'FEEL_THE_THRILL',        'Feel the thrill',          'Feel the thrill Description',      'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(4,   'INACTIVE',               'Inactive',                 'Inactive Description',             'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);

-- User
insert into ROAMY.user
(id,    type,       phone_number,   email,              pass,       fname,      lname,      birth_date,     address,            city,       country,    pin,        status,     v_code,     v_expiry,   verified,   created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     'Email',    '9820098200',   'abcd@abcd.com',    'abcd',     'fname',    'lname',    null,           '100 street rd.',   'Mumbai',   'India',    '100000',   'Active',   '',         null,       1,          'test',         current_timestamp,  'test',             current_timestamp);

insert into ROAMY.trip
(
    id,
    code,
    name,
    description,
    additional_description ,
    difficulty_level,
    number_of_days,
    default_price_per_adult,
    tac,
    itinerary,
    inclusions,
    exclusions,
    meeting_points,
    status,
    created_by,
    created_on,
    last_modified_by,
    last_modified_on
) values
(
    1, --id
    'SCUBA_DIVING_&_DIVING_SAFARI', --code
    'Scuba Diving & Snorkeling Safari', --name
    'Snorkeling is the practice of swimming on or through a body of water while equipped with a diving mask, a shaped tube called a snorkel, and usually swim-fins.', --description
    'Use of this equipment allows the snorkeler to observe underwater attractions for extended periods of time with relatively little effort.Snorkeling is a popular recreational activity. The primary appeal is the opportunity to observe underwater life in a natural setting without the complicated equipment inline', --additional_description
    3, --difficulty_level
    4, --number_of_days
    7500, --default_price_per_adult
    15.00, --tac
    'Day 1: <br/> 07:00 Hrs: Breakfast en-route @ Hotel Alankar, HatKhamba <br/> 11:30 Hrs: Check in Hotel @ Malvan <br/> 12:00 Hrs: Lunch in Hotel (Veg Thali / Chicken Thali / Fish Thali - Soundale Fish + Prawns Curry -Unlimited) <br/> 14:00 Hrs: TSUNAMI Island <br/> 17:00 Hrs: Tarkarli / Devbaug Beach - FUN Time <br/> 19:00 Hrs: Snacks - Kanda Bhajji & Tea <br/> 19:30 Hrs: Free time for Malvan Market / Beach / Rock Garden etc. (Optional) <br/> 21:00 Hrs: Dinner (Veg Thali / Chicken Thali / Fish Thali - Paplet / Surmai Fish as available -Limited)', -- itinerary
    'Dadar to Dadar transportation fare for private vehicle (Non-A/c), Surface Supply ScubaDiving, Snorkeling Charges, Dolphin Safari, Boating charges, Permissions and entry fees, Guide Charges, Food (Veg & Non Veg Thali as mentioned wherever limited and unlimited)', --inclusions
    '', --exclusions
    '21:30Hrs@ National Park,Borivali <br/> 22:30 Hrs : Pritam Hotel , Dadar <br/> 20:00Hrs: Shivajinagar, Pune', --meeting_points
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
);

insert into ROAMY.trip_category
(trip_id,   category_id ) values
(1,         1),
(1,         3);

insert into ROAMY.target_city
(id,    city_id,    trip_id,    price_per_adult,    instructions,   status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1,     1,          1,          null,               null,           'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(2,     2,          1,          null,               null,           'Active',   'test',     current_timestamp,  'test',             current_timestamp);

insert into ROAMY.trip_instance
(id,    trip_id,    traveller_capacity, additional_capacity,    date,                                     display_start_date,    display_end_date,                           status,     created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     1,          50,                 10,                     dateadd('DAY', 10, current_timestamp),    current_timestamp,     dateadd('DAY', 10, current_timestamp),     'Active',   'test',         current_timestamp,  'test',             current_timestamp);
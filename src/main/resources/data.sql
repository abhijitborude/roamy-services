-- City Data
insert into city(id,  code,   name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1, 'MUMBAI',   'Mumbai',   'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(2, 'PUNE',     'Pune',     'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(3, 'PATNA',    'Patna',    'Inactive', 'test',     current_timestamp,  'test',             current_timestamp),
(4, 'ALL',      'All',      'Active',   'test',     current_timestamp,  'test',             current_timestamp);


-- Category Data
insert into category
(id,  code,                     name,                       description,                            image_caption,              image_url,                                                                              status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1,   'GET_OUT_OF_THE_CITY',    'Get out of the city',      'Get out of the city Description',      'Get out of the city',      'http://res.cloudinary.com/abhijitab/image/upload/v1449990238/category1_q9uyh3.png',    'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(2,   'TREK_IT_EASY',           'Trek it easy',             'Trek it easy Description',             'Trek it easy',             'http://res.cloudinary.com/abhijitab/image/upload/v1449990245/category2_dclwtj.png',    'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(3,   'FEEL_THE_THRILL',        'Feel the thrill',          'Feel the thrill Description',          'Feel the thrill',          'http://res.cloudinary.com/abhijitab/image/upload/v1449990238/category1_q9uyh3.png',    'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(4,   'INACTIVE',               'Inactive',                 'Inactive Description',                 '',                         '',                                                                                     'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);


-- User
insert into user
(id,    type,       phone_number,   email,              fname,      lname,      wallet_balance,         profile_image_id,       profile_image_url,                                                                                      birth_date,     address,            city,       country,    pin,        status,     verification_code,     verification_expiry,     referral_code,  device_id,    verified,   created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     'Email',    '12345',        'abcd@abcd.com',    'fname',    'lname',    1000.00,                  'category1_q9uyh3',     'http://res.cloudinary.com/abhijitab/image/upload/v1449990238/category1_q9uyh3.png',                    null,           '100 street rd.',   'Mumbai',   'India',    '100000',   'Active',   null,                   null,                   'abcd',         null,         1,          'test',         current_timestamp,  'test',             current_timestamp);

insert into trip
(
    id,
    type,
    code,
    name,
    description,
    additional_description ,
    thrill_meter,
    number_of_days,
    price_per_adult,
    tac,
    itinerary,
    inclusions,
    exclusions,
    meeting_points,
    things_to_carry,
    status,
    created_by,
    created_on,
    last_modified_by,
    last_modified_on
) values
(
    1, --id
    'PACKAGE',
    'SCUBA_DIVING_&_DIVING_SAFARI', --code
    'Scuba Diving & Snorkeling Safari', --name
    'Snorkeling is the practice of swimming on or through a body of water while equipped with a diving mask, a shaped tube called a snorkel, and usually swim-fins.', --description
    'Use of this equipment allows the snorkeler to observe underwater attractions for extended periods of time with relatively little effort.Snorkeling is a popular recreational activity. The primary appeal is the opportunity to observe underwater life in a natural setting without the complicated equipment inline', --additional_description
    3, --thrill_meter
    4, --number_of_days
    7500, --default_price_per_adult
    15.00, --tac
    'Day 1: <br/> 07:00 Hrs: Breakfast en-route @ Hotel Alankar, HatKhamba <br/> 11:30 Hrs: Check in Hotel @ Malvan <br/> 12:00 Hrs: Lunch in Hotel (Veg Thali / Chicken Thali / Fish Thali - Soundale Fish + Prawns Curry -Unlimited) <br/> 14:00 Hrs: TSUNAMI Island <br/> 17:00 Hrs: Tarkarli / Devbaug Beach - FUN Time <br/> 19:00 Hrs: Snacks - Kanda Bhajji & Tea <br/> 19:30 Hrs: Free time for Malvan Market / Beach / Rock Garden etc. (Optional) <br/> 21:00 Hrs: Dinner (Veg Thali / Chicken Thali / Fish Thali - Paplet / Surmai Fish as available -Limited)', -- itinerary
    'Dadar to Dadar transportation fare for private vehicle (Non-A/c), Surface Supply ScubaDiving, Snorkeling Charges, Dolphin Safari, Boating charges, Permissions and entry fees, Guide Charges, Food (Veg & Non Veg Thali as mentioned wherever limited and unlimited)', --inclusions
    '', --exclusions
    '21:30Hrs@ National Park,Borivali <br/> 22:30 Hrs : Pritam Hotel , Dadar <br/> 20:00Hrs: Shivajinagar, Pune', --meeting_points
    'Shoes<br/>Backpack<br/>First Aid<br/>Torch',   --things_to_carry
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
),
(
    2, --id
    'TICKET',
    'DELLA_ADVENTURE', --code
    'Della Adventure', --name
    'Della offers a range of Adventure activities & Sports, a perfect place for weekend getaway & a holiday destination to visit near Mumbai and Pune for a one day', --description
    '', --additional_description
    4, --thrill_meter
    null, --number_of_days
    2004, --default_price_per_adult
    15.00, --tac
    null, -- itinerary
    null, --inclusions
    null, --exclusions
    null, --meeting_points
    null,   --things_to_carry
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
);

insert into trip_category
(trip_id,   category_id ) values
(1,         1),
(1,         3),
(2,         1),
(2,         3);

insert into trip_city
(trip_id,   city_id ) values
(1,         1),
(1,         2),
(2,         1),
(2,         2);

insert into trip_image
(trip_id,   caption,                url ) values
(1,         'Nice Image',           'http://res.cloudinary.com/abhijitab/image/upload/v1449029733/sample.jpg'),
(1,         null,                   'http://res.cloudinary.com/abhijitab/image/upload/v1449029733/sample.jpg'),
(2,         'Nice Image',           'http://res.cloudinary.com/abhijitab/image/upload/v1449029733/sample.jpg');

insert into trip_option
(id,    trip_id,    name,                               description,    price,  capacity,   age_based_pricing,      adult_price,    senior_price,   child_price,    status,     created_by,         created_on,         last_modified_by,   last_modified_on) values
(1,     1,          'Adult',                            '',             7500,   25,         false,                  null,           null,           null,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(2,     1,          'Children (10 years or below)',     '',             5000,   25,         false,                  null,           null,           null,           'Active',   'test',             current_timestamp,  'test',             current_timestamp);

insert into trip_option
(id,    trip_id,    name,                               description,    price,  capacity,   age_based_pricing,      adult_price,    senior_price,   child_price,    status,     created_by,         created_on,         last_modified_by,   last_modified_on) values
(3,     2,          'Day Pass',                         '',             2004,   null,       true,                   2004,           1431,           1431,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(4,     2,          'Jumbo Pass',                       '',             5000,   null,       true,                   5724,           3500,           3500,           'Active',   'test',             current_timestamp,  'test',             current_timestamp);

insert into favorite_trip
(id,    user_id,   trip_id,     status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1,     1,         1,           'Active',   'test',     current_timestamp,  'test',             current_timestamp);

insert into trip_instance
(
    id,
    type,
    name,
    trip_id,
    description,
    additional_description ,
    thrill_meter,
    number_of_days,
    price_per_adult,
    tac,
    itinerary,
    inclusions,
    exclusions,
    meeting_points,
    things_to_carry,
    traveller_capacity,
    date,
    display_start_date,
    display_end_date,
    status,
    created_by,
    created_on,
    last_modified_by,
    last_modified_on
) values
(
    1,
    'PACKAGE', --type
    'Scuba Diving & Snorkeling Safari 1', --name
    1,  --trip_id
    'Snorkeling is the practice of swimming on or through a body of water while equipped with a diving mask, a shaped tube called a snorkel, and usually swim-fins.', --description
    'Use of this equipment allows the snorkeler to observe underwater attractions for extended periods of time with relatively little effort.Snorkeling is a popular recreational activity. The primary appeal is the opportunity to observe underwater life in a natural setting without the complicated equipment inline', --additional_description
    3, --thrill_meter
    4, --number_of_days
    7500, --price_per_adult
    15.00, --tac
    'Day 1:<br/>07:00 Hrs: Breakfast en-route @ Hotel Alankar, HatKhamba<br/> 11:30 Hrs: Check in Hotel @ Malvan<br/>12:00 Hrs: Lunch in Hotel (Veg Thali / Chicken Thali / Fish Thali - Soundale Fish + Prawns Curry -Unlimited)<br/>14:00 Hrs: TSUNAMI Island<br/>17:00 Hrs: Tarkarli / Devbaug Beach - FUN Time<br/>19:00 Hrs: Snacks - Kanda Bhajji & Tea<br/>19:30 Hrs: Free time for Malvan Market / Beach / Rock Garden etc. (Optional)<br/>21:00 Hrs: Dinner (Veg Thali / Chicken Thali / Fish Thali - Paplet / Surmai Fish as available -Limited)', -- itinerary
    'Dadar to Dadar transportation fare for private vehicle (Non-A/c)<br/>Surface Supply ScubaDiving, Snorkeling Charges, Dolphin Safari, Boating charges, Permissions and entry fees, Guide Charges', --inclusions
    'Food', --exclusions
    '21:30Hrs@ National Park,Borivali <br/> 22:30 Hrs : Pritam Hotel , Dadar <br/> 20:00Hrs: Shivajinagar, Pune', --meeting_points
    'Shoes<br/>Backpack<br/>First Aid<br/>Torch',   --things_to_carry
    50, --traveller_capacity
    dateadd('DAY', 5, current_timestamp),  --date
    current_timestamp,  --display_start_date
    dateadd('DAY', 5, current_timestamp),  --display_end_date
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
),
(
    2,
    'PACKAGE', --type,
    'Scuba Diving & Snorkeling Safari 2', --name
    1,  --trip_id
    'Snorkeling is the practice of swimming on or through a body of water while equipped with a diving mask, a shaped tube called a snorkel, and usually swim-fins.', --description
    'Use of this equipment allows the snorkeler to observe underwater attractions for extended periods of time with relatively little effort.Snorkeling is a popular recreational activity. The primary appeal is the opportunity to observe underwater life in a natural setting without the complicated equipment inline', --additional_description
    3, --thrill_meter
    4, --number_of_days
    10000, --price_per_adult
    15.00, --tac
    'Day 1:<br/>07:00 Hrs: Breakfast en-route @ Hotel Alankar, HatKhamba<br/> 11:30 Hrs: Check in Hotel @ Malvan<br/>12:00 Hrs: Lunch in Hotel (Veg Thali / Chicken Thali / Fish Thali - Soundale Fish + Prawns Curry -Unlimited)<br/>14:00 Hrs: TSUNAMI Island<br/>17:00 Hrs: Tarkarli / Devbaug Beach - FUN Time<br/>19:00 Hrs: Snacks - Kanda Bhajji & Tea<br/>19:30 Hrs: Free time for Malvan Market / Beach / Rock Garden etc. (Optional)<br/>21:00 Hrs: Dinner (Veg Thali / Chicken Thali / Fish Thali - Paplet / Surmai Fish as available -Limited)', -- itinerary
        'Dadar to Dadar transportation fare for private vehicle (Non-A/c)<br/>Surface Supply ScubaDiving, Snorkeling Charges, Dolphin Safari, Boating charges, Permissions and entry fees, Guide Charges<br/>Food', --inclusions
    '', --exclusions
    '21:30Hrs@ National Park,Borivali <br/> 22:30 Hrs : Pritam Hotel , Dadar <br/> 20:00Hrs: Shivajinagar, Pune', --meeting_points
    'Shoes<br/>Backpack<br/>First Aid<br/>Torch',   --things_to_carry
    50, --traveller_capacity
    dateadd('DAY', 10, current_timestamp),  --date
    dateadd('DAY', 5, current_timestamp),  --display_start_date
    dateadd('DAY', 10, current_timestamp),  --display_end_date
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
),
(
    3,
    'TICKET', --type,
    'Della Adventure', --name
    2,  --trip_id
    'Della offers a range of Adventure activities & Sports, a perfect place for weekend getaway & a holiday destination to visit near Mumbai and Pune for a one day', --description
    '', --additional_description
    4, --thrill_meter
    null, --number_of_days
    2004, --price_per_adult
    15.00, --tac
    'Day 1:<br/>07:00 Hrs: Breakfast en-route @ Hotel Alankar, HatKhamba<br/> 11:30 Hrs: Check in Hotel @ Malvan<br/>12:00 Hrs: Lunch in Hotel (Veg Thali / Chicken Thali / Fish Thali - Soundale Fish + Prawns Curry -Unlimited)<br/>14:00 Hrs: TSUNAMI Island<br/>17:00 Hrs: Tarkarli / Devbaug Beach - FUN Time<br/>19:00 Hrs: Snacks - Kanda Bhajji & Tea<br/>19:30 Hrs: Free time for Malvan Market / Beach / Rock Garden etc. (Optional)<br/>21:00 Hrs: Dinner (Veg Thali / Chicken Thali / Fish Thali - Paplet / Surmai Fish as available -Limited)', -- itinerary
        'Dadar to Dadar transportation fare for private vehicle (Non-A/c)<br/>Surface Supply ScubaDiving, Snorkeling Charges, Dolphin Safari, Boating charges, Permissions and entry fees, Guide Charges<br/>Food', --inclusions
    '', --exclusions
    null, --meeting_points
    null,   --things_to_carry
    null, --traveller_capacity
    dateadd('DAY', 1, current_date),  --date
    current_timestamp,  --display_start_date
    dateadd('DAY', 2, current_timestamp),  --display_end_date
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
),
(
    4,
    'TICKET', --type,
    'Della Adventure', --name
    2,  --trip_id
    'Della offers a range of Adventure activities & Sports, a perfect place for weekend getaway & a holiday destination to visit near Mumbai and Pune for a one day', --description
    '', --additional_description
    4, --thrill_meter
    null, --number_of_days
    2004, --price_per_adult
    15.00, --tac
    'Day 1:<br/>07:00 Hrs: Breakfast en-route @ Hotel Alankar, HatKhamba<br/> 11:30 Hrs: Check in Hotel @ Malvan<br/>12:00 Hrs: Lunch in Hotel (Veg Thali / Chicken Thali / Fish Thali - Soundale Fish + Prawns Curry -Unlimited)<br/>14:00 Hrs: TSUNAMI Island<br/>17:00 Hrs: Tarkarli / Devbaug Beach - FUN Time<br/>19:00 Hrs: Snacks - Kanda Bhajji & Tea<br/>19:30 Hrs: Free time for Malvan Market / Beach / Rock Garden etc. (Optional)<br/>21:00 Hrs: Dinner (Veg Thali / Chicken Thali / Fish Thali - Paplet / Surmai Fish as available -Limited)', -- itinerary
        'Dadar to Dadar transportation fare for private vehicle (Non-A/c)<br/>Surface Supply ScubaDiving, Snorkeling Charges, Dolphin Safari, Boating charges, Permissions and entry fees, Guide Charges<br/>Food', --inclusions
    '', --exclusions
    null, --meeting_points
    null,   --things_to_carry
    null, --traveller_capacity
    dateadd('DAY', 2, current_date),  --date
    current_timestamp,  --display_start_date
    dateadd('DAY', 3, current_timestamp),  --display_end_date
    'Active', --status
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
);

insert into trip_instance_city
(trip_instance_id,   city_id ) values
(1,         1),
(1,         2),
(2,         1),
(2,         2),
(3,         1),
(3,         2),
(4,         1),
(4,         2);

insert into trip_instance_option
(id,    trip_instance_id,    name,                              description,    price,  capacity,   age_based_pricing,      adult_price,    senior_price,   child_price,    status,     created_by,         created_on,         last_modified_by,   last_modified_on) values
(1,     1,                  'Adult',                            '',             7500,   25,         false,                  null,           null,           null,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(2,     1,                  'Children (10 years or below)',     '',             5000,   25,         false,                  null,           null,           null,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(3,     2,                  'Adult',                            '',             7500,   25,         false,                  null,           null,           null,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(4,     2,                  'Children (10 years or below)',     '',             5000,   25,         false,                  null,           null,           null,           'Active',   'test',             current_timestamp,  'test',             current_timestamp);

insert into trip_instance_option
(id,    trip_instance_id,    name,                              description,    price,  capacity,   age_based_pricing,      adult_price,    senior_price,   child_price,    status,     created_by,         created_on,         last_modified_by,   last_modified_on) values
(5,     3,                  'Day Pass',                         '',             2004,   null,       true,                   2004,           1431,           1431,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(6,     3,                  'Jumbo Pass',                       '',             5000,   null,       true,                   5724,           3500,           3500,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(7,     4,                  'Day Pass',                         '',             2004,   null,       true,                   2004,           1431,           1431,           'Active',   'test',             current_timestamp,  'test',             current_timestamp),
(8,     4,                  'Jumbo Pass',                       '',             5000,   null,       true,                   5724,           3500,           3500,           'Active',   'test',             current_timestamp,  'test',             current_timestamp);


insert into trip_review
(id,     trip_id,    reservation_id,    user_id,    title,                      description,                                                                        rating,     status,     created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     1,           null,              1,          'Amazing trip!',            'This was an amazing trip. All the arrangements were great. Food was awesome too.',   5,          'Active',   'test',         current_timestamp,  'test',             current_timestamp),
(2,     1,           null,              1,          'Nice trip!',               'This was a nice trip. All the arrangements were fine. Food was good too.',           4,          'Active',   'test',         current_timestamp,  'test',             current_timestamp);

insert into alert
(id,    type,       user_id,    title,                      message,                                                                                                        promo_code,        read_status,    expiry_date,                                status,     created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     'PROMO',    1,          'New Year Special!',        'Book now and earn 20% off on all trips. Offer valid only until January 10th. Hurry up!',                       'WELCOME2016',     0,              dateadd('DAY', 10, current_timestamp),      'Active',   'test',         current_timestamp,  'test',             current_timestamp),
(2,     'PROMO',    1,          'Book 3 and get 1 free!',   'Get a discount on group bookings. Book 3 trips and get 4th trip free. Make plans with your friends now!!',     '3PE1FREE',        1,              dateadd('DAY', 10, current_timestamp),      'Active',   'test',         current_timestamp,  'test',             current_timestamp),
(3,     'INFO',     1,          'Your trip is arriving',    'You trip is scheduled for tomorrow',                                                                           null,              1,              dateadd('DAY', 10, current_timestamp),      'Active',   'test',         current_timestamp,  'test',             current_timestamp);

insert into reservation
(id,    type,           user_id,    email,              number_of_roamies,      amount,     start_date,         end_date,       phone_number,   status,     created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     'PACKAGE',      1,          'abc@abc.com',      4,                      30000,      current_timestamp,  null,           '12345',        'Active',   'test',         current_timestamp,  'test',             current_timestamp),
(2,     'PACKAGE',      1,          'abc@abc.com',      2,                      20000,      current_timestamp,  null,           '12345',        'Active',   'test',         current_timestamp,  'test',             current_timestamp);


insert into reservation_payment
(id,    reservation_id,   type,             amount,     transaction_id,         status,         created_by,     created_on,         last_modified_by,   last_modified_on) values
(1,     1,                'Razorpay',       7500,       'skjfhksjfhkjfhs',      'Success',      'test',         current_timestamp,  'test',             current_timestamp);


insert into email_template
(
    id,
    code,
    description,
    name,
    subject_template,
    template,
    email_type,
    status,
    created_by,
    created_on,
    last_modified_by,
    last_modified_on
) values
(
    1,  --id
    'RESERVATION_CONFIRMATION', --code
    '', --desc
    'Reservation Confirmation', --name
    'Your Roamy Booking is confirmed', --subject_template
    '<p>Reservation #$trip.reservationId</p><p>Congratulations! Your booking for <b>$trip.name</b> on <b>$trip.date</b> is confirmed.</p><p>Thank you for being a ROAMY!</p>', --template
    'TRIP', --email_type
    'Active',
    'test', --created_by
    current_timestamp, --created_on,
    'test', --last_modified_by
    current_timestamp --last_modified_on
);


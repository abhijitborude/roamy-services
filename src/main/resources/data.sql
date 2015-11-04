-- City Data
insert into ROAMY.city(id,  code,   name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values(1, 'MUMBAI',   'Mumbai',   'Active',   'test',     current_timestamp,  'test',             current_timestamp);
insert into ROAMY.city(id,  code,   name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values(2, 'PUNE',     'Pune',     'Active',   'test',     current_timestamp,  'test',             current_timestamp);
insert into ROAMY.city(id,  code,   name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values(3, 'PATNA',    'Patna',    'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);


-- Category Data
insert into ROAMY.category
(id,  code,                     name,                       description,                        status,     created_by, created_on,         last_modified_by,   last_modified_on) values
(1,   'GET_OUT_OF_THE_CITY',    'Get out of the city',      'Get out of the city Description',  'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(2,   'TREK_IT_EASY',           'Trek it easy',             'Trek it easy Description',         'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(3,   'FEEL_THE_THRILL',        'Feel the thrill',          'Feel the thrill Description',      'Active',   'test',     current_timestamp,  'test',             current_timestamp),
(4,   'INACTIVE',               'Inactive',                 'Inactive Description',             'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);

-- User
insert into ROAMY.user (id, type, phone_number, email, pass, fname, lname, birth_date, address, city, country, pin, status, v_code, v_expiry, verified, created_by, created_on, last_modified_by, last_modified_on) values (1, 'Email', '9820098200', 'abcd@abcd.com', 'abcd', 'fname', 'lname', null, '100 street rd.', 'Mumbai', 'India', '100000', 'Active', '', null, 1, 'test', current_timestamp, 'test', current_timestamp);
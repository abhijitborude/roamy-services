-- City Data
insert into ROAMY.city(id,    name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values(1,     'Mumbai',   'Active',   'test',     current_timestamp,  'test',             current_timestamp);
insert into ROAMY.city(id,    name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values(2,     'Pune',     'Active',   'test',     current_timestamp,  'test',             current_timestamp);
insert into ROAMY.city(id,    name,       status,     created_by, created_on,         last_modified_by,   last_modified_on) values(3,     'Patna',    'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);


-- Category Data
insert into ROAMY.category(id,    name,       description,  status,     created_by, created_on,         last_modified_by,   last_modified_on) values(1,     'Adventure',    'Adventure Description', 'Active', 'test',     current_timestamp,  'test',             current_timestamp);
insert into ROAMY.category(id,    name,       description,  status,     created_by, created_on,         last_modified_by,   last_modified_on) values(2,     'Trekking',     'Trekking Description', 'Active', 'test',     current_timestamp,  'test',             current_timestamp);
insert into ROAMY.category(id,    name,       description,  status,     created_by, created_on,         last_modified_by,   last_modified_on) values(3,     'Inactive',     'Inactive Description', 'Inactive', 'test',     current_timestamp,  'test',             current_timestamp);

-- User
insert into ROAMY.user (id, type, phone_number, email, pass, fname, lname, birth_date, address, city, country, pin, status, v_code, v_expiry, verified, created_by, created_on, last_modified_by, last_modified_on) values (1, 'Email', '9820098200', 'abcd@abcd.com', 'abcd', 'fname', 'lname', null, '100 street rd.', 'Mumbai', 'India', '100000', 'Active', '', null, 1, 'test', current_timestamp, 'test', current_timestamp);
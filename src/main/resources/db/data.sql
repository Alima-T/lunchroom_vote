-- DELETE FROM votes;
-- DELETE FROM foods;
-- DELETE FROM restaurants;
-- DELETE FROM user_roles;
-- DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Bob', 'bob@gmail.com', '{noop}password'),
       ('Helen', 'helen@gmail.com', '{noop}password'),
       ('Robert', 'robert@gmail.com', '{noop}password'),
       ('Anna', 'anna@gmail.com', '{noop}password'),
       ('Olef', 'olef@gmail.com', '{noop}password'),
       ('Urgen', 'urgen@gmail.com', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('USER', 100004),
       ('USER', 100005),
       ('USER', 100006),
       ('ADMIN', 100006);

INSERT INTO restaurants (name)
VALUES ('Jamies restaurant'),
       ('Penthouse restaurant'),
       ('Mandarin restaurant'),
       ('Choco-loco cafe'),
       ('Forever cafe');
INSERT INTO dishes (vote_date, description, price, restaurant_id)
VALUES (today(), 'Greek Salad with Olives and Feta', 10.75, 100007),
       (today(), 'Shrimp with Salsa Verde serve in lettuce wraps', 10.25, 100007),
       (today(), 'Ice Lemonade', 1.45, 100007),
       (today(), 'Cappuccino', 2.30, 100007),
       (today(), 'Ice Cream or Sorbet', 3.30, 100007),

       (today(), 'Roast Lamb with Beans', 11.50, 100008),
       (today(), 'Shrimp Caesar Salad', 5.25, 100008),
       (today(), 'Macchiato coffee', 3.00, 100008),
       (today(), 'Cappuccino', 2.30, 100008),
       (today(), 'Chocolate Cookies', 2.00, 100008),

       (today(), 'Orange Chicken Breast grill', 11.25, 100009),
       (today(), 'Shrimp Caesar Salad', 5.25, 100009),
       (today(), 'Fruit juice', 3.00, 100009),
       (today(), 'Cappuccino', 2.30, 100009),
       (today(), 'Chocolate Cookies', 2.00, 100009),

       (today(), 'Roast Beef with potato', 5.75, 100010),
       (today(), 'Mixed Green Salad', 4.50, 100010),
       (today(), 'Mineral Water', 1.30, 100010),
       (today(), 'Americano', 2.30, 100010),
       (today(), 'White Chocolate Creme Brulee', 3.45, 100010),

       ('2022-01-10', 'Mushroom Pasta with White Cream', 11.95, 100007),
       ('2022-01-10', 'Chicken Cesar salad', 4.85, 100007),
       ('2022-01-10', 'Cappuccino', 2.60, 100007),
       ('2022-01-10', 'Orange Fresh', 4.00, 100008),
       ('2022-01-10', 'Crisp Sugar Cookies', 2.00, 100008);



INSERT INTO votes (vote_date, user_id, restaurant_id)
VALUES (today(), 100000, 100007),
       (today(), 100001, 100008),
       (today(), 100002, 100010),
       (today(), 100003, 100008),
       (today(), 100004, 100008),
       (today(), 100005, 100009),
       ('2022-01-10', 100000, 100007),
       ('2022-01-10', 100001, 100010);



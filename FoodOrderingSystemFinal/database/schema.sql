-- CREATE DATABASE

CREATE DATABASE IF NOT EXISTS food_order_db
    DEFAULT CHARACTER SET utf8mb4;
USE food_order_db;

-- CREATE TABLE USER

CREATE TABLE IF NOT EXISTS user (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(64)  NOT NULL,   -- SHA-256 hex digest, see PasswordUtil
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone        VARCHAR(20)  NOT NULL,
    role         INT          NOT NULL DEFAULT 0,   -- 0 = customer, 1 = admin
    create_time  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- CREATE TABLE CATEGORY


CREATE TABLE IF NOT EXISTS category (
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);


-- CREATE TABLE DISH


CREATE TABLE IF NOT EXISTS dish (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    category_id  INT NOT NULL,
    name         VARCHAR(100)   NOT NULL,
    price        DECIMAL(10,2)  NOT NULL,
    image        VARCHAR(255),
    ingredients  VARCHAR(500),
    nutrition    VARCHAR(500),
    description  VARCHAR(500),
    rating       DECIMAL(3,1)   DEFAULT 0,
    stock        INT            NOT NULL DEFAULT 100,
    FOREIGN KEY (category_id) REFERENCES category(id)
);


-- CREATE TABLE ORDERS 


CREATE TABLE IF NOT EXISTS orders (
    id               INT PRIMARY KEY AUTO_INCREMENT,
    order_no         VARCHAR(30)   NOT NULL UNIQUE,
    user_id          INT           NOT NULL,
    recipient_name   VARCHAR(50),
    recipient_phone  VARCHAR(20),
    address          VARCHAR(255),
    subtotal         DECIMAL(10,2) NOT NULL,
    delivery_fee     DECIMAL(10,2) NOT NULL DEFAULT 0,
    total_amount     DECIMAL(10,2) NOT NULL,
    status           INT           NOT NULL DEFAULT 0, 
    create_time      TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);


-- CREATE TABLE ORDERS 

CREATE TABLE IF NOT EXISTS order_item (
    id         INT PRIMARY KEY AUTO_INCREMENT,
    order_id   INT           NOT NULL,
    dish_id    INT           NOT NULL,
    dish_name  VARCHAR(100)  NOT NULL,
    price      DECIMAL(10,2) NOT NULL,
    quantity   INT           NOT NULL,
    subtotal   DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);





INSERT INTO category (id, name) VALUES
    (1, 'Appetizer'),
    (2, 'Main Course'),
    (3, 'Dessert'),
    (4, 'Drink')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO dish (id, category_id, name, price, image, ingredients, nutrition, description, rating, stock) VALUES
    (1, 2, 'Nasi Lemak', 12.00, 'dish1.jpg',
        'Coconut rice, sambal, fried anchovies, roasted peanuts, cucumber, boiled egg.',
        'High in carbohydrates and healthy fats, moderate protein, contains fiber, vitamins and minerals, but also high in sodium.',
        'Traditional Malaysian coconut rice served with sambal, egg, peanuts and anchovies.', 9.5, 100),
    (2, 2, 'Stir-fried rice noodles', 10.00, 'dish2.jpg',
        'Wide rice noodles, meat, vegetables, soy sauce.',
        'High in carbohydrates, moderate protein, contains vitamins and minerals, but also high in fat and sodium.',
        'Wide rice noodles stir-fried in a hot wok with meat, vegetables, and special sauce.', 9.5, 100),
    (3, 3, 'Lemon ice cream', 8.00, 'dish3.jpg',
        'Milk, cream, sugar, lemon juice, lemon zest.',
        'High in sugar and fat, contains calcium, provides energy, but low in fiber.',
        'Fragrant and refreshing, cools the palate. Smooth and fresh taste.', 9.0, 100),
    (4, 3, 'Egg tart', 6.00, 'dish4.jpg',
        'Puff pastry, egg, milk, cream, sugar, vanilla.',
        'High in carbohydrates, fat, and sugar, contains protein and calcium, provides energy.',
        'Crispy, flaky outer skin with a tender, milky filling.', 8.5, 100),
    (5, 1, 'Cucumber salad', 3.00, 'dish5.jpg',
        'Cucumber, garlic, vinegar, soy sauce, sesame oil, chili.',
        'Low in calories, rich in fiber and vitamins, contains healthy fats.',
        'Refreshing and appetizing, crisp and tender.', 8.0, 100),
    (6, 1, 'Cold Seaweed Salad', 2.00, 'dish6.jpg',
        'Seaweed, garlic, soy sauce, vinegar, sesame oil, chili.',
        'Low in calories, rich in iodine, fiber, vitamins, and minerals.',
        'Sour and spicy, crisp and refreshing.', 8.0, 100),
    (7, 4, 'Juice', 5.00, 'dish7.jpg',
        'Fresh fruits, water, sugar, ice.',
        'Rich in vitamins and antioxidants, contains natural sugars.',
        'A variety of fruit-flavored juices, refreshing and thirst-quenching.', 8.0, 100),
    (8, 4, 'Cola', 4.00, 'dish8.jpg',
        'Carbonated water, sugar, caramel color, phosphoric acid, caffeine.',
        'High in sugar, provides energy, contains caffeine, little nutritional value.',
        'An eternal classic, refreshing and satisfying.', 8.0, 100)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- For testing:  username "admin", password "admin123"


INSERT INTO user (id, username, password, email, phone, role) VALUES
    (1, 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9',
     'admin@foodorder.com', '0100000000', 1)
ON DUPLICATE KEY UPDATE username = VALUES(username);

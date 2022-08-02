INSERT INTO products (quantity, price, description, image, "name") VALUES (
    10,
    20.00,
    'A nice pair of headphones',
    'https://i.insider.com/54eb437f6bb3f7697f85da71?width=1000&format=jpeg&auto=webp',
    'Headphones'
),
(
    5,
    45.00,
    'A nice TeeShirt',
    'https://d3o2e4jr3mxnm3.cloudfront.net/Mens-Jake-Guitar-Vintage-Crusher-Tee_68382_1_lg.png',
    'TeeShirt'
),
(
    20,
    2.50,
    'A reusable shopping bag',
    'https://images.ctfassets.net/5gvckmvm9289/3BlDoZxSSjqAvv1jBJP7TH/65f9a95484117730ace42abf64e89572/Noissue-x-Creatsy-Tote-Bag-Mockup-Bundle-_4_-2.png',
    'Shopping Bag'
),
(
    20,
    10.00,
    'A fancy cap for a fancy person',
    'https://revazon-image-bucket.s3.amazonaws.com/cap.png',
    'Baseball Cap'
),
(
    3,
    80.00,
    'A nice coat',
    'https://www.pngarts.com/files/3/Women-Jacket-PNG-High-Quality-Image.png',
    'Coat'
),
(
     500,
     29.99,
    'Red and green Revature Sneakers with extra room for the toes', 
    'https://a.1stdibscdn.com/archivesE/upload/1121209/f_3597242/3597242_z.jpg', 
    'Revature Sneakers'
),
(
    2000,
    1.99,
    '16oz Cup Insulated Stainless Steel Double Walled Coffee Travel Mug', 
    'https://i5.walmartimages.com/asr/a44828d4-922d-4e12-82d5-20f31ac13c5a_1.9db4105357a927a4fcf418c7e1c60095.jpeg', 
    'Coffee Travel Mug', 
),
(
    500,
    1.99,
    'An ornage stress ball to toss as the source of your irritation ', 
    'https://manasah.azureedge.net/pictures/0030237_giftex-100pcs-stress-balls-orange-7-cm_510.jpeg', 
    'Stress Ball (Orange)'
);


INSERT INTO users (email, password, first_name, last_name, role)
VALUES (
           'admin@gmail.com',
           'auth0|62e0f8d6a6c5ffa1e877de65',
           'testAdmin',
           'User',
           'ADMIN'
       );

INSERT INTO purchases (quantity, order_placed, product_id, user_id)
VALUES (
           1,
           CURRENT_TIMESTAMP,
           1,
           1
       );


INSERT INTO addresses (address_id, street, secondary, city, zip, state)
VALUES (
           1,
           '844 california street',
           '',
           'Los Angeles',
           '39999',
           'CA'
       );

INSERT INTO users_addresses (address_id, user_id)
VALUES (
           1,
           1
       );

INSERT INTO reviews (review_id, posted, review, stars, title, updated, product_id, user_id)
VALUES (
           1,
        '2022-07-31 22:38:11.28',
           'this pair of headphones is fire',
           5,
           'great stuff man',
           '2022-07-31 22:38:11.28',
           1,
           1
       );

INSERT INTO reviews (review_id,posted, review, stars, title,updated, product_id, user_id)
VALUES (
           2,
           '2022-07-31 22:38:11.28',
           'A nice TeeShirt',
           4,
           'great stuff man',
           '2022-07-31 22:38:11.28',
           2,
           1
       );

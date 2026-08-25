-- ============================================================
-- Seed data for Menu Service (menu_db)
-- Tables: restaurant_profiles, menu_items
-- ============================================================

-- Clear existing data (safe for repeated dev runs)
DELETE FROM menu_items;
DELETE FROM restaurant_profiles;

-- ============================================================
-- RESTAURANTS (5 total)
-- Note: user_id values below are placeholders (101-105).
-- Replace them with real RESTAURANT_OWNER user IDs from your
-- auth_db if you want ownership checks to work correctly.
-- ============================================================

INSERT INTO restaurant_profiles (id, user_id, restaurant_name, address, cuisine_type, approved) VALUES
(1, 101, 'Pizza Hut',        '123 MG Road, Bangalore',      'Italian',  true),
(2, 102, 'Spice Villa',      '45 Park Street, Kolkata',     'Indian',   true),
(3, 103, 'Sushi Central',    '78 Marine Drive, Mumbai',     'Japanese', true),
(4, 104, 'Burger Barn',      '12 Brigade Road, Bangalore',  'American', true),
(5, 105, 'Taco Fiesta',      '56 Anna Salai, Chennai',      'Mexican',  true);

-- ============================================================
-- MENU ITEMS (10 per restaurant = 50 total)
-- ============================================================

-- Restaurant 1: Pizza Hut (Italian)
INSERT INTO menu_items (restaurant_id, name, description, price, category, available) VALUES
(1, 'Margherita Pizza',   'Classic cheese and tomato pizza',        299.00, 'Main Course', true),
(1, 'Pepperoni Pizza',    'Loaded with spicy pepperoni',            349.00, 'Main Course', true),
(1, 'Farmhouse Pizza',    'Loaded with fresh vegetables',           329.00, 'Main Course', true),
(1, 'Garlic Bread',       'Crispy garlic bread sticks',             149.00, 'Starters',    true),
(1, 'Cheesy Breadsticks', 'Stuffed with mozzarella cheese',         179.00, 'Starters',    true),
(1, 'Caesar Salad',       'Fresh romaine with caesar dressing',     199.00, 'Starters',    true),
(1, 'Penne Alfredo',      'Creamy alfredo pasta',                   279.00, 'Main Course', true),
(1, 'Tiramisu',           'Classic Italian coffee dessert',         199.00, 'Desserts',    true),
(1, 'Chocolate Lava Cake','Warm chocolate cake with molten center', 179.00, 'Desserts',    true),
(1, 'Coke',               '300ml chilled soft drink',                60.00, 'Beverages',   true);

-- Restaurant 2: Spice Villa (Indian)
INSERT INTO menu_items (restaurant_id, name, description, price, category, available) VALUES
(2, 'Butter Chicken',     'Creamy tomato based chicken curry',      349.00, 'Main Course', true),
(2, 'Paneer Tikka Masala','Grilled paneer in spiced gravy',         299.00, 'Main Course', true),
(2, 'Dal Makhani',        'Slow cooked black lentils',               229.00, 'Main Course', true),
(2, 'Chicken Biryani',    'Fragrant basmati rice with chicken',     319.00, 'Main Course', true),
(2, 'Samosa (2 pcs)',     'Crispy fried pastry with spiced potato',  79.00, 'Starters',    true),
(2, 'Chicken Tikka',      'Char-grilled marinated chicken chunks',  249.00, 'Starters',    true),
(2, 'Garlic Naan',        'Tandoor baked garlic flatbread',          59.00, 'Breads',      true),
(2, 'Butter Naan',        'Soft tandoor baked flatbread',            49.00, 'Breads',      true),
(2, 'Gulab Jamun (2 pcs)','Deep fried milk balls in sugar syrup',    99.00, 'Desserts',    true),
(2, 'Masala Chai',        'Spiced Indian tea',                       49.00, 'Beverages',   true);

-- Restaurant 3: Sushi Central (Japanese)
INSERT INTO menu_items (restaurant_id, name, description, price, category, available) VALUES
(3, 'California Roll',    'Crab, avocado and cucumber roll',        399.00, 'Main Course', true),
(3, 'Salmon Nigiri (4 pcs)','Fresh salmon over pressed rice',       449.00, 'Main Course', true),
(3, 'Spicy Tuna Roll',     'Tuna with spicy mayo',                  429.00, 'Main Course', true),
(3, 'Veg Tempura Roll',    'Crispy vegetable tempura roll',         349.00, 'Main Course', true),
(3, 'Miso Soup',           'Traditional soybean paste soup',        129.00, 'Starters',    true),
(3, 'Edamame',             'Steamed and salted soybeans',           149.00, 'Starters',    true),
(3, 'Chicken Katsu',       'Breaded and fried chicken cutlet',      379.00, 'Main Course', true),
(3, 'Gyoza (6 pcs)',       'Pan-fried pork dumplings',               249.00, 'Starters',    true),
(3, 'Mochi Ice Cream',     'Japanese rice cake with ice cream',      199.00, 'Desserts',    true),
(3, 'Green Tea',           'Traditional hot Japanese green tea',      69.00, 'Beverages',   true);

-- Restaurant 4: Burger Barn (American)
INSERT INTO menu_items (restaurant_id, name, description, price, category, available) VALUES
(4, 'Classic Cheeseburger','Beef patty with cheddar cheese',        249.00, 'Main Course', true),
(4, 'Double Bacon Burger', 'Two patties with crispy bacon',         329.00, 'Main Course', true),
(4, 'Veggie Burger',       'Grilled vegetable and bean patty',      219.00, 'Main Course', true),
(4, 'Chicken Wings (6 pcs)','Spicy buffalo chicken wings',          249.00, 'Starters',    true),
(4, 'Loaded Nachos',       'Nachos with cheese, jalapenos, salsa',  199.00, 'Starters',    true),
(4, 'French Fries',        'Crispy golden fries',                   129.00, 'Starters',    true),
(4, 'Onion Rings',         'Crispy battered onion rings',           139.00, 'Starters',    true),
(4, 'Chocolate Milkshake', 'Thick chocolate shake',                  159.00, 'Beverages',   true),
(4, 'Oreo Sundae',         'Vanilla ice cream with crushed oreos',   179.00, 'Desserts',    true),
(4, 'Pepsi',               '300ml chilled soft drink',                60.00, 'Beverages',   true);

-- Restaurant 5: Taco Fiesta (Mexican) - NOT YET APPROVED
INSERT INTO menu_items (restaurant_id, name, description, price, category, available) VALUES
(5, 'Chicken Tacos (3 pcs)','Soft tacos with grilled chicken',       249.00, 'Main Course', true),
(5, 'Beef Burrito',         'Large burrito with seasoned beef',      279.00, 'Main Course', true),
(5, 'Veggie Quesadilla',    'Grilled tortilla with cheese and veg',  219.00, 'Main Course', true),
(5, 'Nachos Supreme',       'Loaded nachos with guacamole',          229.00, 'Starters',    true),
(5, 'Guacamole & Chips',    'Fresh avocado dip with tortilla chips', 179.00, 'Starters',    true),
(5, 'Chicken Quesadilla',   'Grilled tortilla with chicken and cheese',249.00,'Main Course',true),
(5, 'Churros',              'Fried dough pastry with cinnamon sugar',149.00, 'Desserts',    true),
(5, 'Mexican Rice',         'Seasoned tomato rice',                   99.00, 'Sides',       true),
(5, 'Refried Beans',        'Creamy seasoned beans',                  89.00, 'Sides',       true),
(5, 'Horchata',             'Sweet rice and cinnamon drink',          89.00, 'Beverages',   true);

-- ============================================================
-- Reset the auto-increment sequences (PostgreSQL specific)
-- Needed because we inserted explicit IDs for restaurant_profiles
-- and let menu_items auto-generate its own IDs.
-- ============================================================
SELECT setval(pg_get_serial_sequence('restaurant_profiles', 'id'), (SELECT MAX(id) FROM restaurant_profiles));
SELECT setval(pg_get_serial_sequence('menu_items', 'id'), (SELECT MAX(id) FROM menu_items));
-- 1. Clear existing data (optional, if you want to reset the tables)
DELETE FROM sales;
DELETE FROM products;
DELETE FROM sales_representatives;

-- 2. Insert Sample Sales Representatives
INSERT INTO sales_representatives (name, region, hire_date)
VALUES
    ('Alice Johnson', 'North', '2020-01-15'),
    ('Bob Smith', 'South', '2021-05-20'),
    ('Charlie Davis', 'East', '2019-10-10');

-- 3. Insert Sample Products
INSERT INTO products (name, category, price)
VALUES
    ('Laptop', 'Electronics', 1000.00),
    ('Smartphone', 'Electronics', 500.00),
    ('Tablet', 'Electronics', 300.00),
    ('Office Chair', 'Furniture', 150.00);

-- 4. Insert Realistic Sales Data for the Past 12 Months
DO $$
DECLARE
start_date DATE := date_trunc('month', CURRENT_DATE) - INTERVAL '11 months';  -- Start 12 months ago
    sale_date DATE;
    rep_ids INT[] := ARRAY[1, 2, 3];         -- Rep IDs (Alice, Bob, Charlie)
    product_ids INT[] := ARRAY[1, 2, 3, 4];  -- Product IDs (Laptop, Smartphone, Tablet, Office Chair)
    i INT;
    rep_id INT;
    prod_id INT;  -- Renamed loop variable for product
    quantity INT;
    unit_price DECIMAL;
    total DECIMAL;
BEGIN
FOR i IN 0..11 LOOP  -- For each of the last 12 months
        sale_date := start_date + (i * INTERVAL '1 month');

        -- Each month, each rep sells each product
        FOREACH rep_id IN ARRAY rep_ids LOOP
            FOREACH prod_id IN ARRAY product_ids LOOP
                -- Random quantity: 1 to 10
                quantity := FLOOR(1 + RANDOM() * 10);

                -- Get the product price
SELECT p.price INTO unit_price FROM products p WHERE p.product_id = prod_id;

total := unit_price * quantity;

                -- Random day within the month (1 to 28 for safety)
                sale_date := date_trunc('month', sale_date) + (FLOOR(RANDOM() * 28) || ' days')::INTERVAL;

                -- Insert the sale
INSERT INTO sales (rep_id, product_id, sale_date, quantity, total_amount)
VALUES (rep_id, prod_id, sale_date, quantity, total);
END LOOP;
END LOOP;
END LOOP;
END $$;

-- Optional: Check if data was inserted properly (for debugging)
SELECT * FROM sales_representatives;
SELECT * FROM products;
SELECT * FROM sales LIMIT 10;

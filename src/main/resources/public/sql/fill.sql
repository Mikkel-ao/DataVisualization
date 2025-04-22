-- First, clear existing sales data
DELETE FROM sales;

-- Now generate realistic sales for the past 12 months
DO $$
DECLARE
start_date DATE := date_trunc('month', CURRENT_DATE) - INTERVAL '11 months';
    sale_date DATE;
    rep_ids INT[] := ARRAY[1, 2, 3];         -- Alice, Bob, Charlie
    product_ids INT[] := ARRAY[1, 2, 3, 4];  -- Laptop, Smartphone, Tablet, Office Chair
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

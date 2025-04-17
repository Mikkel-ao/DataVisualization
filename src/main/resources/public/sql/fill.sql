-- Insert Sample Sales Representatives
INSERT INTO sales_representatives (name, region, hire_date)
VALUES
    ('Alice Johnson', 'North', '2020-01-15'),
    ('Bob Smith', 'South', '2021-05-20'),
    ('Charlie Davis', 'East', '2019-10-10');

-- Insert Sample Products
INSERT INTO products (name, category, price)
VALUES
    ('Laptop', 'Electronics', 1000.00),
    ('Smartphone', 'Electronics', 500.00),
    ('Tablet', 'Electronics', 300.00),
    ('Office Chair', 'Furniture', 150.00);

-- Insert Sample Sales Data
INSERT INTO sales (rep_id, product_id, sale_date, quantity, total_amount)
VALUES
    (1, 1, '2025-04-10', 5, 5000.00),  -- Alice sold 5 laptops
    (2, 2, '2025-04-12', 10, 5000.00), -- Bob sold 10 smartphones
    (3, 3, '2025-04-15', 3, 900.00),   -- Charlie sold 3 tablets
    (1, 4, '2025-04-16', 7, 1050.00);  -- Alice sold 7 office chairs

-- Insert Sample Sales Targets
INSERT INTO sales_targets (rep_id, target_month, target_amount)
VALUES
    (1, '2025-04-01', 10000.00),  -- Alice's target for April 2025
    (2, '2025-04-01', 8000.00),   -- Bob's target for April 2025
    (3, '2025-04-01', 6000.00);   -- Charlie's target for April 2025

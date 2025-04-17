-- Create sales_representatives table first since it's referenced by the others
CREATE TABLE sales_representatives (
                                       rep_id    SERIAL PRIMARY KEY,
                                       name      VARCHAR(255) NOT NULL,
                                       region    VARCHAR(100),
                                       hire_date DATE
);

-- Create products table next since it's referenced by the sales table
CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          name       VARCHAR(255) NOT NULL,
                          category   VARCHAR(100),
                          price      DECIMAL(10, 2) NOT NULL
);

-- Create sales table which references sales_representatives and products
CREATE TABLE sales (
                       sale_id      SERIAL PRIMARY KEY,
                       rep_id       INT,
                       product_id   INT,
                       sale_date    DATE,
                       quantity     INT,
                       total_amount DECIMAL(10, 2),
                       FOREIGN KEY (rep_id) REFERENCES sales_representatives(rep_id),
                       FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Create sales_targets table which references sales_representatives
CREATE TABLE sales_targets (
                               target_id     SERIAL PRIMARY KEY,
                               rep_id        INT,
                               target_month  DATE,
                               target_amount DECIMAL(10, 2),
                               FOREIGN KEY (rep_id) REFERENCES sales_representatives(rep_id)
);

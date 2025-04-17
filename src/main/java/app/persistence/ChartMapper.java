package app.persistence;

import app.entities.ChartData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChartMapper {
    private final Connection conn;

    public ChartMapper(Connection conn) {
        this.conn = conn;
    }

    public ChartData topSelling() throws SQLException {
        // SQL query to fetch top-selling products for the current month
        String sql = """
    SELECT p.name AS label,
           SUM(s.total_amount) AS value
    FROM sales s
    JOIN products p ON s.product_id = p.product_id
    WHERE date_trunc('month', s.sale_date) = date_trunc('month', CURRENT_DATE)
    GROUP BY p.name
    ORDER BY value DESC;
    """;

        // Execute SQL query and collect data
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<String> labels = new ArrayList<>();
            List<Number> values = new ArrayList<>();

            // Collect the results of the query
            while (rs.next()) {
                labels.add(rs.getString("label"));  // Product name (label)
                values.add(rs.getDouble("value"));  // Total sales value (value)
            }

            // Return the chart data (labels and values)
            return new ChartData(labels, values);
        }
    }

    public ChartData monthlySalesLastYear() throws SQLException {
        String sql = """
        SELECT to_char(s.sale_date, 'YYYY-MM') AS label,
               SUM(s.total_amount) AS value
        FROM sales s
        WHERE s.sale_date >= CURRENT_DATE - INTERVAL '1 year'
        GROUP BY label
        ORDER BY label;
    """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<String> labels = new ArrayList<>();
            List<Number> values = new ArrayList<>();

            while (rs.next()) {
                labels.add(rs.getString("label"));  // Month
                values.add(rs.getDouble("value"));  // Total amount
            }

            return new ChartData(labels, values);
        }
    }

}

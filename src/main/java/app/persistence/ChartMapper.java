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
        String sql = """
        SELECT p.name AS label,
               SUM(s.total_amount) AS value
        FROM sales s
        JOIN products p ON s.product_id = p.product_id
        WHERE s.sale_date >= date_trunc('month', CURRENT_DATE)
          AND s.sale_date < date_trunc('month', CURRENT_DATE) + INTERVAL '1 month'
        GROUP BY p.name
        ORDER BY value DESC
        LIMIT 5;
    """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<String> labels = new ArrayList<>();
            List<Number> values = new ArrayList<>();

            while (rs.next()) {
                labels.add(rs.getString("label"));
                values.add(rs.getDouble("value"));
            }

            if (labels.isEmpty()) {
                labels.add("No Sales");
                values.add(0);
            }

            return new ChartData(labels, values);
        }
    }


    public ChartData monthlySalesLastYear() throws SQLException {
        String sql = """
        SELECT date_trunc('month', s.sale_date)::date AS label,
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

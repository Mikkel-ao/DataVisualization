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

    public ChartData getChartData() throws SQLException {
        String sql = """
        SELECT p.name AS label,
               SUM(s.total_amount) AS value
        FROM sales s
        JOIN products p ON s.product_id = p.product_id
        WHERE date_trunc('month', s.sale_date) = date_trunc('month', CURRENT_DATE)
        GROUP BY p.name
        ORDER BY value DESC;
        """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<String> labels = new ArrayList<>();
            List<Number> values = new ArrayList<>();

            while (rs.next()) {
                labels.add(rs.getString("label"));
                values.add(rs.getDouble("value"));
            }

            return new ChartData(labels, values);
        }
    }

}

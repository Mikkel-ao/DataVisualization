package app;

import app.chart.ChartService;
import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.entities.ChartData;
import app.persistence.ChartMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "DataVisualization";

    public static void main(String[] args) {
        // Initialize Connection Pool
        ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        // Fetch data and generate chart
        try (Connection conn = connectionPool.getConnection()) {
            ChartMapper repo = new ChartMapper(conn);
            ChartData data = repo.getChartData();
            ChartService.generateBarChart(data);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return; // Exit if DB/chart generation fails
        }

        // Start Javalin server
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public"); // Serve /resources/public
            config.jetty.modifyServletContextHandler(handler ->
                    handler.setSessionHandler(SessionConfig.sessionConfig())
            );
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Route: render index.html via Thymeleaf
        app.get("/", ctx -> ctx.render("index.html"));
    }
}

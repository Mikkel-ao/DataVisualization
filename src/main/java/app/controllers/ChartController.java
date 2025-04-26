package app.controllers;

import app.chart.ChartService;
import app.entities.ChartData;
import app.persistence.ChartMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ChartController {

    private static ConnectionPool connectionPool;

    public static void addRoutes(Javalin app, ConnectionPool pool) {
        connectionPool = pool;

        // Render index and generate charts on the fly
        app.get("/", ctx -> renderIndex(ctx));
    }

    private static void renderIndex(Context ctx) {
        try (Connection conn = connectionPool.getConnection()) {
            ChartMapper repo = new ChartMapper(conn);

            // Fetch data for charts
            ChartData data = repo.topSelling();
            ChartData yearly = repo.monthlySalesLastYear();

            // Generate charts
            ChartService.generateBarChart(data);
            ChartService.generatePieChart(data);
            ChartService.generateLineChart(yearly);

            // After chart generation, render the index page
            ctx.render("index.html");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            ctx.status(500).result("Failed to generate charts: " + e.getMessage());
        }
    }
}

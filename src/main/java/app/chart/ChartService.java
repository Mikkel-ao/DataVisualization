package app.chart;

import app.entities.ChartData;
import org.knowm.xchart.*;


import java.io.IOException;

public class ChartService {
    public static void generateBarChart(ChartData data) throws IOException {
        CategoryChart chart = new CategoryChartBuilder()
                .width(600)
                .height(400)
                .title("Top Selling Products - This Month")
                .xAxisTitle("Product Name")
                .yAxisTitle("Total Sales Value ($)")
                .build();

        chart.addSeries("Sales Data", data.labels, data.values);
        BitmapEncoder.saveBitmap(chart, "src/main/resources/public/charts/barchart", BitmapEncoder.BitmapFormat.PNG);
    }

    public static void generatePieChart(ChartData data) throws IOException {
        // Create a Pie Chart using PieChartBuilder
        PieChart chart = new PieChartBuilder()
                .width(600)
                .height(400)
                .title("Product Sales Distribution")
                .build();

        // Ensure values are of type Number (double or integer) if they're not
        for (int i = 0; i < data.labels.size(); i++) {
            try {
                // Convert string to number and add to chart
                chart.addSeries(data.labels.get(i), Double.parseDouble(data.values.get(i).toString()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid value encountered: " + data.values.get(i));
            }
        }

        // Save the pie chart as an image
        BitmapEncoder.saveBitmap(chart, "src/main/resources/public/charts/piechart", BitmapEncoder.BitmapFormat.PNG);
    }

}


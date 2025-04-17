package app.chart;

import app.entities.ChartData;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

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
        BitmapEncoder.saveBitmap(chart, "src/main/resources/public/chart1", BitmapEncoder.BitmapFormat.PNG);
    }
}


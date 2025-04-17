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
                .title("Generated from DB")
                .xAxisTitle("Label")
                .yAxisTitle("Value")
                .build();

        chart.addSeries("Series 1", data.labels, data.values);
        BitmapEncoder.saveBitmap(chart, "src/main/resources/public/chart", BitmapEncoder.BitmapFormat.PNG);
    }
}

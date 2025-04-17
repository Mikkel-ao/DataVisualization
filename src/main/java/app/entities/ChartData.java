package app.entities;

import java.util.List;

public class ChartData {
    public List<String> labels;
    public List<Number> values;

    public ChartData(List<String> labels, List<Number> values) {
        this.labels = labels;
        this.values = values;
    }
}

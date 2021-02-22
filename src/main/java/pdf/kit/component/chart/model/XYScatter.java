package pdf.kit.component.chart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fgm on 2017/7/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XYScatter {
    private double x;
    private double y;
    private String label;

}

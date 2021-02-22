package pdf.kit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fgm on 2017/4/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XYLine {
    private double yValue;
    private String  xValue;
    private String groupName;
}

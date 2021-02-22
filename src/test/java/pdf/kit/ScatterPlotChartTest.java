package pdf.kit;

import com.google.common.collect.Lists;
import pdf.kit.component.chart.ScatterPlotChart;
import pdf.kit.component.chart.model.XYScatter;

import java.util.List;

/**
 * Created by fgm on 2017/7/9.
 */
public class ScatterPlotChartTest {




    public static void main(String[] args) {
        List<XYScatter> list=getData();
        String path= ScatterPlotChart.draw(list,-1,"他评得分(%)","自评得分(%)");
        System.out.println(path);
    }

    public static List<XYScatter> getData() {
        List<XYScatter> list= Lists.newArrayList();
        XYScatter xy=new XYScatter(45,55,"主见");
        list.add(xy);

        XYScatter xy1=new XYScatter(75,80,"定战略");
        list.add(xy1);

        XYScatter xy2=new XYScatter(80,80,"拿结果");
        list.add(xy2);

        XYScatter xy3=new XYScatter(90,25,"带团队");
        list.add(xy3);

        return list;
    }
}

package pdf.kit;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import pdf.kit.component.ExporterUtil;
import pdf.kit.component.PDFHeaderFooter;
import pdf.kit.model.TemplateBO;
import pdf.kit.util.PDFKit;
import pdf.kit.util.chart.ScatterPlotChart;
import pdf.kit.model.chart.XYLine;
import pdf.kit.util.chart.impl.DefaultLineChart;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 * 360报告
 */

/**
 * Connected to the target VM, address: '127.0.0.1:64416', transport: 'socket'
 * 生成的折线图路径： /D:/Projects/Project-Ext/pdf-kit/target/test-classes//images/0/折线图.jpg
 * 生成的散点图路径： /D:/Projects/Project-Ext/pdf-kit/target/test-classes//images/1/scatterChart.png
 * 11:32:35.710 [main] DEBUG freemarker.cache - Couldn't find template in cache for "hello.ftl"("zh_CN", UTF-8, parsed); will try to load it.
 * 11:32:35.716 [main] DEBUG freemarker.cache - TemplateLoader.findTemplateSource("hello_zh_CN.ftl"): Not found
 * 11:32:35.717 [main] DEBUG freemarker.cache - TemplateLoader.findTemplateSource("hello_zh.ftl"): Not found
 * 11:32:35.718 [main] DEBUG freemarker.cache - TemplateLoader.findTemplateSource("hello.ftl"): Found
 * 11:32:35.719 [main] DEBUG freemarker.cache - Loading template for "hello.ftl"("zh_CN", UTF-8, parsed) from "D:\\Projects\\Project-Ext\\pdf-kit\\target\\test-classes\\templates\\hello.ftl"
 * 11:32:37.310 [main] INFO  pdf.kit.component.builder.PDFBuilder - PDF文档字体初始化完成!
 */
@Slf4j
public class ReportKit360 {

    private final static String PDF_SAVE_PATH = "D:\\Projects\\Project-Ext/pdf/hello.pdf";


    public String createPDF(String templatePath, Object data, String fileName) {
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter = new PDFHeaderFooter();
            PDFKit kit = new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath(PDF_SAVE_PATH);
            return kit.exportToFile(fileName, data);
        } catch (Exception e) {
            log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
            return null;
        }

    }

    public static void main(String[] args) {
        ReportKit360 kit = new ReportKit360();
        TemplateBO templateBO = new TemplateBO();
        templateBO.setTemplateName("Hello iText! Hello freemarker! Hello jFreeChart!");
        templateBO.setFreeMarkerUrl("http://www.zheng-hang.com/chm/freemarker2_3_24/ref_directive_if.html");
        templateBO.setITEXTUrl("http://developers.itextpdf.com/examples-itext5");
        templateBO.setJFreeChartUrl("http://www.yiibai.com/jfreechart/jfreechart_referenced_apis.html");
        templateBO.setImageUrl("http://mss.vip.sankuai.com/v1/mss_74e5b6ab17f44f799a524fa86b6faebf/360report/logo_1.png");

        // 设置分数 list集合
        List<String> scores = new ArrayList<String>();
        scores.add("90");
        scores.add("95");
        scores.add("98");
        templateBO.setScores(scores);

        //折线图
        List<XYLine> lineList = getTemperatureLineList();
        DefaultLineChart lineChart = new DefaultLineChart();
        lineChart.setHeight(500);
        lineChart.setWidth(300);
        lineChart.setFileName("折线图.jpg");

        String picUrlPath = ExporterUtil.getClassPath(ReportKit360.class, ExporterUtil.IMAGE_LINE_CHART_PATH, "0");
        lineChart.draw(lineList, picUrlPath);
        templateBO.setPicUrl(picUrlPath);

        String picUrlPath1 = ExporterUtil.getClassPath(ReportKit360.class, ExporterUtil.IMAGE_LINE_CHART_PATH, "1");

        lineChart.draw("测试折线绘图", "xLable", "yLabel", lineList, picUrlPath1);
        templateBO.setPicUrl1(picUrlPath1);

        //散点图
        String scatterUrl = ScatterPlotChart.draw(ScatterPlotChartTest.getData(), 1, "他评得分(%)", "自评得分(%)");
        templateBO.setScatterUrl(scatterUrl);
//        String templatePath="/Users/fgm/workspaces/fix/pdf-kit/src/test/resources/templates";
        String templatePath = "templates/";
        String path = kit.createPDF(templatePath, templateBO, "hello1.pdf");
        System.out.println(path);

        // 饼状图
        DefaultPieDataset pds = new DefaultPieDataset();
        pds.setValue("男生数",10);
        pds.setValue("女生数",90);
        String filePath = "d:/pie.jpg"; //生成图片路径及图片名
        String title = "男女生比例图"; //饼图标题
        createPieChart(pds,filePath,title);
    }

    public static void createPieChart(DefaultPieDataset pds, String filePath,String title) {
        try {
            // 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
            JFreeChart chart = ChartFactory.createPieChart(title, pds, false, false, true);
            // 如果不使用Font,中文将显示不出来
            Font font = new Font("宋体", Font.BOLD, 12);
            // 设置图片标题的字体
            chart.getTitle().setFont(font);
            // 得到图块,准备设置标签的字体
            PiePlot plot = (PiePlot) chart.getPlot();
            // 设置标签字体
            plot.setLabelFont(font);
            plot.setStartAngle(new Float(3.14f / 2f));
//            plot.setSectionPaint((Comparable) Color.BLUE,Color.RED); // 考虑自定义设置颜色——我不会
            // 设置plot的前景色透明度
            plot.setForegroundAlpha(0.7f);
            // 设置plot的背景色透明度
            plot.setBackgroundAlpha(0.0f);
            // 设置标签生成器(默认{0})
            // {0}:key {1}:value {2}:百分比 {3}:sum
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}占{2})"));
            // 将内存中的图片写到本地硬盘
            ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 600, 300); //600宽度，300高度
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<XYLine> getTemperatureLineList() {
        List<XYLine> list = Lists.newArrayList();
        for (int i = 1; i <= 7; i++) {
            XYLine line = new XYLine();
            float random = Math.round(Math.random() * 10);
            line.setXValue("星期" + i);
            line.setYValue(20 + random);
            line.setGroupName("下周");
            list.add(line);
        }
        for (int i = 1; i <= 7; i++) {
            XYLine line = new XYLine();
            float random = Math.round(Math.random() * 10);
            line.setXValue("星期" + i);
            line.setYValue(20 + random);
            line.setGroupName("这周");
            list.add(line);
        }
        return list;
    }
}

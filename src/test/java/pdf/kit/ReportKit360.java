package pdf.kit;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import pdf.kit.component.PDFHeaderFooter;
import pdf.kit.component.PDFKit;
import pdf.kit.component.chart.ScatterPlotChart;
import pdf.kit.component.chart.model.XYLine;
import pdf.kit.component.chart.impl.DefaultLineChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 * 360报告
 *
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

    public static List<XYLine> getTemperatureLineList() {
        List<XYLine> list= Lists.newArrayList();
        for(int i=1;i<=7;i++){
            XYLine line=new XYLine();
            float random=Math.round(Math.random()*10);
            line.setXValue("星期"+i);
            line.setYValue(20+random);
            line.setGroupName("下周");
            list.add(line);
        }
        for(int i=1;i<=7;i++){
            XYLine line=new XYLine();
            float random=Math.round(Math.random()*10);
            line.setXValue("星期"+i);
            line.setYValue(20+random);
            line.setGroupName("这周");
            list.add(line);
        }
        return list;
    }

    public  String createPDF(String templatePath,Object data, String fileName){
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter=new PDFHeaderFooter();
            PDFKit kit=new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath("D:\\Projects\\Project-Ext/pdf/hello.pdf");

            String saveFilePath=kit.exportToFile(fileName,data);
            return  saveFilePath;
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
        String picUrl = lineChart.draw(lineList, 0);
        templateBO.setPicUrl(picUrl);
        //散点图
        String scatterUrl = ScatterPlotChart.draw(ScatterPlotChartTest.getData(), 1, "他评得分(%)", "自评得分(%)");
        templateBO.setScatterUrl(scatterUrl);
//        String templatePath="/Users/fgm/workspaces/fix/pdf-kit/src/test/resources/templates";
        String templatePath = "templates/";
        String path = kit.createPDF(templatePath, templateBO, "hello.pdf");
        System.out.println(path);
    }

}

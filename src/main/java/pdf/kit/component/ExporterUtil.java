package pdf.kit.component;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author : Ahaolin
 * @Description: 通用的导出实现类
 * @date : 2021-02-22 18:32
 */
public class ExporterUtil {

    /**
     * 折线图路径 {@value}
     */
    public static final String IMAGE_LINE_CHART_PATH = "/images/lineChart";

    /**
     * 散点图路径 {@value}
     */
    public static final String IMAGE_SCATTER_PLOT_PATH = "/images/scatterPlot";

    /**
     * 饼路径 {@value}
     */
    public static final String IMAGE_PIE_CHART_PATH = "/images/pieChart";

    public static String getTmpPath(String templePath) {
        String folder = System.getProperty("catalina.home");
        return folder + File.separator + templePath;
    }

    public static String getClassPath(Class<?> clazz, String... templePath) {
        final String path = Objects.requireNonNull(clazz.getClassLoader().getResource(""),"当前类路径不存在！").getPath();
        final String joinPath = Joiner.on(File.separator).join(templePath);
        return path + joinPath;
    }

}

package pdf.kit.model;

import lombok.Data;

import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 *
 * 需要用于导出的的通用对象
 * 默认 1个pdf对应一个
 */
@Data
public class TemplateBO {

    private String templateName;

    private String freeMarkerUrl;

    private String ITEXTUrl;

    private String JFreeChartUrl;

    private List<String> scores;

    private String imageUrl;

    private String picUrl;
    private String picUrl1;

    private String scatterUrl;
}

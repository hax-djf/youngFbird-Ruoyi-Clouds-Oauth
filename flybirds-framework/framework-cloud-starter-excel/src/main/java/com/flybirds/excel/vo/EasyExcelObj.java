package com.flybirds.excel.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class EasyExcelObj {

    // 导出的文件名
    private String exportName;
    // 模板的文件名
    private String templateName;
    // 导出到具体路径
    private String exportPath;
    // 单 list
    private Object[] data;
    // 多 list 使用 此属性填充
    private Map<String,Object> multiListMap;

}

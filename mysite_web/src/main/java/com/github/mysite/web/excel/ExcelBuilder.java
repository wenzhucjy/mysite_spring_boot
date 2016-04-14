package com.github.mysite.web.excel;

import com.github.mysite.common.excel.ExportExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * description: Excel export builder
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-14 14:20
 */
public class ExcelBuilder extends AbstractXlsxView {

    /**
     * Excel 文件名称，默认需要加后缀xlsx格式，默认是"Export"
     */
    private String fileName;

    /**
     * Excel 表格名称，若为空即为默认值
     */
    private String titleName;

    /**
     * 数据集合 List
     */
    private List   data;

    /**
     * 对象的Class
     */
    private Class<?> clazz;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        // get data model which is passed by the Spring container
        ExcelBuilder excelBuilder = (ExcelBuilder) model.get("excelBuilder");

        String fileName = excelBuilder.getFileName();
        String titleName = excelBuilder.getTitleName();
        Class<?> aClass = excelBuilder.getClazz();
        new ExportExcel(titleName, aClass, workbook).setDataList(excelBuilder.getData()).write(request, response, fileName).dispose();
    }

    public String getFileName() {
        return fileName;
    }

    public List getData() {
        return data;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getTitleName() {
        return titleName;
    }


    /**
     * 构造方法
     *
     * @param data 数据
     * @param clazz Class对象
     */
    public ExcelBuilder(List data, Class<?> clazz) {
        this("", "", data, clazz);
    }

    /**
     * 构造方法
     *
     * @param titleName Excel表格名称
     * @param data 数据
     * @param clazz Class对象
     */
    public ExcelBuilder(String titleName, List data, Class<?> clazz) {
        this("", titleName, data, clazz);
    }

    /**
     * 构造方法
     *
     * @param fileName Excel文件名称
     * @param titleName Excel表格名称
     * @param data 数据
     * @param clazz Class对象
     */
    public ExcelBuilder(String fileName, String titleName, List data, Class<?> clazz) {
        this.fileName = fileName;
        this.titleName = titleName;
        this.data = data;
        this.clazz = clazz;
    }

    /**
     * 默认的构造方法，必须存在，否则报错
     */
    public ExcelBuilder(){
    }
}

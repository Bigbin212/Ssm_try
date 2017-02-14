package com.lbcom.dadelion.common.tool;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author 赵春杨
 * @version v1.0
 * @param <T>
 * 应用泛型，代表任意一个符合javabean风格的类
 * 注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 * byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {
	
    public void exportExcel(String[] headers,
            List<String[]> list, OutputStream out) {
        exportExcel("导出EXCEL文档", headers, list, out);
    }
    public void exportExcel(String[] headers, Collection<T> dataset,
            OutputStream out) {
        exportExcel("导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset,
            OutputStream out, String pattern) {
        exportExcel("导出EXCEL文档", headers, dataset, out, pattern);
    }
    
    /**
     * @author 刘斌
     * @description 合并部分数据
     * @param firstFlow 第一行属性
     * @param secondFlow 第二行属性
     * @param headers 表格属性列数组
     * @param list 需要显示的数据
     * @param out
     */
    public void exportExcel(String title,Boolean merge,String firstFlow,String secondFlow,String[] headers,
   		 List<String[]> list,OutputStream out) {
    	exportExcel_merge("导出EXCEL文档",merge,firstFlow,secondFlow,headers,list, out);
   }
    
    /**
     * @param title 表格标题名
     * @param merge 是否合并
     * @param firstFlow 第一行属性
     * @param secondFlow 第二行属性
     * @param headers 表格属性列数组
     * @param list 需要显示的数据。String[] 里存的数组是要填充的每一行数据。
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * 
     */
    @SuppressWarnings("deprecation")
	public void exportExcel_merge(String title,Boolean merge, String firstFlow,String secondFlow,
    		String[] headers, List<String[]> list,OutputStream out) {
 
 	    // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        
        //根据headers的长度决定合并的列数
        //CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
        int lastColum = headers.length - 1;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, lastColum));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, lastColum));
        // 设置表格默认列宽度为30个字节
        sheet.setDefaultColumnWidth(30);
        
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        //style.setFillForegroundColor(HSSFColor.SKY_BLUE.index); //填充的背景颜色
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //填充图案
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //设置边框样式
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  //左边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  //右边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  //上边框
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 15);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        
        //超链接样式
 	    HSSFCellStyle linkStyle = workbook.createCellStyle();
 	    linkStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
 	    linkStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
 	    linkStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
 	    linkStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
 	    //linkStyle.setWrapText(true);//设置自动换行
 	    HSSFFont cellFont= workbook.createFont();
 	    cellFont.setUnderline((byte) 1);
 	    cellFont.setColor(HSSFColor.BLUE.index);
 	    linkStyle.setFont(cellFont);

        /**
         * 第一行数据及样式
         */
        // 生成并设置第一行样式
        HSSFCellStyle styleFirstrow = workbook.createCellStyle();
        styleFirstrow.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
        styleFirstrow.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成第一行字体
        HSSFFont fontfirstFont = workbook.createFont();
        fontfirstFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        fontfirstFont.setFontName("方正小标宋_GBK");    
        fontfirstFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//常规字体    
        fontfirstFont.setFontHeightInPoints((short) 18);  
        // 把字体应用到当前的样式
        styleFirstrow.setFont(fontfirstFont);

        
        HSSFRow firstRow = sheet.createRow(0);
        HSSFCell firstCell = firstRow.createCell(0);
        HSSFRichTextString firstText = new HSSFRichTextString(firstFlow);
        firstCell.setCellStyle(styleFirstrow);
        firstCell.setCellValue(firstText);
       
        /**
         * 第二行数据
         */
        // 生成并设置第二行样式
        HSSFCellStyle styleSecondrow = workbook.createCellStyle();
        // 生成第一行字体
        HSSFFont fontSecondFont = workbook.createFont();
        fontSecondFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        fontSecondFont.setFontName("方正小标宋_GBK");    
        fontSecondFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//常规字体    
        fontSecondFont.setFontHeightInPoints((short) 11);  
        // 把字体应用到当前的样式
        styleSecondrow.setFont(fontSecondFont);
        
        HSSFRow secondtRow = sheet.createRow(1);
        HSSFCell secondCell = secondtRow.createCell(0);
        HSSFRichTextString secondText = new HSSFRichTextString(secondFlow);
        secondCell.setCellStyle(styleSecondrow);//设置单元格样式
        secondCell.setCellValue(secondText);
        
        // 产生表格标题行
        HSSFRow row = sheet.createRow(2);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        if (merge) {
        	for (int i = 0; i < list.size(); i++) {
    			int rowIndex = i + 3;
    			row = sheet.createRow(rowIndex);

    			for (int j = 0; j < headers.length; j++) {
    				HSSFCell cell = row.createCell(j);// 循环创建每一个表格
    				cell.setCellStyle(style2);// 设置单元格样式
    				String[] textString = list.get(i);
    				
    				if(i>0){
    					if(textString[j]!=null 
    							&& textString[j].equals(list.get(i-1)[j])
    							&& textString[0].equals(list.get(i-1)[0])
    							&& textString[1].equals(list.get(i-1)[1])
    							&& textString[2].equals(list.get(i-1)[2])){
    							sheet.addMergedRegion(new CellRangeAddress(rowIndex-1, rowIndex, j, j));
    							continue;
    					}else{
    						cell.setCellValue(textString[j]);
    					}
    				}else{
    					cell.setCellValue(textString[j]);
    				}
    			}
    		}
		}else {
			Iterator<String[]> it = list.iterator();
	        int index = 2;
	        while(it.hasNext()){
		       	index++;
		       	row = sheet.createRow(index);
		       	String[] str = it.next();
		       	for(int i =0;i<str.length;i++){
	       		   HSSFCell cell = row.createCell(i);//循环创建每一个表格
	               cell.setCellStyle(style2);//设置单元格样式
	               cell.setCellValue(str[i]);
		       	}
	       }
		}

        try {
 			workbook.write(out);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}	
	}
	
    
    /**
     * 
     * @param title
     * 				表格标题名
     * @param headers
     * 				表格属性列数组
     * @param list
     * 				需要显示的数据。String[] 里存的数组是要填充的每一行数据。
     * @param out
     * 				与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * 				
     */
    @SuppressWarnings("deprecation")
	public void exportExcel(String title, String[] headers,
            List<String[]> list, OutputStream out){
    	// 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        
        //超链接样式
	    HSSFCellStyle linkStyle = workbook.createCellStyle();
	    linkStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    linkStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    linkStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    linkStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    //linkStyle.setWrapText(true);//设置自动换行
	    HSSFFont cellFont= workbook.createFont();
	    cellFont.setUnderline((byte) 1);
	    cellFont.setColor(HSSFColor.BLUE.index);
	    linkStyle.setFont(cellFont);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        Iterator<String[]> it = list.iterator();
        int index = 0;
        while(it.hasNext()){
        	index++;
        	row = sheet.createRow(index);
        	String[] str = it.next();
        	for(int i =0;i<str.length;i++){
        		String link = str[i];
        		if(link.startsWith("_")){
        			//设置以_开头
        			String str1 = link.substring(1,link.length());
        			if(!"".equals(str1)){
        				HSSFCell  cell= row.createCell(i);
            			cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            		    cell.setCellFormula("HYPERLINK(\"" +str1+ "\",\"" + str1+ "\")");
            		    cell.setCellStyle(linkStyle);
        			}else{
        				HSSFCell cell = row.createCell(i);//循环创建每一个表格
                        cell.setCellStyle(style2);//设置单元格样式
                        cell.setCellValue(str[i]);
        			}
        	
        		}else{
        			HSSFCell cell = row.createCell(i);//循环创建每一个表格
                    cell.setCellStyle(style2);//设置单元格样式
                    cell.setCellValue(str[i]);
        		}
        		
        	}
        }
        try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)    可以自定义vo重新封装
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
    public void exportExcel(String title, String[] headers,
            Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;//行号增加
            row = sheet.createRow(index);//创建一行
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);//循环创建每一个表格
                cell.setCellStyle(style2);//设置单元格样式
                Field field = fields[i];//获取第i列单元格的对象属性
                String fieldName = field.getName();//获取名称
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);//组装get方法
                try {
                    Class tCls = t.getClass();//获取类名
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});//
                    if(value==null){
                    	value="";
                    }
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    // if (value instanceof Integer) {
                    // int intValue = (Integer) value;
                    // cell.setCellValue(intValue);
                    // } else if (value instanceof Float) {
                    // float fValue = (Float) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(fValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Double) {
                    // double dValue = (Double) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(dValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Long) {
                    // long longValue = (Long) value;
                    // cell.setCellValue(longValue);
                    // }
                    if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue) {
                            textValue = "女";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }

        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
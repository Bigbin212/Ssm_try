package com.lbcom.dadelion.common;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
/**
 * @Description 将List,Map,bean 等传递到页面 工具类
 * @Author  liubin
 * @Version 1.0
 */
public class JSONUtil {

	// 将JSONObject写入输出流
	public static void writeJSONObjectToResponse(HttpServletResponse response,JSONObject jobject) {
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getWriter();
			out.print(jobject);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	// 将JSONArray写入输出流
	public static void writeJSONArrayToResponse(HttpServletResponse response,net.sf.json.JSONArray ja) {
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getWriter();
			out.print(ja);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.close();
			}

		}
	}
}

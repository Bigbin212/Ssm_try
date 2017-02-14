package com.lbcom.dadelion.common;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 * 删除日志任务
 *
 */
public class DeleteLogTask {

	private static Logger _log = Logger.getLogger(DeleteLogTask.class);
	
	@Value("#{configProperties['baseFolderPath']}")
	private String _baseFolderPath; 
	
	public void  delete(){
		try {
			_log.info("删除日志任务启动" + _baseFolderPath);
			if(StringUtil.isNotNullOrEmpty(_baseFolderPath)){				
				deleteFolder(_baseFolderPath);
		        _log.info("删除日志任务结束");
			}
		} catch (Exception e) {
			_log.info("执行删除日志定时器出错");
			e.printStackTrace();
		}
	}
	
	//删除文件夹
	public void deleteFolder(String dir) {
		  File delfolder=new File(dir); 
		  if(delfolder != null ){
			  File[] oldFile = delfolder.listFiles();
			  try{ 
				  
				  if(oldFile != null ){
					  for(int i = 0; i < oldFile.length; i++){
						  if(oldFile[i] != null ){
							  if(oldFile[i].isDirectory()){
								  deleteFolder(dir+"/"+oldFile[i].getName());
							  }
							  oldFile[i].delete();
						  }
					  }
				  }
			  }catch (Exception e){ 
				  _log.info("执行删除删除日志定时器出错");
			    e.printStackTrace(); 
			  }
		  }
		}
}

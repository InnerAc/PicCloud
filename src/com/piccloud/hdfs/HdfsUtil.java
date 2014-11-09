package com.piccloud.hdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

/**
 * HDFS相关操作API
 * @author Jet-Muffin
 *
 */
public class HdfsUtil {
	
	private Configuration conf;
	private FileSystem fs;
	
	/**
	 * 构造方法
	 * @throws IOException
	 */
	public HdfsUtil() throws IOException{
		conf = new Configuration();
		String hdfsPath = com.piccloud.hdfs.HdfsConfig.getHDFSUrl();
		fs = FileSystem.get(URI.create(hdfsPath),conf);
	}
	
	/**
	 * 上传文件
	 * @param in
	 * @param hdfsPath
	 * @return boolean
	 */
	public boolean upLoad(InputStream in, String hdfsPath){
		Path p = new Path(hdfsPath);
		try{
			if(fs.exists(p)){
				System.out.println("文件已存在！");
				return false;
			}

			Progressable progress = new Progressable(){
				public void progress() {					
					System.out.print(".");
				}			
			};
			FSDataOutputStream out = fs.create(p,progress);
			IOUtils.copyBytes(in, out, conf);
			
			byte[] buffer = new byte[400];
			int length = 0;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
			out.close();
			in.close();		
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 *  下载文件
	 * @param hdfsPath
	 * @param localPath
	 * @return boolean
	 */
	public boolean downLoad(String hdfsPath,String localPath ){
		Path path = new Path(hdfsPath);
		try {
			if(!fs.exists(path)){
				System.out.println("未找到文件！");
				return false;
			}
			FSDataInputStream in =  fs.open(path);
			OutputStream out = new FileOutputStream(localPath);
			byte[] buffer = new byte[400];
			int length = 0;
			while((length = in.read(buffer))>0){
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	/**
	 * 删除文件
	 * @param hdfsPath
	 * @return boolean
	 */
	public boolean deletePath(String hdfsPath){
		Path path = new Path(hdfsPath);
		try {
			if(!fs.exists(path)){
				System.out.println("未找到文件！");
				return false;
			}
			fs.delete(path, true);
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

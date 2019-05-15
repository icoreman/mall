package com.xuxx.mall.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuxx.entity.Result;

import util.FastDFSClient;

@RestController
public class UploadController {

	@Value("${FILE_SERVER_URL}")
	private String file_server_url;

	@RequestMapping("/upload")
	public Result upload(MultipartFile file) {
		if(file == null) {
			return Result.buildFailResult("文件为空");
		}
		// 获取文件名
		String originalFilename = file.getOriginalFilename();
		// 得到扩展名
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

		try {
			util.FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
			String fileId = client.uploadFile(file.getBytes(), extName);
			String url = file_server_url + fileId;// 图片完整地址
			return Result.buildSuccessResult(url);

		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("上传失败");
		}
	}
}

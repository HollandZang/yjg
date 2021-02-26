package com.holland.holland.controller;

import com.holland.holland.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Api(tags = {"附件类API"})
@RestController
@RequestMapping("/comFile")
public class FileController {

    @Value("${file.savePath}")
    private String savePath;

    @ApiOperation(value = "附件上传")
    @ApiImplicitParam(paramType = "form", name = "file", value = "文件", required = true, dataTypeClass = MultipartFile.class)
    @PostMapping("/upload")
    public Response upload(@ApiIgnore MultipartHttpServletRequest request) throws IOException {
        System.out.println(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());

        MultipartFile file = request.getFiles("file").get(0);
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        FileUtils.writeByteArrayToFile(new File(savePath + fileName), file.getBytes(), false);

        return Response.success(savePath + fileName);
    }

    @ApiOperation(value = "附件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "filePath", value = "文件路径", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileName", value = "文件名", required = true, dataType = "String"),
    })
    @GetMapping("/comFile/download")
    public void download(String filePath, @ApiIgnore HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        File file = new File(filePath);
        fileName = (fileName == null) ? filePath.substring(filePath.lastIndexOf("/") + 1) : fileName;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            // 创建输出流
            os = response.getOutputStream();
            // 创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区当中
            while ((len = bis.read(buffer)) > 0) {
                // 输出缓冲区的内容到浏览器，实现文件下载
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

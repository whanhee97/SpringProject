package springProj.safeRestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
public class FileDownloadController {

    @RequestMapping("fileDownload/details")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = request.getParameter("fileName");
        String originFileName = request.getParameter("originFileName");
        originFileName = new String(originFileName.getBytes("UTF-8"),"ISO-8859-1");

        String realFilename = "";
        try{
            String browser = request.getHeader("User-Agent"); // 브라우저 받아오기
            //파일 인코딘
            if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
                filename = URLEncoder.encode(filename,"UTF-8".replaceAll("\\+","%20"));
            }
            else{
                filename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        realFilename = "C:\\Users\\HHLee\\study\\upload\\" + filename;
        File file1 = new File(realFilename);
        if(!file1.exists()){
            return;
        }

        // 파일명 지정 후 스트림으로 전송
        response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + originFileName + "\"");

        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(realFilename);

            int ncount = 0;
            byte[] bytes = new byte[512];

            while ((ncount = fileInputStream.read(bytes)) != -1){
                os.write(bytes,0,ncount);
            }
            fileInputStream.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

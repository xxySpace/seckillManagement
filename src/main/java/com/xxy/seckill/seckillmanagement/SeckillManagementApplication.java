package com.xxy.seckill.seckillmanagement;

import com.xxy.seckill.seckillmanagement.dao.UserDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.UserDAO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

@SpringBootApplication(scanBasePackages = {"com.xxy.seckill.seckillmanagement"})
@RestController
@MapperScan("com.xxy.seckill.seckillmanagement.dao")
public class SeckillManagementApplication {

    @Autowired
    private UserDAOMapper userDAOMapper;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SeckillManagementApplication.class, args);

        ClassPathResource resource = new ClassPathResource("templates" + File.separator + "login.html");
        String path = resource.getFile().getPath();
        browse(path);
    }

    @RequestMapping("/")
    public String home() {
        UserDAO userDAO = userDAOMapper.selectByPrimaryKey(1);
        if (null == userDAO) {
            return "用户对象不存在";
        } else {
            return userDAO.getName();
        }
    }

    /**
     * 使用默认浏览器打开url
     * @param url 打开文件的路径
     * @throws Exception
     */
    private static void browse(String url) throws Exception {
        // 获取操作系统的名字
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Windows")) {
            // windows
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (osName.startsWith("Mac OS")) {
            // Mac
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", String.class);
            openURL.invoke(null, url);
        } else {
            // Unix or Linux
            String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
            String browser = null;
            // 执行代码，在brower有值后跳出，
            for (int count = 0; count < browsers.length && browser == null; count++) {
                // 这里是如果进程创建成功了，==0是表示正常结束。
                if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
                    browser = browsers[count];
                }
            }
            if (browser == null) {
                throw new RuntimeException("未找到任何可用的浏览器");
            } else {
                // 这个值在上面已经成功的得到了一个进程。
                Runtime.getRuntime().exec(new String[]{browser, url});
            }
        }
    }

}

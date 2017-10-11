package org.alvin.donglan.ops.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.alvin.donglan.ops.bean.AppBean;
import org.alvin.donglan.ops.bean.ServerBean;

public class AppService {

    private SSHToolService sSHToolService = new SSHToolService();

    public void startApp(ServerBean serverBean, AppBean appBean) {
        try {
            //构建脚本
            String cmd = createCmd(appBean);
            String fileName = "opsRun.sh";
            ByteArrayInputStream bis = new ByteArrayInputStream(cmd.getBytes());
            //上传脚本
            this.uploadFile(serverBean, bis, fileName);
            //执行脚本
            Session session = sSHToolService.getSession(serverBean);
            String msg = sSHToolService.exec(session, "sh /root/" + fileName);
            System.out.println(msg);
            //删除脚本
            session = sSHToolService.getSession(serverBean);
            msg = sSHToolService.exec(session, "rm /root/" + fileName);
            System.out.println(msg);
        } catch (Exception ex) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadFile(ServerBean server, File file) {
        try (InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()));) {
            uploadFile(server, is, file.getName());
        } catch (Exception ex) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadFile(ServerBean server, InputStream is, String name) throws IOException {
        SSHClient sshClient = sSHToolService.getSshClient(server);
        sSHToolService.upload(sshClient, "/root/", is, name);
    }

    private String createCmd(AppBean appBean) {
        StringBuilder sb = new StringBuilder();
        sb.append("#/bin/sh").append("\n");
        sb.append("cd ").append(appBean.getLaucnDir()).append("\n");
        sb.append(appBean.getCmd().replaceAll("[&]\\s", "\n")).append("\n");
        return sb.toString();
    }
}

package org.alvin.donglan.ops.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.alvin.donglan.ops.bean.ServerBean;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import net.schmizz.sshj.DefaultConfig;

public class SSHToolService {

    private Logger logger = Logger.getLogger(SSHToolService.class);
    private Cache<String, SSHClient> cache = CacheBuilder.newBuilder().build();

    public String exec(Session session, String cmd) throws IOException {
        logger.info(cmd);
        Session.Command command = session.exec(cmd);
        return IOUtils.readFully(command.getInputStream()).toString();
    }

    private void upload(SSHClient client, String filePath, String target) throws IOException {
        client.useCompression();
        filePath = Paths.get(filePath).toFile().getAbsolutePath();
        client.newSCPFileTransfer().upload(new FileSystemFile(filePath), target);
    }

    public void upload(SSHClient sshClient, String targetPath, InputStream inputStream, String name) throws IOException {
        sshClient.useCompression();
        byte[] buff = ByteStreams.toByteArray(inputStream);
        File tempFile = File.createTempFile("upload", ".temp");
        Files.write(buff, tempFile);
        upload(sshClient, tempFile.getAbsolutePath(), targetPath + name);
    }

    public Session getSession(ServerBean server) throws IOException {
        return getSshClient(server).startSession();
    }

    public SSHClient getSshClient(ServerBean server) throws IOException {
        SSHClient ssh = cache.getIfPresent(server.getIp());
        if (ssh != null && ssh.isConnected()) {
            return ssh;
        }
        cache.invalidate(server.getIp());
        ssh = new SSHClient();
        ssh.addHostKeyVerifier((hostname, port, key) -> true);
        ssh.connect(server.getIp(), server.getPort());
        //开发机器 password
        ssh.authPassword(server.getUser(), server.getPassword());
        cache.put(server.getIp(), ssh);
        return ssh;
    }


    public void destory() {
        this.cache.asMap().forEach((k, v) -> {
            try {
                v.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


}

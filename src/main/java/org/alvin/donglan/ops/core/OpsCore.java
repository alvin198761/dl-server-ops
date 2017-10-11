package org.alvin.donglan.ops.core;

import com.google.gson.Gson;
import org.alvin.donglan.ops.bean.DataConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.swing.UIManager;
import org.alvin.donglan.ops.ui.MainFrame;

public class OpsCore {

    private static Properties properties = new Properties();
    private static DataConfig dataConfig;
    private static MainFrame mainFrame;

    public static void init() throws IOException {
        properties.load(Files.newInputStream(Paths.get(System.getProperty("user.dir"), "config", "application.properties")));
        properties.list(System.out);

        String content = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir"), "config", "data.json")));
        dataConfig = new Gson().fromJson(content, DataConfig.class);

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame = new MainFrame(dataConfig);
                mainFrame.setVisible(true);
            }
        });
    }
}

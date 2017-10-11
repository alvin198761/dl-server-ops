/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alvin.donglan.ops.ui.model;

import com.google.common.collect.Lists;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.alvin.donglan.ops.bean.AppBean;

/**
 *
 * @author Administrator
 */
public class AppBeanTableModel extends AbstractTableModel {

    private List<AppBean> content = Lists.newArrayList();
    private String[] columns = {"名称", "启动命令","位置"};

    @Override
    public int getRowCount() {
        return content.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AppBean app = this.content.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return app.getName();
            case 1:
                return app.getCmd();
            case 2:
                return app.getLaucnDir();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }

    public void setContent(List<AppBean> apps) {
        this.content.clear();
        this.content.addAll(apps);
        fireTableDataChanged();
    }

    public AppBean getRow(int row) {
        return this.content.get(row);
    }

}

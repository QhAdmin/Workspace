/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khannedy.simplepos.view.render;

import com.github.khannedy.simplepos.entity.master.DetailBarang;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author echo
 */
public class DetailBarangTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof DetailBarang) {
            DetailBarang detailBarang = (DetailBarang) value;
            label.setText(detailBarang.getId() + " - " + detailBarang.getBarang().getNama());
        }
        return label;
    }
}

package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BasePanel extends JPanel {
    protected JTable dataTable;
    protected DefaultTableModel tableModel;
    protected JTextField searchField;
    protected JButton addButton, editButton, deleteButton, refreshButton, 
                      detailButton, importButton, exportButton, searchButton;

    public BasePanel() {
        setLayout(new BorderLayout());

        // Thanh công cụ chứa các nút
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Thêm");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        refreshButton = new JButton("Làm mới");
        detailButton = new JButton("Xem chi tiết");
        importButton = new JButton("Nhập Excel");
        exportButton = new JButton("Xuất Excel");
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");

        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.add(refreshButton);
        toolbar.add(detailButton);
        toolbar.add(importButton);
        toolbar.add(exportButton);
        toolbar.add(new JLabel("Tìm kiếm:"));
        toolbar.add(searchField);
        toolbar.add(searchButton);

        add(toolbar, BorderLayout.NORTH);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        // Xử lý tìm kiếm
        searchButton.addActionListener(e -> search(searchField.getText()));

        // Xử lý các nút CRUD (Các class con phải triển khai cụ thể)
        //e-> phương thức là cách viết để thay thế cho cách truyền thống.
//        addButton.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e){
//                addAction();
//            }
//                   
//        });
        
        addButton.addActionListener(e -> addAction());
        editButton.addActionListener(e -> editAction());
        deleteButton.addActionListener(e -> deleteAction());
        refreshButton.addActionListener(e -> refreshAction());
        detailButton.addActionListener(e -> detailAction());
        importButton.addActionListener(e -> importExcel());
        exportButton.addActionListener(e -> exportExcel());
    }

    // Các class con phải triển khai cụ thể
    protected abstract void addAction();
    protected abstract void editAction();
    protected abstract void deleteAction();
    protected abstract void refreshAction();
    protected abstract void detailAction();
    protected abstract void importExcel();
    protected abstract void exportExcel();

    protected void search(String keyword) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        dataTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
    }
}

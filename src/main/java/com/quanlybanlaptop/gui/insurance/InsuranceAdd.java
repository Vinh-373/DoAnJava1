package com.quanlybanlaptop.gui.insurance;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.quanlybanlaptop.bus.InsuranceBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.InsuranceDTO;

public class InsuranceAdd extends JDialog {
    private JTable soldProductTable;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea descriptionArea;
    private JButton btnSave;
    private JButton btnCancel;
    private InsuranceBUS insuranceBUS;
    private SeriProductBUS seriProductBUS;

    public InsuranceAdd(Frame parent, InsuranceBUS insuranceBUS, SeriProductBUS seriProductBUS) {
        super(parent, "Thêm bảo hành mới", true);
        this.insuranceBUS = insuranceBUS;
        this.seriProductBUS = seriProductBUS;
        setSize(750, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Bảng bên trái: Danh sách sản phẩm đã bán, chưa bảo hành (status = 2)
        String[] columnNames = {"Khách hàng","Ngày bán" ,"Tên sản phẩm" ,"Số Seri" };
        Object[][] data = loadSoldProductData();
        soldProductTable = new JTable(data, columnNames);
        soldProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(soldProductTable);
        tableScroll.setPreferredSize(new Dimension(350, 320));

        // Panel nhập thông tin bảo hành bên phải
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Ngày bắt đầu bảo hành
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        startDateField = new JTextField(12);
        rightPanel.add(startDateField, gbc);

        // Ngày kết thúc bảo hành
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Ngày kết thúc (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        endDateField = new JTextField(12);
        rightPanel.add(endDateField, gbc);

        // Mô tả (nếu cần)
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        rightPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(3, 12);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        rightPanel.add(descScroll, gbc);

        // Nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Layout chia đôi
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.add(tableScroll);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            int selectedRow = soldProductTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Lấy số seri và thông tin từ bảng
            String numSeri = soldProductTable.getValueAt(selectedRow, 3).toString().trim();
            System.out.println("numSeri lấy từ bảng: '" + numSeri + "'");
            if (seriProductBUS.getByNumSeri2(numSeri) == null) {
                JOptionPane.showMessageDialog(this, "Số seri này không tồn tại trong hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String productName = soldProductTable.getValueAt(selectedRow, 2).toString();
            String customerName = soldProductTable.getValueAt(selectedRow, 0).toString();
            // Giả sử bạn có thể lấy idProduct, idCustomer từ bảng hoặc ánh xạ từ tên
            int idProduct = getIdProductByName(productName);
            int idCustomer = getIdCustomerByName(customerName);
            int idAdmin = getCurrentAdminId(); // Lấy từ context đăng nhập

            // Lấy ngày bắt đầu và kết thúc bảo hành
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate, endDate;
            try {
                startDate = sdf.parse(startDateStr);
                endDate = sdf.parse(endDateStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Định dạng: yyyy-MM-dd", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String description = descriptionArea.getText();

            // 1. Cập nhật status = 3 cho seri_product
            boolean updateStatus = seriProductBUS.updateStatusTo3BySeri(numSeri);

            // 2. Thêm bản ghi vào bảng INSURANCE
            InsuranceDTO insurance = new InsuranceDTO();
            insurance.setIdAdmin(idAdmin);
            insurance.setIdCustomer(idCustomer);
            insurance.setIdProduct(idProduct);
            insurance.setNumSeri(numSeri);
            insurance.setStartDate(new Timestamp(startDate.getTime()));
            insurance.setEndDate(new Timestamp(endDate.getTime()));
            insurance.setDescription(description);

            boolean insertInsurance = insuranceBUS.addInsurance(insurance);

            if (updateStatus && insertInsurance) {
                JOptionPane.showMessageDialog(this, "Thêm bảo hành thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm bảo hành!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Lấy dữ liệu sản phẩm đã bán, chưa bảo hành (status = 2)
    private Object[][] loadSoldProductData() {
        java.util.List<Object[]> list = insuranceBUS.getSoldSeriInfo();
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }
        return data;
    }

    // TODO: Thay thế các hàm này bằng truy vấn thực tế hoặc truyền vào từ ngoài
    private int getIdProductByName(String productName) {
        // TODO: Lấy id_product từ tên sản phẩm
        return 1;
    }
    private int getIdCustomerByName(String customerName) {
        // TODO: Lấy id_customer từ tên khách hàng
        return 1;
    }
    private int getCurrentAdminId() {
        // TODO: Lấy id_admin từ context đăng nhập
        return 1;
    }
}
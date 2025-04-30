use QLLT;
-- Thêm dữ liệu vào bảng CATEGORY
INSERT INTO CATEGORY (name_category, status) VALUES
('Laptop Gaming', 1),        -- id = 1
('Laptop Văn Phòng', 1),     -- id = 2
('Laptop Siêu Mỏng', 1);     -- id = 3


-- Thêm dữ liệu vào bảng COMPANY
INSERT INTO COMPANY (name_company, address, contact, status) VALUES
('Dell', 'USA', '1900-123-456', 1),
('HP', 'USA', '1900-654-321', 1),
('Lenovo', 'China', '1800-987-654', 1),
('Apple', 'USA', '1800-111-222', 1),
('ASUS', 'Taiwan', '1800-333-444', 1),
('Acer', 'Taiwan', '1800-555-666', 1),
('Microsoft', 'USA', '1800-777-888', 1),
('Razer', 'Singapore', '1800-999-000', 1),
('Framework', 'USA', '1800-246-810', 1);


-- Thêm dữ liệu vào bảng PRODUCT
INSERT INTO PRODUCT (name, cpu, ram, rom, graphics_card, battery, weight, price, quantity, quantity_stock, id_category, id_company, img, size_screen, operating_system, status) VALUES
('Dell XPS 13', 'Intel i7', '16GB', '512GB', 'NVIDIA GTX 1650', '6h', '1.2kg', 25000000, 0, 0 1, 1, 'xps13.jpg', '13.3"', 'Windows 10', 1),
('HP Pavilion', 'Intel i5', '8GB', '256GB', 'Intel UHD', '5h', '1.5kg', 15000000, 0, 0, 2, 2, 'pavilion.jpg', '15.6"', 'Windows 10', 1),
('Lenovo ThinkPad X1', 'Intel i7', '16GB', '512GB', 'Intel Iris Plus', '8h', '1.3kg', 30000000, 0, 0, 3, 3, 'thinkpad.jpg', '14"', 'Windows 10', 1);

-- Thêm dữ liệu vào bảng ADMIN
INSERT INTO ADMIN (name, gender, email, contact, password, status) VALUES
('Nguyen Thi Mai', 'Female', 'nguyenmai@gmail.com', '0901234567', 'password123', 1),
('Tran Minh Tu', 'Male', 'tranminhtu@gmail.com', '0907654321', 'password456', 1);
GO

-- Thêm dữ liệu vào bảng CUSTOMER
-- Thêm dữ liệu vào bảng CUSTOMER
INSERT INTO CUSTOMER (name, gender, birthdate, citizen_id, contact, status) VALUES
('Hoang Minh Tu', 'Male', '1995-05-10', '012345678901', '0912345678', 1),
('Nguyen Thi Lan', 'Female', '1998-08-20', '098765432109', '0908765432', 1);
GO

-- Thêm dữ liệu vào bảng BILL_EXPORT
INSERT INTO BILL_EXPORT (id_admin, id_customer, total_product, total_price, status, date_ex) VALUES
(1, 1, 2, 50000000, 1, '2024-04-10'),
(2, 2, 1, 25000000, 1, '2024-04-15');
GO

-- Thêm dữ liệu vào bảng SERI_PRODUCT


-- Thêm dữ liệu vào bảng BILL_EXPORT_DETAIL
INSERT INTO BILL_EXPORT_DETAIL (id_bill_ex, id_product, num_seri, unit_price) VALUES
(1, 1, 'SP001', 25000000),
(1, 2, 'SP002', 25000000),
(2, 3, 'SP003', 25000000);
GO

-- Thêm dữ liệu vào bảng INSURANCE
INSERT INTO INSURANCE (id_admin, id_customer, id_product, num_seri, start_date, end_date, description) VALUES
(1, 1, 1, 'SP001', '2024-04-10', '2025-04-10', 'Bảo hành 1 năm'),
(2, 2, 3, 'SP003', '2024-04-15', '2025-04-15', 'Bảo hành 1 năm');
GO

-- Thêm dữ liệu vào bảng INSURANCE_CLAIM
INSERT INTO INSURANCE_CLAIM (id_insurance, id_admin, id_product, num_seri, description, date_processed, status) VALUES
(1, 1, 1, 'SP001', 'Màn hình bị vỡ', '2024-04-20', 'Đã xử lý'),
(2, 2, 3, 'SP003', 'Sản phẩm bị lỗi', '2024-04-17', 'Đang xử lý');
GO
-- Thêm dữ liệu vào bảng BILL_IMPORT

-- Thêm dữ liệu vào bảng SERI_PRODUCT

-- Thêm dữ liệu vào bảng BILL_EXPORT_DETAIL
INSERT INTO BILL_EXPORT_DETAIL (id_bill_ex, id_product, num_seri, unit_price) VALUES
(1, 1, 'SR001', 25000000),   -- Phiếu xuất số 1, sản phẩm ASUS TUF Gaming F15, seri SP001
(1, 2, 'SR003', 18000000),   -- Phiếu xuất số 1, sản phẩm DELL Inspiron 15, seri SP003
(2, 2, 'SR003', 18000000);   -- Phiếu xuất số 2, sản phẩm DELL Inspiron 15, seri SP003
GO




-------------------------------------------------------------------
SELECT * FROM [COMPANY];
SELECT * FROM [CATEGORY];
SELECT * FROM [ADMIN];
SELECT * FROM [CUSTOMER];
SELECT * FROM [PRODUCT];
SELECT * FROM [SERI_PRODUCT];
SELECT * FROM [BILL_IMPORT];
SELECT * FROM [BILL_EXPORT];
SELECT * FROM [BILL_EXPORT_DETAIL];
SELECT * FROM [INSURANCE];
SELECT * FROM [INSURANCE_CLAIM];

UPDATE PRODUCT SET img = '/img/lenovo_x1.jpg' WHERE id_product = 3
SELECT b.id_bill_im, b.id_admin, b.id_product, b.unit_price, b.total_price, b.quantity, b.date_import, a.name
FROM BILL_IMPORT b 
JOIN ADMIN a ON b.id_admin = a.id_admin
ORDER BY b.date_import DESC;

SELECT id_bill_ex, id_product, MAX(unit_price) AS unit_price, SUM(unit_price) AS total_price FROM BILL_EXPORT_DETAIL WHERE id_bill_ex = 1  GROUP BY id_bill_ex, id_product

SELECT 
    id_bill_ex, 
    id_product,
    COUNT(*) AS quantity,
    MAX(unit_price) AS unit_price,
    COUNT(*) * MAX(unit_price) AS total_price
FROM BILL_EXPORT_DETAIL
WHERE id_bill_ex = 1
GROUP BY id_bill_ex, id_product
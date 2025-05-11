USE master;
ALTER DATABASE QLLT SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
DROP DATABASE QLLT; -- Thêm DROP để xóa DB cũ nếu cần
CREATE DATABASE QLLT;
USE QLLT;

--------------------------- TẠO BẢNG ------------------------------
CREATE TABLE [PRODUCT] (
    [id_product] INTEGER NOT NULL IDENTITY UNIQUE,
    [name] NVARCHAR(100) NOT NULL,
    [cpu] VARCHAR(50) NOT NULL,
    [ram] VARCHAR(50) NOT NULL,
    [rom] VARCHAR(50) NOT NULL,
    [graphics_card] VARCHAR(150) NOT NULL,
    [battery] VARCHAR(120) NOT NULL,
    [weight] VARCHAR(10) NOT NULL,
    [price] DECIMAL NOT NULL,
    [quantity] INTEGER NOT NULL DEFAULT 0,
    [quantity_stock] INTEGER NOT NULL DEFAULT 0,
    [id_category] INTEGER NOT NULL,
    [id_company] INTEGER NOT NULL,
    [img] VARCHAR(255) NOT NULL,
    [size_screen] VARCHAR(120) NOT NULL,
    [operating_system] VARCHAR(150) NOT NULL,
    [status] INTEGER NOT NULL,
    PRIMARY KEY([id_product])
);
GO

-- Tạo bảng CATEGORY
CREATE TABLE [CATEGORY] (
    [id_category] INTEGER NOT NULL IDENTITY UNIQUE,
    [name_category] NVARCHAR(100) NOT NULL,
    [status] INTEGER NOT NULL,
    PRIMARY KEY([id_category])
);
GO

-- Tạo bảng COMPANY
CREATE TABLE [COMPANY] (
    [id_company] INTEGER NOT NULL IDENTITY UNIQUE,
    [name_company] NVARCHAR(100) NOT NULL,
    [address] NVARCHAR(255) NOT NULL,
    [contact] VARCHAR(20) NOT NULL,
    [status] INTEGER NOT NULL,
    PRIMARY KEY([id_company])
);
GO

-- Tạo bảng CUSTOMER
CREATE TABLE [CUSTOMER] (
    [id_customer] INTEGER NOT NULL IDENTITY UNIQUE,
    [name] NVARCHAR(50) NOT NULL,
    [gender] NVARCHAR(10) NOT NULL,
    [birthdate] DATE NOT NULL,         -- Ngày sinh
    [citizen_id] VARCHAR(20) NOT NULL, -- Số CCCD
    [contact] VARCHAR(20) NOT NULL,
    [status] INTEGER NOT NULL,
    PRIMARY KEY([id_customer])
);
GO

-- Tạo bảng BILL_EXPORT
CREATE TABLE [BILL_EXPORT] (
    [id_bill_ex] INTEGER NOT NULL IDENTITY UNIQUE,
    [id_admin] INTEGER NOT NULL,
    [id_customer] INTEGER NULL,
    [total_product] INTEGER NOT NULL,
    [total_price] DECIMAL NOT NULL,
    [status] INTEGER NOT NULL,
    [date_ex] DATETIME NOT NULL,  -- Thêm cột ngày xuất
    PRIMARY KEY([id_bill_ex])
);
GO

-- Tạo bảng BILL_EXPORT_DETAIL
CREATE TABLE [BILL_EXPORT_DETAIL] (
    [id_bill_ex] INTEGER NOT NULL,
    [id_product] INTEGER NOT NULL,
    [num_seri] VARCHAR(50) NOT NULL,
    [unit_price] DECIMAL NOT NULL,
    PRIMARY KEY([id_bill_ex], [id_product], [num_seri])
);
GO

-- Tạo bảng SERI_PRODUCT
CREATE TABLE [SERI_PRODUCT] (
    [num_seri] VARCHAR(50) NOT NULL UNIQUE,
    [id_product] INTEGER NOT NULL,
    [status] INTEGER NOT NULL,
    PRIMARY KEY([num_seri])
);
GO

-- Tạo bảng BILL_IMPORT
CREATE TABLE [BILL_IMPORT] (
    [id_bill_im] INTEGER NOT NULL IDENTITY UNIQUE,
    [id_admin] INTEGER NOT NULL,
    [id_product] INTEGER NOT NULL,
    [unit_price] DECIMAL NOT NULL,
    [total_price] DECIMAL NOT NULL,
    [quantity] INTEGER NOT NULL,
    [date_import] DATETIME NOT NULL,
    PRIMARY KEY([id_bill_im])
);
GO

-- Tạo bảng ADMIN
CREATE TABLE [ADMIN] (
    [id_admin] INTEGER NOT NULL IDENTITY UNIQUE,
	[id_role] INTEGER NOT NULL,
    [name] NVARCHAR(50) NOT NULL,
    [gender] NVARCHAR(10) NOT NULL,
    [email] VARCHAR(100) NOT NULL,
    [contact] VARCHAR(20) NOT NULL,
    [password] VARCHAR(50) NOT NULL,
    [status] INTEGER NOT NULL,
    PRIMARY KEY([id_admin])
);
GO

-- Tạo bảng INSURANCE
CREATE TABLE [INSURANCE] (
    [id_insurance] INTEGER NOT NULL IDENTITY UNIQUE,
    [id_admin] INTEGER NOT NULL,
    [id_customer] INTEGER NOT NULL,
    [id_product] INTEGER NOT NULL,
    [num_seri] VARCHAR(50) NOT NULL,
    [start_date] DATETIME NOT NULL,
    [end_date] DATETIME NOT NULL,
    [description] NVARCHAR(255) NULL,
    PRIMARY KEY ([id_insurance])
);
GO

-- Tạo bảng INSURANCE_CLAIM
CREATE TABLE [INSURANCE_CLAIM] (
    [id_claim] INTEGER NOT NULL IDENTITY UNIQUE,
    [id_insurance] INTEGER NOT NULL,
    [id_admin] INTEGER NOT NULL,
    [id_product] INTEGER NOT NULL,
    [num_seri] VARCHAR(50) NOT NULL,
    [description] NVARCHAR(255) NOT NULL,
    [date_processed] DATETIME NOT NULL DEFAULT GETDATE(),
    [status] NVARCHAR(50) NOT NULL,
    PRIMARY KEY ([id_claim])
);
GO


-- Tạo bảng ROLE
CREATE TABLE [ROLE] (
	[id_role] INTEGER NOT NULL IDENTITY UNIQUE,
	[name_role] NVARCHAR(50) NOT NULL,
	[status] INTEGER NOT NULL,
	 PRIMARY KEY([id_role])
);
GO
-- Tạo bảng AUTHORITIES
CREATE TABLE [AUTHORITIES] (
	[id_role] INTEGER NOT NULL,
	[id_authorities] INTEGER NOT NULL ,
	[name_authorities] NVARCHAR(150) NOT NULL,
	[status] INTEGER NOT NULL,
	 PRIMARY KEY([id_role],[id_authorities])
);
GO

CREATE TABLE [AUTHORITIES_DETAIL] (
	[id_role] INTEGER NOT NULL,
	[id_authorities] INTEGER NOT NULL ,
	[id_detail] INTEGER NOT NULL ,
	[name] NVARCHAR(150) NOT NULL,
	[status] INTEGER NOT NULL,
	 PRIMARY KEY([id_role],[id_authorities],[id_detail])
);
GO
-- Ràng buộc khóa ngoại
ALTER TABLE [PRODUCT]
ADD CONSTRAINT FK_PRODUCT_CATEGORY
    FOREIGN KEY ([id_category]) REFERENCES [CATEGORY]([id_category])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO
ALTER TABLE [ADMIN]
ADD CONSTRAINT FK_ADMIN_ROLE
    FOREIGN KEY ([id_role]) REFERENCES [ROLE]([id_role])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO
ALTER TABLE [AUTHORITIES]
ADD CONSTRAINT FK_AUTHORITIES_ROLE
    FOREIGN KEY ([id_role]) REFERENCES [ROLE]([id_role])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO
ALTER TABLE AUTHORITIES_DETAIL
ADD CONSTRAINT FK_AUTHORITIES_DETAIL_AUTHORITIES
    FOREIGN KEY (id_role, id_authorities)
    REFERENCES AUTHORITIES(id_role, id_authorities);
GO
ALTER TABLE [PRODUCT]
ADD CONSTRAINT FK_PRODUCT_COMPANY
    FOREIGN KEY ([id_company]) REFERENCES [COMPANY]([id_company])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [SERI_PRODUCT]
ADD CONSTRAINT FK_SERI_PRODUCT_PRODUCT
    FOREIGN KEY ([id_product]) REFERENCES [PRODUCT]([id_product])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_IMPORT]
ADD CONSTRAINT FK_BILL_IMPORT_ADMIN
    FOREIGN KEY ([id_admin]) REFERENCES [ADMIN]([id_admin])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_IMPORT]
ADD CONSTRAINT FK_BILL_IMPORT_PRODUCT
    FOREIGN KEY ([id_product]) REFERENCES [PRODUCT]([id_product])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_EXPORT]
ADD CONSTRAINT FK_BILL_EXPORT_ADMIN
    FOREIGN KEY ([id_admin]) REFERENCES [ADMIN]([id_admin])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_EXPORT]
ADD CONSTRAINT FK_BILL_EXPORT_CUSTOMER
    FOREIGN KEY ([id_customer]) REFERENCES [CUSTOMER]([id_customer])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_EXPORT_DETAIL]
ADD CONSTRAINT FK_BILL_EXPORT_DETAIL_BILL_EXPORT
    FOREIGN KEY ([id_bill_ex]) REFERENCES [BILL_EXPORT]([id_bill_ex])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_EXPORT_DETAIL]
ADD CONSTRAINT FK_BILL_EXPORT_DETAIL_PRODUCT
    FOREIGN KEY ([id_product]) REFERENCES [PRODUCT]([id_product])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [BILL_EXPORT_DETAIL]
ADD CONSTRAINT FK_BILL_EXPORT_DETAIL_SERI
    FOREIGN KEY ([num_seri]) REFERENCES [SERI_PRODUCT]([num_seri])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE]
ADD CONSTRAINT FK_INSURANCE_ADMIN
    FOREIGN KEY ([id_admin]) REFERENCES [ADMIN]([id_admin])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE]
ADD CONSTRAINT FK_INSURANCE_CUSTOMER
    FOREIGN KEY ([id_customer]) REFERENCES [CUSTOMER]([id_customer])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE]
ADD CONSTRAINT FK_INSURANCE_PRODUCT
    FOREIGN KEY ([id_product]) REFERENCES [PRODUCT]([id_product])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE]
ADD CONSTRAINT FK_INSURANCE_SERI
    FOREIGN KEY ([num_seri]) REFERENCES [SERI_PRODUCT]([num_seri])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE_CLAIM]
ADD CONSTRAINT FK_INSURANCE_CLAIM_INSURANCE
    FOREIGN KEY ([id_insurance]) REFERENCES [INSURANCE]([id_insurance])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE_CLAIM]
ADD CONSTRAINT FK_INSURANCE_CLAIM_ADMIN
    FOREIGN KEY ([id_admin]) REFERENCES [ADMIN]([id_admin])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE_CLAIM]
ADD CONSTRAINT FK_INSURANCE_CLAIM_PRODUCT
    FOREIGN KEY ([id_product]) REFERENCES [PRODUCT]([id_product])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO

ALTER TABLE [INSURANCE_CLAIM]
ADD CONSTRAINT FK_INSURANCE_CLAIM_SERI
    FOREIGN KEY ([num_seri]) REFERENCES [SERI_PRODUCT]([num_seri])
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
GO
-- Thêm dữ liệu vào bảng CATEGORYINSERT 
INSERT INTO CATEGORY (name_category, status) VALUES
(N'Laptop Gaming', 1),
(N'Laptop Văn Phòng', 1),
(N'Laptop Siêu Mỏng', 1);

-- Thêm dữ liệu vào bảng COMPANY
INSERT INTO COMPANY (name_company, address, contact, status) VALUES
(N'Dell', N'USA', N'1900-123-456', 1),
(N'HP', N'USA', N'1900-654-321', 1),
(N'Lenovo', N'China', N'1800-987-654', 1),
(N'Apple', N'USA', N'1800-111-222', 1),
(N'ASUS', N'Taiwan', N'1800-333-444', 1),
(N'Acer', N'Taiwan', N'1800-555-666', 1),
(N'Microsoft', N'USA', N'1800-777-888', 1),
(N'Razer', N'Singapore', N'1800-999-000', 1),
(N'Framework', N'USA', N'1800-246-810', 1);

INSERT INTO ROLE (name_role, status) VALUES
(N'Chủ', 1),
(N'Quản Lý', 1),
(N'Nhân Viên', 1);
GO

INSERT INTO AUTHORITIES (id_role, id_authorities, name_authorities, status) VALUES
(1, 1, N'Sản phẩm', 1),
(1, 2, N'Loại hãng', 1),
(1, 3, N'Khách hàng', 1),
(1, 4, N'Tài khoản', 1),
(1, 5, N'Hóa đơn', 1),
(1, 6, N'Kho hàng', 1),
(1, 7, N'Bảo hành', 1),
(1, 8, N'Thống kê', 1),
(1, 9, N'Phân quyền', 1),
(2, 1, N'Sản phẩm', 1),
(2, 2, N'Loại hãng', 1),
(2, 3, N'Khách hàng', 1),
(2, 4, N'Tài khoản', 1),
(2, 5, N'Hóa đơn', 1),
(2, 6, N'Kho hàng', 1),
(2, 7, N'Bảo hành', 1),
(2, 8, N'Thống kê', 1),
(2, 9, N'Phân quyền', 0),
(3, 1, N'Sản phẩm', 1),
(3, 2, N'Loại hãng', 1),
(3, 3, N'Khách hàng', 1),
(3, 4, N'Tài khoản', 0),
(3, 5, N'Hóa đơn', 1),
(3, 6, N'Kho hàng', 1),
(3, 7, N'Bảo hành', 1),
(3, 8, N'Thống kê', 0),
(3, 9, N'Phân quyền', 0);
GO

INSERT INTO AUTHORITIES_DETAIL (id_role, id_authorities, id_detail, name, status) VALUES
-- Chủ (role 1)
(1,1,1,N'Thêm',1),
(1,1,2,N'Sửa',1),
(1,1,3,N'Xóa',1),
(1,1,4,N'Chi tiết',1),
(1,1,5,N'DS Seri',1),
(1,1,6,N'Nhập Ex',1),
(1,1,7,N'Xuất Ex',1),
(1,2,1,N'Thêm',1),
(1,2,2,N'Sửa',1),
(1,2,3,N'Xóa',1),
(1,3,1,N'Thêm',1),
(1,3,2,N'Sửa',1),
(1,3,3,N'Xóa',1),
(1,3,4,N'Xuất Ex',1),
(1,4,1,N'Thêm',1),
(1,4,2,N'Sửa',1),
(1,4,3,N'Xóa',1),
(1,5,1,N'Thêm',1),
(1,5,2,N'Xóa',1),
(1,5,3,N'Xuất PDF',1),
(1,6,1,N'Nhập',1),
(1,6,2,N'Xuất',1),
(1,7,1,N'Thêm',1),
(1,7,2,N'Xóa',1),
(1,7,3,N'ThêmYC',1),
(1,7,4,N'Xuất Ex',1),
(1,8,1,N'TQuan',1),
(1,8,2,N'KHàng',1),
(1,8,3,N'Tồn',1),
(1,8,4,N'DThu',1),
(1,8,5,N'SPham',1),

-- Quản lý (role 2)
(2,1,1,N'Thêm',1),
(2,1,2,N'Sửa',1),
(2,1,3,N'Xóa',1),
(2,1,4,N'Chi tiết',1),
(2,1,5,N'DS Seri',1),
(2,1,6,N'Nhập Ex',1),
(2,1,7,N'Xuất Ex',1),
(2,2,1,N'Thêm',1),
(2,2,2,N'Sửa',1),
(2,2,3,N'Xóa',1),
(2,3,1,N'Thêm',1),
(2,3,2,N'Sửa',1),
(2,3,3,N'Xóa',1),
(2,3,4,N'Xuất Ex',1),
(2,4,1,N'Thêm',1),
(2,4,2,N'Sửa',1),
(2,4,3,N'Xóa',1),
(2,5,1,N'Thêm',1),
(2,5,2,N'Xóa',1),
(2,5,3,N'Xuất PDF',1),
(2,6,1,N'Nhập',1),
(2,6,2,N'Xuất',1),
(2,7,1,N'Thêm',1),
(2,7,2,N'Xóa',1),
(2,7,3,N'ThêmYC',1),
(2,7,4,N'Xuất Ex',1),
(2,8,1,N'TQuan',1),
(2,8,2,N'KHàng',1),
(2,8,3,N'Tồn',0),
(2,8,4,N'DThu',0),
(2,8,5,N'SPham',1),

-- Nhân viên (role 3)
(3,1,1,N'Thêm',0),
(3,1,2,N'Sửa',0),
(3,1,3,N'Xóa',0),
(3,1,4,N'Chi tiết',1),
(3,1,5,N'DS Seri',1),
(3,1,6,N'Nhập Ex',0),
(3,1,7,N'Xuất Ex',0),
(3,2,1,N'Thêm',0),
(3,2,2,N'Sửa',0),
(3,2,3,N'Xóa',0),
(3,3,1,N'Thêm',0),
(3,3,2,N'Sửa',0),
(3,3,3,N'Xóa',0),
(3,3,4,N'Xuất Ex',0),
(3,4,1,N'Thêm',0),
(3,4,2,N'Sửa',0),
(3,4,3,N'Xóa',0),
(3,5,1,N'Thêm',1),
(3,5,2,N'Xóa',0),
(3,5,3,N'Xuất PDF',1),
(3,6,1,N'Nhập',1),
(3,6,2,N'Xuất',1),
(3,7,1,N'Thêm',1),
(3,7,2,N'Xóa',0),
(3,7,3,N'ThêmYC',1),
(3,7,4,N'Xuất Ex',1),
(3,8,1,N'TQuan',0),
(3,8,2,N'KHàng',0),
(3,8,3,N'Tồn',0),
(3,8,4,N'DThu',0),
(3,8,5,N'SPham',0);
GO

INSERT INTO ADMIN (id_role, name, gender, email, contact, password, status) VALUES
(1, N'Nguyen Thi Mai', N'Female', N'nguyenmai@gmail.com', N'0901234567', N'password123', 1),
(2, N'Tran Minh Tu', N'Male', N'tranminhtu@gmail.com', N'0907654321', N'password456', 1),
(3, N'Nguyễn Văn Hùng', N'Male', N'nguyenvanhung@gmail.com', N'0916826432', N'password789', 1);
GO

INSERT INTO CUSTOMER (name, gender, birthdate, citizen_id, contact, status)
VALUES 
(N'Nguyễn Văn A', N'Nam', '1995-03-15', '012345678901', '0912345678', 1),
(N'Trần Thị B', N'Nữ', '1990-07-22', '012345678902', '0987654321', 1),
(N'Lê Văn C', N'Nam', '1988-12-05', '012345678903', '0901122334', 1);

INSERT INTO PRODUCT (name, cpu, ram, rom, graphics_card, battery, weight, price, quantity, quantity_stock, id_category, id_company, img, size_screen, operating_system, status)
VALUES
(N'Dell Inspiron 15', 'Intel Core i5-1135G7', '8GB', '512GB SSD', 'Intel Iris Xe', '3-cell, 42Wh', '1.75kg', 15990000, 3, 3, 2, 1, 'dell_inspiron_15.jpg', '15.6"', 'Windows 11', 1),
(N'HP Pavilion x360', 'Intel Core i7-1255U', '16GB', '1TB SSD', 'Intel Iris Xe', '3-cell, 43Wh', '1.65kg', 19990000, 1, 2, 2, 2, 'hp_pavilion_x360.jpg', '14"', 'Windows 11', 1),
(N'MacBook Air M2', 'Apple M2', '8GB', '256GB SSD', 'Apple GPU 8-core', 'Li-Po 52.6Wh', '1.24kg', 28990000, 0, 2, 3, 4, 'macbook_air_m2.jpg', '13.6"', 'macOS Ventura', 1);

INSERT INTO SERI_PRODUCT (num_seri,id_product,status)
VALUES
('[01]1',1,2),
('[01]2',1,2),
('[01]3',1,2),
('[01]4',1,1),
('[01]5',1,1),
('[01]6',1,1),
('[01]7',1,0),
('[01]8',1,0),
('[01]9',1,0),
('[02]1',1,2),
('[02]2',1,2),
('[02]3',1,1),
('[02]4',1,0),
('[02]5',1,0),
('[03]1',1,2),
('[03]2',1,0),
('[03]3',1,0);

INSERT INTO BILL_IMPORT (id_admin, id_product, unit_price, total_price, quantity, date_import)
VALUES 
(1, 1, 13000000, 117000000, -9, '2025-03-01T10:00:00'),
(3, 2, 17000000, 85000000, -5, '2025-04-03T11:30:00'),
(2, 3, 25000000, 75000000, -3, '2025-05-05T09:15:00'),
(3, 1, 13000000, 78000000, 6, '2025-03-03T10:00:00'),
(3, 2, 17000000, 51000000, 3, '2025-04-06T11:30:00'),
(2, 3, 25000000, 25000000, 1, '2025-05-08T09:15:00');

INSERT INTO BILL_EXPORT (id_admin, id_customer, total_product, total_price, status, date_ex)
VALUES
(1, 1, 1, 15990000, 1, '2025-03-03T14:00:00'),  
(3, 2, 1, 15990000, 1, '2025-03-04T14:00:00'),  
(3, 3, 1, 15990000, 1, '2025-03-20T14:00:00'),  
(2, 2, 1, 19990000, 1, '2025-04-07T09:45:00'),  
(3, 3, 1, 19990000, 1, '2025-04-08T16:30:00'),
(3, 1, 1, 28990000, 1, '2025-05-09T16:30:00');  

INSERT INTO BILL_EXPORT_DETAIL (id_bill_ex, id_product, num_seri, unit_price)
VALUES 
(1, 1, '[01]1', 15990000),
(2, 1, '[01]2', 15990000),
(3, 1, '[01]3', 15990000),
(4, 2, '[02]1', 19990000),
(5, 2, '[02]2', 19990000),
(6, 3, '[03]1', 28990000);

INSERT INTO INSURANCE (id_admin, id_customer, id_product, num_seri, start_date, end_date, description)
VALUES
(1, 1, 1, '[01]1', '2025-03-03', '2026-03-03', N'Bảo hành toàn máy 12 tháng'),
(2, 2, 1, '[01]2', '2025-03-04', '2026-03-04', N'Bảo hành toàn máy 12 tháng'),
(3, 3, 1, '[01]3', '2025-03-20', '2026-03-20', N'Bảo hành toàn máy 12 tháng'),
(2, 2, 2, '[02]1', '2025-04-07', '2026-04-07', N'Bảo hành toàn máy 12 tháng'),
(2, 3, 2, '[02]1', '2025-04-07', '2026-04-07', N'Bảo hành toàn máy 12 tháng'),
(1, 1, 3, '[03]1', '2025-05-09', '2027-05-09', N'Bảo hành toàn máy 24 tháng');





# LAB 3: Mã Hóa Khóa Công Khai

- **Sinh viên:** Hồ Ngọc Thái Thông - MSSV: 1150080077

---

## 1. Yêu cầu hệ thống
- **Java:** JDK 17 trở lên
- **Build tool:** Apache Maven 3.8+
- **CSDL (Tùy chọn):** Microsoft SQL Server (dùng cho chức năng lưu lịch sử thao tác)

---

## 2. Cài đặt Cơ sở Dữ liệu (Tùy chọn)
Nếu sử dụng chức năng lưu lịch sử, mở **SQL Server Management Studio (SSMS)** và chạy lần lượt 2 file script trong thư mục `03_SQL_SERVER/`:
1. `01_Tao_CSDL.sql`: Tạo database `LAB3_MaHoaKhoaCongKhai` và bảng `History`.
2. `02_Tao_TaiKhoan_LAB.sql`: Tạo tài khoản đăng nhập `lab3_user` (mật khẩu: `LabUser@2026`).

---

## 3. Hướng dẫn chạy chương trình

### Cách 1: Chạy bằng IDE (NetBeans / IntelliJ / Eclipse / VS Code)
1. Mở IDE và chọn **Open Project** trỏ đến thư mục chứa file `pom.xml`.
2. Bấm **Run Project** (hoặc phím `F6`) để khởi động Menu giao diện chính (`frm_Menu`).

### Cách 2: Chạy bằng dòng lệnh (Terminal / Command Prompt)

```bash
# 1. Khởi chạy Menu giao diện chính:
mvn compile exec:java -Dexec.mainClass="week_03.frm_Menu"

# 2. Hoặc chạy chương trình Console RSA (Bài 3.1):
mvn compile exec:java -Dexec.mainClass="week_03.RSADemo"

# 3. Chạy kiểm thử tự động (Unit Tests):
mvn test
```

---


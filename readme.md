# BÁO CÁO VÀ HƯỚNG DẪN CHẠY BÀI THỰC HÀNH LAB 3
## MÃ HÓA KHÓA CÔNG KHAI
**Học phần:** An toàn và Bảo mật Thông tin  
**Họ và tên:** Hồ Ngọc Thái Thông  
**MSSV:** 1150080077  
**Lớp / Nhóm:** Bảo mật Thông tin  

---

## 1. TỔNG QUAN DỰ ÁN

Project Java Maven `LAB3_MaHoaKhoaCongKhai` trong package `week_03` thực hiện đầy đủ các yêu cầu trong tài liệu hướng dẫn thực hành từng bước Lab 3:
1. **Bài 3.1 - RSA (Console):** Lớp `RSACipher` và chương trình console `RSADemo`, hỗ trợ khóa RSA 2048 và 3072 bit, đệm OAEP (SHA-256), kiểm soát giới hạn bản rõ (190 byte / 318 byte).
2. **Bài 3.2 - RSA (Swing):** Giao diện `frm_RSA` tạo cặp khóa, mã hóa/giải mã bản rõ "HELLO RSA", minh họa tính ngẫu nhiên của chuẩn đệm OAEP.
3. **Bài 3.3 - ECC (Swing):** Lớp `ECCCipher` và giao diện `frm_ECC`, mã hóa ECIES trên đường cong elliptic `secp256r1` (P-256) kết hợp ECDH, HKDF-SHA256 và AES-256-GCM. Hỗ trợ Save Keys / Load Keys; kiểm thử cơ chế từ chối khi dùng sai khóa riêng.
4. **Bài 3.4 - Hệ mã lai RSA + AES (Swing):** Lớp `RSA_AES_Cipher` và giao diện `frm_RSA_AES`, mã hóa văn bản dài (> 1.000 ký tự) bằng AES-256-GCM và mã hóa khóa phiên AES bằng RSA-OAEP. Kiểm thử nút "Sửa 1 ký tự bản mã" chứng minh tính toàn vẹn của AES-GCM (phát hiện giả mạo).
5. **Bài 3.5 - Chữ ký số ECDSA (Swing):** Lớp `ECCSignature` và giao diện `frm_ECC_Signature`, ký số và xác minh với thuật toán `SHA256withECDSA` trên đường cong `secp256r1`. Kiểm thử trạng thái `VALID` (xanh) và `INVALID` (đỏ) khi sửa thông điệp hoặc thay đổi khóa.
6. **Tích hợp SQL Server:** Lớp `DbConfig`, `HistoryDao`, giao diện `frm_KetNoiSQL` và `frm_LichSu` ghi nhận lịch sử các thao tác `ENCRYPT`, `DECRYPT`, `SIGN`, `VERIFY` vào bảng `dbo.History`.
7. **Menu chính:** Giao diện điều phối `frm_Menu` làm điểm khởi chạy trung tâm mở tất cả các form chức năng.

---

## 2. CẤU TRÚC THƯ MỤC DỰ ÁN

```
LAB3_1150080077_Ho-Ngoc-Thai-Thong/
├── pom.xml                                     # Cấu hình Maven (Java 17, mssql-jdbc, junit-jupiter)
├── 03_SQL_SERVER/
│   ├── 01_Tao_CSDL.sql                         # Script tạo database LAB3_MaHoaKhoaCongKhai & bảng dbo.History
│   ├── 02_Tao_TaiKhoan_LAB.sql                 # Script tạo SQL Login lab3_user & cấp quyền
│   └── 03_KiemTra_LichSu.sql                   # Script truy vấn và thống kê lịch sử
├── src/
│   ├── main/java/week_03/
│   │   ├── CryptoSupport.java                  # Tiện ích mã hóa (Base64, UTF-8, AES-GCM, HKDF, tệp khóa)
│   │   ├── FileTextIO.java                     # Mở / lưu tệp văn bản .txt (JFileChooser)
│   │   ├── DbConfig.java                       # Quản lý cấu hình & kết nối JDBC SQL Server
│   │   ├── HistoryDao.java                     # Thao tác DAO bảng dbo.History (PreparedStatement)
│   │   ├── RSACipher.java                      # RSA với đệm OAEP (SHA-256)
│   │   ├── RSADemo.java                        # Chương trình console RSA 2048/3072 bit
│   │   ├── ECCCipher.java                      # ECIES (secp256r1 + ECDH + HKDF + AES-GCM)
│   │   ├── RSA_AES_Cipher.java                 # Hệ mã lai RSA + AES-256-GCM
│   │   ├── ECCSignature.java                   # Chữ ký số ECDSA (SHA256withECDSA)
│   │   ├── frm_Menu.java                       # Cửa sổ chính điều phối các form
│   │   ├── frm_RSA.java                        # Giao diện Swing RSA
│   │   ├── frm_ECC.java                        # Giao diện Swing ECC
│   │   ├── frm_RSA_AES.java                    # Giao diện Swing Hệ mã lai RSA + AES
│   │   ├── frm_ECC_Signature.java              # Giao diện Swing Chữ ký số ECDSA
│   │   ├── frm_KetNoiSQL.java                  # Giao diện kết nối Microsoft SQL Server
│   │   └── frm_LichSu.java                     # Giao diện xem lịch sử thao tác từ SQL Server
│   └── test/java/week_03/                      # Bộ Unit Test TDD (25/25 test passed)
│       ├── CryptoSupportTest.java
│       ├── DbConfigHistoryDaoTest.java
│       ├── RSACipherTest.java
│       ├── ECCCipherTest.java
│       ├── RSA_AES_CipherTest.java
│       └── ECCSignatureTest.java
└── readme.md                                   # Tài liệu hướng dẫn và kết quả
```

---

## 3. HƯỚNG DẪN CẤU HÌNH VÀ CHẠY CHƯƠNG TRÌNH

### 3.1. Chuẩn bị Cơ sở Dữ liệu SQL Server
1. Mở **SQL Server Configuration Manager**:
   - Vào `SQL Server Network Configuration` -> `Protocols for SQLEXPRESS` (hoặc `MSSQLSERVER`).
   - Đặt `TCP/IP = Enabled`.
   - Nhấp đúp vào `TCP/IP` -> tab `IP Addresses` -> kéo xuống mục `IPAll`: đặt `TCP Port = 14333`, xóa trống `TCP Dynamic Ports`.
   - Khởi động lại dịch vụ SQL Server (`Restart`).
2. Mở **SQL Server Management Studio (SSMS)**:
   - Đăng nhập bằng `Windows Authentication`.
   - Chuột phải vào tên Server -> `Properties` -> `Security` -> chọn `SQL Server and Windows Authentication mode` (Mixed mode).
   - Mở và chạy lần lượt các script trong thư mục `03_SQL_SERVER/`:
     + `01_Tao_CSDL.sql`: Tạo cơ sở dữ liệu `LAB3_MaHoaKhoaCongKhai` và bảng `dbo.History`.
     + `02_Tao_TaiKhoan_LAB.sql`: Tạo login `lab3_user` (mật khẩu: `LabUser@2026`) và gán quyền `SELECT`, `INSERT`.

### 3.2. Biên dịch và Chạy Kiểm thử Tự động (Maven)
Mở terminal tại thư mục gốc của project:
```bash
# Biên dịch và chạy toàn bộ 25 bài Unit Test
mvn clean test

# Đóng gói thành tệp JAR
mvn clean package
```

### 3.3. Chạy Chương trình

#### Cách 1: Chạy Menu Chính (GUI Launcher)
```bash
mvn exec:java
# Hoặc chạy trực tiếp từ tệp JAR đã build:
java -cp target/classes;"target/dependency/*" week_03.frm_Menu
```

#### Cách 2: Chạy Chương trình Console Bài 3.1 (RSADemo)
```bash
java -cp target/classes week_03.RSADemo
```
- Nhập kích thước khóa: `2048` hoặc `3072`
- Nhập bản rõ: `HELLO RSA`

#### Cách 3: Chạy từng Form riêng lẻ
```bash
# Form RSA Swing
java -cp target/classes week_03.frm_RSA

# Form ECC Swing
java -cp target/classes week_03.frm_ECC

# Form Hệ mã lai RSA + AES
java -cp target/classes week_03.frm_RSA_AES

# Form Chữ ký số ECDSA
java -cp target/classes week_03.frm_ECC_Signature

# Form Kết nối SQL Server
java -cp target/classes week_03.frm_KetNoiSQL

# Form Lịch sử thao tác
java -cp target/classes week_03.frm_LichSu
```

---

## 4. BẢNG TỔNG HỢP KẾT QUẢ THỰC HÀNH

### Bảng 4.1. Kết quả kiểm thử bắt buộc (Theo Bảng 3.1 trong tài liệu)
| Bài | Dữ liệu kiểm thử | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|---|---|---|---|---|
| **RSA (3.1 & 3.2)** | `HELLO RSA` | Giải mã đúng bản rõ (cả 2048 và 3072 bit); 2 lần mã hóa cho bản mã khác nhau (OAEP) | Giải mã khôi phục chính xác 100% `HELLO RSA`; 2 lần mã hóa chuỗi Base64 khác nhau | **ĐẠT (PASS)** |
| **ECC (3.3)** | `HELLO ECC` | Giải mã đúng; đổi khóa (khóa sai) $\rightarrow$ thất bại | Giải mã đúng; dùng khóa riêng khác bị chặn với lỗi `AEADBadTagException: Tag mismatch` | **ĐẠT (PASS)** |
| **RSA-AES (3.4)** | Văn bản dài > 1.000 ký tự | Giải mã đúng toàn văn; sửa bản mã $\rightarrow$ thất bại | Giải mã chính xác 1.300+ ký tự; nút "Sửa 1 ký tự bản mã" khiến giải mã thất bại | **ĐẠT (PASS)** |
| **ECDSA (3.5)** | `THONG DIEP A` | `VALID` khi chưa sửa; `INVALID` sau khi sửa thành `THONG DIEP B` hoặc đổi khóa | Khớp chữ ký: `VALID` (xanh); sửa thông điệp hoặc đổi khóa: `INVALID` (đỏ) | **ĐẠT (PASS)** |
| **SQL Server** | Một lần Encrypt | Ghi 1 bản ghi vào `dbo.History` | Ghi nhận chính xác Algorithm, Operation, InputLength, Success | **ĐẠT (PASS)** |

### Bảng 4.2. Bảng so sánh kết quả RSA 2048 bit và 3072 bit (Theo Bảng 3.7)
| Kích thước khóa | Thời gian tạo khóa (ms) | Độ dài bản mã Base64 | Giới hạn bản rõ tối đa |
|---|---|---|---|
| **2048 bit** | ~487 ms | **344 ký tự** | **190 byte** |
| **3072 bit** | ~741 ms | **512 ký tự** | **318 byte** |

---

## 5. KẾT QUẢ KIỂM THỬ ĐƠN VỊ (UNIT TESTS)

Bộ kiểm thử TDD tự động đã thực thi toàn bộ 25 kịch bản kiểm thử:
- `CryptoSupportTest`: 5/5 passed (Base64, Concat, Slice, AES-256-GCM, HKDF-SHA256, toKeyFile/fromKeyFile).
- `DbConfigHistoryDaoTest`: 4/4 passed (Validation cấu hình, PreparedStatement, xử lý mềm khi mất kết nối).
- `RSACipherTest`: 4/4 passed (RSA 2048, RSA 3072, OAEP randomness, kiểm tra quá giới hạn 190 byte).
- `ECCCipherTest`: 4/4 passed (ECIES secp256r1, nạp/lưu khóa, từ chối khóa sai, chống sửa bản mã).
- `RSA_AES_CipherTest`: 3/3 passed (Văn bản dài > 1.000 ký tự, tamper detection AEADBadTagException, lưu/nạp khóa).
- `ECCSignatureTest`: 5/5 passed (Chữ ký VALID, thông điệp sửa INVALID, khóa khác INVALID, xác minh khóa công khai đơn lẻ).

**Tổng kết:** `Tests run: 25, Failures: 0, Errors: 0, Skipped: 0` $\rightarrow$ **BUILD SUCCESS**.

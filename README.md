<!-- Mục Lục -->
**Mục Lục**
- [Mô Tả](#mô-tả)
- [Tính Năng](#tính-năng)
- [Hướng Dẫn Cài Đặt](#hướng-dẫn-cài-đặt)
- [Hướng Dẫn Sử Dụng](#hướng-dẫn-sử-dụng)
- [Đóng Góp](#đóng-góp)

   
## Ứng Dụng Bán Điện Thoại Android
Trang chủ  
![Ảnh Home](https://media.discordapp.net/attachments/728486003051987035/1266061041637261323/image.png?ex=66a3c6ba&is=66a2753a&hm=42e8b3b30cbd9ad72579dcf5a64e6f09c2af8ce9fb3a2e1cf11e90d19be45dff&=&format=webp&quality=lossless&width=161&height=350)  


Cấu trúc database  
![Cấu trúc Firebase Realtime](https://media.discordapp.net/attachments/728486003051987035/1266066961503355000/image.png?ex=66a3cc3e&is=66a27abe&hm=f36cdf466cddaa85fb490b7c7c4d5ba31b8876b370db1aea76676743c9868ee7&=&format=webp&quality=lossless)  


Cấu trúc hệ thống file dự án  
![Cấu trúc file dự án](https://media.discordapp.net/attachments/728486003051987035/1266067002837962803/image.png?ex=66a3cc47&is=66a27ac7&hm=1366d96397caa1c69cc4b19cc4e7ef2f0e6d5f84072716590334f90c5cd518cc&=&format=webp&quality=lossless)  


## Mô tả

Ứng dụng Bán Điện Thoại Android là một ứng dụng di động phục vụ mua bán điện thoại di động. Ứng dụng này cho phép người dùng xem danh sách các điện thoại di động, xem chi tiết từng sản phẩm, thêm vào giỏ hàng và tiến hành thanh toán. Ngoài ra, ứng dụng cũng cung cấp tính năng đăng nhập và đăng ký người dùng.

![Ảnh Màn Hình Sản Phẩm](https://firebasestorage.googleapis.com/v0/b/shopdt-5c0d7.appspot.com/o/image.README%2Fchitietsanpham.png?alt=media&token=9110493d-732c-43f8-8d63-82c5198734ff&_gl=1*1t6uz0k*_ga*MTY3Mjg3NDM3OC4xNjk3MzEwMTI2*_ga_CW55HF8NVT*MTY5ODgzMTIxNy4zMS4xLjE2OTg4MzUxNDQuNDcuMC4w)


## Công Nghệ Sử Dụng
|Công nghệ                                             |
|------------------------------------------------------|
|Java                                                  |
|FireBase RealtimeDatabase                             |
|FireBase Authentication                               |
|FireBase FireStore                                    |


## Tính Năng

- Hiển thị danh sách điện thoại di động.
- Xem chi tiết sản phẩm với mô tả, giá cả và hình ảnh.
- Thêm sản phẩm vào giỏ hàng.
- Quản lý giỏ hàng và thực hiện thanh toán.
- Đăng nhập và đăng ký tài khoản người dùng.
- Có phân quyền User bằng Admin, có Màn hình riêng cho Admin quản lý CRUD Sản Phẩm, Danh Mục, Xác nhận trạng thái đơn hàng, User,...

## Hướng Dẫn Cài Đặt

1. Clone dự án từ GitHub:

   ```bash
   git clone https://github.com/NhaDuX/MobilePhoneShop_Java_Firebase_Stripe.git
   ```

2. Mở dự án bằng Android Studio.

3. Chạy ứng dụng trên máy ảo hoặc thiết bị Android thật.

4. Mở file Stripe-payment bằng VS Code và chạy lệnh 'npm start' để bật giả lập thanh toán bằng Stripe

## Hướng Dẫn Sử Dụng

1. Mở ứng dụng.
2. Duyệt danh sách sản phẩm bằng cách cuộn xuống.
3. Nhấn vào sản phẩm để xem chi tiết.
4. Trong màn hình chi tiết sản phẩm, bạn có thể thêm sản phẩm vào giỏ hàng.
5. Để xem giỏ hàng, nhấn vào biểu tượng giỏ hàng ở góc trên cùng bên phải.
6. Trong giỏ hàng, bạn có thể xem danh sách sản phẩm và tiến hành thanh toán.

![Ảnh Màn Hình Giỏ Hàng](https://firebasestorage.googleapis.com/v0/b/shopdt-5c0d7.appspot.com/o/image.README%2Fcart.png?alt=media&token=cd12782c-ecd5-4275-827b-6249322988de&_gl=1*t3mhmq*_ga*MTY3Mjg3NDM3OC4xNjk3MzEwMTI2*_ga_CW55HF8NVT*MTY5ODgzMTIxNy4zMS4xLjE2OTg4MzUyNzkuNTcuMC4w)

## Đóng Góp

Nếu bạn muốn đóng góp vào dự án này, bạn có thể tạo pull request hoặc báo cáo lỗi thông qua GitHub Issues.

---

<!-- Mục Lục -->
**Mục Lục**
- [Mô Tả](#mô-tả)
- [Tính Năng](#tính-năng)
- [Hướng Dẫn Cài Đặt](#hướng-dẫn-cài-đặt)
- [Hướng Dẫn Sử Dụng](#hướng-dẫn-sử-dụng)
- [Đóng Góp](#đóng-góp)

   
## Ứng Dụng Bán Điện Thoại Android
**Trang chủ**  
![Ảnh Home](https://firebasestorage.googleapis.com/v0/b/shopdt-5c0d7.appspot.com/o/images_4_git%2Fhome_page.png?alt=media&token=aeb10652-25fc-4994-9b58-1cb380d27838)  
**Cấu trúc database**  
![Cấu trúc Firebase Realtime](https://firebasestorage.googleapis.com/v0/b/shopdt-5c0d7.appspot.com/o/images_4_git%2Ffirebase%20struccture.png?alt=media&token=987eeecc-1f5f-4289-ac86-886e9037e3e6)  


**Cấu trúc hệ thống file dự án**  
![Cấu trúc file dự án](https://firebasestorage.googleapis.com/v0/b/shopdt-5c0d7.appspot.com/o/images_4_git%2Fproject%20folder%20tree.png?alt=media&token=9437c378-fc3e-48da-9468-87620e73ac0b)  

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

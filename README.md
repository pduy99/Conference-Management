# Conference Management 

# Công nghệ sử dụng
  - JavaFX 14
  - Hibernate (JPA Annotations)
  - MySQL

## Cài đặt 
  - Clone Project về máy
  - Mở project bằng IntelliJ (khuyến khích)
  - **Thêm vào VM options các dòng sau:**
    
    `--module-path
"C:\Program Files\Java\javafx-sdk-11.0.2\lib"
--add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web
--add-opens javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED
--add-opens javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
--add-opens javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED
--add-opens javafx.base/com.sun.javafx.binding=ALL-UNNAMED
--add-opens javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED
--add-opens javafx.base/com.sun.javafx.event=ALL-UNNAMED`

  *Lưu ý* : module-path là đường dẫn đến thư viện javafx, bạn có thể tải về [tại đây](https://gluonhq.com/products/javafx/)

  - Biên dịch và chạy chương trình
  
## Database
  - Tải file script chứa database có nghĩa [tại đây](https://drive.google.com/file/d/1514KkKKHfk5hEY4ELGGUKXHtgkrvdRm2/view?usp=sharing)
  - Chạy file script trên bằng MySQL Workbench
  - Tìm file **hibernate.cfg.xml** trong source code và sửa đổi các trường sau cho phù hợp:
    ` <property name="connection.url">jdbc:mysql://localhost:3306/ql_hoinghi</property>` : port và tên cơ sở dữ liệu 
    `<property name="connection.username">root</property>` : username truy cập vào cơ sở dữ liệu
    `<property name="connection.password">Snowwhite99#</property>` : Password của username để truy cập vào cơ sở dữ liệu

## Screenshots

  ### 1. Màn hình đăng nhập / đăng kí
  
  Màn hình đăng nhập            |  Màn hình đăng kí    |
:-------------------------:|:-------------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/signin.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/signup.png?raw=true) 

  ### 2. Màn hình chính
  Người dùng có thể đăng nhập dưới dạng khách bằng chức năng **Login as Guest**, có thể xem danh sách hội nghị và thông tin chi tiết của các hội nghị này nhưng không thể đăng kí tham dự
  
  Xem dạng List view           |  Xem dạng Table view   |
:-------------------------:|:-------------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/guest_login.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/tableview.png?raw=true) 

  ### 3. Màn hình chi tiết hội nghị
  
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/detail.png?raw=true)

  ### 4. dialog đăng nhập nhanh
    Chương trình hỗ trợ Login Dialog cho khách đăng nhập nhanh
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/login_dialog.png?raw=true)
  
  ### 5. Đăng kí tham dự hội nghị 
    *Pending: Đang chờ Admin xét duyệt*
    *Approved: Đã được admin xét duyệt*
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/enroll.png?raw=true)

  ### 6. Xem danh sách hội nghị đã đăng kí

![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/my_conferences.png?raw=true)

  ### 7. Chức năng tìm kiếm hội nghị

![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/search.png?raw=true)

  ### 8. Màn hình hồ sơ cá nhân
Màn hình hồ sơ cá nhân            |  Sửa đổi thông tin cá nhân    | Thay đổi mật khẩu |
:-------------------------:|:-------------------------:|:---------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/profile.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/edit_profile.png?raw=true) | ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/change_password.png?raw=true)

  ### 9. Màn hình Dashboard
   *Admin có thể block/unblock user truy cập vào ứng dụng tại màn hình này*
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/Dashboard.png?raw=true)
  
  ### 10. Màn hình quản lý sự kiện
  
 Danh sách các sự kiện            |  Thay đổi thông tin sự kiện    |
:-------------------------:|:-------------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/manage_conference.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/edit_conference.png?raw=true) 
  
  ### 11. Màn hình xét duyệt các yêu cầu tham dự hội nghị
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/management_pendingList.png?raw=true)
  
  
## Video Demo
Xem Video demo [tại đây](https://www.youtube.com/watch?v=HmuM40pd6n8)

## Các thư viện hỗ trợ:

  * [JFoenix](https://github.com/jfoenixadmin/JFoenix): JavaFX Material Design Library
  * [AnimateFX](https://github.com/Typhon0/AnimateFX) : JavaFX animations
  
## Contributing

  Feels free to pull request.

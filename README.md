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

  ### 1. Sign In / Sign Up Screen
  
  Main Screen            |  Select trending period    |
:-------------------------:|:-------------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/signin.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/signup.png?raw=true) 

  ### 2. MainScreen
  You can Login as Guest to see MainScreen but cannot enroll any conferences
  
   Main Screen            |  Select trending period    |
:-------------------------:|:-------------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/guest_login.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/tableview.png?raw=true) 

  ### 3. Conference Detail
  Guest can also view conference's detail
  
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/detail.png?raw=true)

  ### 4. Login dialog
  Guest can login via dialog
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/login_dialog.png?raw=true)
  
  ### 5. Enroll Conference
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/enroll.png?raw=true)

  ### 6. My enrolled conferences

![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/my_conferences.png?raw=true)

  ### 7. Search Conference

![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/search.png?raw=true)

  ### 8. Profile
Profile            |  Edit Profile    | Change Password |
:-------------------------:|:-------------------------:|:---------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/profile.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/edit_profile.png?raw=true) | ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/change_password.png?raw=true)

  ### 9. Dashboard
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/Dashboard.png?raw=true)
  
  ### 10. Admin Manage Conferences
  
 Conference Manage Screen            |  Edit Conference    |
:-------------------------:|:-------------------------:
![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/manage_conference.png?raw=true) |  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/edit_conference.png?raw=true) 
  
  ### 11. Admin Approve/Disapprove Request Enrollment
  ![alt text](https://github.com/pduy99/Conference-Management/blob/master/screenshot/management_pendingList.png?raw=true)
  

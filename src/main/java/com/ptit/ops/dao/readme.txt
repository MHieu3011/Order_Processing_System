tầng truy xuất dữ liệu từ database

mô hình chính:
Client --> Controller --> Service --> Cache --> DAO (nếu cache miss)
                                  --> API bên ngoài (nếu có)
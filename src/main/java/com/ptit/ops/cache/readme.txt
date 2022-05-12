Tầng Cache giúp nâng cao hiệu năng ứng dụng
Trong project này lựa chọn local cache

mô hình chính:
Client --> Controller --> Service --> Cache --> DAO (nếu cache miss)
                                  --> API bên ngoài (nếu có)
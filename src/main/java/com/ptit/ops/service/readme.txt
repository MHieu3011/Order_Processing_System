tầng xử lý nghiệp vụ chính của ứng dụng

mô hình chính:
Client --> Controller --> Service --> Cache --> DAO (nếu cache miss)
                                  --> API bên ngoài (nếu có)
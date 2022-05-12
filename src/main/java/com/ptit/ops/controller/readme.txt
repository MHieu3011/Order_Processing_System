Nhận request api từ client, chuyển yêu cầu tới tầng Service
mô hình chính:

Client --> Controller --> Service --> Cache --> DAO (nếu cache miss)
                                  --> API bên ngoài (nếu có)
<h1 align="center">  This is a Machine Learning / AI Project </h1>

<p align="center"> 
AI Development Documentation for LyfeStox
</p>

<div align="center">
    <!-- Your badges here -->
    <img src="https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54">
    <img src="https://img.shields.io/badge/jupyter-%23FA0F00.svg?style=for-the-badge&logo=jupyter&logoColor=white">
    <img src="https://img.shields.io/badge/flask-%23000.svg?style=for-the-badge&logo=flask&logoColor=white">
    <img src="https://img.shields.io/badge/TensorFlow-%23FF6F00.svg?style=for-the-badge&logo=TensorFlow&logoColor=white">
    <img src="https://img.shields.io/badge/Keras-%23D00000.svg?style=for-the-badge&logo=Keras&logoColor=white">
    <img src="https://img.shields.io/badge/scikit--learn-%23F7931E.svg?style=for-the-badge&logo=scikit-learn&logoColor=white">
    <img src="https://img.shields.io/badge/pandas-%23150458.svg?style=for-the-badge&logo=pandas&logoColor=white">
    <img src="https://img.shields.io/badge/numpy-%23013243.svg?style=for-the-badge&logo=numpy&logoColor=white">
   <img src="https://img.shields.io/badge/pillow-%23000000.svg?style=for-the-badge&logo=pillow&logoColor=white">
    <img src="https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white">
    <img src="https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white">
</div>

## Teams

- Marshall Al Karim (Design Researcher)
- Roland (Data Engineer)
- Jeffry Irwan Gultom (Data Engineer)
- Andi Wahyu (Machine Learning Engineer)
- Salwa Az Zahra P.M (Machine Learning Engineer)
- Roy Robin (Machine Learning Ops)

## Mentor

- Arifian Saputra

## Idea Background

### 1. Theme
Tema : 
Peternakan, Optimalisasi Pengelolaan peternakan, Mendiagnosa kesehatan Hewan           ternak.

### 2. Problem
Masalah : 
- Penyakit yang menyerang ternak ayam sangat berdampak pada produksi telur               dan menimbulkan kerugian besar bagi peternak. 
- Kurangnya pendataan hewan ternak ayam secara terstruktur.
- Diagnosa manual terhadap pertumbuhan dan kesehatan ayam individu.

### 3. Solution
Solusi : 
Aplikasi untuk Optimalisasi proses pengelolaan pada peternakan ayam yang               menyediakan solusi untuk pengelolaan peternakan ayam serta dilengkapi dengan           AI untuk memudahkan mendiagnosa kesehatan ayam. Dengan produk ini dapat                meningkatkan efisiensi dan produktivitas peternakan ayam.

## Dataset and Algorithm

### 1. Dataset
- Data Collection <br />
Kami Menggunakan dataset Kaggle, dataset berupa 8067 foto tinja ayam dengan 4 kelas yaitu "Coccidiosis" ,"Healthy", "New Castle Disease" ,"Salmonella".
- Data Cleaning <br />
Kami menggunakan pandas untuk membersihkan data. 


### 2. Algorithm

- Framework <br />
Kami menggunakan TensorFlow dan Keras.

- Pembangunan Model <br />
Kami menggunakan model MobileNetV2.

- kami melatih model dengan 20 epoch

- Model Evaluation <br />
  ```bash
                       precision    recall   f1-score  support

         Coccidiosis       0.97      0.95      0.96       247
             Healthy       0.87      0.95      0.91       241
  New Castle Disease       0.89      0.59      0.71        56
          Salmonella       0.92      0.93      0.93       263

            accuracy                           0.92       807
           macro avg       0.91      0.85      0.88       807
        weighted avg       0.92      0.92      0.92       807
   ```

  ```bash
  Validation Loss: 0.3209264874458313
  Validation Accuracy: 0.9442379474639893
   ```
- dan kami menyimpan model dalam format .h5

## Prototype
- Backend: Menggunakan Retrofit untuk mengintegrasikan aplikasi mobile dengan API backend yang menangani inferensi model AI.
- Frontend: Dibangun menggunakan Jetpack Compose untuk memberikan UI modern dan responsif.
- Database Lokal: SQLite digunakan untuk menyimpan data lokal seperti riwayat diagnosa, data pengguna, atau informasi ternak.

## Integration
- Beberapa teknologi backend yang kami gunakan Flask, Flask-CORS, TensorFlow, Numpy, Pillow
- Model dan weights dimuat menggunakan TensorFlow
- Framework: Flask + Flask-CORS
- Endpoint: /predict (diagnosa) dan /weather (cuaca)   
      dengan metode POST
  ### Proses Utama
  - Validasi file (format, ukuran, tipe).
  - Penyimpanan file sementara.
  - Preprocessing gambar menggunakan Pillow dan numpy.
  - Prediksi kelas dan confidence score menggunakan model.
  - Prediksi cuaca dengan api dari openweather.

## Deployment
Sebelum deploy kami melakukan testing secara lokal dengan ngrok lalu setelah itu kami 
menjadikan flask menjadi docker image dan push ke dockerhub lalu menerapkan CI/CD github action untuk deploy/hosting ke ibm cloud code engine.

## Result
Endpoint API yang telah kita deploy dapat digunakan tim mobile untuk di implementasikan di aplikasi mobile untuk melakukan prediksi diagnosa dan prediksi cuaca.

## Conclusion
Development berjalan lancar dan aplikasi LyfeStox dapat digunakan oleh user (peternak ayam) dalam mengelola peternakan, meningkatkan produktivitas, dan mengurangi kerugian!  

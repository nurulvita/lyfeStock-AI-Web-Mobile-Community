const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');
const multer = require('multer');
const path = require('path');
const fs = require('fs');

const app = express();
const PORT = 3000;

// Middleware untuk parsing body request
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Melayani file pengguna.html yang ada di root folder
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'pengguna.html')); // Pastikan file pengguna.html ada di root folder
});



// Database Connection
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root', // Sesuaikan dengan user database Anda
  password: '', // Sesuaikan dengan password database Anda
  database: 'lyfestox', // Nama database
});

db.connect(err => {
  if (err) throw err;
  console.log('Connected to database');
});

// Multer setup for file uploads
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    const uploadPath = './uploads';
    if (!fs.existsSync(uploadPath)) fs.mkdirSync(uploadPath);
    cb(null, uploadPath);
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname));
  },
});

const upload = multer({ storage });

// Routes untuk API (Tambah, Edit, Hapus Pengguna)
app.get('/users', (req, res) => {
  const query = 'SELECT * FROM pengguna';
  db.query(query, (err, results) => {
    if (err) return res.status(500).json(err);
    res.json(results);
  });
});

app.post('/users', upload.single('photo'), (req, res) => {
  const { email, namapengguna, notlpn, ttl, alamat, password } = req.body;
  const photo = req.file ? `/uploads/${req.file.filename}` : null;

  const query = `INSERT INTO pengguna (email, namapengguna, no_tlpn, ttl, alamat, photo, password) 
                 VALUES (?, ?, ?, ?, ?, ?, ?)`;
  const values = [email, namapengguna, notlpn, ttl, alamat, photo, password];

  db.query(query, values, (err, results) => {
    if (err) return res.status(500).json(err);
    res.status(201).json({ message: 'User added successfully', userId: results.insertId });
  });
});

app.put('/users/:id', upload.single('photo'), (req, res) => {
  const { id } = req.params;
  const { email, namapengguna, notlpn, ttl, alamat, password } = req.body;
  const photo = req.file ? `/uploads/${req.file.filename}` : null;

  const query = `UPDATE pengguna 
                 SET email = ?, namapengguna = ?, no_tlpn = ?, ttl = ?, alamat = ?, password = ?, photo = COALESCE(?, photo)
                 WHERE user_id = ?`;
  const values = [email, namapengguna, notlpn, ttl, alamat, password, photo, id];

  db.query(query, values, (err) => {
    if (err) return res.status(500).json(err);
    res.json({ message: 'User updated successfully' });
  });
});

app.delete('/users/:id', (req, res) => {
  const { id } = req.params;
  const query = 'DELETE FROM pengguna WHERE user_id = ?';

  db.query(query, [id], (err) => {
    if (err) return res.status(500).json(err);
    res.json({ message: 'User deleted successfully' });
  });
});

// Mulai server
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});

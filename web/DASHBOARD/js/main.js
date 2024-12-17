// add hovered class to selected list item
let list = document.querySelectorAll(".navigasi li");

function activeLink() {
    list.forEach((item) => {
        item.classList.remove("hovered");
    });
    this.classList.add("hovered");
}

list.forEach((item) => item.addEventListener("mouseover", activeLink));

// menu toggle
let toggle = document.querySelector(".toggle");
let navigasi = document.querySelector(".navigasi");
let main = document.querySelector(".main");
let nav = document.querySelector(".nav-logo")

toggle.onclick = function() {
    navigasi.classList.toggle("active");
    main.classList.toggle("active");
};

// Tampilkan atau sembunyikan form pengguna
document.getElementById('tambah-user').addEventListener('click', function (e) {
  e.preventDefault();
  document.getElementById('form-tambah').style.display = 'block';
});

document.getElementById('cancel-tambah').addEventListener('click', function () {
  document.getElementById('form-tambah').style.display = 'none';
});

// Menampilkan form edit dengan data pengguna
document.querySelectorAll(".edit-btn").forEach((btn) => {
  btn.addEventListener("click", function (e) {
    e.preventDefault();
    // Ambil data pengguna dari baris tabel
    const row = e.target.closest("tr");
    const userId = row.cells[0].textContent;
    const email = row.cells[1].textContent;
    const namaPengguna = row.cells[2].textContent;
    const noTlpn = row.cells[3].textContent;
    const ttl = row.cells[4].textContent;
    const alamat = row.cells[5].textContent;
    const password = row.cells[6].textContent;

    // Isi data ke dalam form
    document.getElementById("edit_user_id").value = userId;
    document.getElementById("edit_email").value = email;
    document.getElementById("edit_nama_pengguna").value = namaPengguna;
    document.getElementById("edit_no_tlpn").value = noTelp;
    document.getElementById("edit_ttl").value = ttl;
    document.getElementById("edit_alamat").value = alamat;
    document.getElementById("edit_password").value = password;

    // Tampilkan form
    document.getElementById("form-edit-pengguna").style.display = "block";
  });
});

// Menutup form edit
document.getElementById("cancel-edit-pengguna").addEventListener("click", function () {
  document.getElementById("form-edit-pengguna").style.display = "none";
});

// Menampilkan form delete dengan data pengguna
document.querySelectorAll(".delete-btn").forEach((btn) => {
  btn.addEventListener("click", function (e) {
    e.preventDefault();
    // Ambil data pengguna dari baris tabel
    const row = e.target.closest("tr");
    const userId = row.cells[0].textContent;
    const email = row.cells[1].textContent;
    const namaPengguna = row.cells[2].textContent;

    // Isi data ke dalam form delete
    document.getElementById("delete_user_id").textContent = userId;
    document.getElementById("delete_email").textContent = email;
    document.getElementById("delete_nama_pengguna").textContent = namaPengguna;

    // Tampilkan form delete
    document.getElementById("form-delete-pengguna").style.display = "block";
  });
});

// Menutup form delete
document.getElementById("cancel-delete").addEventListener("click", function () {
  document.getElementById("form-delete-pengguna").style.display = "none";
});

// Konfirmasi delete
document.getElementById("confirm-delete").addEventListener("click", function () {
  const userId = document.getElementById("delete_user_id").textContent;

  // Lakukan penghapusan data (implementasi sesuai backend atau logika Anda)
  alert(`Pengguna dengan user_id: ${userId} berhasil dihapus.`);

  // Tutup form delete
  document.getElementById("form-delete-pengguna").style.display = "none";
});


// Tampilkan atau sembunyikan form kandang
document.getElementById("tambah-kandang").addEventListener("click", function (e) {
  e.preventDefault();
  document.getElementById("form-tambah-kandang").style.display = "block";
});

document.getElementById("cancel-tambah-kandang").addEventListener("click", function () {
  document.getElementById("form-tambah-kandang").style.display = "none";
});


//DATABASES LYFESTOX

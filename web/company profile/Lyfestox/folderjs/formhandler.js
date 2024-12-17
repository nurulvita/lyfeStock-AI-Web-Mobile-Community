// Ambil elemen form dan pop-up
const form = document.getElementById("dataForm");
const popup = document.getElementById("popup");
const closeButton = document.getElementById("closePopup");

// Event listener untuk submit form
form.addEventListener("submit", async function (event) {
  event.preventDefault(); // Mencegah reload halaman

  // Ambil data dari form
  const formData = new FormData(form);
  const formObject = Object.fromEntries(formData);

  try {
    // Kirim data ke Google Apps Script Web App
    const response = await fetch(
      "https://script.google.com/macros/s/AKfycbzyXk8sWUPEY-mmo50u7wN-C7aNM61RDlIqmwxwjonhvnbhIUQ5zNKqNeU4YThYL9YZLg/exec",
      {
        method: "POST",
        body: new URLSearchParams(formObject),
      }
    );

    const result = await response.text();
    console.log(result); // Debugging

    // Tampilkan pop-up setelah data berhasil dikirim
    popup.style.display = "flex";
  } catch (error) {
    console.error("Error:", error);
    alert("Gagal mengirim data. Silakan coba lagi.");
  }
});

// Event listener untuk tombol "Tutup" pada pop-up
closeButton.addEventListener("click", function closePopup() {
  const popup = document.getElementById("popup");
  popup.style.display = "none"; // Sembunyikan pop-up
  console.log(closeButton); // Pastikan tombol ditemukan
});

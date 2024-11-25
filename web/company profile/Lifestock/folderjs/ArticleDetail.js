// Ambil ID artikel dari URL
const urlParams = new URLSearchParams(window.location.search);
const articleId = urlParams.get("id");

// Fungsi untuk memuat artikel utama
function loadMainArticle() {
  fetch("articles.json")
    .then((response) => response.json())
    .then((articles) => {
      const article = articles.find((art) => art.id == articleId);
      const articleContainer = document.getElementById("article-content");

      if (article) {
        // Menampilkan detail artikel di halaman
        articleContainer.innerHTML = `
          <h1>${article.title}</h1>
          <img src="${article.image}" alt="${article.title}">
          <div class="article-info">
            <p>Ditulis oleh: ${article.author} | ${new Date(
          article.date
        ).toLocaleDateString()}</p>
          </div>
          <p>${article.content}</p>
        `;
      } else {
        articleContainer.innerHTML = "<p>Artikel tidak ditemukan.</p>";
      }
    })
    .catch((error) => console.error("Error loading article:", error));
}

// Fungsi untuk memuat box preview artikel terkait
function loadRelatedArticles() {
  fetch("articles.json")
    .then((response) => response.json())
    .then((articles) => {
      const relatedContainer = document.getElementById("related-articles");

      // Filter artikel untuk menampilkan empat artikel lain
      const relatedArticles = articles
        .filter((article) => article.id != articleId)
        .slice(0, 4);

      // Buat elemen untuk setiap artikel terkait
      relatedArticles.forEach((article) => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `
          <a href="?id=${article.id}">
            <img src="${article.image}" alt="${article.title}">
            <h3>${article.title.substring(0, 20)}...</h3>
            <p>${article.content.substring(0, 27)}...</p>
            
          </a>
        `;
        relatedContainer.appendChild(listItem);
      });
    })
    .catch((error) => console.error("Error loading related articles:", error));
}

// Panggil kedua fungsi
loadMainArticle();
loadRelatedArticles();

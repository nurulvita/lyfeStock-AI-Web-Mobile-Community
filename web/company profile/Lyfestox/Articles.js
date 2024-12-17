let articlesData = []; // Menyimpan data artikel yang diambil

// Fungsi untuk memuat artikel dan ditampilkan dalam card
fetch("articles.json")
  .then((response) => response.json())
  .then((articles) => {
    articlesData = articles; // save data u pencarian
    displayArticles(articlesData);
  })
  .catch((error) => console.error("Error loading articles:", error));

// Fungsi untuk nampilin artikel dalam card
function displayArticles(articles) {
  const blogContainer = document.getElementById("blog-container");
  blogContainer.innerHTML = ""; // Kosongkan kontainer sebelum mengisi ulang

  articles.forEach((article) => {
    const articleCard = document.createElement("div");
    articleCard.classList.add("article-card");

    // klik ke seluruh card
    articleCard.onclick = () => {
      window.location.href = `article.html?id=${article.id}`;
    };

    articleCard.innerHTML = `
            <img src="${article.image}" alt="${article.title}">
            <h2>${article.title.substring(0, 23)}...</h2>
            <p>${article.content.substring(0, 60)}...</p>
        `;

    blogContainer.appendChild(articleCard);
  });
}

// Menyaring artikel berdasarkan input pencarian
function filterArticles() {
  const searchTerm = document.getElementById("search-bar").value.toLowerCase();
  const filteredArticles = articlesData.filter(
    (article) =>
      article.title.toLowerCase().includes(searchTerm) ||
      article.content.toLowerCase().includes(searchTerm)
  );
  displayArticles(filteredArticles);
}

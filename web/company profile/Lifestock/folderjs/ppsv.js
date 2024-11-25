const form = document.getElementById("dataForm");
const popup = document.getElementById("popup");

form.addEventListener("submit", function (event) {
  event.preventDefault();
  popup.style.display = "flex"; // Show popup
});

function closePopup() {
  popup.style.display = "none"; // Hide popup
}

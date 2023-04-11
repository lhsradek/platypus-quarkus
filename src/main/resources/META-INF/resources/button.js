document.getElementById("topBtn").addEventListener("click", topFunction);
var number = 300;

// When the user scrolls down 300px from the top of the document, show the button
window.onscroll = function() {
    scrollFunction()
};

function scrollFunction() {
  if (document.body.scrollTop > number || document.documentElement.scrollTop > number) {
    topBtn.style.display = "inline-block";
  } else {
    topBtn.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}
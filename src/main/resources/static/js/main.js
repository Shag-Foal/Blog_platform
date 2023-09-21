'use strict'
document.getElementById("sorting-button").addEventListener("click", function() {
    let sortingMenu = document.getElementById("sorting-menu");
    if (sortingMenu.style.display === "block") {
        sortingMenu.style.display = "none";
    } else {
        sortingMenu.style.display = "block";
    }
});

const sortingLinks = document.querySelectorAll(".sorting-menu a");
sortingLinks.forEach(function(link) {
    link.addEventListener("click", function(event) {
        event.preventDefault();
        let selectedSort = link.getAttribute("data-sort");
        document.getElementById("sorting-button").textContent =link.textContent;
        console.log("Выбрана сортировка: " + selectedSort);
        document.getElementById("sorting-menu").style.display = "none";
        selectedSort = selectedSort.charAt(0).toUpperCase() + selectedSort.slice(1);
        if (selectedSort === 'Date'){
            window.location.href = "http://localhost:8080";
        }
        else window.location.href = "/sortBy"+ selectedSort;
    });
});
// selectedSort = selectedSort.charAt(0).toUpperCase() + selectedSort.slice(1);
// fetch('/sortBy'+ selectedSort, {
//     method: "GET",
// });
//Function Calls
sidebar();
windProgressBar();
humidityProgressBar();
addToSidebar();

//Open | Close Sidebar
function sidebar() {
  const openSidebarBtn = document.querySelector(".nav-icon");
  const closeSidebarBtn = document.querySelector(".sidebar-closeBtn");
  const sidebar = document.querySelector(".sidebar-w");
  const overlay = document.querySelector(".sidebar-overlay");

  // SideBar Toggle
  if (openSidebarBtn.classList !== "active") {
    openSidebar();
    closeSidebar();
  }

  // Open SideBar
  function openSidebar() {
    openSidebarBtn.addEventListener("click", () => {
      sidebar.classList.add("active");
      overlay.classList.add("active");
    });
  }

  // Close SideBar
  function closeSidebar() {
    closeSidebarBtn.addEventListener("click", () => {
      sidebar.classList.remove("active");
      overlay.classList.remove("active");
    });
  }
}

//Dynamic Progress Bar ( Wind )
function windProgressBar() {
  let totalProgress = document.querySelector(".wind-progress");
  let progressValue = document.querySelector(".wind-value");

  let progressStartValue = 0;
  let progressEndValue = 33;
  let speed = 75;

  let progress = setInterval(() => {
    progressStartValue++;

    progressValue.textContent = `${progressStartValue} Mph`;
    totalProgress.style.background = `conic-gradient(#ededed ${
      progressStartValue * 3.6
    }deg,transparent 0deg)`;

    if (progressStartValue == progressEndValue) {
      clearInterval(progress);
    }
  }, speed);
}

//Dynamic Progress Bar ( Humidity )
function humidityProgressBar() {
  let totalProgress = document.querySelector(".humidity-progress");
  let progressValue = document.querySelector(".humidity-value");

  let progressStartValue = 0;
  let progressEndValue = 77;
  let speed = 75;

  let progress = setInterval(() => {
    progressStartValue++;

    progressValue.textContent = `${progressStartValue}%`;
    totalProgress.style.background = `conic-gradient(#ededed ${
      progressStartValue * 3.6
    }deg,transparent 0deg)`;

    if (progressStartValue == progressEndValue) {
      clearInterval(progress);
    }
  }, speed);
}

//Add | Remove Content to Sidebar
let listArray = [];
function addToSidebar() {
  //Html to JS Link
  const searchIcon = document.querySelector(".search-icon");
  const list = document.querySelector(".nav-list");

  // Load sidebar items from localStorage when the page loads
  window.onload = function () {
    const storedItems = localStorage.getItem("sidebarItems");
    if (storedItems) {
      listArray = JSON.parse(storedItems);
      listArray.forEach((item) => {
        setList(item);
      });
    }
  };

  // On 'Enter Key' Press ( Add Location )
  const input = document.querySelector(".location-searchbar");
  input.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      searchIcon.click();
    }
  });

  // On "Search Icon" Click ( Add Location )
  searchIcon.addEventListener("click", () => {
    const searchValue = document.querySelector(".location-searchbar").value;
    if (searchValue.trim() !== "") {
      // Check if input is not empty
      setList(searchValue); // Add the new item to the list
      listArray.push(searchValue); // Add the new item to the listArray
      localStorage.setItem("sidebarItems", JSON.stringify(listArray)); // Update localStorage
    }
  });

  // On "Remove All Locations Icon" Click ( Remove All Locations )
  const clearSidebar = document.querySelector(".sidebar-clearBtn");
  clearSidebar.addEventListener("click", () => {
    list.innerHTML = "";
    listArray = [];
    localStorage.removeItem("sidebarItems");
  });

  // Function to create and append a list item
  function setList(content) {
    // Create List
    const listItem = document.createElement("li");
    listItem.textContent = content;
    listItem.classList.add("navList-item");
    listItem.tabIndex = 1;

    // Create Remove Button
    const removeButton = document.createElement("button");
    removeButton.textContent = "❌";
    removeButton.classList.add("remove-location");
    listItem.appendChild(removeButton);

    // Remove Specific Location On Click
    removeButton.addEventListener("click", () => {
      listItem.remove();
      removeFromLocalStorage(content);
    });

    // Attach List Item to List
    list.appendChild(listItem);
  }

  // Remove Element from list and storage
  function removeFromLocalStorage(itemText) {
    const itemIndex = listArray.indexOf(itemText);
    if (itemIndex !== -1) {
      listArray.splice(itemIndex, 1);
      localStorage.setItem("sidebarItems", JSON.stringify(listArray));
    }
  }
}

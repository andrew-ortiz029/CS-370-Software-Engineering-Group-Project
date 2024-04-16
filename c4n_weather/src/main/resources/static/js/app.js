sidebar();
windProgressBar();
humidityProgressBar();

function sidebar(){
  const openSidebarBtn = document.querySelector('.nav-icon');
  const closeSidebarBtn = document.querySelector('.sidebar-closeBtn');
  const sidebar = document.querySelector('.sidebar-w');
  const overlay = document.querySelector('.sidebar-overlay');



  if(openSidebarBtn.classList !== 'active'){
    openSidebar();
    closeSidebar();
  }

  function openSidebar() {
    openSidebarBtn.addEventListener('click', () => {
      if (sidebar.classList.contains('active')) {
        // If the sidebar is already open, close it
        sidebar.classList.remove('active');
        overlay.classList.remove('active');
      } else {
        // If the sidebar is closed, open it
        sidebar.classList.add('active');
        overlay.classList.add('active');
      }
    });
  }

  function closeSidebar(){
    closeSidebarBtn.addEventListener('click', ()=> {
      sidebar.classList.remove('active');
      overlay.classList.remove('active');
    })
  }  
}

function windProgressBar(){
  let totalProgress = document.querySelector('.wind-progress');
  let progressValue = document.querySelector('.wind-value');

  let progressStartValue = 0;
  let progressEndValue = 33;
  let speed = 75;

  let progress = setInterval(()=> {
    progressStartValue++;



    progressValue.textContent = `${progressStartValue} Mph`;
    totalProgress.style.background = `conic-gradient(#ededed ${progressStartValue * 3.6}deg,transparent 0deg)`;

    if(progressStartValue == progressEndValue){
      clearInterval(progress);
    }
  }, speed);

}

function humidityProgressBar(){
  let totalProgress = document.querySelector('.humidity-progress');
  let progressValue = document.querySelector('.humidity-value');

  let progressStartValue = 0;
  let progressEndValue = 77;
  let speed = 75;

  let progress = setInterval(()=> {
    progressStartValue++;



    progressValue.textContent = `${progressStartValue}%`;
    totalProgress.style.background = `conic-gradient(#ededed ${progressStartValue * 3.6}deg,transparent 0deg)`;

    if(progressStartValue == progressEndValue){
      clearInterval(progress);
    }
  }, speed);

}


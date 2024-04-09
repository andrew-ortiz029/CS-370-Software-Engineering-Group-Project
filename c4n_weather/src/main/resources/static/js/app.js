sidebar();

function sidebar(){
  const openSidebarBtn = document.querySelector('.nav-icon');
  const closeSidebarBtn = document.querySelector('.sidebar-closeBtn');
  const sidebar = document.querySelector('.sidebar-w');
  const overlay = document.querySelector('.sidebar-overlay');



  if(openSidebarBtn.classList !== 'active'){
    openSidebar();
    closeSidebar();
  }

  function openSidebar(){
    openSidebarBtn.addEventListener('click', ()=> {
      sidebar.classList.add('active');
      overlay.classList.add('active');
    })
  }

  function closeSidebar(){
    closeSidebarBtn.addEventListener('click', ()=> {
      sidebar.classList.remove('active');
      overlay.classList.remove('active');
    })
  }
  



}


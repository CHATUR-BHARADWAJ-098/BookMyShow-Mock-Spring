const removeDeadControls = () => {
  document.querySelectorAll('.section-head .text-btn, .food-tabs, .admin-head > .btn, .booking-search').forEach((element) => element.remove());
  document.querySelectorAll('nav button[data-action="offers"], nav button[data-action="theaters"]').forEach((element) => element.remove());
};

new MutationObserver(removeDeadControls).observe(document.body, { childList: true, subtree: true });
removeDeadControls();

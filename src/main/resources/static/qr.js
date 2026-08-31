const qrObserver = new MutationObserver(() => {
  const qr = document.querySelector('#qr');
  if (!qr || qr.dataset.ready) return;
  const bookingId = document.querySelector('.booking-id strong')?.textContent?.trim() || 'CB202608310001';
  qr.replaceChildren(Object.assign(document.createElement('img'), {
    src: `https://api.qrserver.com/v1/create-qr-code/?size=190x190&data=${encodeURIComponent(bookingId)}`,
    alt: `QR code for ${bookingId}`
  }));
  qr.dataset.ready = 'true';
});
qrObserver.observe(document.body, { childList: true, subtree: true });

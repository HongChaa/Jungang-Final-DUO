export function fillRateStars() {
    const rates = document.querySelectorAll('.reviewRate');
    rates.forEach(rate => {
        rate.addEventListener('click', () => {
            rates.forEach(r => {
                r.src = '/assets/img/chattingModal/rate-filled.png';
            });
            for (let i = 0; i < rates.length; i++) {
                if (rates[i] === rate && i !== 4) {
                    for (let j = i + 1; j <= 4; j++) {
                        rates[j].src = '/assets/img/chattingModal/rate-empty.png';
                    }
                    rate.closest('#give-rate').dataset.rate = `${i}`;
                    return;
                }
            }
        });
    });

}

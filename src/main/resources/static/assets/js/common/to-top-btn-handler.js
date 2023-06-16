export function toTopBtnHandler() {

    const $topBtn = document.getElementById('to-top-btn');

    $topBtn.onmouseup = e => {
        window.scrollTo({
            top: 0,
            left: 0,
            // behavior: "smooth" // 가끔 동작안할때 있어서 주석처리
        });
    }

    // 맨 위가 아니면 버튼 감추기
    window.onscroll = e => {
        if (window.scrollY === 0) {
            $topBtn.style.display = 'none';
        } else {
            $topBtn.style.display = 'block';
        }
    }

}
import {clickSelectDate} from "./matching.js";

export function renderMatchingCalendar() {
    const realNow = new Date();
    let match_now = new Date();
    let match_today = match_now.getDate();
    let match_year = match_now.getFullYear();
    let match_month = match_now.getMonth() + 1;

    const prev = document.getElementById('prev');
    prev.addEventListener('click', prevMonth)
    const next = document.getElementById('next');
    next.addEventListener('click', nextMonth)

    function prevMonth() {
        // 오늘 날짜와 비교하여 더 앞으로 갈 수 없도록 검사
        const prev_today = 1;
        const prev_month = match_month === 1 ? 12 : match_month - 1;
        const prev_year = prev_month === 12 ? match_year - 1 : match_year;
        const prev_now = new Date(prev_year, prev_month - 1, prev_today);

        if (prev_now <= realNow) {
            const realNow = new Date();
            match_now = realNow;
            match_today = realNow.getDate();
            match_year = realNow.getFullYear();
            match_month = realNow.getMonth() + 1;
            getMyCalendar(match_now, match_today, match_month, match_year);
        } else {
            match_now = prev_now;
            match_today = prev_today;
            match_year = prev_year;
            match_month = prev_month;
            getMyCalendar(match_now, match_today, match_month, match_year);
        }
    }

    function nextMonth() {
        // 오늘 날짜와 비교하여 더 앞으로 갈 수 없도록 검사
        match_today = 1;
        match_month = match_month === 12 ? 1 : match_month + 1;
        match_year = match_month === 1 ? match_year + 1 : match_year;
        match_now = new Date(match_year, match_month - 1, match_today);

        getMyCalendar(match_now, match_today, match_month, match_year);
    }


    function getMyCalendar(match_now, match_today, match_month, match_year) {

// 1일의 요일 구하기
        const match_firstDay = new Date(match_now.setDate(1)).getDay();
//마지막 날짜 구하기
        const match_lastDate = new Date(match_year, match_month, 0).getDate();
//이번연도 출력
        document.getElementById('now-year').innerText = match_year;
//이번달 출력
        document.getElementById('now-month').innerText = match_month.toString();

// 달력 렌더링
        let dates = '';
        for (let i = 0; i < match_firstDay; i++) {
            dates += '<div class = "date my-calendar"></div>';
        }
        // 1일부터 어제까지
        for (let i = 1; i < match_today; i++) {
            dates += `<div class = "prev-date date my-calendar">${i}</div>`;
        }
        // 오늘부터 말일까지
        for (let i = match_today; i <= match_lastDate; i++) {
            dates += `<div class = "date my-calendar available-date">${i}</div>`;
        }

        const myCalendar = document.getElementById('dates');
        myCalendar.innerHTML = dates;

        selectDateEvent();
    }

    getMyCalendar(match_now, match_today, match_month, match_year);

}

export function selectDateEvent(){
    const dates = document.querySelectorAll('.available-date');
    dates.forEach( d => d.onclick = e => {
        const selected = e.target.closest('.available-date');
        dates.forEach(d => {
            d.style.color = 'black';
            d.style.background = '';
            d.style.fontWeight = '400';
        });
        selected.style.color = '#1EA1F7';
        selected.style.fontWeight = '700';
        selected.style.background = '#e1ecff';

        let matchingYear = document.getElementById('now-year').innerText;
        let matchingMonth = document.getElementById('now-month').innerText;
        let matchingDate = e.target.closest('.available-date').innerText;

        [...document.querySelectorAll('.message-dialog')].forEach(m => {
            if(m.hasAttribute('open')){
                const $chattingRoom = m.closest('.chatting-card');
                const $chattingNo = m.closest('.chatting-card').id;
                const $matchingNo = $chattingRoom.querySelector('.matching-accept-btn').dataset.matchingNo;
                clickSelectDate($chattingNo, $matchingNo, matchingYear, matchingMonth, matchingDate);
            }
        })

        }
    );

}

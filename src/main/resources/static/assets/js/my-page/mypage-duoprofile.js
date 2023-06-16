const $registerBtn = document.getElementById("register-duo");
const $matchingPoint = document.getElementById("matching-point"); // 매칭포인트 input 부분

// 프로필카드 등록버튼 클릭시 함수
function registerCard() {
    $registerBtn.onclick = e => {
        const $positionOption = document.querySelectorAll(".select-position"); // 포지션 선택 라디오 버튼
        const $comment = document.getElementById("comment"); // 자기소개 입력 부분
        const form = document.getElementById("position-comment-form"); // 폼 태그 부분

        let selectedPosition = '';
        // 체크된 버튼 있는지 확인
        for (let i = 0; i < $positionOption.length; i++) {
            if ($positionOption[i].checked) {
                selectedPosition = $positionOption[i].value;
            }
        }
        // 포지션 미선택 시 알림창 + 리턴
        if (selectedPosition === '') {
            alert(`포지션을 선택해주세요.`);
            return;
        }
        // 자기소개글 미입력시 알림창 + 리턴
        else if ($comment.value === '') {
            alert(`자기소개글을 입력해주세요.`);
            return;
        }
        // 매칭 포인트 미기재시 알림창 + 리턴
        else if ($matchingPoint.value === '') {
            alert(`매칭 포인트를 입력해주세요.`);
            return;
        }
        // 기재 포인트가 100포인트 미만시 알림창 + 리턴
        else if ($matchingPoint.value < 100) {
            alert(`매칭 포인트는 100point 이상 입력해주세요`);
            return;
        }

        form.submit();
    }
};

// 매칭포인트 입력시 숫자만 입력가능 + 3자리까지만 입력가능 함수
function checkPoint() {
    $matchingPoint.oninput = e => {
        const value = e.target.value;
        e.target.value = value.replace(/[^0-9]/g, "");

        if (e.target.value.length > 3) {
            e.target.value = e.target.value.slice(0, 3);
        }
    }
};

//========= 메인 실행부 =========//
(function () {
    // 프로필 카드 등록 함수
    registerCard();
    // 매칭포인트 값 검증 함수
    checkPoint();

})();
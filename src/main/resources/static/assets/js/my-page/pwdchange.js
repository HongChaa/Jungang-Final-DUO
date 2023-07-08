// 비밀번호 변경 요청
const changePassword = () => {
    const nowPwdInput = document.getElementById('user-password').value; // 현재 비밀번호 입력 값
    const newPwdInput = document.getElementById('new-user-password').value; // 새로운 비밀번호 입력 값


    // 서버로 전송할 데이터 객체 생성
    const requestData = {
        userPassword: nowPwdInput,
        newPassword: newPwdInput
    };


    // 비밀번호 변경 요청을 서버로 전송
    fetch('/api/v1/users/change-password', {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (response.ok) {
                // 비밀번호 변경 성공
                console.log('비밀번호가 성공적으로 변경되었습니다.');
            } else if (response.status === 401) {
                // 기존 비밀번호 일치하지 않음
                console.log('기존 비밀번호가 일치하지 않습니다.');
            } else {
                // 비밀번호 변경 실패
                console.log('비밀번호 변경에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('네트워크 오류 또는 예외 발생:', error);

        });
};

// 비밀번호 변경 버튼 클릭 이벤트
const changePwdButton = document.querySelector('.alter-change');
changePwdButton.addEventListener('click', () => {
    // 모든 입력값 검증 통과 여부 확인
    const isAllChecked = checkResultList.every(item => item === true);
    if (isAllChecked) {
        // 입력값이 모두 검증 통과했을 때의 처리 로직
        changePassword();
    } else {
        // 입력값에 오류가 있을 때의 처리 로직
        alert('입력값을 모두 작성해주세요.');
    }
});


// 입력값 검증 통과 여부 배열
const checkResultList = [false, false, false];

// 패스워드 검사 정규표현식
const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*?_~]).{8,16}$/;

// 기존 패스워드 일치 검증식
const nowPwdInput = document.getElementById('user-password');
const nowPwdMessage = document.getElementById('nowPwdChk');

nowPwdInput.addEventListener('keyup', () => {
    const nowPwdValue = nowPwdInput.value;

    if (nowPwdValue.trim() === '') {
        nowPwdInput.style.borderColor = 'red';
        nowPwdMessage.innerHTML = '<b style="color: red;">[비밀번호는 필수값입니다!]</b>';
        checkResultList[0] = false;
    } else if (!passwordPattern.test(nowPwdValue)) {
        nowPwdInput.style.borderColor = 'red';
        nowPwdMessage.innerHTML = '<b style="color: red;">[특수문자 포함 8자 이상!]</b>';
        checkResultList[0] = false;
    } else {
        nowPwdInput.style.borderColor = 'skyblue';
        nowPwdMessage.innerHTML = '<b style="color: skyblue;">[사용 가능한 비밀번호입니다.]</b>';
        checkResultList[0] = true;
    }
});

// 패스워드 입력값 검증
const passwordInput = document.getElementById('new-user-password');
const passwordCheckMessage = document.getElementById('pwChk');

passwordInput.addEventListener('keyup', () => {
    const passwordValue = passwordInput.value;

    if (passwordValue.trim() === '') {
        passwordInput.style.borderColor = 'red';
        passwordCheckMessage.innerHTML = '<b style="color: red;">[비밀번호는 필수값입니다!]</b>';
        checkResultList[1] = false;
    } else if (!passwordPattern.test(passwordValue)) {
        passwordInput.style.borderColor = 'red';
        passwordCheckMessage.innerHTML = '<b style="color: red;">[특수문자 포함 8자 이상!]</b>';
        checkResultList[1] = false;
    } else {
        passwordInput.style.borderColor = 'skyblue';
        passwordCheckMessage.innerHTML = '<b style="color: skyblue;">[사용가능한 비밀번호입니다.]</b>';
        checkResultList[1] = true;
    }
});

// 패스워드 확인란 입력값 검증
const passwordCheckInput = document.getElementById('new-user-password-check');

passwordCheckInput.onkeyup = () => {
    const passwordCheckValue = passwordCheckInput.value;

    if (passwordCheckValue.trim() === '') {
        passwordCheckInput.style.borderColor = 'red';
        document.getElementById('pwChk2').innerHTML = '<b style="color: red;">[비밀번호 확인란은 필수값입니다!]</b>';
        checkResultList[2] = false;
    } else if (passwordCheckInput.value !== passwordInput.value) {
        passwordCheckInput.style.borderColor = 'red';
        document.getElementById('pwChk2').innerHTML = '<b style="color: red;">[비밀번호를 똑같이 입력해주세요.]</b>';
        checkResultList[2] = false;
    } else {
        passwordCheckInput.style.borderColor = 'skyblue';
        document.getElementById('pwChk2').innerHTML = '<b style="color: skyblue;">[비밀번호가 일치합니다.]</b>';
        checkResultList[2] = true;
    }
};
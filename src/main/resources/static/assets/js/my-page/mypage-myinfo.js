// import {addModalBtnEvent, addModalCloseEvent} from "../common/modal-handler";


(() => {

    // addModalBtnEvent();
    // addModalCloseEvent();

    // 닉네임 입력값 검증
    const nicknameInput = document.getElementById('userNickname');
    const nicknamePattern = /^[가-힣a-zA-Z0-9]{2,8}$/;

    nicknameInput.onblur = () => {
        const nicknameValue = nicknameInput.value.trim();

        if (nicknameValue === '') {
            nicknameInput.style.borderColor = 'red';
            document.getElementById('nicknameChk').innerHTML = '';
        } else if (!nicknamePattern.test(nicknameValue)) {
            nicknameInput.style.borderColor = 'red';
            document.getElementById('nicknameChk').innerHTML = '';
        } else {
            nicknameInput.style.borderColor = 'skyblue';
            document.getElementById('nicknameChk').innerHTML = '';
        }
    };

    // 생일 입력값 검증
    const birthdayInput = document.getElementById('userBirthday');

    birthdayInput.onblur = () => {
        const birthdayValue = birthdayInput.value;

        if (birthdayValue.trim() === '') {
            birthdayInput.style.borderColor = 'red';
            document.getElementById('birthdayChk').innerHTML = '';
        } else {
            birthdayInput.style.borderColor = 'skyblue';
            document.getElementById('birthdayChk').innerHTML = '';
        }
    };

    // 소환사 아이디 입력값 검증
    const lolNicknameInput = document.getElementById('lolNickname');
    const lolNicknamePattern = /^[a-zA-Z0-9가-힣\s]{3,16}$/; // 롤 소환사 아이디 3~16글자(힌글,영문,숫자, 가운데 공백 포함)

    lolNicknameInput.onblur = () => {
        const lolNicknameValue = lolNicknameInput.value.trim();

        if (lolNicknameValue === '') {
            lolNicknameInput.style.borderColor = 'red';
            document.getElementById('lolNicknameChk').innerHTML = '';
        } else if (!lolNicknamePattern.test(lolNicknameValue)) {
            lolNicknameInput.style.borderColor = 'red';
            document.getElementById('lolNicknameChk').innerHTML = '';
        } else {
            lolNicknameInput.style.borderColor = 'skyblue';
            document.getElementById('lolNicknameChk').innerHTML = '';
        }
    };

    // SNS계정은 선택값(공백이어도 상관없고, 입력값이 잘못됐을 경우만 빨간색 표시)
    // 인스타 사용자 아이디 검증식
    const instagramInput = document.getElementById('instagram');
    const instagramUsernamePattern = /^[a-z0-9](?:[a-z0-9._]*[a-z0-9]){5,20}$/;  // 인스타그램 사용자 아이디 패턴

    instagramInput.onblur = () => {
        const username = instagramInput.value.trim();

        if (username !== '' && !instagramUsernamePattern.test(username)) {
            instagramInput.style.borderColor = 'red';
            document.getElementById('instagramChk').innerHTML = '';
        } else {
            instagramInput.style.borderColor = 'skyblue';
            document.getElementById('instagramChk').innerHTML = '';
        }
    };

    // 페이스북 사용자 아이디 검증식
    const facebookInput = document.getElementById('facebook');
    const facebookUsernamePattern = /^[a-zA-Z0-9._]{5,20}$/; // 페이스북 사용자 이름 패턴

    facebookInput.onblur = () => {
        const username = facebookInput.value.trim();

        if (username !== '' && !facebookUsernamePattern.test(username)) {
            facebookInput.style.borderColor = 'red';
            document.getElementById('facebookChk').innerHTML = '';
        } else {
            facebookInput.style.borderColor = 'skyblue';
            document.getElementById('facebookChk').innerHTML = '';
        }
    };

    // 트위터 사용자 아이디 검증식
    const twitterInput = document.getElementById('twitter');
    const userIdPattern = /^[a-zA-Z0-9_]{4,15}$/; // 사용자 아이디 패턴

    twitterInput.onblur = () => {
        const userId = twitterInput.value.trim();

        if (userId !== '' && !userIdPattern.test(userId)) {
            twitterInput.style.borderColor = 'red';
            document.getElementById('twitterChk').innerHTML = '';
        } else {
            twitterInput.style.borderColor = 'skyblue';
            document.getElementById('twitterChk').innerHTML = '';
        }
    };


    // 정보변경 버튼에 클릭 이벤트 리스너 추가
    document.getElementById('modify-btn').addEventListener('click', modifyUserInfo);

    function modifyUserInfo() {
        // 변경할 정보를 가져오기
        const userNickname = document.getElementById('userNickname').value;
        const userBirthday = document.getElementById('userBirthday').value;
        const lolNickname = document.getElementById('lolNickname').value;
        const userInstagram = document.getElementById('instagram').value;
        const userFacebook = document.getElementById('facebook').value;
        const userTwitter = document.getElementById('twitter').value;

        // 변경할 정보를 담을 객체 생성
        const requestBody = {
            userAccount: document.getElementById('loginUserInfo').dataset.userAccount,
            userNickname: userNickname,
            userBirthday: userBirthday,
            lolNickname: lolNickname,
            userInstagram: userInstagram,
            userFacebook: userFacebook,
            userTwitter: userTwitter,
        };

        // // 입력된 값만 객체에 추가
        // if (userNickname) {
        //     requestBody.userNickname = userNickname;
        // }
        // if (userBirthday) {
        //     requestBody.userBirthday = userBirthday;
        // }
        // if (lolNickname) {
        //     requestBody.lolNickname = lolNickname;
        // }
        // if (userInstagram) {
        //     requestBody.userInstagram = userInstagram;
        // }
        // if (userFacebook) {
        //     requestBody.userFacebook = userFacebook;
        // }
        // if (userTwitter) {
        //     requestBody.userTwitter = userTwitter;
        // }

        // 로그 출력
        console.log('정보변경 요청 발생');
        console.log('요청 데이터:', requestBody);

        // fetch를 사용하여 서버로 데이터 전송
        fetch('/api/v1/users', {
            method: 'PUT',
            headers: {
                'content-type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        })
            .then(function (response) {
                if (response.ok) {
                    // 요청이 성공적으로 완료된 경우
                    return response.json();
                } else {
                    // 요청이 실패한 경우
                    throw new Error('요청 실패');
                }
            })
            .then(function (data) {
                // 응답 데이터 처리
                console.log(data);
            })
            .catch(function (error) {
                // 에러 처리
                console.error(error);
            });
    }

    // 프로필 사진
    const addProfileButton = document.getElementById('add-profile');
    const fileInput = document.getElementById('profileImages');
    const mainProfileImage = document.getElementById('main-profile-image');

    addProfileButton.addEventListener('click', () => {
        fileInput.click();
    });

    fileInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();

            reader.onload = () => {
                mainProfileImage.src = reader.result;
            };

            reader.readAsDataURL(file);
        }
    });





})();

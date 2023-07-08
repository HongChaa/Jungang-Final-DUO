import {addModalBtnEvent, addModalCloseEvent} from "../common/modal-handler.js";

(() => {

    // 인증 버튼 핸들러
    function addConfirmBtnHandler() {
        document.getElementById('email-confirm-modal')
            .querySelector('button').onclick = async e => {
            e.preventDefault();
            const res = await fetch("/user/check-email",
                {
                    method: 'POST',
                    body: document.getElementById('confirm-code').value
                });

            if (res.status === 499) {
                alert('인증번호가 맞지 않습니다!');
                return false;
            } else if (res.status === 200) {
                alert('인증되었습니다!');

                e.target.closest('dialog').close();

                document.getElementById('user-email').style.borderColor = 'skyblue';

                return true;
            }

        }
    }

    async function checkEmailValidation() {
        // 이메일 검사 정규표현식
        const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

        // 이메일 입력값 검증
        const emailInput = document.getElementById('user-email');
        const emailCheckMessage = document.getElementById('emailChk');
        let isEmailValid = false;

        emailInput.addEventListener('keyup', () => {
            const emailValue = emailInput.value.trim();

            if (emailValue === '') {
                emailInput.style.borderColor = 'red';
                emailCheckMessage.innerHTML = '<b style="color: red;">[이메일 필수값입니다!]</b>';
                isEmailValid = false;
            } else if (!emailPattern.test(emailValue)) {
                emailInput.style.borderColor = 'red';
                emailCheckMessage.innerHTML = '<b style="color: red;">[이메일 형식을 지켜주세요~]</b>';
                isEmailValid = false;
            } else {
                fetch(`/check?type=email&keyword=${encodeURIComponent(emailValue)}`)
                    .then(res => res.json())
                    .then(isDuplicate => {
                        if (isDuplicate) {
                            emailInput.style.borderColor = 'red';
                            emailCheckMessage.innerHTML = '<b style="color: red;">[이메일이 중복되었습니다.]</b>';
                            isEmailValid = false;
                        } else {
                            emailInput.style.borderColor = 'yellow';
                            emailCheckMessage.innerHTML = '<b style="color: skyblue;">[인증을 끝마쳐주세요]</b>';
                            isEmailValid = true;
                            checkResultList[6] = false;
                        }
                    });
            }
        });

        return isEmailValid;
    }

    async function sendVerificationEmail(isEmailValid) {

        document.getElementById("verification-btn").onclick = async e => {

            e.preventDefault();

            const userEmail = document.getElementById('user-email').value.trim();

            if (isEmailValid) {
                const res = await fetch('/user/send-email',
                    {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            'userEmail': userEmail
                        })
                    });

                if (res.status === 200) {
                    return showConfirmationModal();
                } else {
                    alert('이메일 전송에 실패하였습니다');
                }
            } else {
                alert('올바른 이메일을 입력해주세요');
            }

            return false;
        };
    }

// 모달 창 열기 및 인증 코드 표시
    function showConfirmationModal() {
        const modal = document.querySelector("dialog");
        const confirmCodeInput = modal.querySelector("#confirm-code");

        let remainTime = 180;

        const timer = setInterval(() => {
            if (remainTime === 0) {
                clearInterval(timer);
                modal.close();
                return;
            }
            remainTime--;
            confirmCodeInput.placeholder = "남은 시간 : " + remainTime + '초';
        }, 1000);

        modal.showModal();

        return addConfirmBtnHandler();
    }

    addModalBtnEvent();
    addModalCloseEvent();

    document.getElementById("verification-btn").onclick = null;

    // 회원가입 입력값 검증 처리

    // 입력값 검증 통과 여부 배열
    const checkResultList = [false, false, false, false, false, false, false];

    // 닉네임 입력값 검증
    const nicknameInput = document.getElementById('user-nickname');
    const nicknamePattern = /^[가-힣a-zA-Z0-9]{2,8}$/;

    nicknameInput.onblur = () => {
        const nicknameValue = nicknameInput.value.trim();

        if (nicknameValue === '') {
            nicknameInput.style.borderColor = 'red';
            document.getElementById('nicknameChk').innerHTML = '<b style="color: red;">[닉네임은 필수값입니다!]</b>';
            checkResultList[0] = false;
        } else if (!nicknamePattern.test(nicknameValue)) {
            nicknameInput.style.borderColor = 'red';
            document.getElementById('nicknameChk').innerHTML = '<b style="color: red;">[닉네임은 2~8자리여야 하며, 한글, 영문, 숫자만 포함해야 합니다.]</b>';
            checkResultList[0] = false;
        } else {
            nicknameInput.style.borderColor = 'skyblue';
            document.getElementById('nicknameChk').innerHTML = '<b style="color: skyblue;">[사용가능한 닉네임 입니다.]</b>';
            checkResultList[0] = true;
        }
    };

    // 성별 선택 검증
    const userGenderSelect = document.getElementById('user-gender');
    const selectedGender = userGenderSelect.value;

    // 테두리 색상을 빨간색으로 설정하는 함수
    function setRedBorder() {
        userGenderSelect.style.borderColor = 'red';
    }

    // 초기에는 테두리 색상을 설정하지 않음
    userGenderSelect.style.borderColor = '';

    // 선택이 변경될 때마다 테두리 색상을 검사하고, 선택되지 않은 경우에만 빨간색으로 설정
    userGenderSelect.addEventListener('change', () => {
        if (userGenderSelect.value === 'none') {
            setRedBorder();
            checkResultList[1] = false;
        } else {
            userGenderSelect.style.borderColor = '';
            checkResultList[1] = true;
        }
    });

    // 이메일 입력값 검증

    const isEmailValid = checkEmailValidation().then();

    checkResultList[6] = sendVerificationEmail(isEmailValid).then();

    // 패스워드 검사 정규표현식
    const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*?_~]).{8,16}$/;

    // 패스워드 입력값 검증
    const passwordInput = document.getElementById('user-password');
    const passwordCheckMessage = document.getElementById('pwChk');

    passwordInput.addEventListener('keyup', () => {
        const passwordValue = passwordInput.value;

        if (passwordValue.trim() === '') {
            passwordInput.style.borderColor = 'red';
            passwordCheckMessage.innerHTML = '<b style="color: red;">[비밀번호는 필수값입니다!]</b>';
            checkResultList[2] = false;
        } else if (!passwordPattern.test(passwordValue)) {
            passwordInput.style.borderColor = 'red';
            passwordCheckMessage.innerHTML = '<b style="color: red;">[특수문자 포함 8자 이상!]</b>';
            checkResultList[2] = false;
        } else {
            passwordInput.style.borderColor = 'skyblue';
            passwordCheckMessage.innerHTML = '<b style="color: skyblue;">[사용가능한 비밀번호입니다.]</b>';
            checkResultList[2] = true;
        }
    });

    // 패스워드 확인란 입력값 검증
    const passwordCheckInput = document.getElementById('user-password-check');

    passwordCheckInput.onkeyup = () => {
        const passwordCheckValue = passwordCheckInput.value;

        if (passwordCheckValue.trim() === '') {
            passwordCheckInput.style.borderColor = 'red';
            document.getElementById('pwChk2').innerHTML = '<b style="color: red;">[비밀번호 확인란은 필수값입니다!]</b>';
            checkResultList[3] = false;
        } else if (passwordCheckInput.value !== passwordInput.value) {
            passwordCheckInput.style.borderColor = 'red';
            document.getElementById('pwChk2').innerHTML = '<b style="color: red;">[비밀번호를 똑같이 입력해주세요.]</b>';
            checkResultList[3] = false;
        } else {
            passwordCheckInput.style.borderColor = 'skyblue';
            document.getElementById('pwChk2').innerHTML = '<b style="color: skyblue;">[비밀번호가 일치합니다.]</b>';
            checkResultList[3] = true;
        }
    };

    // 생일 입력값 검증
    const birthdayInput = document.getElementById('user-birthday');

    birthdayInput.onblur = () => {
        const birthdayValue = birthdayInput.value;

        if (birthdayValue.trim() === '') {
            birthdayInput.style.borderColor = 'red';
            document.getElementById('birthdayChk').innerHTML = '<b style="color: red;">[생년월일은 필수값입니다!]</b>';
            checkResultList[4] = false;
        } else {
            birthdayInput.style.borderColor = 'skyblue';
            document.getElementById('birthdayChk').innerHTML = '<b style="color: skyblue;">[확인됐습니다.]</b>';
            checkResultList[4] = true;
        }
    };

    // 소환사 아이디 입력값 검증
    const lolNicknameInput = document.getElementById('lol-nickname');
    const lolNicknamePattern = /^[a-zA-Z0-9가-힣\s]{3,16}$/; // 롤 소환사 아이디 3~16글자(힌글,영문,숫자, 가운데 공백 포함)

    lolNicknameInput.onblur = () => {
        const lolNicknameValue = lolNicknameInput.value.trim();

        if (lolNicknameValue === '') {
            lolNicknameInput.style.borderColor = 'red';
            document.getElementById('lolNicknameChk').innerHTML = '<b style="color: red;">[소환사 닉네임은 필수값입니다!]</b>';
            checkResultList[5] = false;
        } else if (!lolNicknamePattern.test(lolNicknameValue)) {
            lolNicknameInput.style.borderColor = 'red';
            document.getElementById('lolNicknameChk').innerHTML = '<b style="color: red;">[인게임의 소환사 닉네임을 입력해주세요]</b>';
            checkResultList[5] = false;
        } else {
            lolNicknameInput.style.borderColor = 'skyblue';
            document.getElementById('lolNicknameChk').innerHTML = '<b style="color: skyblue;">[확인됐습니다.]</b>';
            checkResultList[5] = true;
        }
    };

    document.getElementById('signup-btn').onclick = e => {
        const form = document.getElementById('signUpForm');
        const formResult = document.getElementById('signupResult');

        if (checkResultList.includes(false)) {
            e.preventDefault();
            alert('입력란을 다시 확인하세요!');
        } else {
            formResult.action = '/user/sign-up';
        }
    };

    // (선택값)SNS 계정(가입 이메일,휴대폰이 아닌 사용자 아이디) 입력 검증식
    // 인스타 사용자 아이디 검증식
    const instagramInput = document.getElementById('instagram');
    const instagramUsernamePattern = /^[a-z0-9](?:[a-z0-9._]*[a-z0-9]){5,20}$/  // 인스타그램 사용자 아이디 패턴

    instagramInput.onblur = () => {
        const username = instagramInput.value.trim();

        if (username === '') {
            instagramInput.style.borderColor = 'red';
            document.getElementById('instagramChk').innerHTML = '<b style="color: red;">[insta id는 선택값입니다.!]</b>';
        } else if (!instagramUsernamePattern.test(username)) {
            instagramInput.style.borderColor = 'red';
            document.getElementById('instagramChk').innerHTML = '<b style="color: red;">[사용자 아이디는 영문 소문자와 숫자로만 구성되며, 최소 5자부터 최대 20자까지 허용됩니다. 밑줄 (_)과 마침표 (.)는 사용할 수 있지만, 연속으로 사용하거나 시작/끝에 위치할 수 없습니다.]</b>';
        } else {
            instagramInput.style.borderColor = 'skyblue';
            document.getElementById('instagramChk').innerHTML = '<b style="color: skyblue;">[확인됐습니다.]</b>';
        }
    };

    // 페이스북 사용자 아이디 검증식
    const facebookInput = document.getElementById('facebook');
    const facebookUsernamePattern = /^[a-zA-Z0-9._]{5,20}$/; // 페이스북 사용자 이름 패턴

    facebookInput.onblur = () => {
        const username = facebookInput.value.trim();

        if (username === '') {
            facebookInput.style.borderColor = 'red';
            document.getElementById('facebookChk').innerHTML = '<b style="color: red;">[페이스북 사용자 이름은 필수값입니다!]</b>';
        } else if (!facebookUsernamePattern.test(username)) {
            facebookInput.style.borderColor = 'red';
            document.getElementById('facebookChk').innerHTML = '<b style="color: red;">[영문 대소문자, 숫자, 점(.), 밑줄(_)로만 구성된 5자 이상, 20자 이하의 사용자 이름을 입력해주세요.]</b>';
        } else {
            facebookInput.style.borderColor = 'skyblue';
            document.getElementById('facebookChk').innerHTML = '<b style="color: skyblue;">[확인됐습니다.]</b>';
        }
    };

    // 트위터 사용자 아이디 검증식
    const twitterInput = document.getElementById('twitter');
    const userIdPattern = /^[a-zA-Z0-9_]{4,15}$/; // 사용자 아이디 패턴

    twitterInput.onblur = () => {
        const userId = twitterInput.value.trim();

        if (userId === '') {
            twitterInput.style.borderColor = 'red';
            document.getElementById('twitterChk').innerHTML = '<b style="color: red;">[twitter id는 선택값입니다.!]</b>';
        } else if (!userIdPattern.test(userId)) {
            twitterInput.style.borderColor = 'red';
            document.getElementById('twitterChk').innerHTML = '<b style="color: red;">[사용자 아이디는 영문 기준 최소 4자부터 최대 15자까지 허용됩니다.]</b>';
        } else {
            twitterInput.style.borderColor = 'skyblue';
            document.getElementById('twitterChk').innerHTML = '<b style="color: skyblue;">[확인됐습니다.]</b>';
        }
    };

    // 프로필 사진 관련 스크립트
    const fileInputs = document.querySelectorAll('#sign-up-wrapper input[type="file"]');
    const imgWrappers = document.querySelectorAll('#sign-up-wrapper .img-wrapper');
    const profileSection = document.querySelector('#profile-img-section');

    fileInputs.forEach((fileInput, index) => {
        fileInput.addEventListener('change', () => {
            const file = fileInput.files[0];
            const imgWrapper = imgWrappers[index];

            if (file) {
                const reader = new FileReader();

                reader.onload = () => {
                    imgWrapper.style.backgroundImage = `url('${reader.result}')`;
                };

                reader.readAsDataURL(file);
            }
        });
    });

    // 프로필 추가 영역 클릭 이벤트
    profileSection.addEventListener('click', e => {
        const target = e.target;
        if (target.classList.contains('img-wrapper') || target.classList.contains('btn')) {
            const fileInput = target.querySelector('input[type="file"]');
            fileInput.click();
        }
    });


})();

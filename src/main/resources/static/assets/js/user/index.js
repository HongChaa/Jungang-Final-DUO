import { getChampionImg } from "../common/get-champion-img.js";
import {makeChattingRoom} from "../chatting/chatting-modal.js";

// 현재까지 렌더링된 페이지
let page = 1;
// 마지막 페이지 확인 변수
let end = false;
// 프로필카드 불러오는 URL
const profileCardListURL = "/api/v1/users";
const $profileCardWrapper = document.getElementById('profile-cards-wrapper'); 

let onlyFollow = 'all';
let keyword = '';
const size = 20;
let position = 'all';
const $positionOption = document.querySelectorAll(".select-position"); // 포지션 선택 라디오 버튼
let gender = 'all';
const $genderOption = document.querySelectorAll(".select-gender");  // 성별 선택 라디오 버튼
let tier = 'all';
const $tierOption = document.querySelectorAll(".select-tier");  // 티어 선택 라디오 버튼
const sort = document.getElementById("order-list").value;

// 프로필 카드 내부 자식 태그들 전부 지우는 함수
function removeTag() {

    while($profileCardWrapper.firstChild) {
        $profileCardWrapper.removeChild($profileCardWrapper.firstChild);
    }
}

// 포지션 선택시 작동 함수
function selectPosition() {

    document.getElementById('searchBy-position').onclick = e => {       
        page = 1;
        end = false;
        removeTag(); 
        if (e.target.classList.contains('select-position')) {
            // 체크된 버튼 있는지 확인
            for (let i = 0; i < $positionOption.length; i++) {
                if ($positionOption[i].checked) {
                    position = $positionOption[i].value;
                    getProfileCardList();
                }
            }
        }
    }
}

// 성별 클릭시 작동 함수
function selectGender() {

    document.getElementById('searchBy-gender').onclick = e => {
        page = 1;
        end = false;
        removeTag();
        e.stopPropagation();
        if (e.target.classList.contains('select-gender')) {
            // 체크된 성별 있는지 확인
            for (let i = 0; i < $genderOption.length; i++) {
                if ($genderOption[i].checked) {
                    gender = $genderOption[i].value;
                    getProfileCardList();    
                }
            }
        }
    }
}

// 티어 클릭시 작동 함수
function selectTier() {

    document.getElementById('searchByTier').onclick = e => {
        page = 1;
        end = false;
        removeTag();
        e.stopPropagation();
        if (e.target.classList.contains('select-tier')) {
            // 체크된 티어 있는지 확인
            for (let i = 0; i < $tierOption.length; i++) {
                if ($tierOption[i].checked) {
                    tier = $tierOption[i].value;
                    getProfileCardList();
                }
            }
        }
    }
}

// 검색 키워드 입력시 작동 함수
function searchName() {
  let searchTimer = null;
  document.getElementById('searchBy-nickname').onkeyup = e => {
    clearTimeout(searchTimer); // 이전에 예약된 호출을 취소
    searchTimer = setTimeout(() => {
      page = 1;
      end = false;
      removeTag();
      keyword = document.getElementById('searchBy-nickname').value;
      getProfileCardList();
    }, 500); // 500ms 딜레이 후에 함수를 호출
  };
}

// 팔로잉 버튼 클릭시 작동 함수
function filterFollowing() {
    document.getElementById('view-followings').onclick = e => {
        if (document.getElementById('login-check').dataset.login === '') {
            alert("로그인이 필요한 기능입니다.");
            return;
        } 
        page = 1;
        end = false;
        removeTag();
        onlyFollow = 'followers';
        getProfileCardList();
    }

}

// 정렬 조건 변경시 작동 함수
function selectSort() {

    document.getElementById('order-list').onchange = e => {
        
        getProfileCardList();
    }
}

// tier 변환 함수 - 티어 이미지를 넣기 위한 함수
function transTier(tier) {
    switch (tier) {
        case 'CHA':
            return "Challenger";
        case 'IRO':
            return "Iron";
        case 'BRO':
            return "Bronze";
        case 'SIL':
            return "Silver";
        case 'GOL':
            return "Gold";
        case 'PLA':
            return "Platinum";
        case 'DIA':
            return "Diamond";
        case 'MAS':
            return "Master";
        case 'GRA':
            return "GrandMaster";
        default:
            return null;
    }
}

// 프로필이미지가 null인지 확인 후 null일 경우 기본이미지 삽입
function checkProfile(profileImage) {
    return profileImage === "basic" ? "/assets/img/main/basic-profile.png" : profileImage;
}

// 팔로잉 상태에 따라 하트 이미지 주기
function checkFollowing(followed) {
    return followed === false ? "not-following" : "following";
}

// sns null 값 확인 함수
function checkSNS(userInstagram, userFacebook, userTwitter) {
    let snsTag = '';

    if(userInstagram !== null) {
        snsTag += '<a href="https://instagram.com/'+ userInstagram +'" class="sns-type instagram"><img class="sns-image" src="/assets/img/main/instagram.png" alt="instagram"></a>'
    } else {
        snsTag += '<img class="sns-image sns-image-with-null" src="/assets/img/main/instagram.png" alt="instagram">'
    }
    if(userFacebook !== null) {
        snsTag += '<a href="https://facebook.com/'+ userFacebook +'" class="sns-type facebook"><img class="sns-image" src="/assets/img/main/facebook.png" alt="facebook"></a>'
    } else {
        snsTag += '<img class="sns-image sns-image-with-null" src="/assets/img/main/facebook.png" alt="facebook">'
    }
    if(userTwitter !== null) {
        snsTag += '<a href="https://twitter.com/'+ userTwitter +'" class="sns-type twitter"><img class="sns-image" src="/assets/img/main/twitter.png" alt="twitter"></a>'
    } else {
        snsTag += '<img class="sns-image sns-image-with-null" src="/assets/img/main/twitter.png" alt="twitter">'
    }

    return snsTag;
}

// 프로필 카드 비동기 요청 렌더링 함수
function getProfileCardList() {
    let profileCardTag = '';
    let mostOne = '';
    let mostTwo = '';
    let mostThree = '';

    if(keyword === '') keyword = '-';

    fetch(profileCardListURL +'/' + page + '/' + keyword + '/' + size + '/' + position + '/' + gender + '/' + tier + '/' + onlyFollow + '/' + sort)
    .then(res => res.json())
    .then(resResult => {

        if(Object.keys(resResult).length === 0) {
            page--; 
            end = true; 
            return;}
        
        for (let rep of resResult) {
            const {avgRate, followed, mostChampList, profileImage, tier, userAccount, userComment, userFacebook, userGender, userInstagram, userMatchingPoint, userNickname, userPosition, userTwitter} = rep;
          
            let mostChampTag = '';
            for (let i = 0; i < mostChampList.length; i++) {
                if (mostChampList[i].mostNo === 1 && mostChampList[i].champName !== '') mostChampTag += '<li class="most-pic first-champ"><img src="'+ getChampionImg(mostChampList[i].champName) +'" alt="first-champ"></li>'
                else if (mostChampList[i].mostNo === 2 && mostChampList[i].champName !== '') mostChampTag += '<li class="most-pic second-champ"><img src="'+ getChampionImg(mostChampList[i].champName) +'" alt="second-champ"></li>'
                else if (mostChampList[i].mostNo === 3 && mostChampList[i].champName !== '') mostChampTag += '<li class="most-pic third-champ"><img src="'+ getChampionImg(mostChampList[i].champName) +'" alt="third-champ"></li>'
            }        

            const tierImgSrc = transTier(tier) !== null ? '"/assets/img/main/TFT_Regalia_'+ transTier(tier) +'.png"' : '/assets/img/main/unranked-removebg-preview.png';
            
            profileCardTag += `<div id = "`+ userAccount +`"data-userAccount="`+ userAccount +`" class="duo-profile" onclick="window.location.href='/user/user-history?userAccount=${userAccount}'">`
                                   + '<img class="duo-tier" src=' + tierImgSrc + ' alt="tier">'
                                    
                                   + '<div class="profile-left-side">'
                                      + '<div class="profile-frame">'
                                           + '<img class="profile-image" src="'+ checkProfile(profileImage) +'" alt="프로필 이미지">'
                                       + '</div>'
                                      + '<div class="profile-sns">'
                                           + checkSNS(userInstagram, userFacebook, userTwitter)
                                           + '<div class="sns-type chatting-icon"><img class="sns-image" src="/assets/img/main/chatting-icon.png" alt="chatting"></div>'
                                       + '</div>'
                                   + '</div>'
                                    
                                   + '<div class="profile-right-side">'
                                      + '<div class="position-nickname">'
                                           + '<img class="preferred-position" src="/assets/img/main/'+ userPosition +'.png" alt="포지션">'
                                           + '<p class="user-nickname">'+ userNickname +'</p>'
                                           + '<img class="follow-status" src="/assets/img/main/'+ checkFollowing(followed) +'.png" alt="following" data-following="'+ followed +'">'
                                       + '</div>'
                                       + '<div class="rate-matching-point">'
                                           + '<div class="rate-matching-point rate-point-box"><img class="rate-matching-point-image" src="/assets/img/main/star.png" alt="rate">'
                                               + '<p class="avg-rate">'+ avgRate +'</p></div>'
                                           + '<div class="rate-matching-point matching-point-box"><img class="rate-matching-point-image" src="/assets/img/main/coin.png" alt="coin">'
                                               + '<p class="matching-point">'+ userMatchingPoint +'</p></div>'
                                       + '</div>'
                                       + '<div class="profile-comment"><p>'+ subStrComment(userComment) +'</p></div>'
                                       + '<div class="profile-most-champ">'
                                           + '<ul class="champ-list">'
                                               + mostChampTag
                                           + '</ul>'
                                       + '</div>'
                                   + '</div>'
                               + '</div>'

              
                            }
            $profileCardWrapper.innerHTML += profileCardTag;                       

        //채팅 생성하기
        makeChattingRoom();
        // 팔로우 기능
        follow();
            
    });
 }

 // userComment 글자수 오버시 ...처리
 function subStrComment(userComment) {
    if (userComment.length >= 25) {
        return userComment.substring(0, 25) + "...";
    } else {
        return userComment;
    }
 }

 // 전체보기 클릭시 동작함수
 function viewAll() {
    document.getElementById('view-all').onclick = e => {
        removeTag();
        page = 1;
        end = false;
        keyword = '';
        onlyFollow = 'all';
        // 체크된 티어 있는지 확인
        for (let i = 0; i < $tierOption.length; i++) {
            if ($tierOption[i].checked) {
                $tierOption[i].checked = false;
                tier = 'all';
            }
        }


        // 체크된 포지션 있는지 확인
        for (let i = 0; i < $positionOption.length; i++) {
            if ($positionOption[i].checked) {
                $positionOption[i].checked = false;
                position = 'all';
            }
        }


        // 체크된 성별 있는지 확인
        for (let i = 0; i < $genderOption.length; i++) {
            if ($genderOption[i].checked) {
                $genderOption[i].checked = false;
                gender = 'all';
            }
        }

        // 검색란 비워주기
        document.getElementById('searchBy-nickname').value = '';

        getProfileCardList(); 

    }
 }

// 팔로우 / 언팔로우 기능
function follow() {
    const $followImgs = document.querySelectorAll('.follow-status');
    
    $followImgs.forEach($followImg => {
        
        $followImg.onclick = e => {
            e.stopPropagation(); // 이벤트 전파 중지
            if (document.getElementById('login-check').dataset.login === '') {
                alert("로그인이 필요한 기능입니다.");
                return;
            }
            if (document.getElementById('login-check').dataset.login === e.target.closest('.duo-profile').dataset.useraccount) {
                alert("본인의 프로필카드입니다.");
                return;
            }

            const $followingImg = e.target.closest('.follow-status');
            
            console.log(e.target.closest('.duo-profile').dataset.useraccount);
            
            fetch(profileCardListURL + '/' + e.target.closest('.duo-profile').dataset.useraccount, {
                method: 'PATCH'
            }).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        if (data) {
                            $followingImg.src = '/assets/img/main/following.png';
                        } else {
                            $followingImg.src = '/assets/img/main/not-following.png';
                        }
                    })                        
                }
            });
        }
    });
}

 
 let loading = false; // 초기에는 로딩 상태가 아님을 나타내는 변수

 window.addEventListener('scroll', () => {
    const { scrollTop, clientHeight, scrollHeight } = document.documentElement;
        if ((scrollTop + clientHeight >= scrollHeight) && !loading) {
            loading = true; // 로딩 상태로 변경하여 중복 요청을 방지

            if(end !== true) {
                page ++;

                // 스크롤이 페이지 하단에 도달하면 새로운 데이터를 로드
                // console.log("스크롤하단도착");

                // 데이터를 로드하는 비동기 요청
                getProfileCardList();
                loading = false;
                
                console.log("page"+page);
                console.log("loading"+loading);
                console.log("end"+end);
            }
        }
  });

 


//========= 메인 실행부 =========//
(function () {
    
    // 포지션 선택시 동작
    selectPosition();
    // 성별 선택시 동작
    selectGender();
    // 티어 선택시 동작
    selectTier();
    // 검색창 입력시 동작
    searchName();   
    // 전체보기 클릭시
    viewAll();

    filterFollowing();
    
    // 프로필 카드 불러오기 함수(비동기)
   getProfileCardList();

    // 로그인 실패시 메세지
    renderFailMessage();

})();

function renderFailMessage() {
    const signInFailMsg = new URL(window.location.href).searchParams.get("msg");

    let msg = null;

    switch (signInFailMsg) {
        case 'NOT_ADMIN':
            msg = '관리자만 접근 가능합니다.';
            break;
        case 'NEED_LOGIN' :
            msg = '로그인이 필요합니다.';
            break;
        case 'NO_ACC':
            msg = '존재하지 않는 계정입니다.';
            break;
        case 'NO_PW':
            msg = '비밀번호가 틀렸습니다.';
            break;
        default:
    }

    if (msg !== null)
        alert(msg);
}
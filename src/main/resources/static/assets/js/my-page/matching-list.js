import {scrollPaging} from "../common/scroll-paging.js";
import {renderRateModal} from "../review/write-rate.js";
import {addModalBtnEvent, addModalCloseEvent} from "../common/modal-handler.js";
import {fillRateStars} from "../review/review-rate.js";

(() => {

    // 로그인한 사용자의 계정
    const userAccount = document.getElementById('loginUserInfo').dataset.userAccount;

    // 받은 리뷰 정보 페이지
    const increaseGottenReviewPage = (() => {
        let gottenPageNo = 0;
        return () => ++gottenPageNo;
    })();

    const increaseWrittenReviewPage = (() => {
        let writtenPageNo = 0;
        return () => ++writtenPageNo;
    })();

    // 페이지가 로드될 때 1 페이지 씩 렌더링
    renderGottenReviewOnMyPage({
        userAccount: userAccount,
        pageNo: increaseGottenReviewPage}).then();
    renderWrittenReviewOnMyPage({
        userAccount: userAccount,
        pageNo: increaseWrittenReviewPage}).then();

    // 스크롤 이벤트 등록
    scrollPaging(document.getElementById('gotten-matching-wrapper'), renderGottenReviewOnMyPage,
        {
            userAccount: userAccount,
            pageNo: increaseGottenReviewPage
        },
        500);
    scrollPaging(document.getElementById('applied-matching-wrapper'), renderWrittenReviewOnMyPage,
        {
            userAccount: userAccount,
            pageNo: increaseWrittenReviewPage
        },
        500);

})();

async function renderGottenReviewOnMyPage({userAccount, pageNo}) {
    const res = await fetch(`/api/v1/matchings/reviews/gotten/${userAccount}/${pageNo()}`);

    const $wrapper = document.getElementById('gotten-matching-wrapper');

    if (res.status === 200) {
        const matchingList = (await res.json()).list;
        for (const matching of matchingList) {

            await renderMatchingList($wrapper, matching);

            if (matching.matchingReviewContent !== null) {

                const $review = document.createElement('div');
                $review.textContent = matching.matchingReviewContent;
                $wrapper.appendChild($review);

            } else {

                // 빈 칸을 위해 빈 태그 추가
                $wrapper.appendChild(document.createElement('div'));

            }

        }
    }
}

async function renderWrittenReviewOnMyPage({userAccount, pageNo}) {

    const res = await fetch(`/api/v1/matchings/reviews/written/${userAccount}/${pageNo()}`);

    const $wrapper = document.getElementById('applied-matching-wrapper');

    if (res.status === 200) {
        const matchingList = (await res.json()).list;
        for (const matching of matchingList) {

            await renderMatchingList($wrapper, matching);

            if (matching.matchingReviewContent !== null) {

                const $review = document.createElement('div');
                $review.textContent = matching.matchingReviewContent;
                $wrapper.appendChild($review);

            } else {

                const $btn = document.createElement('button');
                $btn.textContent = '리뷰 쓰러 가기';
                $btn.dataset.matchingNo = matching.matchingNo;
                $btn.classList.add('modal-btn');
                $btn.classList.add('review-write-btn');
                $wrapper.appendChild($btn);

                await $wrapper.appendChild(await renderRateModal(
                    matching.matchingNo,
                    matching.opponentNickname,
                    matching.profileImage));

                fillRateStars();

                addModalBtnEvent();
                addModalCloseEvent();
            }

        }
    }
}

async function renderMatchingList($wrapper, {opponentNickname, matchingDate}) {
    const $opponent = document.createElement('div');
    $opponent.textContent = opponentNickname;
    $wrapper.appendChild($opponent);

    const $date = document.createElement('div');
    $date.textContent = matchingDate;
    $wrapper.appendChild($date);

}
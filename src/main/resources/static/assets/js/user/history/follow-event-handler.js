export function followEventHandler(userAccount, $followBtn) {

    $followBtn.addEventListener("click", followFetch);

}

async function followFetch(e) {
    const res = await fetch(`/api/v1/users/${e.target.closest('#history-wrapper').dataset.userAccount}`,
        {
            method: 'PATCH'
        });

    if (res.ok) {
        const isFollowed = await res.json();

        const $followingImg = e.target.closest('#user-follow-heart').querySelector('img');

        if (isFollowed) {
            $followingImg.src = '/assets/img/main/following.png';
        } else {
            $followingImg.src = '/assets/img/main/not-following.png';
        }
    } else {
        alert(await res.text());
    }
}
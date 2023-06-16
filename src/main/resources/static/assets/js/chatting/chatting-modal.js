function closeRecentModal($toBack){
    $toBack.onclick = e => {
        const $dialog = e.target.closest('dialog');
        $dialog.close();
    }
}

export function toBack() {

    [...document.querySelectorAll('.toBack')].forEach(
        $toBack => closeRecentModal($toBack)
    );

}

export function setAutofocus(target){

    const $input = target.querySelector('#msg');
    $input.setAttribute('autofocus', 'true');

}